package controller;

import Model.NewUser;
import animatefx.animation.FadeIn;
import animatefx.animation.FadeOut;
import animatefx.animation.FadeOutDown;
import animatefx.animation.LightSpeedOut;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import view.SceneChanger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML controller class
 *
 * @author : Kaleb
 */
public class SignUpController implements Initializable {
    @FXML private TextField usr;
    @FXML private TextField pass;
    @FXML private TextField conPass;
    @FXML private Label lblUsr;
    @FXML private Label lblPas;
    @FXML private Label lblPas2;
    @FXML private Button btnSignUp;
    @FXML private Label messageLbl;


    /**
     *
     * @param event
     */
    public void SignUp(ActionEvent event) throws IOException{

        try {
            if (!pass.getText().equals(conPass.getText())) {
                throw  new IllegalArgumentException("Passwords must match");
            }
            // Create a new user
            NewUser user = new NewUser(usr.getText(), pass.getText());
            // Insert user into the DB
            user.insertUserIntoDB();

        } catch (Exception e) {
            messageLbl.setText(e.getMessage());
            new FadeIn(messageLbl).play();
        }
        playTheAnimations();

         //Change the scene
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene(event, "LogInView.fxml", "Log In Page");
    }

    /**
     *
     */
    public void playTheAnimations() {
        //TODO

        // Animation styles
        new LightSpeedOut(usr).play();
        new FadeOut(lblUsr).play();
        // Animation styles
        new LightSpeedOut(pass).play();
        new FadeOut(lblPas).play();
        // Animation styles
        new LightSpeedOut(conPass).play();
        new FadeOut(lblPas2).play();
        // Animation styles
        new FadeOutDown(btnSignUp).play();
    }

    /**
     * Initializes the controller class
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        messageLbl.setText("");

    }

}