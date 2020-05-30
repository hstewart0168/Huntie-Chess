package sample;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
    public void start(Stage stage) throws Exception{

        Text text = new Text(50,150,"Chess to Be Added in Future Update");
        Group root = new Group(text);

        Scene scene = new Scene(root, 300, 275);
        scene.setFill(Color.BEIGE);

        stage.setTitle("Huntie Chess");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
