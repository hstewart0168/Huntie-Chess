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
            canD = false;
        }
        else if(type.equals("pawn")){
            maxX = 2;
            maxY = 0;
            canP = true;
            canD = false;
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

    public boolean checkEligibility(int x, int y) {

        int xpieceLocation = 0, xpieceLocation2 = 0;
        int ypieceLocation = 0, ypieceLocation2 = 0;
        int dxpieceLocation1 = 0, dxpieceLocation2 = 0, dxpieceLocation3 = 0, dxpieceLocation4 = 0;
        int dypieceLocation1 = 0, dypieceLocation2 = 0, dypieceLocation3 = 0, dypieceLocation4 = 0;
        for (int i = locX; i < Board.board.length; i++) {
            if (Board.board[i][0] != null) {
                xpieceLocation = i;
                break;
            }
            xpieceLocation = i;
        }
        for (int i = locX; i >= 0; i--) {
            if (Board.board[i][0] != null) {
                xpieceLocation2 = i;
                break;
            }
            xpieceLocation2 = i;
        }
        for (int i = locY; i < Board.board.length; i++) {
            if (Board.board[i][0] != null) {
                ypieceLocation = i;
                break;
            }
            ypieceLocation = i;
        }
        for (int i = locY; i >= 0; i--) {
            if (Board.board[i][0] != null) {
                ypieceLocation2 = i;
                break;
            }
            ypieceLocation2 = i;
        }
        for (int i = locX; i < Board.board.length; i++){
            for (int j = locY; j < Board.board.length; j++) {
                if (Board.board[i][i] != null) {
                    dxpieceLocation1 = i;
                    dypieceLocation1 = j;
                    break;
                }
                dxpieceLocation1 = i;
                dypieceLocation1 = j;
            }
        }
        for (int i = locX; i < Board.board.length; i++){
            for (int j = locY; j > 0; j--) {
                if (Board.board[i][i] != null) {
                    dxpieceLocation2 = i;
                    dypieceLocation2 = j;
                    break;
                }
                dxpieceLocation2 = i;
                dypieceLocation2 = j;
            }
        }
        for (int i = locX; i > 0; i--){
            for (int j = locY; j < Board.board.length; j++) {
                if (Board.board[i][i] != null) {
                    dxpieceLocation3 = i;
                    dypieceLocation3 = j;
                    break;
                }
                dxpieceLocation3 = i;
                dypieceLocation3 = j;
            }
        }
        for (int i = locX; i > 0; i--){
            for (int j = locY; j > 0; j--) {
                if (Board.board[i][i] != null) {
                    dxpieceLocation4 = i;
                    dypieceLocation4 = j;
                    break;
                }
                dxpieceLocation4 = i;
                dypieceLocation4 = j;
            }
        }

        if(type.equals("knight")){
            if(((Math.abs((x - locX)) == 2) && (Math.abs((y - locY)) == 1)) || ((Math.abs((x - locX)) == 1) && (Math.abs((y - locY)) == 2))){
                if(Board.board[x][y] == null)
                    return true;
            }
            return false;
        }
        else if(type.equals("pawn")){
            if(enemy){
                if(((x - locX) <= maxX)&& (x - locX) > 0 && (y - locY) == 0){
                    if(Board.board[x][y] == null)
                        return true;
                }
                return false;
            }
            else{
                if(((locX - x) <= maxX)&& (locX - x) > 0 && (y - locY) == 0){
                    if(Board.board[x][y] == null)
                        return true;
                }
                return false;
            }

        }
        else if(((Math.abs((x - locX)) <= maxX) && (Math.abs((y - locY)) <= maxY))){
            if(canD && canP){
                if(((x == locX) || (y== locY)) || ((Math.abs((x - locX)) == Math.abs((y - locY))))) {
                    if((x < dxpieceLocation1 && y < dypieceLocation1) && (x < dxpieceLocation2 && y > dypieceLocation2) && (x > dxpieceLocation3 && y < dypieceLocation3) && (x > dxpieceLocation4 && y > dypieceLocation4) && y < ypieceLocation && x < xpieceLocation && x > xpieceLocation2 && y > ypieceLocation2)
                        return true;
                }
            }
            else if(canD){
                if((x != locX && y != locY) && (Math.abs((x - locX)) == Math.abs((y - locY)))){
                    if((x < dxpieceLocation1 && y < dypieceLocation1) && (x < dxpieceLocation2 && y > dypieceLocation2) && (x > dxpieceLocation3 && y < dypieceLocation3) && (x > dxpieceLocation4 && y > dypieceLocation4))
                        return true;
                }
                return false;
            }
            else if(canP){
                if(((x == locX) || (y== locY))){
                    if(y < ypieceLocation && x < xpieceLocation && x > xpieceLocation2 && y > ypieceLocation2)
                        return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }
}
