package Chess;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main extends Application {
    public void start(Stage stage) throws Exception{

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
                    pane.add(new ImageView(new Image(new FileInputStream(new File("Sprites\\blankSpace.png")))), x, y, 1, 1);
                }
            }
        }

        GridPane backPane = new GridPane();
        for (int x = 0; x < Board.board.length; x++){
            for(int y = 0; y <Board.board[x].length; y++) {
                if(x % 2 == 0 && y % 2 != 0){
                    backPane.add(new ImageView(new Image(new FileInputStream(new File("Sprites\\whiteTile.png")))), y, x, 1, 1);
                }
                else if(x % 2 != 0 && y % 2 != 0){
                    backPane.add(new ImageView(new Image(new FileInputStream(new File("Sprites\\greenTile.png")))), y, x, 1, 1);
                }
                else if(x % 2 == 0){
                    backPane.add(new ImageView(new Image(new FileInputStream(new File("Sprites\\greenTile.png")))), y, x, 1, 1);
                }
                else {
                    backPane.add(new ImageView(new Image(new FileInputStream(new File("Sprites\\whiteTile.png")))), y, x, 1, 1);
                }
            }
        }

        Group root = new Group(backPane, pane, selector, overlay);

        Scene scene = new Scene(root, 256, 256);
        scene.setFill(Color.BEIGE);

        scene.setOnKeyPressed(event -> {
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
                if(Board.board[(int)selector.getArrayX()][(int)selector.getArrayY()] != null) {
                    selector.setFill(Color.LIGHTBLUE);
                    for(int x = 0; x < Board.board.length; x++){
                        for(int y = 0; y < Board.board[x].length; y++){
                            if(Board.board[(int)selector.getArrayX()][(int)selector.getArrayY()].checkEligibility(x, y)){
                                Rectangle rectangle = new Rectangle(32,32);
                                rectangle.setTranslateX(x * 32);
                                rectangle.setTranslateY(y * 32);
                                rectangle.setFill(Color.DARKRED);
                                rectangle.setOpacity(.5);
                                overlay.getChildren().add(rectangle);
                            }
                        }
                    }
                    selected = Board.board[(int)selector.getArrayX()][(int)selector.getArrayY()];
                    spacePressed = true;
                }
            }
            else if(event.getCode() == KeyCode.SPACE) {
                root.getChildren().remove(overlay);
                overlay = new Group();
                root.getChildren().add(overlay);
                spacePressed = false;
                selector.setFill(Color.LIME);
                if(selected.checkEligibility((int)selector.getArrayX(), (int)selector.getArrayY())){
                    System.out.println("valid move");
                    selected.move((int)selector.getArrayX(), (int)selector.getArrayY());
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
    }


    public static void main(String[] args) {
        Board.initializeBoard();
        launch(args);
    }

    private static boolean spacePressed = false;
    private static Group overlay = new Group();
    private static Piece selected;
    private static GridPane pane = new GridPane();

}
