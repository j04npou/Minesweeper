import java.util.Random;
import java.util.Scanner;

public class Minesweeper {
    private int[][] field = new int[8][10];
    private int[][] visibility = new int[field.length][field[0].length];
    private final String boxNumber= "\u20E3";
    private final String mine = "\uD83D\uDCA3";
    private final String flag = "\uD83D\uDEA9";
    private final String cover = "\uD83D\uDFE9";
    private int totalMines = 10;

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
                        // Celda tapada
                        System.out.print(cover + " ");
                        break;
                    case 1:
                        // Celda destapada
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
            System.out.println();
        }
    }

    public void putMines() {
        int xMax = field.length;
        int yMax =  field[0].length;

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
                                System.out.println("⛔ You cannot put a flag here ⛔");
                            }
                        }
                        System.out.println(move);
                    }
                }

                break;
            case '0':
                return false;
            default:

        }
        return true;
    }
}
