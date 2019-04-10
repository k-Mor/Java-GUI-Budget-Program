package view;

import animatefx.animation.FadeInDown;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainDriver extends Application {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void init() throws Exception {
        super.init();
        Font.loadFont(getClass().getClassLoader().getResource("view/fonts/Raleway-Regular.ttf").toExternalForm(), 12);
        Font.loadFont(getClass().getClassLoader().getResource("view/fonts/Raleway-Thin.ttf").toExternalForm(), 12);
    }

    /**
     *
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("LogInView.fxml"));
        primaryStage.setTitle("My App");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();

        // This makes the initial sign in menu animated
        new FadeInDown(root).play();
    }
}
