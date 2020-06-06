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
        if(Board.board[x][y] == this){
            return false;
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
            if(canD && canP) {
                if (y == locY) {
                    if (x < locX) {
                        for (int i = locX; i > x; i--) {
                            if (Board.board[i][y] != null && Board.board[i][y] != this) {
                                return false;
                            }
                        }
                    } else if (x > locX) {
                        for (int i = locX; i < x; i++) {
                            if (Board.board[i][y] != null && Board.board[i][y] != this) {
                                return false;
                            }
                        }
                    }
                    return true;
                } else if (x == locX) {
                    if (y < locY) {
                        for (int i = locY; i > y; i--) {
                            if (Board.board[x][i] != null && Board.board[x][i] != this) {
                                return false;
                            }
                        }
                    } else if (y > locY) {
                        for (int i = locY; i < y; i++) {
                            if (Board.board[x][i] != null && Board.board[x][i] != this) {
                                return false;
                            }
                        }
                    }
                    return true;
                }
                if((x != locX && y != locY) && (Math.abs((x - locX)) == Math.abs((y - locY)))){
                    if(x > locX && y > locY) {
                        for (int i = locX; i < x; i++) {
                            for (int j = locY; j < y; j++) {
                                if (Board.board[i][j] != null && Board.board[i][j] != this && (Math.abs((x - i)) == Math.abs((y - j)))) {
                                    return false;
                                }
                            }
                        }
                        return true;
                    }
                    else if(x < locX && y < locY) {
                        for (int i = locX; i > x; i--) {
                            for (int j = locY; j > y; j--) {
                                if (Board.board[i][j] != null && Board.board[i][j] != this && (Math.abs((x - i)) == Math.abs((y - j)))) {
                                    return false;
                                }
                            }
                        }
                        return true;
                    }
                    else if(x > locX) {
                        for (int i = locX; i < x; i++) {
                            for (int j = locY; j > y; j--) {
                                if (Board.board[i][j] != null && Board.board[i][j] != this && (Math.abs((x - i)) == Math.abs((y - j)))) {
                                    return false;
                                }
                            }
                        }
                        return true;
                    }
                    else {
                        for (int i = locX; i > x; i--) {
                            for (int j = locY; j < y; j++) {
                                if (Board.board[i][j] != null && Board.board[i][j] != this && (Math.abs((x - i)) == Math.abs((y - j)))) {
                                    return false;
                                }
                            }
                        }
                        return true;
                    }
                }
                return false;
            }
            if(canD){
                if((x != locX && y != locY) && (Math.abs((x - locX)) == Math.abs((y - locY)))){
                    if(x > locX && y > locY) {
                        for (int i = locX; i < x; i++) {
                            for (int j = locY; j < y; j++) {
                                if (Board.board[i][j] != null && Board.board[i][j] != this && (Math.abs((x - i)) == Math.abs((y - j)))) {
                                    return false;
                                }
                            }
                        }
                        return true;
                    }
                    else if(x < locX && y < locY) {
                        for (int i = locX; i > x; i--) {
                            for (int j = locY; j > y; j--) {
                                if (Board.board[i][j] != null && Board.board[i][j] != this && (Math.abs((x - i)) == Math.abs((y - j)))) {
                                    return false;
                                }
                            }
                        }
                        return true;
                    }
                    else if(x > locX) {
                        for (int i = locX; i < x; i++) {
                            for (int j = locY; j > y; j--) {
                                if (Board.board[i][j] != null && Board.board[i][j] != this && (Math.abs((x - i)) == Math.abs((y - j)))) {
                                    return false;
                                }
                            }
                        }
                        return true;
                    }
                    else {
                        for (int i = locX; i > x; i--) {
                            for (int j = locY; j < y; j++) {
                                if (Board.board[i][j] != null && Board.board[i][j] != this && (Math.abs((x - i)) == Math.abs((y - j)))) {
                                    return false;
                                }
                            }
                        }
                        return true;
                    }
                }
                return false;
            }
            if(canP){
                if(y == locY){
                    if(x < locX){
                        for(int i = locX; i > x; i--){
                            if(Board.board[i][y] != null && Board.board[i][y] != this){
                                return false;
                            }
                        }
                    }
                    else if(x > locX){
                        for(int i = locX; i < x; i++){
                            if(Board.board[i][y] != null && Board.board[i][y] != this){
                                return false;
                            }
                        }
                    }
                    return true;
                }
                else if(x == locX){
                    if(y < locY){
                        for(int i = locY; i > y; i--){
                            if(Board.board[x][i] != null && Board.board[x][i] != this){
                                return false;
                            }
                        }
                    }
                    else if(y > locY){
                        for(int i = locY; i < y; i++){
                            if(Board.board[x][i] != null && Board.board[x][i] != this){
                                return false;
                            }
                        }
                    }
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }
}
