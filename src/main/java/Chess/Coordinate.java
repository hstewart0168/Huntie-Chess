package Chess;

public class Coordinate {
    private int x;
    private int y;
    private int x2;
    private int y2;
    private int score;

    public Coordinate(int x, int y, int x2, int y2){
        this.x = x;
        this.y = y;
        this.x2 = x2;
        this.y2 = y2;
        score = 0;
    }

    public void findScore(){
        score = 0;
    }

    public int getScore(){
        return score;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }
}
