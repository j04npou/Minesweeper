import java.util.Random;
import java.util.Scanner;

public class Minesweeper {
    private Tile[][] tiles;
    private final String boxNumber= "\u20E3";
    private final String mine = "\uD83D\uDCA3";
    private final String flag = "\uD83D\uDEA9";
    private final String cover = "\uD83D\uDFE9";
    private final String fail = "\uD83C\uDFF4";
    private final String skull = "\uD83D\uDC80";
    private int totalMines;
    private int winnerCounter = 0;
    private long timeElapsed;
    private boolean firstMove = true;

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
                if (tiles[x][y].isVisible() == false && tiles[x][y].isFlag() == false) {
                    // Cel·la tapada i no es bandera
                    fieldString = fieldString.concat(cover + " ");
                    winnerCounter++;
                } else if (tiles[x][y].isFlag()) {
                    // Bandera
                    fieldString = fieldString.concat(flag + " ");
                    winnerCounter++;
               } else if (tiles[x][y].isVisible()) {
                    // Cel·la destapada
                    if (tiles[x][y].isMine()) {
                        fieldString = fieldString.concat(mine + " ");
                    } else if (tiles[x][y].getNearMines() == 0) {
                        fieldString = fieldString.concat("   ");
                    } else {
                        fieldString = fieldString.concat(tiles[x][y].getNearMines() + boxNumber + "  ");
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
            // Si ja hi ha una mina a les coordenades actuals o està a prop del primer moviment botam el procés y tornam a calcular
            if ( tiles[x][y].isMine() == false && !(x >= firstMoveX-1 && x <= firstMoveX+1 && y >= firstMoveY-1 && y <= firstMoveY+1) ) {
                tiles[x][y].setMine();
                counter++;
                // Col·loca els numeros rodetjant les mines
                for (int x1 = x-1; x1 <= x+1; x1++) {
                    for (int y1 = y-1; y1 <= y+1; y1++) {
                        if (x1 >= 0 && x1 < tiles.length && y1 >= 0 && y1 < tiles[0].length && !tiles[x1][y1].isMine()) {
                            // Incrementam les mines properes a aquesta cel·la
                            tiles[x1][y1].setNearMines();
                        }
                    }
                }
            }
        }
        timeElapsed = System.currentTimeMillis();
    }

    private static Integer tryParse(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public boolean enterMove() {
        // Si la mateixa quantitat de cel·les tapades que de mines hem guanyat
        if (winnerCounter == totalMines) {
            youWin();
            return false;
        }

        Scanner keys = new Scanner(System.in);

        System.out.print("\uD83D\uDC49Introduce coordinate (ex. 'B7') \uD83D\uDC49[.] Put/Remove flag (ex. '.C3') \uD83D\uDC49[0] Leave game");
        if (timeElapsed > 0) {
            System.out.println(" ⏱ " + (int) (System.currentTimeMillis() - timeElapsed) / 1000 + "s");
        } else {
            System.out.println();
        }
        String move = keys.next();
        move = move.toUpperCase();

        switch (move.charAt(0)) {
            case '.':
                // Banderes

                // Estreim les coordenades per col·locar o llevar bandera
                if (move.length() >= 3) {
                    int x = move.charAt(1)-'A'+1;
                    if (x >= 1 && x <= tiles.length) {
                        int y = tryParse(move.substring(2));
                        if (y >= 1 && y <= tiles[0].length) {
                            // Col·locam o llevam bandera
                            if (tiles[x-1][y-1].isVisible() == false) {
                                if (tiles[x - 1][y - 1].isFlag() == false) {
                                    tiles[x - 1][y - 1].setFlag(true);
                                } else {
                                    tiles[x - 1][y - 1].setFlag(false);
                                }
                            } else {
                                System.out.println("⛔ You cannot put a flag on " + move.substring(1) + " ⛔");
                            }
                        }
                    }
                }
                break;
            case '0':
                // Abandonam la partida
                return false;
            default:
                // Estreim les coordenades xy de l'input i aplicam el moviment
                if (move.length() >= 2) {
                    int x = move.charAt(0)-'A'+1;
                    if (x >= 1 && x <= tiles.length) {
                        int y = tryParse(move.substring(1));
                        if (y >= 1 && y <= tiles[0].length) {
                            // Només podem destapar cel·la si está "tapada" i no es bandera
                            if (tiles[x-1][y-1].isVisible() == false && tiles[x-1][y-1].isFlag() == false) {
                                // Cridam putMine si es el primer pic que destapam una cel·la
                                if (firstMove) {
                                    firstMove = false;
                                    putMines(x-1,y-1);
                                }
                                // Aplicam el moviment
                                if (uncover(x - 1, y - 1) == false) {
                                    // Hem destapat una bomba i hem perdut
                                    gameOver();
                                    return false;
                                }
                            }
                        }
                    }
                }
                break;
        }
        return true;
    }

    private boolean uncover(int x, int y) {
        // Comprovam que les coordenades estiguin dins el tauler
        if (x < 0 || x >= tiles.length || y < 0 || y >= tiles[0].length || tiles[x][y].isVisible())
            return true;

        // Destapam la cel·la
        tiles[x][y].setVisible(true);

        if (tiles[x][y].isMine() == false &&  tiles[x][y].getNearMines() == 0) {
            // Si la cel·la està buida, destapam les que l'envolten recursivament
            for (int x1 = x - 1; x1 <= x + 1; x1++) {
                for (int y1 = y - 1; y1 <= y + 1; y1++) {
                    uncover(x1, y1);
                }
            }
        } else if (tiles[x][y].isMine() == true) {
            // Si la cel·la conté una mina desencadenam el game over
            return false;
        }
        return true;
    }

    private void gameOver() {
        System.out.print("    ");
        for (int i = 0; i < tiles.length; i++) {
            System.out.print("[" + (char)('A'+i) + "]");
        }
        System.out.println();
        for (int y = 0; y < tiles[0].length; y++) {
            System.out.printf("[%02d] ", y+1);
            for (int x = 0; x < tiles.length; x++) {
                if (tiles[x][y].isFlag() && tiles[x][y].isMine()) {
                    // Bandera correcte
                    System.out.print(flag + " ");
                } else if (tiles[x][y].isFlag() && tiles[x][y].isMine() == false) {
                    // Bandera incorrecte
                    System.out.print(fail + " ");
                } else if (tiles[x][y].isVisible() == false && tiles[x][y].isMine()) {
                    // Cel·la tapada i mina
                    System.out.print(mine + " ");
                } else if (tiles[x][y].isVisible() == false) {
                    // Cel·la sense destapar
                    System.out.print(cover + " ");
                } else if (tiles[x][y].isVisible()) {
                    // Cel·la destapada
                    if (tiles[x][y].isMine()) {
                        System.out.print(skull + " ");
                    } else if (tiles[x][y].getNearMines() == 0) {
                        System.out.print("   ");
                    } else {
                        System.out.print(tiles[x][y].getNearMines() + boxNumber + "  ");
                    }
                }

            }
            System.out.println("|");
        }
        System.out.println("-".repeat(tiles.length*3+6));
        System.out.println("" +
                    " _____                  _____             \n" +
                    "|   __|___ _____ ___   |     |_ _ ___ ___ \n" +
                    "|  |  | .'|     | -_|  |  |  | | | -_|  _|\n" +
                    "|_____|__,|_|_|_|___|  |_____|\\_/|___|_|  ");
    }

    private void youWin() {
        System.out.println("" +
                " __ __            _ _ _ _     \n" +
                "|  |  |___ _ _   | | | |_|___ \n" +
                "|_   _| . | | |  | | | | |   |\n" +
                "  |_| |___|___|  |_____|_|_|_|");
        System.out.println("⏱  Your time is: " + (int)(System.currentTimeMillis()-timeElapsed)/1000 + "s");
        System.out.println("\uD83E\uDD73 \uD83C\uDF8A \uD83E\uDD84 \uD83C\uDF08 \uD83C\uDF89 \uD83E\uDD29");
    }
}
