import java.util.Scanner;

public class Game {

    private static boolean menu() {
        Scanner keys = new Scanner(System.in);

        System.out.println("[1] - Easy (10x8 10)");
        System.out.println("[2] - Medium (19x14 40)");
        System.out.println("[3] - Hard (24x20 99)");
        System.out.println("[4] - Custom");
        System.out.println("[0] - Exit Game");
        String menu = keys.next();

        switch (menu) {
            case "1":
                newGame(10, 8, 10);
                break;
            case "2":
                newGame(19, 14, 40);
                break;
            case "3":
                newGame(24, 20, 99);
                break;
            case "4":
                menuCustomGame();
                break;
            case "0":
                return false;
            default:
                System.out.println("⛔ Select one of the indicated options, please. ⛔");
                break;
        }
        return true;
    }

    private static void menuCustomGame() {
        Scanner keys = new Scanner(System.in);

        System.out.println("Introduce width:");
        String customWidth = keys.next();
        int cWidth = Integer.parseInt(customWidth);

        if (cWidth < 3 || cWidth > 26) {
            System.out.println("⛔ The width must be between 3 and 26. ⛔");
            return;
        }

        System.out.println("Introduce height:");
        String customHeight = keys.next();
        int cHeight = Integer.parseInt(customHeight);

        if (cHeight < 3 || cHeight > 99) {
            System.out.println("⛔ The height must be between 3 and 99. ⛔");
            return;
        }

        System.out.println("How many mines you want?");
        String customMines = keys.next();
        int cMines = Integer.parseInt(customMines);

        if (cMines > 0 && cMines <= (cWidth-2) * (cHeight-2)) {
            newGame(cWidth, cHeight, cMines);
        } else {
            System.out.println("⛔ The mines for a board of " + cWidth + "x" + cHeight + " must be between 1 and " + ((cWidth-2) * (cHeight-2)) + ", try again. ⛔\n");
        }
    }

    private static void newGame(int x, int y, int mines) {
        Minesweeper Field = new Minesweeper();
        boolean game = true;
        Field.setField(x, y, mines);

        while (game) {
            Field.printField();
            game = Field.enterMove();
        }
    }

    public static void main(String[] args) {
        while (menu()) {
            System.out.println();
        }
    }
}
