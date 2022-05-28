public class Tile {
    private boolean isMine = false;
    private boolean isVisible = false;
    private boolean isFlag = false;
    private int nearMines = 0;
    private int coordinateX = 0;
    private int coordinateY = 0;

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

    public void setNearMines() {
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
}
