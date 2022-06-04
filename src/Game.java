public class Game {

    private static boolean menu() {

        InputOutput.printLN("[1] - Easy (10x8 10)");
        InputOutput.printLN("[2] - Medium (19x14 40)");
        InputOutput.printLN("[3] - Hard (24x20 99)");
        InputOutput.printLN("[4] - Custom");
        InputOutput.printLN("[0] - Exit Game");
        String menu = InputOutput.input();

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
                InputOutput.printLN("⛔ Select one of the indicated options, please. ⛔");
                break;
        }
        return true;
    }

    private static void menuCustomGame() {

        InputOutput.printLN("Introduce width:");
        String customWidth = InputOutput.input();
        int cWidth = Integer.parseInt(customWidth);

        if (cWidth < 3 || cWidth > 26) {
            InputOutput.printLN("⛔ The width must be between 3 and 26. ⛔");
            return;
        }

        InputOutput.printLN("Introduce height:");
        String customHeight = InputOutput.input();
        int cHeight = Integer.parseInt(customHeight);

        if (cHeight < 3 || cHeight > 99) {
            InputOutput.printLN("⛔ The height must be between 3 and 99. ⛔");
            return;
        }

        InputOutput.printLN("How many mines you want?");
        String customMines = InputOutput.input();
        int cMines = Integer.parseInt(customMines);

        if (cMines > 0 && cMines <= (cWidth-2) * (cHeight-2)) {
            newGame(cWidth, cHeight, cMines);
        } else {
            InputOutput.printLN("⛔ The mines for a board of " + cWidth + "x" + cHeight + " must be between 1 and " + ((cWidth-2) * (cHeight-2)) + ", try again. ⛔\n");
        }
    }

    private static void newGame(int x, int y, int mines) {
        Minesweeper Field = new Minesweeper(x, y, mines);

        boolean exit = true;
        do {
            InputOutput.printLN(Field.toString());
            exit = Field.enterMove();
        } while (exit);
    }

    public static void main(String[] args) {
        boolean exit = false;
        do {
            exit = menu();
            InputOutput.printLN();
        } while (exit);
    }
}
