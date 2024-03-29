import java.util.Random;

public class Minesweeper {
    private Tile[][] tiles;
    private final String BOXNUMBER = "\u20E3";
    private final String MINE = "\uD83D\uDCA3";
    private final String FLAG = "\uD83D\uDEA9";
    private final String COVER = "\uD83D\uDFE9";
    private final String FAIL = "\uD83C\uDFF4";
    private final String SKULL = "\uD83D\uDC80";
    private int totalMines;
    private int winnerCounter;
    private long timeElapsed;
    private boolean firstMove;
    private boolean endGame;

    public Minesweeper(int x, int y, int totalMines) {
        this.winnerCounter = 0;
        this.firstMove = true;
        this.endGame = false;
        setField(x,y,totalMines);
    }

    public void setField(int x, int y, int mines) {
        tiles = new Tile[x][y];
        totalMines = mines;
        for (int x0 = 0; x0 < x; x0++) {
            for (int y0 = 0; y0 < y; y0++) {
                tiles[x0][y0] = new Tile(x0,y0);
            }
        }
    }

    public String toString() {
        String fieldString = "";

        winnerCounter = 0;

        // Imprimim les coordenades verticals
        fieldString =fieldString.concat("    ");
        for (int i = 0; i < tiles.length; i++) {
            fieldString = fieldString.concat("[" + (char)('A'+i) + "]");
        }
        fieldString = fieldString.concat("\n");

        for (int y = 0; y < tiles[0].length; y++) {
            // Imprimim les coordenades horitzontals
            fieldString = fieldString.concat(String.format("[%02d] ", y+1));
            for (int x = 0; x < tiles.length; x++) {
                if (endGame) {
                    if (tiles[x][y].isMine()) {
                        if (tiles[x][y].isVisible()) {
                            // si es mina i está destapada pintam una calavera
                            fieldString = fieldString.concat(SKULL + " ");
                        } else if (tiles[x][y].isFlag()) {
                            // si es mina i te una bandera pintam una bandera correcte
                            fieldString = fieldString.concat(FLAG + " ");
                        } else {
                            // si es mina i está tapada i no te bandera pintam una mina
                            fieldString = fieldString.concat(MINE + " ");
                        }
                    } else if (tiles[x][y].isFlag()) {
                        // si no es mina i es bandera pintam una bandera incorrecte
                        fieldString = fieldString.concat(FAIL + " ");
                    } else if (!tiles[x][y].isVisible()){
                        // Cel·la tapada i no es bandera
                        fieldString = fieldString.concat(COVER + " ");
                    }
                } else {
                    // si no es final de partida
                    if (tiles[x][y].isFlag()) {
                        // Cel·la tapada i es bandera
                        fieldString = fieldString.concat(FLAG + " ");
                        winnerCounter++;
                    } else if (!tiles[x][y].isVisible()){
                        // Cel·la tapada i no es bandera
                        fieldString = fieldString.concat(COVER + " ");
                        winnerCounter++;
                    }
                }
                if (tiles[x][y].isVisible() && !tiles[x][y].isMine()) {
                    // Cel·la destapada
                    if (tiles[x][y].getNearMines() == 0) {
                        fieldString = fieldString.concat("   ");
                    } else {
                        fieldString = fieldString.concat(tiles[x][y].getNearMines() + BOXNUMBER + "  ");
                    }
                }
            }
            // Imprimim les coordenades horitzontals
            fieldString = fieldString.concat(String.format("[%02d] ", y+1) + "\n");
        }
        // Imprimim les coordenades verticals
        fieldString = fieldString.concat("    ");
        for (int i = 0; i < tiles.length; i++) {
            fieldString = fieldString.concat("[" + (char)('A'+i) + "]");
        }
        fieldString = fieldString.concat("\n");

        return fieldString;
    }

    private void putMines(int firstMoveX, int firstMoveY) {
        int xMax = tiles.length;
        int yMax =  tiles[0].length;

        Random random = new Random();

        int counter = 0;
        while (counter < totalMines) {
            int x = random.nextInt(xMax);
            int y = random.nextInt(yMax);
            // Si ja hi ha una mina a les coordenades actuals, col·locam la mina a un altre lloc
            if ( !tiles[x][y].isMine() ) {
                //  Ens asseguram de no colocar mina aprop del primer moviment
                if (!(x >= firstMoveX - 1 && x <= firstMoveX + 1 && y >= firstMoveY - 1 && y <= firstMoveY + 1)) {
                    tiles[x][y].setMine();
                    counter++;
                    // Col·loca els numeros rodetjant les mines
                    for (int x1 = x - 1; x1 <= x + 1; x1++) {
                        for (int y1 = y - 1; y1 <= y + 1; y1++) {
                            if (x1 >= 0 && x1 < tiles.length && y1 >= 0 && y1 < tiles[0].length) {
                                // Incrementam les mines properes a aquesta cel·la
                                tiles[x1][y1].incNearMines();
                            }
                        }
                    }
                }
            }
        }
        timeElapsed = System.currentTimeMillis();
    }

    public boolean enterMove() {
        // Si la mateixa quantitat de cel·les tapades que de mines hem guanyat
        if (winnerCounter == totalMines) {
            Tile.youWin(timeElapsed);
            return false;
        }

        InputOutput.print("\uD83D\uDC49Introduce coordinate (ex. 'B7') \uD83D\uDC49[.] Put/Remove flag (ex. '.C3') \uD83D\uDC49[0] Leave game");
        if (timeElapsed > 0) {
            InputOutput.printLN(" ⏱ " + (int) (System.currentTimeMillis() - timeElapsed) / 1000 + "s");
        } else {
            InputOutput.printLN();
        }
        String move = InputOutput.input();

        ParseText parseText = new ParseText(move, tiles.length, tiles[0].length);

        if (parseText.isExit()) {
            return false;
        }

        if (parseText.isError()) {
            InputOutput.printLN(parseText.getErrorMessage());
        } else if (parseText.isFlag()) {
            // Col·locam o llevam bandera
            plantFlag(parseText);
        } else {
            // Destapam cel·la
            return exposeTile(parseText);
        }
        return true;
    }

    private void plantFlag(ParseText parseText) {
        // Col·locam o llevam bandera
        if (!tiles[parseText.getX() - 1][parseText.getY() - 1].isVisible()) {
            if (!tiles[parseText.getX() - 1][parseText.getY() - 1].isFlag()) {
                tiles[parseText.getX() - 1][parseText.getY() - 1].setFlag(true);
            } else {
                tiles[parseText.getX() - 1][parseText.getY() - 1].setFlag(false);
            }
        }
    }

    private boolean exposeTile(ParseText parseText) {
        // Només podem destapar cel·la si está "tapada" i no es bandera
        if (!tiles[parseText.getX() - 1][parseText.getY() - 1].isVisible() && !tiles[parseText.getX() - 1][parseText.getY() - 1].isFlag()) {
            // Cridam putMine si es el primer pic que destapam una cel·la
            if (firstMove) {
                firstMove = false;
                putMines(parseText.getX() - 1, parseText.getY() - 1);
            }
            // Aplicam el moviment
            if (!Tile.uncover(parseText.getX() - 1, parseText.getY() - 1, tiles)) {
                // Hem destapat una bomba i hem perdut
                endGame = true;
                InputOutput.printLN(toString());
                Tile.gameOver();
                return false;
            }
        }
        return true;
    }
}
