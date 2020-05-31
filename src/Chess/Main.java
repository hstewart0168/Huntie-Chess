package Chess;

import com.sun.javafx.css.StyleCacheEntry;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;

public class Main extends Application {

    public void start(Stage stage) throws Exception{
        Selector selector = new Selector();
        selector.setWidth(32);
        selector.setHeight(32);
        selector.setFill(Color.LIME);
        selector.setOpacity(.5);

        GridPane pane = new GridPane();
        for (int x = 0; x < Board.board.length; x++){
            for(int y = 0; y <Board.board[x].length; y++) {
                if(Board.board[x][y] != null) {
                    pane.add(new ImageView(Board.board[x][y].getImage()), x, y, 1, 1);
                }
                else{
                    pane.add(new ImageView(new Image(new FileInputStream(new File("src\\Chess\\Sprites\\blankSpace.png")))), x, y, 1, 1);
                }
            }
        }

        GridPane backPane = new GridPane();
        for (int x = 0; x < Board.board.length; x++){
            for(int y = 0; y <Board.board[x].length; y++) {
                if(x % 2 == 0 && y % 2 != 0){
                    backPane.add(new ImageView(new Image(new FileInputStream(new File("src\\Chess\\Sprites\\whiteTile.png")))), y, x, 1, 1);
                }
                else if(x % 2 != 0 && y % 2 != 0){
                    backPane.add(new ImageView(new Image(new FileInputStream(new File("src\\Chess\\Sprites\\greenTile.png")))), y, x, 1, 1);
                }
                else if(x % 2 == 0 && y % 2 == 0){
                    backPane.add(new ImageView(new Image(new FileInputStream(new File("src\\Chess\\Sprites\\greenTile.png")))), y, x, 1, 1);
                }
                else if(x % 2 != 0 && y % 2 == 0){
                    backPane.add(new ImageView(new Image(new FileInputStream(new File("src\\Chess\\Sprites\\whiteTile.png")))), y, x, 1, 1);
                }
            }
        }

        Group root = new Group(backPane, pane, selector);

        Scene scene = new Scene(root, 256, 256);
        scene.setFill(Color.BEIGE);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
            public void handle(KeyEvent event) {
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
            }
        });

        stage.setResizable(false);
        stage.setTitle("Huntie Chess");
        stage.getIcons().add(new Image(new FileInputStream(new File("src\\Chess\\Sprites\\blackPawn.png"))));
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        Board.initializeBoard();
        launch(args);
    }
}
