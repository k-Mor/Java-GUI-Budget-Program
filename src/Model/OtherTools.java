/*
 * Multiline comment at the top of the file
 */
package Model;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.time.LocalDateTime;

/**
 * The purpose of this class is to house all of the miscellaneous methods
 * that have complex functionality, which would other wise clutter the
 * controllers.
 *
 * @author : Kaleb
 * @version : 2019-04-25
 */
public class OtherTools {

    /**
     * This is the minute field.
     */
    private int myMinute;

    /**
     * This is the hour field.
     */
    private int myHour;

    /**
     * This is the second field.
     */
    private int mySecond;

    /**
     * This method is responsible for getting the current time, and running an
     * animated counter in the calling program.
     *
     * @param theTime : This is the FX time id that is passed
     *                in the controller.
     */
    public void getTheTime(Text theTime) {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            mySecond = LocalDateTime.now().getSecond();
            myMinute = LocalDateTime.now().getMinute();
            myHour = LocalDateTime.now().getHour();
            theTime.setText(myHour + ":" + (myMinute) + ":" + mySecond);
        }),
                new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
}
