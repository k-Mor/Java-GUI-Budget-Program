/*
 * Multiline comment at the top of the file.
 */
package controller;

import animatefx.animation.*;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import view.SceneChanger;

/**
 * This class is the controller for the LogInView.FXML file.
 *
 * @author : Kaleb
 * @version : 2019-04-10
 */
public class LogInController implements Initializable {

    /**
     * This field is the user name.
     */
    @FXML private TextField myUserName;

    /**
     * This field is the password.
     */
    @FXML private TextField myPassWord;

    /**
     * This field is a label for the username
     * text field.
     */
    @FXML private Label myUserLbl;

    /**
     * This field is the label for the password.
     */
    @FXML private Label myPassLbl;

    /**
     * This is the log in button.
     */
    @FXML private Button myBtnLogIn;

    /**
     * This is the sign up button.
     */
    @FXML private Button mySignUpBtn;

    /**
     * This is the error label that indicates a problem
     * should one occur during sign in.
     */
    @FXML private Label myErrLbl;

    /**
     * This method is responsible for handling the log in button event
     * click.
     *
     * @param theEvent : This is the event.
     */
    public void logInButtonPushed(ActionEvent theEvent) {
        //TODO (Check if user in DB)

        // Play animations
        new LightSpeedOut(myUserName).play();
        new FadeOut(myUserLbl).play();

        new LightSpeedOut(myPassWord).play();
        new FadeOut(myPassLbl).play();

        new FadeOutDown(myBtnLogIn).play();
        new FadeOutDown(mySignUpBtn).play();

        try {
            SceneChanger sceneChanger = new SceneChanger();
            sceneChanger.changeScene(theEvent, "HomePage.fxml", " Home Page");
            myErrLbl.setText("");
        } catch (Exception e) {
            myErrLbl.setText(e.getMessage());
            if (myErrLbl != null) {
                new FadeIn(myErrLbl).play();
            }
        }
    }

    /**
     * This method handles the sign up botton and changes the view to
     * the sign up page.
     *
     * @param theEvent : This is the event that triggers the change.
     */
    public void signUpButtonPushed(ActionEvent theEvent) throws IOException{
        // Animation styles

        try {
            SceneChanger sceneChanger = new SceneChanger();
            sceneChanger.changeScene(theEvent, "SignUpView.fxml", "Sign up Page");
            myErrLbl.setText("");
        } catch (Exception e) {
            myErrLbl.setText(e.getMessage());
            if (myErrLbl != null) {
                new FadeIn(myErrLbl).play();
            }
        }
    }

    /**
     *  This is the overriden method that
     *  initializes the controller class.
     *
     * @param theUrl : This is an unused parameter.
     * @param theRb : This is an unused parameter.
     */
    @Override
    public void initialize(URL theUrl, ResourceBundle theRb) {
        myErrLbl.setText("");
    }
}