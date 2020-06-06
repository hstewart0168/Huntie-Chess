package Chess;

import javafx.scene.shape.Rectangle;

public class Selector extends Rectangle{
    double x;
    double y;

    public Selector(){
        super();
        x = 0;
        y = 0;
    }

    public double getYCoord() {
        return y;
    }
    public void addYCoord(double i) {
        y += i;
    }

    public double getXCoord() {
        return x;
    }
    public void addXCoord(double i) {
        x += i;
    }


    public double getArrayX() {
        return (int)(x / 32);
    }
    public double getArrayY() {
        return (int)(y / 32);
    }
}
