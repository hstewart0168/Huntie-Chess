package Chess;

import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Piece {
    private final String type;
    private String notation;
    private int maxX, maxY;
    private int locX, locY;
    private int firstMove;
    boolean black, canD, canP, unMoved;
    ArrayList<Piece> checkingPieces;
    Piece checker;


    private static int turn = 0;

    public Piece(String type, int x, int y, boolean e) {
        black = e;
        unMoved = true;
        this.type = type;
        checkingPieces = new ArrayList<Piece>();
        switch (type) {
            case "queen" -> {
                maxX = 7;
                maxY = 7;
                canP = true;
                canD = true;
                notation = "Q";
            }
            case "king" -> {
                maxX = 1;
                maxY = 1;
                canP = true;
                canD = true;
                notation = "K";
            }
            case "bishop" -> {
                maxX = 7;
                maxY = 7;
                canP = false;
                canD = true;
                notation = "B";
            }
            case "rook" -> {
                maxX = 7;
                maxY = 7;
                canP = true;
                canD = false;
                notation = "R";
            }
            case "knight" -> {
                maxX = 2;
                maxY = 2;
                canP = true;
                canD = false;
                notation = "N";
            }
            case "pawn" -> {
                maxX = 2;
                maxY = 0;
                canP = true;
                canD = false;
                notation = "";
            }
        }
        locX = x;
        locY = y;
    }

    public String move(int newX, int newY, boolean test) {
        String moveType = "none";
        if (type.equals("king") && locY == 4 && unMoved && !test) {
            if (newX == locX && newY == 6) {
                Board.board[locX][7].move(locX, 5, false);
                moveType = "0-0";
            } else if (newX == locX && newY == 2) {
                Board.board[locX][0].move(locX, 3, false);
                moveType = "0-0-0";
            }
        }

        if (type.equals("pawn") && black && (locX == newX - 1) && ((locY == newY - 1) || (locY == newY + 1)) && Board.board[newX - 1][newY] != null && !test) {
            if (Board.board[newX - 1][newY].getType().equals("pawn") && Board.board[newX - 1][newY].getFirstMove() == turn - 1) {
                Board.board[newX - 1][newY] = null;
                moveType = "e.p";
            }
        }

        if (type.equals("pawn") && !black && (locX == newX + 1) && ((locY == newY - 1) || (locY == newY + 1)) && Board.board[newX + 1][newY] != null && !test) {
            if (Board.board[newX + 1][newY].getType().equals("pawn") && Board.board[newX + 1][newY].getFirstMove() == turn - 1) {
                Board.board[newX + 1][newY] = null;
                moveType = "e.p";
            }
        }

        Board.board[newX][newY] = this;
        Board.board[locX][locY] = null;
        locX = newX;
        locY = newY;

        if (unMoved && !test) {
            firstMove = turn;
            unMoved = false;
        }
        if (type.equals("pawn") && !test) {
            maxX = 1;
        }
        if (type.equals("pawn") && (newX == 7 || newX == 0) && !test) {
            Boolean type = this.getState();
            Board.board[newX][newY] = new Piece("queen", newX, newY, type);
            moveType = ("promo");
        }
        if (!test) {
            turn++;
        }

        return moveType;
    }

    public Image getImage() throws FileNotFoundException {
        if (black) {
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
        } else {
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

    public boolean checkEligibility(int x, int y, boolean first, boolean test) {
        if (Board.board[x][y] == this) {
            return false;
        }

        if (type.equals("king") && first) {
            if (underSiege(x, y)) {
                return false;
            }
        }

        if (Board.board[locX][7] != null) {
            if (type.equals("king") && Board.board[locX][7].getType().equals("rook") && unMoved && Board.board[locX][7].isUnMoved() && !test && saveKing()) {
                if (x == locX && y == 6 && Board.board[locX][6] == null && Board.board[locX][5] == null) {
                    if (testMove(x, y)) {
                        return true;
                    }
                }
            }
        }

        if (Board.board[locX][0] != null) {
            if (type.equals("king") && Board.board[locX][0].getType().equals("rook") && unMoved && Board.board[locX][0].isUnMoved() && !test && saveKing()) {
                if (x == locX && y == 2 && Board.board[locX][3] == null && Board.board[locX][2] == null && Board.board[locX][1] == null) {
                    return testMove(x, y);
                }
            }
        }

        if (type.equals("knight")) {
            if (((Math.abs((x - locX)) == 2) && (Math.abs((y - locY)) == 1)) || ((Math.abs((x - locX)) == 1) && (Math.abs((y - locY)) == 2))) {
                if (Board.board[x][y] == null) {
                    return (testMove(x, y));
                } else if (Board.board[x][y].getState() != getState()) {
                    return (testMove(x, y));
                }
            }
        } else if (type.equals("pawn")) {
            if (black) {
                if (((x - locX) <= maxX) && (x - locX) > 0 && (y - locY) == 0) {
                    if (Board.board[x][y] == null && (Board.board[x - 1][y] == null || Board.board[x - 1][y] == this))
                        return testMove(x, y);
                }
                if (Board.board[x][y] != null && x == locX + 1 && (y == locY - 1 || y == locY + 1) && Board.board[x][y].getState() != black) {
                    return testMove(x, y);
                }
                if (locX == 4 && y != 0 && x != 0) {
                    if (locX == x - 1 && locY == y - 1) {
                        if (Board.board[x - 1][y] != null) {
                            if (Board.board[x - 1][y].getType().equals("pawn") && Board.board[x - 1][y].getState() != black && Board.board[x - 1][y].getFirstMove() == turn - 1) {
                                return testMove(x, y);
                            }
                        }
                    }
                }
                if (locX == 4 && y != 7 && x != 7) {
                    if (locX == x - 1 && locY == y + 1) {
                        if (Board.board[x - 1][y] != null) {
                            if (Board.board[x - 1][y].getType().equals("pawn") && Board.board[x - 1][y].getState() != black && Board.board[x - 1][y].getFirstMove() == turn - 1) {
                                return testMove(x, y);
                            }
                        }
                    }
                }
            } else {
                if (((locX - x) <= maxX) && (locX - x) > 0 && (y - locY) == 0) {
                    if (Board.board[x][y] == null && (Board.board[x + 1][y] == null || Board.board[x + 1][y] == this))
                        return testMove(x, y);
                }
                if (Board.board[x][y] != null && x == locX - 1 && (y == locY - 1 || y == locY + 1) && Board.board[x][y].getState() != black) {
                    return testMove(x, y);
                }
                if (locX == 3 && y != 0 && x != 0) {
                    if (locX == x + 1 && locY == y - 1) {
                        if (Board.board[x + 1][y] != null) {
                            if (Board.board[x + 1][y].getType().equals("pawn") && Board.board[x + 1][y].getState() != black && Board.board[x + 1][y].getFirstMove() == turn - 1) {
                                return testMove(x, y);
                            }
                        }
                    }
                }
                if (locX == 3 && y != 7 && x != 7) {
                    if (locX == x + 1 && locY == y + 1) {
                        if (Board.board[x + 1][y] != null) {
                            if (Board.board[x + 1][y].getType().equals("pawn") && Board.board[x + 1][y].getState() != black && Board.board[x + 1][y].getFirstMove() == turn - 1) {
                                return testMove(x, y);
                            }
                        }
                    }
                }
            }
            return false;
        } else if (((Math.abs((x - locX)) <= maxX) && (Math.abs((y - locY)) <= maxY))) {
            if (canD && canP) {
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
                    if (Board.board[x][y] == null)
                        return testMove(x, y);
                    else if (Board.board[x][y].getState() != this.getState())
                        return testMove(x, y);
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
                    if (Board.board[x][y] == null)
                        return testMove(x, y);
                    else if (Board.board[x][y].getState() != this.getState())
                        return testMove(x, y);
                }
                if ((x != locX && y != locY) && (Math.abs((x - locX)) == Math.abs((y - locY)))) {
                    if (x > locX && y > locY) {
                        for (int i = locX; i < x; i++) {
                            for (int j = locY; j < y; j++) {
                                if (Board.board[i][j] != null && Board.board[i][j] != this && (Math.abs((x - i)) == Math.abs((y - j)))) {
                                    return false;
                                }
                            }
                        }
                        if (Board.board[x][y] == null)
                            return testMove(x, y);
                        else if (Board.board[x][y].getState() != getState()) {
                            return testMove(x, y);
                        }
                    } else if (x < locX && y < locY) {
                        for (int i = locX; i > x; i--) {
                            for (int j = locY; j > y; j--) {
                                if (Board.board[i][j] != null && Board.board[i][j] != this && (Math.abs((x - i)) == Math.abs((y - j)))) {
                                    return false;
                                }
                            }
                        }
                        if (Board.board[x][y] == null)
                            return testMove(x, y);
                        else if (Board.board[x][y].getState() != getState()) {
                            return testMove(x, y);
                        }
                    } else if (x > locX) {
                        for (int i = locX; i < x; i++) {
                            for (int j = locY; j > y; j--) {
                                if (Board.board[i][j] != null && Board.board[i][j] != this && (Math.abs((x - i)) == Math.abs((y - j)))) {
                                    return false;
                                }
                            }
                        }
                        if (Board.board[x][y] == null)
                            return testMove(x, y);
                        else if (Board.board[x][y].getState() != getState()) {
                            return testMove(x, y);
                        }
                    } else {
                        for (int i = locX; i > x; i--) {
                            for (int j = locY; j < y; j++) {
                                if (Board.board[i][j] != null && Board.board[i][j] != this && (Math.abs((x - i)) == Math.abs((y - j)))) {
                                    return false;
                                }
                            }
                        }
                        if (Board.board[x][y] == null) {
                            return testMove(x, y);
                        } else if (Board.board[x][y].getState() != getState()) {
                            return testMove(x, y);
                        }
                    }
                }
                return false;
            }
            if (canD) {
                if ((x != locX && y != locY) && (Math.abs((x - locX)) == Math.abs((y - locY)))) {
                    if (x > locX && y > locY) {
                        for (int i = locX; i < x; i++) {
                            for (int j = locY; j < y; j++) {
                                if (Board.board[i][j] != null && Board.board[i][j] != this && (Math.abs((x - i)) == Math.abs((y - j)))) {
                                    return false;
                                }
                            }
                        }
                        if (Board.board[x][y] == null) {
                            return testMove(x, y);
                        } else if (Board.board[x][y].getState() != getState()) {
                            return testMove(x, y);
                        }
                    } else if (x < locX && y < locY) {
                        for (int i = locX; i > x; i--) {
                            for (int j = locY; j > y; j--) {
                                if (Board.board[i][j] != null && Board.board[i][j] != this && (Math.abs((x - i)) == Math.abs((y - j)))) {
                                    return false;
                                }
                            }
                        }
                        if (Board.board[x][y] == null) {
                            return testMove(x, y);
                        } else if (Board.board[x][y].getState() != getState()) {
                            return testMove(x, y);
                        }
                    } else if (x > locX) {
                        for (int i = locX; i < x; i++) {
                            for (int j = locY; j > y; j--) {
                                if (Board.board[i][j] != null && Board.board[i][j] != this && (Math.abs((x - i)) == Math.abs((y - j)))) {
                                    return false;
                                }
                            }
                        }
                        if (Board.board[x][y] == null) {
                            return testMove(x, y);
                        } else if (Board.board[x][y].getState() != getState()) {
                            return testMove(x, y);
                        }
                    } else {
                        for (int i = locX; i > x; i--) {
                            for (int j = locY; j < y; j++) {
                                if (Board.board[i][j] != null && Board.board[i][j] != this && (Math.abs((x - i)) == Math.abs((y - j)))) {
                                    return false;
                                }
                            }
                        }
                        if (Board.board[x][y] == null) {
                            return testMove(x, y);
                        } else if (Board.board[x][y].getState() != getState()) {
                            return testMove(x, y);
                        }
                    }
                }
                return false;
            }
            if (canP) {
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
                    if (Board.board[x][y] == null) {
                        return testMove(x, y);
                    } else if (Board.board[x][y].getState() != getState()) {
                        return testMove(x, y);
                    }
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
                    if (Board.board[x][y] == null) {
                        return testMove(x, y);
                    } else if (Board.board[x][y].getState() != getState()) {
                        return testMove(x, y);
                    }
                }
                return false;
            }
            return false;
        }
        return false;
    }

    public boolean canMove() {
        for (int x = 0; x < Board.board.length; x++) {
            for (int y = 0; y < Board.board[x].length; y++) {
                if (this.checkEligibility(x, y, true, false)) {
                    return true;
                }
            }
        }
        return false;
    }

    private int getFirstMove() {
        return firstMove;
    }


    public boolean getState() {
        return black;
    }

    public String getType() {
        return type;
    }

    private boolean isUnMoved() {
        return unMoved;
    }

    public boolean inCheck() {
        checkingPieces.clear();
        for (int i = 0; i < Board.board.length; i++) {
            for (int j = 0; j < Board.board[i].length; j++) {
                if (Board.board[i][j] != null) {
                    if (Board.board[i][j].getState() != getState() && Board.board[i][j].checkEligibility(locX, locY, true, true)) {
                        checkingPieces.add(Board.board[i][j]);
                        checker = Board.board[i][j];
                    }
                }
            }
        }
        if (!checkingPieces.isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean underSiege(int x, int y) {
        for (int i = 0; i < Board.board.length; i++) {
            for (int j = 0; j < Board.board[i].length; j++) {
                if (Board.board[i][j] != null) {
                    if (Board.board[i][j].getState() != getState() && Board.board[i][j].checkEligibility(x, y, false, false)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean saveKing() {
        if (Main.whiteTurn) {
            if (Main.blackKing.inCheck()) {
                return false;
            }
        } else {
            if (Main.whiteKing.inCheck()) {
                return false;
            }
        }
        return true;
    }

    public boolean oppKing() {
        if (!Main.whiteTurn) {
            if (Main.blackKing.inCheck()) {
                return false;
            }
        } else {
            if (Main.whiteKing.inCheck()) {
                return false;
            }
        }
        return true;
    }

    public boolean testMove(int newX, int newY) {
        int oldX = locX, oldY = locY;
        Piece oldEnemy = null;
        boolean test;
        if (Board.board[newX][newY] != null) {
            if (Board.board[newX][newY].getState() != getState()) {
                oldEnemy = Board.board[newX][newY];
            }
        }

        move(newX, newY, true);
        test = saveKing();
        move(oldX, oldY, true);
        if (oldEnemy != null) {
            Board.board[newX][newY] = oldEnemy;
        }

        return test;
    }

    public ArrayList<Piece> getCheckingPieces() {
        return checkingPieces;
    }

    public Piece getChecker() {
        return checker;
    }

    public String getNotation(Piece capture, String typ, int oldX, int oldY) {
        String tempNot = notation;

        String tempY = String.valueOf(9 - (locX + 1));
        String x = String.valueOf(Character.toLowerCase(getChar(locY)));

        boolean sit1 = false, sit2 = false, sit3 = false;

        for(int i = 0; i < Board.board.length; i++){
            for(int j = 0; j < Board.board[i].length; j++){
                if(Board.board[i][j] != null){
                    if(Board.board[i][j].getState() == getState() && Board.board[i][j].getType().equals(type) && Board.board[i][j] != this){
                        Piece temp = Board.board[locX][locY];
                        Board.board[locX][locY] = null;
                        if(Board.board[i][j].checkEligibility(locX, locY, true, false)){
                            if(oldY != Board.board[i][j].getLocY()){
                                sit1 = true;
                            }
                            if(Board.board[i][j].getLocX() != locX && oldY == locY){
                                sit2 = true;
                            }
                        }
                        Board.board[locX][locY] = temp;
                    }
                }
            }
        }

        if(sit1 && sit2){
            sit1 = false;
            sit2 = false;
            sit3 = true;
        }

        if(sit1){
            tempNot += String.valueOf(Character.toLowerCase(getChar(oldY)));
        }
        else if(sit2){
            tempNot += (9 - (oldX + 1));
        }
        else if(sit3){
            tempNot += String.valueOf(Character.toLowerCase(getChar(oldY))) + (9 - (oldX + 1));
        }

        if(capture != null && !type.equals("pawn")){
            x = "x" + x;
        }
        else if(capture != null){
            x += "x";
        }

        if(typ.equals("0-0")){
            return "0-0";
        }

        if(typ.equals("0-0-0")){
            return "0-0-0";
        }

        if(typ.equals("promo")){
            tempY += "=Q";
        }

        if(typ.equals("e.p")){
            x += "x";
            tempY += "e.p";
        }

        if(!oppKing()){
            tempY += ("+");
        }

        return (tempNot + x + tempY);
    }

    public int getLocY() {
        return locY;
    }

    public char getChar(int i) {
        return i<0 || i>25 ? '?' : (char)('A' + i);
    }

    public int getLocX(){
        return locX;
    }

    public static int getTurn(){
        return turn;
    }
}
