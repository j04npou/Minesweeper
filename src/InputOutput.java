import java.util.Scanner;

public class InputOutput {
    public static void printLN(String inputText) {
        // Imprimeix amb bot de linea
        System.out.println(inputText);
    }
    public static void printLN() {
        System.out.println();
    }

    public static void print(String inputText) {
        // Imprimeix sense bot de linea
        System.out.print(inputText);
    }

    public static String input() {
        Scanner keys = new Scanner(System.in);
        return keys.next();
    }
}
