package Chess;

import javafx.application.Application;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Border;
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

        TextArea textStream = new TextArea();
        textStream.setLayoutX(32);
        textStream.setLayoutY(384);
        textStream.setMaxSize(448,64);

        textStream.setEditable(false);
        textStream.setDisable(true);
        textStream.setOpacity(1);

        Selector selector = new Selector();
        selector.setLayoutY(64);
        selector.setLayoutX(128);
        selector.setWidth(32);
        selector.setHeight(32);
        selector.setFill(Color.LIME);
        selector.setOpacity(.5);

        pane.setLayoutX(128);
        pane.setLayoutY(64);

        for (int x = 0; x < Board.board.length; x++){
            for(int y = 0; y <Board.board[x].length; y++) {
                if(Board.board[x][y] != null) {
                    pane.add(new ImageView(Board.board[x][y].getImage()), y, x, 1, 1);
                }
                else{
                    pane.add(new ImageView(new Image(new FileInputStream(new File("Sprites\\blankSpace.png")))), y, x, 1, 1);
                }
            }
        }


        whiteKing = Board.board[0][4];
        blackKing = Board.board[7][4];

        GridPane backPane = new GridPane();

        backPane.setLayoutX(128);
        backPane.setLayoutY(64);

        for (int x = 0; x < Board.board.length; x++){
            for(int y = 0; y <Board.board[x].length; y++) {
                if(x % 2 == 0 && y % 2 != 0){
                    backPane.add(new ImageView(new Image(new FileInputStream(new File("Sprites\\greenTile.png")))), x, y, 1, 1);
                }
                else if(x % 2 != 0 && y % 2 == 0){
                    backPane.add(new ImageView(new Image(new FileInputStream(new File("Sprites\\greenTile.png")))), x, y, 1, 1);
                }
                else if(x % 2 == 0 && y % 2 == 0){
                    backPane.add(new ImageView(new Image(new FileInputStream(new File("Sprites\\whiteTile.png")))), x, y, 1, 1);
                }
                else if(x % 2 != 0 && y % 2 != 0){
                    backPane.add(new ImageView(new Image(new FileInputStream(new File("Sprites\\whiteTile.png")))), x, y, 1, 1);
                }
            }
        }


        Group root = new Group(textStream, backPane, pane, selector, overlay);

        Scene scene = new Scene(root, 512, 512);
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
                if(Board.board[(int)selector.getArrayY()][(int)selector.getArrayX()] != null && Board.board[(int)selector.getArrayY()][(int)selector.getArrayX()].getState() != whiteTurn) {
                        selector.setFill(Color.LIGHTBLUE);
                        for (int x = 0; x < Board.board.length; x++) {
                            for (int y = 0; y < Board.board[x].length; y++) {
                                if (Board.board[(int) selector.getArrayY()][(int) selector.getArrayX()].checkEligibility(x, y, true, false)) {
                                    Rectangle rectangle = new Rectangle(32, 32);
                                    rectangle.setTranslateX(y * 32 + 128);
                                    rectangle.setTranslateY(x * 32 + 64);
                                    rectangle.setFill(Color.DARKRED);
                                    rectangle.setOpacity(.5);
                                    overlay.getChildren().add(rectangle);
                                }
                            }
                        }
                        selected = Board.board[(int) selector.getArrayY()][(int) selector.getArrayX()];
                        spacePressed = true;
                }
            }
            else if(event.getCode() == KeyCode.SPACE) {
                root.getChildren().remove(overlay);
                overlay = new Group();
                root.getChildren().add(overlay);
                spacePressed = false;
                selector.setFill(Color.LIME);
                if(selected.checkEligibility((int)selector.getArrayY(), (int)selector.getArrayX(), true, false)){
                    int oldX = selected.getLocX();
                    int oldY = selected.getLocY();
                    Piece capture = null;
                    System.out.println("valid move");
                    if(Board.board[(int) selector.getArrayY()][(int) selector.getArrayX()] != null){
                        capture = Board.board[(int) selector.getArrayY()][(int) selector.getArrayX()];
                    }
                    String moveType = selected.move((int)selector.getArrayY(), (int)selector.getArrayX(), false);
                    root.getChildren().remove(pane);
                    pane = new GridPane();
                    for (int x = 0; x < Board.board.length; x++){
                        for(int y = 0; y <Board.board[x].length; y++) {
                            if(Board.board[x][y] != null) {
                                try {
                                    pane.add(new ImageView(Board.board[x][y].getImage()), y, x, 1, 1);
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
                    pane.setLayoutX(128);
                    pane.setLayoutY(64);
                    textStream.appendText(String.format("  %d. %s", Piece.getTurn() ,selected.getNotation(capture, moveType, oldX, oldY)));
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
                boolean gameover = true;
                String winner = "bruh";
                while (gameover) {
                    gameover = false;
                    if(whiteTurn) {
                        for (int x = 0; x < Board.board.length; x++) {
                            for (int y = 0; y < Board.board[x].length; y++) {
                                if (Board.board[x][y] != null && !Board.board[x][y].getState()) {
                                    if(Board.board[x][y].canMove()){
                                        gameover = true;
                                        winner = "1/0";
                                    }
                                }
                            }
                        }
                    }
                    else {
                        for (int x = 0; x < Board.board.length; x++) {
                            for (int y = 0; y < Board.board[x].length; y++) {
                                if (Board.board[x][y] != null && Board.board[x][y].getState()) {
                                    if(Board.board[x][y].canMove()){
                                        gameover = true;
                                        winner = "0/1";
                                    }
                                }
                            }
                        }
                    }
                    try {
                        gameThread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                textStream.appendText(String.format("+ %s", winner));
                textStream.setDisable(false);
            }
        };

        gameThread = new Thread(runnable);

        gameThread.start();
    }


    public static void main(String[] args) {
        Board.initializeBoard();
        launch(args);
    }

    private static boolean spacePressed = false;
    private static Group overlay = new Group();
    private static Piece selected;
    private GridPane pane = new GridPane();
    public static boolean whiteTurn = true;
    private static Thread gameThread;
    public static Piece whiteKing;
    public static Piece blackKing;
}
