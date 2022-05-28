public class Tile {
    private boolean isMine = false;
    private boolean isVisible = false;
    private boolean isFlag = false;
    private int nearMines = 0;
    private int coordinateX = 0;
    private int coordinateY = 0;

    public Tile(boolean isMine, boolean isVisible, boolean isFlag, int nearMines, int coordinateX, int coordinateY) {
        this.isMine = isMine;
        this.isVisible = isVisible;
        this.isFlag = isFlag;
        this.nearMines = nearMines;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }

    public boolean isMine() {
        return isMine;
    }

    public boolean isVisible() {
        return isVisible;
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

    public int getCoordinateX() {
        return coordinateX;
    }

    public int getCoordinateY() {
        return coordinateY;
    }
}
