import java.util.Random;
import java.util.Scanner;

public class Minesweeper {
    private int[][] field = new int[0][];
    private int[][] visibility = new int[0][];
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
        field = new int[x][y];
        visibility = new int[x][y];
        totalMines = mines;
    }

    public void printField() {
        winnerCounter = 0;

        // Imprimim les coordenades horitzontals
        System.out.print("    ");
        for (int i = 0; i < field.length; i++) {
            System.out.print("[" + (char)('A'+i) + "]");
        }
        System.out.println();

        for (int y = 0; y < field[0].length; y++) {
            // Imprimim les coordenades verticals
            System.out.printf("[%02d] ", y+1);
            for (int x = 0; x < field.length; x++) {
                switch (visibility[x][y]) {
                    case 2:
                        // Bandera
                        System.out.print(flag + " ");
                        winnerCounter++;
                        break;
                    case 0:
                        // Cel·la tapada
                        System.out.print(cover + " ");
                        winnerCounter++;
                        break;
                    case 1:
                        // Cel·la destapada
                        switch (field[x][y]) {
                            case -1:
                                System.out.print(mine + " ");
                                break;
                            case 0:
                                System.out.print("   ");
                                break;
                            default:
                                System.out.print(field[x][y] + boxNumber + "  ");
                                break;
                        }
                        break;
                }

            }
            // Imprimim les coordenades verticals
            System.out.printf("[%02d]%n", y+1);
        }
        // Imprimim les coordenades horitzontals
        System.out.print("    ");
        for (int i = 0; i < field.length; i++) {
            System.out.print("[" + (char)('A'+i) + "]");
        }
        System.out.println();
    }

    private void putMines() {
        int xMax = field.length;
        int yMax =  field[0].length;
        setField(xMax, yMax, totalMines);

        Random random = new Random();

        int counter = 0;
        while (counter < totalMines) {
            int x = random.nextInt(xMax);
            int y = random.nextInt(yMax);
            // Si ja hi ha una mina a les coordenades actuals botam el procés y tornam a calcular
            if (field[x][y] != -1) {
                field[x][y] = -1;
                counter++;
                // Col·loca els numeros rodetjant les mines
                for (int x1 = x-1; x1 <= x+1; x1++) {
                    for (int y1 = y-1; y1 <= y+1; y1++) {
                        if (x1 >= 0 && x1 < field.length && y1 >= 0 && y1 < field[0].length && field[x1][y1] != -1) {
                            field[x1][y1] ++;
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
            youWin();
            return false;
        }

        Scanner keys = new Scanner(System.in);

        System.out.print("\uD83D\uDC49Introduce coordinate (ex. 'B7') \uD83D\uDC49[.] Put/Remove flag (ex. '.C3') \uD83D\uDC49[0] Leave game");
        if (timeElapsed > 0) {
            System.out.println(" ⏱" + (int) (System.currentTimeMillis() - timeElapsed) / 1000 + "s");
        } else {
            System.out.println();
        }
        String move = keys.next();
        move = move.toUpperCase();

        switch (move.charAt(0)) {
            case '.':
                // Estreim les coordenades per col·locar o llevar bandera
                if (move.length() >= 3) {
                    int x = move.charAt(1)-'A'+1;
                    if (x >= 1 && x <= field.length) {
                        int y = Integer.parseInt(move.substring(2));
                        if (y >= 1 && y <= field[0].length) {
                            // Col·locam o llevam bandera
                            if (visibility[x-1][y-1] == 0) {
                                visibility[x - 1][y - 1] = 2;
                            } else if (visibility[x-1][y-1] == 2) {
                                visibility[x - 1][y - 1] = 0;
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
                    if (x >= 1 && x <= field.length) {
                        int y = Integer.parseInt(move.substring(1));
                        if (y >= 1 && y <= field[0].length) {
                            // Només podem destapar cel·la si está "tapada" (0) i no es bandera (2)
                            if (visibility[x-1][y-1] == 0) {
                                // Cridam putMine si es el primer pic que destapam una cel·la
                                if (firstMove) {
                                    while (field[x - 1][y - 1] != 0 || firstMove) {
                                        firstMove = false;
                                        putMines();
                                    }
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
        if (x < 0 || x >= field.length || y < 0 || y >= field[0].length || visibility[x][y] == 1)
            return true;

        // Destapam la cel·la
        visibility[x][y] = 1;

        switch (field[x][y]) {
            case 0:
                // Si la cel·la està buida (0) destapam les que l'envolten recursivament
                for (int x1 = x - 1; x1 <= x + 1; x1++) {
                    for (int y1 = y - 1; y1 <= y + 1; y1++) {
                        uncover(x1, y1);
                    }
                }
                break;
            case -1:
                // Si la cel·la conté una mina desencadenam el game over
                return false;
        }
        return true;
    }

    public void gameOver() {
        System.out.print("    ");
        for (int i = 0; i < field.length; i++) {
            System.out.print("[" + (char)('A'+i) + "]");
        }
        System.out.println();
        for (int y = 0; y < field[0].length; y++) {
            System.out.printf("[%02d] ", y+1);
            for (int x = 0; x < field.length; x++) {
                if (visibility[x][y] == 2 && field[x][y] == -1) {
                    // Bandera correcte
                    System.out.print(flag + " ");
                } else if (visibility[x][y] == 2 && field[x][y] != -1) {
                    // Bandera incorrecte
                    System.out.print(fail + " ");
                } else if (visibility[x][y] == 0 && field[x][y] == -1) {
                    // Cel·la tapada i mina
                    System.out.print(mine + " ");
                } else if (visibility[x][y] == 0) {
                    // Cel·la sense destapar
                    System.out.print(cover + " ");
                } else if (visibility[x][y] == 1) {
                    // Cel·la destapada
                    switch (field[x][y]) {
                        case -1:
                            System.out.print(skull + " ");
                            break;
                        case 0:
                            System.out.print("   ");
                            break;
                        default:
                            System.out.print(field[x][y] + boxNumber + "  ");
                            break;
                    }
                }

            }
            System.out.println("|");
        }
        System.out.println("-".repeat(field.length*3+6));
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
        System.out.println("⏱ Your time is: " + (int)(System.currentTimeMillis()-timeElapsed)/1000 + "s");
        System.out.println("\uD83E\uDD73 \uD83C\uDF8A \uD83E\uDD84 \uD83C\uDF08 \uD83C\uDF89 \uD83E\uDD29");
    }
}
