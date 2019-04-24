/*
 * Multiline comment at the top of the file.
 */
package view;

import Model.Budget;
import Model.DataBaseTools;
import Model.PasswordGenerator;
import Model.Transaction;
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
 * This class is the controller for the LogInView.FXML file.
 *
 * @author : Kaleb
 * @version : 2019-04-10
 */
public interface ControllerInterface {

    /**
     *
     *
     * @param theObject
     */
    public abstract void preLoadData(Object theObject);

//    /**
//     *
//     * @param theBudget
//     */
//    public abstract void preLoadData( theBudget);

//    /**
//     *
//     * @param theAccount
//     */
//    public abstract void preLoadData(Account theAccount);

}