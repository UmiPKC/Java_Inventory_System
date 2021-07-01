package Model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/mainScreen.fxml"));
        //Scene
        Scene mainScene = new Scene(root);
        primaryStage.setTitle("");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    /**
     * Launches application.
     * <p><<b>Javadocs contained in separate, attached ZIP file.</b></p>
     * <p><b>FUTURE FEATURE</b></p>
     * A new feature that would improve this application would be the ability to save the current configuration onto a separate file.
     * This file could then be loaded and the application would fill in all the tables as specified by the file.
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
