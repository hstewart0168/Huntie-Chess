package Chess;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
    public void start(Stage stage) throws Exception{

        Text text = new Text(512,288,"Chess to Be Added in Future Update");
        Group root = new Group(text);

        Scene scene = new Scene(root, 1024, 576);
        scene.setFill(Color.BEIGE);

        stage.setTitle("Huntie Chess");
        stage.setMinHeight(576);
        stage.setMinWidth(1024);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
