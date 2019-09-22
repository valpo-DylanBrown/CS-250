/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint_overhaul.alerts;

import java.io.File;
import java.util.Optional;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import paint_overhaul.other.BetterFileChooser;
import paint_overhaul.other.Main;
import paint_overhaul.other.PaintCanvas;

/**
 *
 * @author Dylan
 */
public class SmartSaveAlert {
    private PaintCanvas paintCanvas;
    private BetterFileChooser betterFC;
    File file;
    public SmartSaveAlert(PaintCanvas paintCanvas, BetterFileChooser betterFC){
        this.paintCanvas = paintCanvas;
        this.betterFC = betterFC;
    }
    public void setSmartSaveAlert(){
        Alert alert = createCloseAlertDialog();
        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");
        ButtonType cancelButton = new ButtonType("Cancel");
                
        alert.getButtonTypes().setAll(yesButton,noButton,cancelButton);
        
        Optional<ButtonType> result = alert.showAndWait();
        handleButtonTypeLogic(result, yesButton, noButton, cancelButton);
    }
    private Alert createCloseAlertDialog(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning!");
        alert.setHeaderText("Your file is not currently saved. Would you "
                + "like to save it?");
        alert.setContentText("Please choose an option");
        return alert;
    }
    private void handleButtonTypeLogic(Optional<ButtonType> result, 
            ButtonType yesButton, ButtonType noButton, ButtonType cancelButton ){
            if(result.get()==yesButton){
                if(paintCanvas.getSavedFile() == null){
                    paintCanvas.setSavedFile(betterFC.getFileChooser().showSaveDialog(Main.paintController.getBorderPane().getScene().getWindow())); 
                    }
                paintCanvas.saveCanvasToFile(paintCanvas.getSavedFile());
                Platform.exit();
            }
            
            else if(result.get()==noButton){
                Platform.exit();
            }
            else if(result.get() == cancelButton){
            }
    }
}
