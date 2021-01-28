import java.awt.*;

public class Square {
    public int x;
    public int y;
    public int size;
    public Color color = Color.WHITE;
    public boolean isEmpty = true;
    public boolean isThereTurtle = false;

    public Square(int _x, int _y, int _size) {
        x = _x;
        y = _y;
        size = _size;
    }
}
