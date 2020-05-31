package Chess;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;

public class Main extends Application {
    public void start(Stage stage) throws Exception{

        GridPane pane = new GridPane();
        for (int x = 0; x < Board.board.length; x++){
            for(int y = 0; y <Board.board[x].length; y++) {
                if(Board.board[x][y] != null) {
                    pane.add(new ImageView(Board.board[x][y].getImage()), y, x, 1, 1);
                }
                else{
                    pane.add(new ImageView(new Image(new FileInputStream(new File("src\\Chess\\Sprites\\blankSpace.png")))), y, x, 1, 1);
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

        Group root = new Group(backPane, pane);

        Scene scene = new Scene(root, 256, 256);
        scene.setFill(Color.BEIGE);

        stage.setResizable(false);
        stage.setTitle("Huntie Chess");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        Board.initializeBoard();
        launch(args);
    }
}
