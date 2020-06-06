package Chess;

import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Piece {
    private final String type;
    private int maxX, maxY;
    private int locX, locY;
    boolean enemy, canD, canP;

    public Piece(String type, int x, int y, boolean e){
        enemy = e;
        this.type = type;
        switch (type) {
            case "queen" -> {
                maxX = 7;
                maxY = 7;
                canP = true;
                canD = true;
            }
            case "king" -> {
                maxX = 1;
                maxY = 1;
                canP = true;
                canD = false;
            }
            case "bishop" -> {
                maxX = 7;
                maxY = 7;
                canP = false;
                canD = true;
            }
            case "rook" -> {
                maxX = 7;
                maxY = 7;
                canP = true;
                canD = false;
            }
            case "knight" -> {
                maxX = 2;
                maxY = 2;
                canP = true;
                canD = false;
            }
            case "pawn" -> {
                maxX = 2;
                maxY = 0;
                canP = true;
                canD = false;
            }
        }
        locX = x;
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
            switch (type) {
                case "king":
                    return (new Image(new FileInputStream(new File("Sprites\\blackKing.png"))));
                case "bishop":
                    return (new Image(new FileInputStream(new File("Sprites\\blackBishop.png"))));
                case "pawn":
                    return (new Image(new FileInputStream(new File("Sprites\\blackPawn.png"))));
                case "knight":
                    return (new Image(new FileInputStream(new File("Sprites\\blackKnight.png"))));
                case "queen":
                    return (new Image(new FileInputStream(new File("Sprites\\blackQueen.png"))));
                case "rook":
                    return (new Image(new FileInputStream(new File("Sprites\\blackRook.png"))));
            }
        }
        else{
            switch (type) {
                case "king":
                    return (new Image(new FileInputStream(new File("Sprites\\whiteKing.png"))));
                case "bishop":
                    return (new Image(new FileInputStream(new File("Sprites\\whiteBishop.png"))));
                case "pawn":
                    return (new Image(new FileInputStream(new File("Sprites\\whitePawn.png"))));
                case "knight":
                    return (new Image(new FileInputStream(new File("Sprites\\whiteKnight.png"))));
                case "queen":
                    return (new Image(new FileInputStream(new File("Sprites\\whiteQueen.png"))));
                case "rook":
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
                else return Board.board[x][y].getState() != this.getState();
            }
        }
        else if(type.equals("pawn")){
            if(enemy){
                if(((x - locX) <= maxX)&& (x - locX) > 0 && (y - locY) == 0){
                    return Board.board[x][y] == null;
                }
            }
            else{
                if(((locX - x) <= maxX)&& (locX - x) > 0 && (y - locY) == 0){
                    return Board.board[x][y] == null;
                }
            }
            return false;

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
                    if(Board.board[x][y] == null)
                        return true;
                    else if(Board.board[x][y].getState() != this.getState())
                        return true;
                } else if (x == locX) {
                    if (y < locY) {
                        for (int i = locY; i > y; i--) {
                            if (Board.board[x][i] != null && Board.board[x][i] != this) {
                                return false;
                            }
                        }
                    } else {
                        for (int i = locY; i < y; i++) {
                            if (Board.board[x][i] != null && Board.board[x][i] != this) {
                                return false;
                            }
                        }
                    }
                    if(Board.board[x][y] == null)
                        return true;
                    else if(Board.board[x][y].getState() != this.getState())
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
                        if(Board.board[x][y] == null)
                            return true;
                        else return Board.board[x][y].getState() != this.getState();
                    }
                    else if(x < locX && y < locY) {
                        for (int i = locX; i > x; i--) {
                            for (int j = locY; j > y; j--) {
                                if (Board.board[i][j] != null && Board.board[i][j] != this && (Math.abs((x - i)) == Math.abs((y - j)))) {
                                    return false;
                                }
                            }
                        }
                        if(Board.board[x][y] == null)
                            return true;
                        else return Board.board[x][y].getState() != this.getState();
                    }
                    else if(x > locX) {
                        for (int i = locX; i < x; i++) {
                            for (int j = locY; j > y; j--) {
                                if (Board.board[i][j] != null && Board.board[i][j] != this && (Math.abs((x - i)) == Math.abs((y - j)))) {
                                    return false;
                                }
                            }
                        }
                        if(Board.board[x][y] == null)
                            return true;
                        else return Board.board[x][y].getState() != this.getState();
                    }
                    else {
                        for (int i = locX; i > x; i--) {
                            for (int j = locY; j < y; j++) {
                                if (Board.board[i][j] != null && Board.board[i][j] != this && (Math.abs((x - i)) == Math.abs((y - j)))) {
                                    return false;
                                }
                            }
                        }
                        if(Board.board[x][y] == null)
                            return true;
                        else return Board.board[x][y].getState() != this.getState();
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
                        if(Board.board[x][y] == null)
                            return true;
                        else return Board.board[x][y].getState() != this.getState();
                    }
                    else if(x < locX && y < locY) {
                        for (int i = locX; i > x; i--) {
                            for (int j = locY; j > y; j--) {
                                if (Board.board[i][j] != null && Board.board[i][j] != this && (Math.abs((x - i)) == Math.abs((y - j)))) {
                                    return false;
                                }
                            }
                        }
                        if(Board.board[x][y] == null)
                            return true;
                        else return Board.board[x][y].getState() != this.getState();
                    }
                    else if(x > locX) {
                        for (int i = locX; i < x; i++) {
                            for (int j = locY; j > y; j--) {
                                if (Board.board[i][j] != null && Board.board[i][j] != this && (Math.abs((x - i)) == Math.abs((y - j)))) {
                                    return false;
                                }
                            }
                        }
                        if(Board.board[x][y] == null)
                            return true;
                        else return Board.board[x][y].getState() != this.getState();
                    }
                    else {
                        for (int i = locX; i > x; i--) {
                            for (int j = locY; j < y; j++) {
                                if (Board.board[i][j] != null && Board.board[i][j] != this && (Math.abs((x - i)) == Math.abs((y - j)))) {
                                    return false;
                                }
                            }
                        }
                        if(Board.board[x][y] == null)
                            return true;
                        else return Board.board[x][y].getState() != this.getState();
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
                    if(Board.board[x][y] == null)
                        return true;
                    else return Board.board[x][y].getState() != this.getState();
                }
                else if(x == locX){
                    if(y < locY){
                        for(int i = locY; i > y; i--){
                            if(Board.board[x][i] != null && Board.board[x][i] != this){
                                return false;
                            }
                        }
                    }
                    else {
                        for(int i = locY; i < y; i++){
                            if(Board.board[x][i] != null && Board.board[x][i] != this){
                                return false;
                            }
                        }
                    }
                    if(Board.board[x][y] == null)
                        return true;
                    else return Board.board[x][y].getState() != this.getState();
                }
                return false;
            }
            return false;
        }
        return false;
    }



    private boolean getState() {
        return enemy;
    }


}
