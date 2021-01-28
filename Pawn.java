public class Pawn {
    public int x = 0;
    public int y = 0;
    private int lastX;
    private  int lastY;
    public boolean isAlive = true;
    public int index = 0;
    public int row;
    public int column;

    public void setCoordinates(int _x, int _y) {
        x = _x;
        y = _y;
    }

    public void setLastCoordinates() {
        lastX = x;
        lastY = y;
    }

    public void changeToLastCoodrinates(){
        x = lastX;
        y = lastY;
    }

    public int getLastX(){
        return lastX;
    }

    public int getLastY(){
        return lastY;
    }
}
