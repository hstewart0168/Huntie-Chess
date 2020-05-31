package Chess;

import javafx.scene.image.Image;

import javax.imageio.stream.FileImageInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Piece {
    private String type;
    private int maxX, maxY, maxD;
    private int locX, locY;
    boolean enemy;

    public Piece(String type, int x, int y, boolean e){
        enemy = e;
        this.type = type;
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

    public Image getImage() throws FileNotFoundException {
        if(enemy) {
            if(type.equals("king")) {
                return (new Image(new FileInputStream(new File("src\\Chess\\Sprites\\blackKing.png"))));
            }
            else if(type.equals("bishop")) {
                return (new Image(new FileInputStream(new File("src\\Chess\\Sprites\\blackBishop.png"))));
            }
            else if(type.equals("pawn")) {
                return (new Image(new FileInputStream(new File("src\\Chess\\Sprites\\blackPawn.png"))));
            }
            else if(type.equals("knight")) {
                return (new Image(new FileInputStream(new File("src\\Chess\\Sprites\\blackKnight.png"))));
            }
            else if(type.equals("queen")) {
                return (new Image(new FileInputStream(new File("src\\Chess\\Sprites\\blackQueen.png"))));
            }
            else if(type.equals("rook")) {
                return (new Image(new FileInputStream(new File("src\\Chess\\Sprites\\blackRook.png"))));
            }
        }
        else{
            if(type.equals("king")) {
                return (new Image(new FileInputStream(new File("src\\Chess\\Sprites\\whiteKing.png"))));
            }
            else if(type.equals("bishop")) {
                return (new Image(new FileInputStream(new File("src\\Chess\\Sprites\\whiteBishop.png"))));
            }
            else if(type.equals("pawn")) {
                return (new Image(new FileInputStream(new File("src\\Chess\\Sprites\\whitePawn.png"))));
            }
            else if(type.equals("knight")) {
                return (new Image(new FileInputStream(new File("src\\Chess\\Sprites\\whiteKnight.png"))));
            }
            else if(type.equals("queen")) {
                return (new Image(new FileInputStream(new File("src\\Chess\\Sprites\\whiteQueen.png"))));
            }
            else if(type.equals("queen")) {
                return (new Image(new FileInputStream(new File("src\\Chess\\Sprites\\whiteRook.png"))));
            }
        }
        return (new Image(new FileInputStream(new File("src\\Chess\\Sprites\\blankSpace.png"))));
    }
}
