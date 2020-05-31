package Chess;

import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Piece {
    private String type;
    private int maxX, maxY;
    private int locX, locY;
    boolean enemy, canD, canP;

    public Piece(String type, int x, int y, boolean e){
        enemy = e;
        this.type = type;
        if(type.equals("queen")){
            maxX = 7;
            maxY = 7;
            canP = true;
            canD = true;
        }
        else if(type.equals("king")){
            maxX = 1;
            maxY = 1;
            canP = true;
            canD = false;
        }
        else if(type.equals("bishop")){
            maxX = 7;
            maxY = 7;
            canP = false;
            canD = true;
        }
        else if(type.equals("rook")){
            maxX = 7;
            maxY = 7;
            canP = true;
            canD = false;
        }
        else if(type.equals("knight")){
            maxX = 2;
            maxY = 2;
            canP = true;
        }
        else if(type.equals("pawn")){
            maxX = 0;
            maxY = 1;
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

    public Image getImage() throws FileNotFoundException {
        if(enemy) {
            if(type.equals("king")) {
                return (new Image(new FileInputStream(new File("Sprites\\blackKing.png"))));
            }
            else if(type.equals("bishop")) {
                return (new Image(new FileInputStream(new File("Sprites\\blackBishop.png"))));
            }
            else if(type.equals("pawn")) {
                return (new Image(new FileInputStream(new File("Sprites\\blackPawn.png"))));
            }
            else if(type.equals("knight")) {
                return (new Image(new FileInputStream(new File("Sprites\\blackKnight.png"))));
            }
            else if(type.equals("queen")) {
                return (new Image(new FileInputStream(new File("Sprites\\blackQueen.png"))));
            }
            else if(type.equals("rook")) {
                return (new Image(new FileInputStream(new File("Sprites\\blackRook.png"))));
            }
        }
        else{
            if(type.equals("king")) {
                return (new Image(new FileInputStream(new File("Sprites\\whiteKing.png"))));
            }
            else if(type.equals("bishop")) {
                return (new Image(new FileInputStream(new File("Sprites\\whiteBishop.png"))));
            }
            else if(type.equals("pawn")) {
                return (new Image(new FileInputStream(new File("Sprites\\whitePawn.png"))));
            }
            else if(type.equals("knight")) {
                return (new Image(new FileInputStream(new File("Sprites\\whiteKnight.png"))));
            }
            else if(type.equals("queen")) {
                return (new Image(new FileInputStream(new File("Sprites\\whiteQueen.png"))));
            }
            else if(type.equals("rook")) {
                return (new Image(new FileInputStream(new File("Sprites\\whiteRook.png"))));
            }
        }
        return (new Image(new FileInputStream(new File("Sprites\\blankSpace.png"))));
    }

    public boolean checkEligibility(int x, int y){
        if(type.equals("knight")){
            if((((x - locX) == 2) && ((y - locY) == 1)) || ((x - locX) == 1) && ((y - locY) == 2)){
                return true;
            }
            return false;
        }
        else if(((x - locX) <= maxX) && ((y - locY) <= maxY)){
            if(canD && canP){
                return true;
            }
            else if(canD && !canP){
                if((x != locX && y != locX) && (((x - locX) == (y - locY)))){
                    return true;
                }
                return false;
            }
            else if(!canD && canP){
                if(((x == locX) || (y== locY))){
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }
}
