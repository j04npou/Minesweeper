public class Tile {
    private boolean isMine;
    private boolean isVisible;
    private boolean isFlag;
    private int nearMines;
    private int coordinateX;
    private int coordinateY;

    public Tile(int coordinateX, int coordinateY) {
        this.isMine = false;
        this.isVisible = false;
        this.isFlag = false;
        this.nearMines = 0;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine() {
        isMine = true;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public boolean isFlag() {
        return isFlag;
    }

    public void setFlag(boolean flag) {
        isFlag = flag;
    }

    public int getNearMines() {
        return nearMines;
    }

    public void incNearMines() {
        this.nearMines++;
    }

    public int getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(int coordinateX) {
        this.coordinateX = coordinateX;
    }

    public int getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(int coordinateY) {
        this.coordinateY = coordinateY;
    }

    public static boolean uncover(int x, int y, Tile[][] tiles) {
        // Comprovam que les coordenades estiguin dins el tauler
        if (x < 0 || x >= tiles.length || y < 0 || y >= tiles[0].length || tiles[x][y].isVisible())
            // Les coordenades no estaven dins el tauler o ja estava destapada la cel·la
            return true;

        // Destapam la cel·la
        tiles[x][y].setVisible(true);

        if (!tiles[x][y].isMine() &&  tiles[x][y].getNearMines() == 0) {
            // Si la cel·la està buida, destapam les que l'envolten recursivament
            for (int x1 = x - 1; x1 <= x + 1; x1++) {
                for (int y1 = y - 1; y1 <= y + 1; y1++) {
                    uncover(x1, y1, tiles);
                }
            }
        } else if (tiles[x][y].isMine()) {
            // Si la cel·la conté una mina desencadenam el game over
            return false;
        }
        return true;
    }

    public static void gameOver() {
        InputOutput.printLN("" +
                " _____                  _____             \n" +
                "|   __|___ _____ ___   |     |_ _ ___ ___ \n" +
                "|  |  | .'|     | -_|  |  |  | | | -_|  _|\n" +
                "|_____|__,|_|_|_|___|  |_____|\\_/|___|_|  ");
    }

    public static void youWin(long time) {
        InputOutput.printLN("" +
                " __ __            _ _ _ _     \n" +
                "|  |  |___ _ _   | | | |_|___ \n" +
                "|_   _| . | | |  | | | | |   |\n" +
                "  |_| |___|___|  |_____|_|_|_|");
        InputOutput.printLN("⏱  Your time is: " + (int)(System.currentTimeMillis()-time)/1000 + "s");
        InputOutput.printLN("\uD83E\uDD73 \uD83C\uDF8A \uD83E\uDD84 \uD83C\uDF08 \uD83C\uDF89 \uD83E\uDD29");
    }
}
