import java.util.Random;
import java.util.Scanner;

public class Minesweeper {
    private int[][] field = new int[8][10];
    private int[][] visibility = new int[field.length][field[0].length];
    private final String boxNumber= "\u20E3";
    private final String mine = "\uD83D\uDCA3";
    private final String flag = "\uD83D\uDEA9";
    private final String cover = "\uD83D\uDFE9";
    private final String fail = "\uD83C\uDFF4";
    private final String skull = "\uD83D\uDC80";
    private int totalMines = 10;
    private boolean firstMove = true;

    public void setField(int x, int y, int mines) {
        field = new int[x][y];
        visibility = new int[x][y];
        totalMines = mines;
    }

    public void printField() {
        System.out.print("    ");
        for (int i = 0; i < field.length; i++) {
            System.out.print("[" + (char)('A'+i) + "]");
        }
        System.out.println();
        for (int y = 0; y < field[0].length; y++) {
            System.out.printf("[%02d] ", y+1);
            for (int x = 0; x < field.length; x++) {
                switch (visibility[x][y]) {
                    case 2:
                        // Bandera
                        System.out.print(flag + " ");
                        break;
                    case 0:
                        // Cel·la tapada
                        System.out.print(cover + " ");
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
            System.out.println("|");
        }
        System.out.println("-".repeat(field.length*3+6));
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
            if (field[x][y] != -1) {
                field[x][y] = -1;
                counter++;
                // Col·loca els numeros
                for (int x1 = x-1; x1 <= x+1; x1++) {
                    for (int y1 = y-1; y1 <= y+1; y1++) {
                        if (x1 >= 0 && x1 < field.length && y1 >= 0 && y1 < field[0].length && field[x1][y1] != -1) {
                            field[x1][y1] ++;
                        }
                    }
                }
            }
        }
    }

    public boolean enterMove() {
        Scanner keys = new Scanner(System.in);

        System.out.println("\uD83D\uDC49Introduce coordinate (ex. 'B7') \uD83D\uDC49[.] Put/Remove flag (ex. '.C3') \uD83D\uDC49[0] Leave game");
        // (char)('A'+field.length)
        String move = keys.next();
        move = move.toUpperCase();

        switch (move.charAt(0)) {
            case '.':
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
                return false;
            default:
                if (move.length() >= 2) {
                    int x = move.charAt(0)-'A'+1;
                    if (x >= 1 && x <= field.length) {
                        int y = Integer.parseInt(move.substring(1));
                        if (y >= 1 && y <= field[0].length) {
                            // Cridam putMine si es el primer pic que destapam una cel·la
                            if (firstMove) {
                                while (field[x-1][y-1] != 0 || firstMove) {
                                    firstMove = false;
                                    putMines();
                                }
                            }
                            if (uncover(x-1,y-1) == false) {
                                // Hem destapat una bomba i hem perdut
                                gameOver(x-1,y-1);
                                return false;
                            }
                        }
                    }
                }
                break;
        }
        return true;
    }

    private boolean uncover(int x, int y) {
        if (x < 0 || x >= field.length || y < 0 || y >= field[0].length || visibility[x][y] == 1)
            return true;

        visibility[x][y] = 1;

        if (field[x][y] == 0) {
            for (int x1 = x-1; x1 <= x+1; x1++) {
                for (int y1 = y-1; y1 <= y+1; y1++) {
                    uncover(x1,y1);
                }
            }
        } else if (field[x][y] == -1) {
            return false;
        }
        return true;
    }

    public void gameOver(int xMine, int yMine) {
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
}
