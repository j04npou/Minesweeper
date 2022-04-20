import java.util.Scanner;

public class Game {

    private static boolean menu() {
        Scanner keys = new Scanner(System.in);

        System.out.println("[1] - Easy (10x8 10)");
        System.out.println("[2] - Medium (19x14 40)");
        System.out.println("[3] - Hard (24x20 99)");
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
            case "0":
                return false;
            default:
                System.out.println("⛔ Select one of the indicated options, please. ⛔");
                break;
        }
        return true;
    }

    private static void newGame(int x, int y, int mines) {
        Minesweeper Game = new Minesweeper();
        Game.setField(x,y,mines);
        Game.printField();
    }

    public static void main(String[] args) {
        while (menu()) {
            System.out.println(1);
        }
    }
}
