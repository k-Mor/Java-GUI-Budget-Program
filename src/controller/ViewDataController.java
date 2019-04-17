/*
 * Multiline comment at the top of the file.
 */
package controller;

import Model.DataBaseTools;
import Model.PasswordGenerator;
import animatefx.animation.*;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import view.SceneChanger;

/**
 * This class is the controller for the ViewData.FXML file.
 *
 * @author : Kaleb
 * @version : 2019-04-10
 */
public class ViewDataController implements Initializable {

    /**
     * This field is the user name.
     */
    @FXML
    private TextField myUserName;


    /**
     * This is the overriden method that
     * initializes the controller class.
     *
     * @param theUrl : This is an unused parameter.
     * @param theRb  : This is an unused parameter.
     */
    @Override
    public void initialize(URL theUrl, ResourceBundle theRb) {
    }

    /**
     * This method handles the flow of control when the input data
     * button is pushed.
     *
     * @param theEvent : This is the event.
     */
    public void goBackButtonPushed(ActionEvent theEvent) {
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene(theEvent, "HomePage.fxml", "Home Page");
    }
}