import java.io.IOException;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.util.Duration;


public class WelcomeController {

    @FXML
    Button doubleListSortButton;
    @FXML
    Button bubbleSortButton;


    /**
     * Sets the root of the scene graph to be one of the sorting UIs.
     * Places the parent at the bottom of the scene graph such that it is not initially
     * visible.
     * Timeline animates the sliding transition of the root upwards until it is 
     * fully visible in the scene graph.
     * 
     * @param fxml -- string representing the fxml file to be loaded
     * @throws IOException
     */
    private void sortingViewSlideIn(String fxml) throws IOException {
        Parent mainBox = App.loadFXML(fxml);
        Scene scene = bubbleSortButton.getScene();

        mainBox.translateYProperty().set(scene.getHeight());
        scene.setRoot(mainBox);

        Timeline animationTimeline = new Timeline();
        KeyValue decreaseY = new KeyValue(mainBox.translateYProperty(), 0, Interpolator.EASE_IN);
        KeyFrame decreaseYFrame = new KeyFrame(Duration.millis(1000), decreaseY);
        animationTimeline.getKeyFrames().add(decreaseYFrame);
        animationTimeline.play();

    }

     /**
      * Changes the fxml file associated with the scene to the "SortingView" fxml
      * file. The scene proceeds to show the UI of the bubble sort. 
      * This method runs when the user selects "Bubble Sort" in the 
      * welcome screen.
      * 
      * @throws IOException
      */
    @FXML
    private void changeToBubbleSort() throws IOException {
        sortingViewSlideIn("SortingView");
    }

    /**
     * Changes the fxml file associated with the scene to the "SecondSortingView"
     * fxml file. The scene proceeds to show the UI of the double-list sort. 
     * This method runs when the user selects "Double List Sort" in the welcome
     * screen.
     * 
     * @throws IOException
     */
    @FXML
    private void changeToSecondSort() throws IOException {
        sortingViewSlideIn("SecondSortingView");
    }
    
}
