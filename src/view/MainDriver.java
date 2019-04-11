/*
 * Multiline comment at the top of the file.
 */
package view;

import animatefx.animation.FadeInDown;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * This class is the driver for myApp.
 * Custom fonts are held in the view package.
 *
 * @author Kaleb
 * @version 2019-04-10
 */
public class MainDriver extends Application {

    /**
     * This is the main method that launches the program.
     *
     * @param args : The command line arguments.
     */
    public static void main(String[] args) {

        launch(args);
    }

    /**
     * This is an overriden method from the super class.
     * This is the very first method that is called upon
     * running this program.
     *
     * @throws Exception : Throws an unspecified exception.
     */
    @Override
    public void init() throws Exception {
        super.init();
        // Raleway regular weight font
        Font.loadFont(getClass().getClassLoader().getResource("view/fonts/Raleway-Regular.ttf").toExternalForm(), 12);

        // Raleway light weight font
        Font.loadFont(getClass().getClassLoader().getResource("view/fonts/Raleway-Thin.ttf").toExternalForm(), 12);
    }

    /**
     * This is an overriden method from the super class.
     * THIS Method loads the appropriate startinf FXML view
     * and sets the scene measurment parameters.
     *
     * @throws Exception : Throws an uspecified exception.
     */
    @Override
    public void start(Stage thePrimaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("LogInView.fxml"));
        thePrimaryStage.setTitle("My App");
        thePrimaryStage.setScene(new Scene(root, 600, 400));
        thePrimaryStage.show();

        // This makes the initial sign in menu animated
        new FadeInDown(root).play();
    }
}
