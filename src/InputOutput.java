import java.util.Scanner;

public class InputOutput {
    private boolean isFlag;
    private int x;
    private int y;
    private String errorMessage;
    private boolean isError;
    private boolean isExit;

    public InputOutput(String text, int maxX, int maxY) {
        isFlag = false;
        isExit = false;
        isError = false;
        errorMessage = "";
        x = 0;
        y = 0;
        parseText(text,maxX,maxY);
    }

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

    public static Integer tryParse(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private void parseText(String text, int maxX, int maxY){
        switch (text.charAt(0)) {
            case '.':
                // Banderes
                isFlag = true;

                // Estreim les coordenades per col·locar o llevar bandera
                if (text.length() >= 3) {
                    x = text.charAt(1)-'A'+1;
                    y = InputOutput.tryParse(text.substring(2));
                    if (getX() < 1 || getX() > maxX || getY() < 1 || getY() > maxY) {
                        errorMessage = "⛔ You cannot put a flag on " + text.substring(1) + " ⛔";
                        isError = true;
                    }
                }
                break;
            case '0':
                // Abandonam la partida
                isExit = true;
                break;
            default:
                isFlag = false;
                // Estreim les coordenades xy de l'input i aplicam el moviment
                if (text.length() >= 2) {
                    x = text.charAt(0)-'A'+1;
                    y = InputOutput.tryParse(text.substring(1));
                    if (getX() < 1 || getX() > maxX || getY() < 1 || getY() > maxY) {
                        errorMessage = "⛔ Incorrect coordinates " + text + " ⛔";
                        isError = true;
                    }
                }
                break;
        }
    }


    public boolean isFlag() {
        return isFlag;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isExit() {
        return isExit;
    }


    public boolean isError() {
        return isError;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
