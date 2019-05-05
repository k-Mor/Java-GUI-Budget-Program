/*
 * Multiline comment at the top of the file
 */
package view;

import Model.Transaction;
import animatefx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class is a scene changing class where upon instantiation, access to the
 * necessary method is gained.
 *
 * @author : Kaleb
 * @version : 2019-04-08
 */
public class SceneChanger {

    /**
     * This method handles all of the scene changes with the different FXML files
     *
     * @param theEvent      : This is the event that triggers the event change.
     * @param theViewName   : This is the name of the FXML file to be changed to.
     * @param theSceneTitle : This is the desired name of the new Scene.
     */
    public void changeScene(ActionEvent theEvent, String theViewName, String theSceneTitle) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(theViewName));
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(parent, 1000, 800);
        Stage stage = (Stage) ((Node) theEvent.getSource()).getScene().getWindow();
        stage.setTitle(theSceneTitle);
        stage.setScene(scene);
        new FadeInDown(parent).play();
        stage.show();
    }

    public void changeScene(ActionEvent theEvent, String theViewName, String theSceneTitle, Object theObject, ControllerInterface controllerInterface) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(theViewName));
        Parent parent = loader.load();

        Scene scene = new Scene(parent, 1000, 800);

        // Access controller class and preload data
        controllerInterface = loader.getController();
        controllerInterface.preLoadData(theObject);

        Stage stage = (Stage) ((Node) theEvent.getSource()).getScene().getWindow();
        stage.setTitle(theSceneTitle);
        stage.setScene(scene);
        new FadeInDown(parent).play();
        stage.show();
    }

}
