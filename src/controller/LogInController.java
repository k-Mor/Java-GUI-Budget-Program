package controller;

import Model.NewUser;
import animatefx.animation.*;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import view.SceneChanger;

import javax.imageio.ImageIO;
/**
 * FXML controller class
 *
 * @author : Kaleb
 */
public class LogInController implements Initializable {
    @FXML private TextField userName;
    @FXML private TextField passWord;
    @FXML private Label lblUser;
    @FXML private Label lblPass;
    @FXML private Button btnLogIn;
    @FXML private Button btnSignUp;
    @FXML private Label errLabel;



    /**
     *
     * @param event
     */
    public void logInButtonPushed(ActionEvent event) {
        // Animation styles

        // Check to see if user in DB

        // Play animations
        new LightSpeedOut(userName).play();
        new FadeOut(lblUser).play();

        new LightSpeedOut(passWord).play();
        new FadeOut(lblPass).play();

        new FadeOutDown(btnLogIn).play();
        new FadeOutDown(btnSignUp).play();

        try {
            SceneChanger sceneChanger = new SceneChanger();
            sceneChanger.changeScene(event, "HomePage.fxml", " Home Page");
            errLabel.setText("");
        } catch (Exception e) {
            errLabel.setText(e.getMessage());
            if (errLabel != null) {
                new FadeIn(errLabel).play();
            }
        }


    }

    /**
     *
     * @param event
     */
    public void signUpButtonPushed(ActionEvent event) throws IOException{
        // Animation styles

        try {
            SceneChanger sceneChanger = new SceneChanger();
            sceneChanger.changeScene(event, "SignUpView.fxml", "Sign up Page");
            errLabel.setText("");
        } catch (Exception e) {
            errLabel.setText(e.getMessage());
            if (errLabel != null) {
                new FadeIn(errLabel).play();
            }
        }

    }

    /**
     * Initializes the controller class
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        errLabel.setText("");


    }

}