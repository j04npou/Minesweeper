import java.util.Random;

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
}
