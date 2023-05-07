
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    public static Scene scene;


    /**
     * Loads a specific fxml file and assigns this file to the current scene.
     * Sets the scene to the stage and displays the window.
     */
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("WelcomeScreen"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * method to change the fxml file that is associated with the scene
     * 
     * @param fxml -- name of fxml file to load
     * @throws IOException
     */
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }


    /**
     * Passes fxml file to FXMLLoader class
     * 
     * @param fxml -- name of fxml file to load
     * @return -- parses the file and finds out the necessary FXML elements that
     *         are needed.
     * @throws IOException
     */
    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * Program looks for main() method to call launch() method, which causes the
     * start() method to be called.
     * 
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }

}