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

public class SortingController2 {

    @FXML
    VBox root;
    @FXML
    HBox elementBox;
    @FXML
    HBox elementBox2;
    @FXML
    Button randomizeButton;
    @FXML
    TextField delayTextField;
    @FXML
    Button singleStepButton;
    @FXML
    Button completeRunButton;
    @FXML
    Button sortChangeButton;
    @FXML
    Label finishMessage;


    /**
     * Changes the colour of retangle objects to green.
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
     * 
     * @param rect1 -- rectangle object to be moved down
     * @param rectList -- list of sorted rectangles in which rect1 is to be added
     * @param delay --  base number of milliseconds after the animation starts before 
     * this KeyFrame occurs
     * @param timelineCount -- multiplier for the delay variable to put keyframes
     * in a sequential order along a timeline --> events not all happening at once
     * @param position -- position within the sorted array in which rect1 will be placed.
     * It is an index number of rectList which will correspond to rect1
     * @return -- KeyFrame object for adding a rectangle to its sorted position in 
     * a list.
     */
    private KeyFrame moveDown(Rectangle rect1, ObservableList<Node> rectList, int delay, int timelineCount,
            int position) {
        if (position < 0) {
            return new KeyFrame(Duration.millis(delay * timelineCount), (ActionEvent event) -> {
                rectList.add(rect1);
            });
        } else {
            return new KeyFrame(Duration.millis(delay * timelineCount), (ActionEvent event) -> {
                rectList.add(position, rect1);
            });
        }
    }


    /**
     * Main logic for the double-list sort. Gets the first element of the unsorted
     * list and determines its placement in the sorted list.
     * 
     * Method compares the heights of the unsorted rectangle to the heights of sorted
     * rectangles. Rectangles are sorted in ascending order.
     * 
     * Creates KeyFrame objects for the placement of individual rectangles in the sorted list
     * and adds them to the animation timeline. -- multiplier for the delay variable to put keyframes
     * in a sequential order along a timeline --> events not all happening at once
     * 
     * @param animationTimeline -- timeline for the animation of the double-list sort
     * @param rectangles -- list of nodes representing the rectangles to be sorted
     * @param delay --  base number of milliseconds after the animation starts before 
     * this KeyFrame occurs
     * @param timelineCount -- multiplier for the delay variable to put keyframes
     * in a sequential order along a timeline --> events not all happening at once
     */
    private int doubleListSortLogic(Timeline animationTimeline, ObservableList<Node> rectangles, int delay, int timelineCount) {
        while (rectangles.size() > 0) {

            Rectangle unsortedRect = (Rectangle) rectangles.get(0);
            timelineCount++;
            animationTimeline.getKeyFrames().add(changeToGreen(unsortedRect, unsortedRect, delay, timelineCount));
            ObservableList<Node> rectangles2 = elementBox2.getChildren();
            for (int j = 0; j < rectangles2.size(); j++) {
                Rectangle sortedRect = (Rectangle) rectangles2.get(j);
                if (rectangles2.size() == 1) {
                    if (unsortedRect.getHeight() > sortedRect.getHeight()) {
                        timelineCount++;
                        animationTimeline.getKeyFrames()
                                .add(moveDown(unsortedRect, rectangles2, delay, timelineCount, -1));
                        timelineCount++;
                        animationTimeline.getKeyFrames()
                                .add(changeToBlue(unsortedRect, unsortedRect, delay, timelineCount));
                        break;
                    } else if (unsortedRect.getHeight() < sortedRect.getHeight()) {
                        timelineCount++;
                        animationTimeline.getKeyFrames()
                                .add(moveDown(unsortedRect, rectangles2, delay, timelineCount, 0));
                        timelineCount++;
                        animationTimeline.getKeyFrames()
                                .add(changeToBlue(unsortedRect, unsortedRect, delay, timelineCount));
                    } else {

                        break;
                    }
                } else {
                    if (j == 0 && unsortedRect.getHeight() < sortedRect.getHeight()) {
                        timelineCount++;
                        animationTimeline.getKeyFrames()
                                .add(moveDown(unsortedRect, rectangles2, delay, timelineCount, 0));
                        timelineCount++;
                        animationTimeline.getKeyFrames()
                                .add(changeToBlue(unsortedRect, unsortedRect, delay, timelineCount));

                        break;
                    } else if (j == rectangles2.size() - 1 && unsortedRect.getHeight() > sortedRect.getHeight()) {
                        timelineCount++;
                        animationTimeline.getKeyFrames()
                                .add(moveDown(unsortedRect, rectangles2, delay, timelineCount, -1));
                        timelineCount++;
                        animationTimeline.getKeyFrames()
                                .add(changeToBlue(unsortedRect, unsortedRect, delay, timelineCount));

                        break;
                    } else if (unsortedRect.getHeight() > sortedRect.getHeight()
                            && unsortedRect.getHeight() < ((Rectangle) rectangles2.get(j + 1)).getHeight()) {
                        timelineCount++;
                        animationTimeline.getKeyFrames()
                                .add(moveDown(unsortedRect, rectangles2, delay, timelineCount, j + 1));
                        timelineCount++;
                        animationTimeline.getKeyFrames()
                                .add(changeToBlue(unsortedRect, unsortedRect, delay, timelineCount));
                        break;
                    }

                }
            }

            break;
        }
        return timelineCount;
    }

    /**
     * Animates the transition to the bubble sort UI from the double-list sort UI.
     * Method loads the "SortingView" fxml file and sets it to be the root of
     * the scene, starting to the left of the scene graph
     * 
     * Timeline animates the rightward transition of the new sorting view until it is
     * fully visible in the scene graph.
     * 
     * @throws IOException
     */
    @FXML
    private void changingSortSlideIn() throws IOException {
        Parent mainParent = App.loadFXML("SortingView");
        Scene scene = sortChangeButton.getScene();

        mainParent.translateXProperty().set(-scene.getWidth());
        scene.setRoot(mainParent);

        Timeline animationTimeline = new Timeline();
        KeyValue increaseX = new KeyValue(mainParent.translateXProperty(), 0, Interpolator.EASE_IN);
        KeyFrame increaseXFrame = new KeyFrame(Duration.millis(1000), increaseX);
        animationTimeline.getKeyFrames().add(increaseXFrame);
        animationTimeline.play();
    }

    /**
     * sets the delay of a KeyFrame within an animation to the value entered by the
     * user in the UI.
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
        for (int i = 0; i < rectangles.size(); i++) {
            Rectangle temp = (Rectangle) rectangles.get(i);
            temp.setFill(Color.BLUE);
        }

        randomize();
    }

    /**
     * Randomizes the rectangular elements of the sorting visualizer.
     * Creates initial conditions of the visualizer before the sort occurs.
     */
    @FXML
    private void randomize() {
        Random rand = new Random();
        Timeline animationTimeline = new Timeline();
        Timeline buttonTimeline = new Timeline();
        int delay = setDelay();
        int timelineCount = 0;
        ObservableList<Node> rectangles = elementBox.getChildren();
        ObservableList<Node> rectangles2 = elementBox2.getChildren();
        finishMessage.setVisible(false);

        if (rectangles.size() < 13) {
            while (rectangles2.size() > 0) {
                rectangles.add(rectangles2.get(0));
            }
        }

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

        buttonTimeline.getKeyFrames().add(disableButton(randomizeButton, 0, 0));
        buttonTimeline.getKeyFrames().add(disableButton(completeRunButton, 0, 0));
        buttonTimeline.getKeyFrames().add(disableButton(singleStepButton, 0, 0));

        buttonTimeline.getKeyFrames().add(enableButton(randomizeButton, delay, timelineCount));
        buttonTimeline.getKeyFrames().add(enableButton(completeRunButton, delay, timelineCount));
        buttonTimeline.getKeyFrames().add(enableButton(singleStepButton, delay, timelineCount));

        buttonTimeline.play();
        animationTimeline.play();

    }

    /**
     * Performs the sorting algorithm until the rectangles are fully sorted.
     * One rectangle is added to the sorted list, and the rest of the rectangles
     * arrange themselves in the new list depending on their heights relative to the 
     * rectangle(s) in the sorted list. 
     * 
     * Algorithm finds the position in the sorted list where all the elements to the
     * left of the current rectangle are shorter, and all the elements to the right
     * of the current rectangle are taller.
     * 
     * The logic for a complete run is similar to the logic for a single step, but a 
     * complete run is a series of single-step animations until a sorted list is created.
     */
    @FXML
    private void doubleListSortCompleteRun() throws InterruptedException {
        Random rand = new Random();
        ObservableList<Node> rectangles = elementBox.getChildren();
        Timeline firstTimeline = new Timeline();
        Timeline animationTimeline = new Timeline();
        Timeline buttonTimeline = new Timeline();
        int delay = setDelay();
        int timelineCount = 0;
        boolean firstStep = false;

        if (elementBox2.getChildren().size() == 0) {
            Rectangle firstRect = (Rectangle) rectangles.get( rand.nextInt(rectangles.size()) );
            timelineCount++;
            KeyFrame changeToRed = new KeyFrame(Duration.millis(delay * timelineCount), (ActionEvent event) -> {
                firstRect.setFill(Color.RED);
            });
            firstTimeline.getKeyFrames().add(changeToRed);
            timelineCount++;
            firstTimeline.getKeyFrames().add(moveDown(firstRect, elementBox2.getChildren(), delay, timelineCount, -1));
            timelineCount++;
            firstTimeline.getKeyFrames().add(changeToBlue(firstRect, firstRect, delay, timelineCount));
            firstStep = true;

        }
        firstTimeline.play();

        if (firstStep) {
            firstTimeline.setOnFinished((ActionEvent event) -> {
                try {
                    doubleListSortCompleteRun();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            
        }

        if (firstStep == false) {
           timelineCount =  doubleListSortLogic(animationTimeline, rectangles, delay, timelineCount);
        }

        buttonTimeline.getKeyFrames().add(disableButton(randomizeButton, 0, 0));
        buttonTimeline.getKeyFrames().add(disableButton(completeRunButton, 0, 0));
        buttonTimeline.getKeyFrames().add(disableButton(singleStepButton, 0, 0));
        buttonTimeline.getKeyFrames().add(enableButton(randomizeButton, delay, timelineCount));
        buttonTimeline.getKeyFrames().add(enableButton(completeRunButton, delay, timelineCount));
        buttonTimeline.getKeyFrames().add(enableButton(singleStepButton, delay, timelineCount));
        buttonTimeline.play();
        animationTimeline.play();

        animationTimeline.setOnFinished((ActionEvent event1) -> {
            if (rectangles.size() > 0) {
                try {
                    doubleListSortCompleteRun();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                singleStepButton.setDisable(true);
                completeRunButton.setDisable(true);
                finishMessage.setVisible(true);
                return;

            }
        });

    }

    /**
     * Performs a single step of the sorting algorithm.
     * 
     * Transfers a single rectangle from the unsorted list to the sorted list, and 
     * places the rectangle in a position such that all the elements to the left are
     * shorter and all the elements to the right are taller.
     * 
     * Method compares the unsorted rectangle's height to every 
     * rectangle's height in the sorted list until all elements to the right are 
     * taller and the closest rectangle on the left is shorter.
     */
    @FXML
    private void doubleListSortSingleStep() {
        Random rand = new Random();
        ObservableList<Node> rectangles = elementBox.getChildren();
        Timeline firstTimeline = new Timeline();
        Timeline animationTimeline = new Timeline();
        Timeline buttonTimeline = new Timeline();
        int delay = setDelay();
        int timelineCount = 0;
        boolean firstStep = false;

        Rectangle firstRect = (Rectangle) rectangles.get(rand.nextInt(rectangles.size()));

        if (elementBox2.getChildren().size() == 0) {
            timelineCount++;
            KeyFrame changeToRed = new KeyFrame(Duration.millis(delay * timelineCount), (ActionEvent event) -> {
                firstRect.setFill(Color.RED);
            });
            firstTimeline.getKeyFrames().add(changeToRed);
            timelineCount++;
            KeyFrame firstMove = new KeyFrame(Duration.millis(delay * timelineCount), (ActionEvent event) -> {
                elementBox2.getChildren().add(firstRect);
            });
            firstTimeline.getKeyFrames().add(firstMove);
            timelineCount++;
            firstTimeline.getKeyFrames().add(changeToBlue(firstRect, firstRect, delay, timelineCount));

            firstTimeline.play();

            firstStep = true;
        }

        timelineCount++;

        if (firstStep == false) {

            timelineCount = doubleListSortLogic(animationTimeline, rectangles, delay, timelineCount);
        }
        buttonTimeline.getKeyFrames().add(disableButton(randomizeButton, 0, 0));
        buttonTimeline.getKeyFrames().add(disableButton(singleStepButton, 0, 0));
        buttonTimeline.getKeyFrames().add(enableButton(randomizeButton, delay, timelineCount));
        buttonTimeline.getKeyFrames().add(enableButton(singleStepButton, delay, timelineCount));
        buttonTimeline.play();
        animationTimeline.play();

        animationTimeline.setOnFinished((ActionEvent event) -> {
            if (rectangles.size() == 0) {
                singleStepButton.setDisable(true);
                completeRunButton.setDisable(true);
                finishMessage.setVisible(true);
                return;
            }
        });
        

    }

}
