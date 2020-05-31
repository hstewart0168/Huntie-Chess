package Chess;

public class Board {
    public static Piece[][] board = new Piece[8][8];

    public void initializeBoard(){
        for(int i = 0; i < board.length; i++){
            if(i == 0 || i == 7){
                for(int j = 0; j < board[i].length ; j++){
                    if(j == 0 || j == 7){
                        board[i][j] = new Piece("pawn", i, j);
                    }
                    else if(j == 1 || j == 6){
                        board[i][j] = new Piece("knight", i, j);
                    }
                    else if(j == 2 || j == 5){
                        board[i][j] = new Piece("bishop", i, j);
                    }
                    else if(j == 3){
                        board[i][j] = new Piece("queen", i, j);
                    }
                    else if(j == 4){
                        board[i][j] = new Piece("king", i, j);
                    }
                }
            }
            else if(i == 1 || i == 6){
                for(int j = 0; j <  board[i].length; j++){
                    board[i][j] = new Piece("pawn", i, j);
                }
            }
        }
    }
}
