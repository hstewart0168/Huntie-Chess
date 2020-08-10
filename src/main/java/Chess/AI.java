package Chess;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static Chess.Main.*;

public class AI {
    boolean type;
    int xMove;
    int yMove;

    public AI(){
        type = Main.player;
        xMove = 0;
        yMove = 0;
    }

    public String setMove(){
        String returnText = "";
        for (int x = 0; x < Board.board.length; x++) {
            for (int y = 0; y < Board.board[x].length; y++) {
                if (Board.board[x][y] != null) {
                    if (Board.board[x][y].getState() == type) {
                        for (int x2 = 0; x2 < Board.board.length; x2++) {
                            for (int y2 = 0; y2 < Board.board[x2].length; y2++) {
                                if (Board.board[x][y].checkEligibility(x2, y2, true, false)) {
                                    Piece selected = Board.board[x][y];
                                    int oldX = x;
                                    int oldY = y;
                                    Piece capture = null;
                                    System.out.println("valid move");
                                    if (Board.board[x2][y2] != null) {
                                        capture = Board.board[x2][y2];
                                    }
                                    String moveType = selected.move(x2, y2, false);
                                    try {
                                        refreshBoard();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    if (Piece.getTurn() == 1) {
                                        returnText = String.format("%d. %s", turn, selected.getNotation(capture, moveType, oldX, oldY));
                                    } else if (whiteTurn) {
                                        turn++;
                                        returnText = String.format("  %d. %s", turn, selected.getNotation(capture, moveType, oldX, oldY));
                                    } else {
                                        returnText = String.format("  %s", selected.getNotation(capture, moveType, oldX, oldY));
                                    }
                                    whiteTurn = !whiteTurn;
                                    player = !player;
                                    return returnText;
                                }
                            }
                        }
                    }
                }
            }
        }
        return returnText;
    }
}
