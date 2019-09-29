/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint_overhaul.alerts;

import java.io.File;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Class to create an about alert for the application. This class sets the
 * icon of the window, and creates an including the information about the app.
 * @author Dylan
 */
public class AboutAlert {
    String iconFilePath = "src/paint_overhaul/icons/alertIcon.png";
    public void createAboutAlert(){
        Alert alert = new Alert(AlertType.INFORMATION);
        ImageView icon = loadIcon();
        alert.setTitle("About");
        alert.setHeaderText("About PAIN(T) by Dylan Brown");
        alert.setContentText("This program attempts (poorly) to "
                + "recreate MS Paint.\nPray for me");
        alert.getDialogPane().setGraphic(icon);
        alert.showAndWait();
    }
    public ImageView loadIcon(){
        File file = new File(iconFilePath);
        Image image = new Image(file.toURI().toString(), 50, 50, true, true);
        ImageView icon = new ImageView(image);
        return icon;
    }
}
