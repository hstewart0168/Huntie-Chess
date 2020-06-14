package Chess;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main extends Application {
    public void start(Stage stage) throws Exception{

        AtomicBoolean wCheckmate = new AtomicBoolean(false);
        AtomicBoolean bCheckmate = new AtomicBoolean(false);

        Selector selector = new Selector();
        selector.setWidth(32);
        selector.setHeight(32);
        selector.setFill(Color.LIME);
        selector.setOpacity(.5);

        for (int x = 0; x < Board.board.length; x++){
            for(int y = 0; y <Board.board[x].length; y++) {
                if(Board.board[x][y] != null) {
                    pane.add(new ImageView(Board.board[x][y].getImage()), x, y, 1, 1);
                }
                else{
                    pane.add(new ImageView(new Image(new FileInputStream(new File("Sprites\\blankSpace.png")))), y, x, 1, 1);
                }
            }
        }


        whiteKing = Board.board[0][4];
        blackKing = Board.board[7][4];

        GridPane backPane = new GridPane();
        for (int x = 0; x < Board.board.length; x++){
            for(int y = 0; y <Board.board[x].length; y++) {
                if(x % 2 == 0 && y % 2 != 0){
                    backPane.add(new ImageView(new Image(new FileInputStream(new File("Sprites\\whiteTile.png")))), x, y, 1, 1);
                }
                else if(x % 2 != 0 && y % 2 != 0){
                    backPane.add(new ImageView(new Image(new FileInputStream(new File("Sprites\\greenTile.png")))), x, y, 1, 1);
                }
                else if(x % 2 == 0){
                    backPane.add(new ImageView(new Image(new FileInputStream(new File("Sprites\\greenTile.png")))), x, y, 1, 1);
                }
                else {
                    backPane.add(new ImageView(new Image(new FileInputStream(new File("Sprites\\whiteTile.png")))), x, y, 1, 1);
                }
            }
        }

        Group root = new Group(backPane, pane, selector, overlay);

        Scene scene = new Scene(root, 256, 256);
        scene.setFill(Color.BEIGE);

        scene.setOnKeyPressed((KeyEvent event) -> {
            if(event.getCode() == KeyCode.D && selector.getArrayX() < 7){
                selector.addXCoord(32);
                selector.setTranslateX(selector.getXCoord());
            }
            else if(event.getCode() == KeyCode.W && selector.getArrayY() > 0){
                selector.addYCoord(-32);
                selector.setTranslateY(selector.getYCoord());
            }
            else if(event.getCode() == KeyCode.A && selector.getArrayX() > 0){
                selector.addXCoord(-32);
                selector.setTranslateX(selector.getXCoord());
            }
            else if(event.getCode() == KeyCode.S && selector.getArrayY() < 7){
                selector.addYCoord(32);
                selector.setTranslateY(selector.getYCoord());
            }
            else if(event.getCode() == KeyCode.SPACE && !spacePressed){
                if(Board.board[(int)selector.getArrayX()][(int)selector.getArrayY()] != null && Board.board[(int)selector.getArrayX()][(int)selector.getArrayY()].getState() != whiteTurn) {
                    if (whiteKing.inCheck() && whiteTurn) {
                        if (Board.board[(int) selector.getArrayX()][(int) selector.getArrayY()] == whiteKing){
                            selector.setFill(Color.LIGHTBLUE);
                            for (int x = 0; x < Board.board.length; x++) {
                                for (int y = 0; y < Board.board[x].length; y++) {
                                    if (Board.board[(int) selector.getArrayX()][(int) selector.getArrayY()].checkEligibility(x, y, true, false)) {
                                        Rectangle rectangle = new Rectangle(32, 32);
                                        rectangle.setTranslateX(x * 32);
                                        rectangle.setTranslateY(y * 32);
                                        rectangle.setFill(Color.DARKRED);
                                        rectangle.setOpacity(.5);
                                        overlay.getChildren().add(rectangle);
                                    }
                                }
                            }
                            selected = Board.board[(int) selector.getArrayX()][(int) selector.getArrayY()];
                            spacePressed = true;
                        }
                        Piece checker = null;
                        for(Piece piece : whiteKing.checkingPieces){
                            piece.inCheck();
                            checker = piece;
                        }
                        if(checker != null && checker.getCheckingPieces().contains(Board.board[(int) selector.getArrayX()][(int) selector.getArrayY()])){
                            selector.setFill(Color.LIGHTBLUE);
                            for (int x = 0; x < Board.board.length; x++) {
                                for (int y = 0; y < Board.board[x].length; y++) {
                                    if (Board.board[(int) selector.getArrayX()][(int) selector.getArrayY()].checkEligibility(x, y, true, false) && Board.board[x][y] == checker) {
                                        Rectangle rectangle = new Rectangle(32, 32);
                                        rectangle.setTranslateX(x * 32);
                                        rectangle.setTranslateY(y * 32);
                                        rectangle.setFill(Color.DARKRED);
                                        rectangle.setOpacity(.5);
                                        overlay.getChildren().add(rectangle);
                                    }
                                }
                            }
                            selected = Board.board[(int) selector.getArrayX()][(int) selector.getArrayY()];
                            spacePressed = true;
                        }
                        else{
                            wCheckmate.set(true);
                        }
                    }
                    else if (blackKing.inCheck() && !whiteTurn) {
                        if (Board.board[(int) selector.getArrayX()][(int) selector.getArrayY()].getType().equals("king")) {
                            selector.setFill(Color.LIGHTBLUE);
                            for (int x = 0; x < Board.board.length; x++) {
                                for (int y = 0; y < Board.board[x].length; y++) {
                                    if (Board.board[(int) selector.getArrayX()][(int) selector.getArrayY()].checkEligibility(x, y, true, false)) {
                                        Rectangle rectangle = new Rectangle(32, 32);
                                        rectangle.setTranslateX(x * 32);
                                        rectangle.setTranslateY(y * 32);
                                        rectangle.setFill(Color.DARKRED);
                                        rectangle.setOpacity(.5);
                                        overlay.getChildren().add(rectangle);
                                    }
                                }
                            }
                            selected = Board.board[(int) selector.getArrayX()][(int) selector.getArrayY()];
                            spacePressed = true;
                        }
                        Piece checker = null;
                        for(Piece piece : blackKing.checkingPieces){
                            piece.inCheck();
                            checker = piece;
                        }
                        if(checker != null && checker.getCheckingPieces().contains(Board.board[(int) selector.getArrayX()][(int) selector.getArrayY()])){
                            selector.setFill(Color.LIGHTBLUE);
                            for (int x = 0; x < Board.board.length; x++) {
                                for (int y = 0; y < Board.board[x].length; y++) {
                                    if (Board.board[(int) selector.getArrayX()][(int) selector.getArrayY()].checkEligibility(x, y, true, false) && Board.board[x][y] == checker) {
                                        Rectangle rectangle = new Rectangle(32, 32);
                                        rectangle.setTranslateX(x * 32);
                                        rectangle.setTranslateY(y * 32);
                                        rectangle.setFill(Color.DARKRED);
                                        rectangle.setOpacity(.5);
                                        overlay.getChildren().add(rectangle);
                                    }
                                }
                            }
                            selected = Board.board[(int) selector.getArrayX()][(int) selector.getArrayY()];
                            spacePressed = true;
                        }
                        else{
                            bCheckmate.set(true);
                        }
                    }
                    else{
                        selector.setFill(Color.LIGHTBLUE);
                        for (int x = 0; x < Board.board.length; x++) {
                            for (int y = 0; y < Board.board[x].length; y++) {
                                if (Board.board[(int) selector.getArrayX()][(int) selector.getArrayY()].checkEligibility(x, y, true, false)) {
                                    Rectangle rectangle = new Rectangle(32, 32);
                                    rectangle.setTranslateX(x * 32);
                                    rectangle.setTranslateY(y * 32);
                                    rectangle.setFill(Color.DARKRED);
                                    rectangle.setOpacity(.5);
                                    overlay.getChildren().add(rectangle);
                                }
                            }
                        }
                        selected = Board.board[(int) selector.getArrayX()][(int) selector.getArrayY()];
                        spacePressed = true;
                    }
                }
            }
            else if(event.getCode() == KeyCode.SPACE) {
                root.getChildren().remove(overlay);
                overlay = new Group();
                root.getChildren().add(overlay);
                spacePressed = false;
                selector.setFill(Color.LIME);
                if(selected.checkEligibility((int)selector.getArrayX(), (int)selector.getArrayY(), true, false)){
                    System.out.println("valid move");
                    selected.move((int)selector.getArrayX(), (int)selector.getArrayY(), false);
                    root.getChildren().remove(pane);
                    pane = new GridPane();
                    for (int x = 0; x < Board.board.length; x++){
                        for(int y = 0; y <Board.board[x].length; y++) {
                            if(Board.board[x][y] != null) {
                                try {
                                    pane.add(new ImageView(Board.board[x][y].getImage()), x, y, 1, 1);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                            else{
                                try {
                                    pane.add(new ImageView(new Image(new FileInputStream(new File("Sprites\\blankSpace.png")))), x, y, 1, 1);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    root.getChildren().add(pane);
                    whiteTurn = !whiteTurn;
                }
                else{
                    System.out.println("invalid move");
                }
            }
        });

        stage.setResizable(false);
        stage.setTitle("Huntie Chess");
        stage.getIcons().add(new Image(new FileInputStream(new File("Sprites\\blackPawn.png"))));
        stage.setScene(scene);
        stage.show();

        Runnable runnable = new Runnable() {
            public void run() {
                while (!wCheckmate.get() && !bCheckmate.get()) {
                    try {
                        gameThread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("game end");
            }
        };

        gameThread = new Thread(runnable);

        gameThread.start();
    }


    public static void main(String[] args) {
        Board.initializeBoard();
        launch(args);
        gameThread.stop();
    }

    private static boolean spacePressed = false;
    private static Group overlay = new Group();
    private static Piece selected;
    private static GridPane pane = new GridPane();
    public static boolean whiteTurn = true;
    private static Thread gameThread;
    public static Piece whiteKing;
    public static Piece blackKing;
}
