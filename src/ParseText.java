public class ParseText {
    private boolean isFlag;
    private int x;
    private int y;
    private String errorMessage;
    private boolean isError;
    private boolean isExit;

    public ParseText(String text, int maxX, int maxY) {
        isFlag = false;
        isExit = false;
        isError = false;
        errorMessage = "";
        x = 0;
        y = 0;
        parseString(text.toUpperCase(),maxX,maxY);
    }

    private void parseString(String text, int maxX, int maxY){
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
