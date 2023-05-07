
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


public class SortingController {

    @FXML
    VBox root;
    @FXML
    HBox elementBox;
    @FXML
    Button randomizeButton;
    @FXML
    TextField delayTextField;
    @FXML
    Button completeRunButton;
    @FXML
    Button singlePassButton;
    @FXML
    Button singleStepButton;
    @FXML
    Button sortChangeButton;
    @FXML
    Label finishMessage;


    /**
     * Changes the colour of rectangle objects to green.
     * 
     * @param rect1 -- rectangle object, its fill colour is changed to green
     * @param rect2 -- rectangle object, its fil colour is changed to green.
     * @param delay -- represents the base number of milliseconds after the animation
     * starts before this keyframe occurs.
     * @param timelineCount -- multiplier for the delay variable to put keyframes
     * in a sequential order along a timeline --> events not all happening at once
     * @return -- KeyFrame object for changing the rectangles' colours to green.
     */
    private KeyFrame changeToGreen(Rectangle rect1, Rectangle rect2, int delay, int timelineCount) {

        return new KeyFrame(Duration.millis(timelineCount * delay), (ActionEvent event) -> {
            rect1.setFill(Color.GREEN);
            rect2.setFill(Color.GREEN);
        });
    }

    /**
     * 
     * Changes the colour of rectangle objects to blue.
     * 
     * @param rect1 -- rectangle object, its fill colour is changed to blue
     * @param rect2 -- rectangle object, its fill colour is changed to blue
     * @param delay -- represents the base number of milliseconds after the animation
     * starts before this KeyFrame occurs
     * @param timelineCount -- multiplier for the delay variable to put keyframes
     * in a sequential order along a timeline --> events not all happening at once
     * @return -- KeyFrame object for changing the rectangles' colours to blue.
     */
    private KeyFrame changeToBlue(Rectangle rect1, Rectangle rect2, int delay, int timelineCount) {

        return new KeyFrame(Duration.millis(timelineCount * delay), (ActionEvent event) -> {
            rect1.setFill(Color.BLUE);
            rect2.setFill(Color.BLUE);
        });

    }

    /**
     * Swaps the heights of two rectangles if they are unsorted. Makes the rectangles
     * arrange themselves in ascending order
     * 
     * @param rect1 -- rectangle object
     * @param rect2 -- rectangle object
     * @param delay -- base number of milliseconds after the animation starts before 
     * this KeyFrame occurs
     * @param timelineCount -- multiplier for the delay variable to put keyframes
     * in a sequential order along a timeline --> events not all happening at once
     * @return -- KeyFrame object for the swapping of the heights of two rectangles
     */
    private KeyFrame swapShape(Rectangle rect1, Rectangle rect2, int delay, int timelineCount) {

        return new KeyFrame(Duration.millis(timelineCount * delay), (ActionEvent event) -> {
            if (rect2.getHeight() < rect1.getHeight()) {
                double rect1Height = rect1.getHeight();
                double rect2Height = rect2.getHeight();
                rect1.setHeight(rect2Height);
                rect2.setHeight(rect1Height);
            }
        });
    }

    /**
     * Disables a button during an animation, at a specified time after the animation
     * starts.
     * 
     * @param button -- button to be disabled
     * @param delay -- base number of milliseconds after the animation starts before 
     * this KeyFrame occurs
     * @param timelineCount -- multiplier for the delay variable to put keyframes
     * in a sequential order along a timeline --> events not all happening at once
     * @return -- KeyFrame object for disabling a button during an animation
     */
    private KeyFrame disableButton(Button button, int delay, int timelineCount) {

        return new KeyFrame(Duration.millis(delay * timelineCount), (ActionEvent event) -> {
            button.setDisable(true);
        });
    }

    /**
     * Enables a button during an animation, at a specified time after the animation
     * starts.
     * 
     * @param button -- button to be enabled
     * @param delay -- base number of milliseconds after the animation starts before 
     * this KeyFrame occurs
     * @param timelineCount -- multiplier for the delay variable to put keyframes
     * in a sequential order along a timeline --> events not all happening at once
     * @return -- KeyFrame object for enabling a button during an animation
     */
    private KeyFrame enableButton(Button button, int delay, int timelineCount) {

        return new KeyFrame(Duration.millis(delay * timelineCount), (ActionEvent event) -> {
            button.setDisable(false);
        });
    }


    /** 
     * Main logic for the bubble sort. Creates KeyFrames for highlighting adjacent 
     * rectangles that need to be swapped, swapping their heights, and changing 
     * their colours back to blue.
     * 
     * Adds the KeyFrames to a timeline representing the entire animation of the 
     * bubble sort.
     * 
     * @param animationTimeline -- timeline for the animation of the bubble sort
     * @param rectangles -- list of nodes which represent the rectangles to be sorted
     * @param delay -- base number of milliseconds after the animation starts before 
     * this KeyFrame occurs
     * @param timelineCount -- multiplier for the delay variable to put keyframes
     * in a sequential order along a timeline --> events not all happening at once
     */
    private void bubbleSortLogic(Timeline animationTimeline, ObservableList<Node> rectangles, int delay, int timelineCount) {
        for (int i = 0; i < rectangles.size() - 1; i++) {

            Rectangle rect1 = (Rectangle) rectangles.get(i);
            Rectangle rect2 = (Rectangle) rectangles.get(i + 1);

            timelineCount++;
            animationTimeline.getKeyFrames().add(changeToGreen(rect1, rect2, delay, timelineCount));

            timelineCount++;
            animationTimeline.getKeyFrames().add(swapShape(rect1, rect2, delay, timelineCount));

            timelineCount++;
            animationTimeline.getKeyFrames().add(changeToBlue(rect1, rect2, delay, timelineCount));
        }

    }

    /**
     * Animates the transition to the double-list sort UI from the bubble sort UI.
     * Method loads the "SecondSortingView" fxml file and sets it to be the root of the
     * scene, starting to the right of the scene graph
     * 
     * Timeline animates the leftward transition of the new sorting view until it is
     * fully visible in the scene graph.
     * 
     * @throws IOException
     */
    @FXML
    private void changingSortSlideIn() throws IOException {
        Parent mainParent = App.loadFXML("SecondSortingView");
        Scene scene = sortChangeButton.getScene();

        mainParent.translateXProperty().set(scene.getWidth());
        scene.setRoot(mainParent);

        Timeline animationTimeline = new Timeline();
        KeyValue decreaseX = new KeyValue(mainParent.translateXProperty(), 0, Interpolator.EASE_IN);
        KeyFrame decreaseXFrame = new KeyFrame(Duration.millis(1000), decreaseX);
        animationTimeline.getKeyFrames().add(decreaseXFrame);
        animationTimeline.play();
    }


    /**
     * sets the delay of a KeyFrame within an animation to the value entered by the user
     * in the UI.
     * Numerical values entered by the user are in units of milliseconds.
     * If the user enters an invalid value (ex. alphabets/symbols), the method sets
     * the delay variable to a default value of 200.
     */
    @FXML
    private int setDelay() {
        String text = delayTextField.getText();
        int delay = 0;
        root.requestFocus();

        try {
            delay = Integer.parseInt(text);
        } catch (Exception exception) {
            delay = 200;
        }
        return delay;
    }

    /**
     * Initialize method is called immediately after fxml file and all its
     * elements are loaded.
     * Prepares the elements for the visualization.
     */
    @FXML
    public void initialize() {
        ObservableList<Node> rectangles = elementBox.getChildren();
        finishMessage.setVisible(false);
        for (int i = 0; i < rectangles.size(); i++) {
            Rectangle temp = (Rectangle) rectangles.get(i);
            temp.setFill(Color.BLUE);
        }
        randomize();
    }

    /**
     * Randomizes the rectangular elements of the sorting visualizer.
     * Initial conditions of the visualizer before the sort occurs.
     */
    @FXML
    private void randomize() {
        Random rand = new Random();
        Timeline animationTimeline = new Timeline();
        int delay = setDelay();
        int timelineCount = 0;
        ObservableList<Node> rectangles = elementBox.getChildren();
        finishMessage.setVisible(false);

        for (int i = 0; i < rectangles.size(); i++) {

            int num1 = rand.nextInt(rectangles.size());
            int num2 = rand.nextInt(rectangles.size());

            if (num2 == num1 && num1 > 5) {
                num2 = rand.nextInt(6);
            } else if (num2 == num1 && num1 <= 5) {
                num2 = ThreadLocalRandom.current().nextInt(6, rectangles.size());
            }

            Rectangle rect1 = (Rectangle) rectangles.get(num1);
            Rectangle rect2 = (Rectangle) rectangles.get(num2);

            timelineCount++;
            animationTimeline.getKeyFrames().add(changeToGreen(rect1, rect2, delay, timelineCount));

            timelineCount++;
            KeyFrame randomizeRect = new KeyFrame(Duration.millis(delay * timelineCount), (ActionEvent event) -> {
                double rect1Height = rect1.getHeight();
                double rect2Height = rect2.getHeight();
                rect1.setHeight(rect2Height);
                rect2.setHeight(rect1Height);
            });
            animationTimeline.getKeyFrames().add(randomizeRect);

            timelineCount++;
            animationTimeline.getKeyFrames().add(changeToBlue(rect1, rect2, delay, timelineCount));

        }
        Timeline buttonTimeline = new Timeline();

        buttonTimeline.getKeyFrames().add(disableButton(randomizeButton, 0, 0));
        buttonTimeline.getKeyFrames().add(enableButton(randomizeButton, delay, timelineCount));
        buttonTimeline.getKeyFrames().add(disableButton(singlePassButton, 0, 0));
        buttonTimeline.getKeyFrames().add(enableButton(singlePassButton, delay, timelineCount));
        buttonTimeline.getKeyFrames().add(disableButton(singleStepButton, 0, 0));
        buttonTimeline.getKeyFrames().add(enableButton(singleStepButton, delay, timelineCount));
        buttonTimeline.getKeyFrames().add(disableButton(completeRunButton, 0, 0));
        buttonTimeline.getKeyFrames().add(enableButton(completeRunButton, delay, timelineCount));

        buttonTimeline.play();
        animationTimeline.play();

    }

    /**
     * Performs the bubble sort algorithm until the list of rectangles is fully sorted 
     * and arranged in ascending order.
     * 
     * Iterates through the unsorted list and swaps the heights of two adjacent 
     * rectangles if they are not arranged in ascending order.
     * 
     * When the iteration and animation is complete, the method checks to see if 
     * any rectangles are still unsorted. If so, the method runs itself again to 
     * animate the sorting of the remaining unsorted rectangles.
     */
    @FXML
    private void bubbleSortCompleteRun() {
        ObservableList<Node> rectangles = elementBox.getChildren();
        Timeline animationTimeline = new Timeline();
        Timeline buttonTimeline = new Timeline();
        int delay = setDelay();
        int timelineCount = 0;

        bubbleSortLogic(animationTimeline, rectangles, delay, timelineCount);

        buttonTimeline.getKeyFrames().add(disableButton(randomizeButton, 0, 0));
        buttonTimeline.getKeyFrames().add(enableButton(randomizeButton, delay, timelineCount));
        buttonTimeline.play();
        animationTimeline.play();

        animationTimeline.setOnFinished((ActionEvent event) -> {
            boolean runAgain = false;
            for (int i = 0; i < rectangles.size()-1; i ++) {
                if (((Rectangle) rectangles.get(i)).getHeight() > ((Rectangle) rectangles.get(i+1)).getHeight()) {
                    runAgain = true;
                    break;
                }
            }
            if (runAgain) {
                bubbleSortCompleteRun();
            }
            else {
                singlePassButton.setDisable(true);
                singleStepButton.setDisable(true);
                completeRunButton.setDisable(true);
                finishMessage.setVisible(true);
            }

        });

    }

    /**
     * Performs a single pass of the bubble sort. 
     * Performs one full iteration through the list of rectangles, swapping the heights
     * of two adjacent rectangles if they are not in ascending order.
     */
    @FXML
    private void bubbleSortSinglePass() {
        ObservableList<Node> rectangles = elementBox.getChildren();
        Timeline animationTimeline = new Timeline();
        Timeline buttonTimeline = new Timeline();
        int delay = setDelay();
        int timelineCount = 0;
        
        bubbleSortLogic(animationTimeline, rectangles, delay, timelineCount);

        buttonTimeline.getKeyFrames().add(disableButton(randomizeButton, 0, 0));
        buttonTimeline.getKeyFrames().add(enableButton(randomizeButton, delay, timelineCount));
        buttonTimeline.play();
        animationTimeline.play();

        animationTimeline.setOnFinished((ActionEvent event) -> {
            boolean sorted = true;
            for (int i = 0; i < rectangles.size() - 1; i++) {
                if (((Rectangle) rectangles.get(i)).getHeight() > ((Rectangle) rectangles.get(i + 1)).getHeight()) {
                    sorted = false;
                    break;
                }
            }
            if (sorted) {
                singlePassButton.setDisable(true);
                singleStepButton.setDisable(true);
                completeRunButton.setDisable(true);
                finishMessage.setVisible(true);
            }

        });

    }

    /**
     * Performs a single step of the bubble sort
     * Finds the first pair of unsorted adjacent rectangles and swaps their heights
     * to put them in ascending order.
     * Exits the iteration once a singlular pair of shapes has been swapped.
     */
    @FXML
    private void bubbleSortSingleStep() {
        ObservableList<Node> rectangles = elementBox.getChildren();
        Timeline animationTimeline = new Timeline();
        Timeline buttonTimeline = new Timeline();
        int timelineCount = 0;
        int delay = setDelay();

        for (int i = 0; i < rectangles.size() - 1; i++) {
            Rectangle rect1 = (Rectangle) rectangles.get(i);
            Rectangle rect2 = (Rectangle) rectangles.get(i + 1);
            boolean shapeSwapped = false;

            timelineCount++;
            animationTimeline.getKeyFrames().add(changeToGreen(rect1, rect2, delay, timelineCount));

            timelineCount++;
            if (rect2.getHeight() < rect1.getHeight()) {
                shapeSwapped = true;
                animationTimeline.getKeyFrames().add(swapShape(rect1, rect2, delay, timelineCount));
            }

            timelineCount++;
            animationTimeline.getKeyFrames().add(changeToBlue(rect1, rect2, delay, timelineCount));

            if (shapeSwapped) {
                break;
            }

        }
        buttonTimeline.getKeyFrames().add(disableButton(randomizeButton, 0, 0));
        buttonTimeline.getKeyFrames().add(enableButton(randomizeButton, delay, timelineCount));
        buttonTimeline.play();
        animationTimeline.play();

        animationTimeline.setOnFinished((ActionEvent event) -> {
            boolean sorted = true;
            for (int i = 0; i < rectangles.size() - 1; i++) {
                if (((Rectangle) rectangles.get(i)).getHeight() > ((Rectangle) rectangles.get(i + 1)).getHeight()) {
                    sorted = false;
                    break;
                }
            }
            if (sorted) {
                singlePassButton.setDisable(true);
                singleStepButton.setDisable(true);
                completeRunButton.setDisable(true);
                finishMessage.setVisible(true);
            }

        });
    }

}
