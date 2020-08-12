package Chess;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

import static Chess.Main.*;

public class AI {
    private boolean type;
    private ArrayList<Coordinate> possibleMoves;

    public AI(){
        type = Main.player;
    }

    public String setMove(){
        possibleMoves = new ArrayList<Coordinate>();
        String returnText = "";
        for (int x = 0; x < Board.board.length; x++) {
            for (int y = 0; y < Board.board[x].length; y++) {
                if (Board.board[x][y] != null) {
                    if (Board.board[x][y].getState() == type) {
                        for (int x2 = 0; x2 < Board.board.length; x2++) {
                            for (int y2 = 0; y2 < Board.board[x2].length; y2++) {
                                if (Board.board[x][y].checkEligibility(x2, y2, true, false)) {
                                    possibleMoves.add(new Coordinate(x,y,x2,y2));
                                }
                            }
                        }
                    }
                }
            }
        }
        Coordinate bestCoord = possibleMoves.get(0);
        for(Coordinate coord : possibleMoves){
            if(coord.getScore() > bestCoord.getScore()){
                bestCoord = coord;
            }
        }

        Piece selected = Board.board[bestCoord.getX()][bestCoord.getY()];
        Piece capture = null;
        System.out.println("valid move");
        if (Board.board[bestCoord.getX2()][bestCoord.getY2()] != null) {
            capture = Board.board[bestCoord.getX2()][bestCoord.getY2()];
        }
        String moveType = selected.move(bestCoord.getX2(),bestCoord.getY2(), false);
        try {
            refreshBoard();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (Piece.getTurn() == 1) {
            returnText = String.format("%d. %s", turn, selected.getNotation(capture, moveType, bestCoord.getX(), bestCoord.getY()));
        } else if (whiteTurn) {
            turn++;
            returnText = String.format("  %d. %s", turn, selected.getNotation(capture, moveType, bestCoord.getX(), bestCoord.getY()));
        } else {
            returnText = String.format("  %s", selected.getNotation(capture, moveType, bestCoord.getX(), bestCoord.getY()));
        }
        whiteTurn = !whiteTurn;
        player = !player;
        return returnText;
    }
}
