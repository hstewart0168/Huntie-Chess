package Chess;

public class Piece {
    private int maxX, maxY, maxD;
    private int locX, locY;

    public Piece(String type, int x, int y){
        if(type.equals("queen")){
            maxX = 7;
            maxY = 7;
            maxD = 7;
        }
        else if(type.equals("king")){
            maxX = 1;
            maxY = 1;
            maxD = 0;
        }
        else if(type.equals("bishop")){
            maxX = 0;
            maxY = 0;
            maxD = 7;
        }
        else if(type.equals("rook")){
            maxX = 7;
            maxY = 7;
            maxD = 0;
        }
        else if(type.equals("knight")){
            maxX = 1;
            maxY = 1;
            maxD = 1;
        }
        else if(type.equals("pawn")){
            maxX = 0;
            maxY = 1;
            maxD = 0;
        }
        locX = x;
        locY = y;
    }

    public void setX(int x){
        locX = x;
    }
    public void setY(int y){
        locY = y;
    }

    public void move(int newX, int newY){
        Board.board[newX][newY] = this;
        Board.board[locX][locY] = null;
        locX = newX;
        locY = newY;
    }

}
