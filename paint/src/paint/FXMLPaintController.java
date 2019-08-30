/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.FileChooser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.application.Platform;

/**
 *
 * @author dylan
 */
public class FXMLPaintController {
    /*grabs desktop from computer
    * used for pulling files from fileChooser
    */
    Desktop desktop = Desktop.getDesktop();
    
    @FXML
    private Label label;
    private Button closeButton;
    
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    /*exitApplication()
    * FMXL
    * exitApplication is a menu option under File (File-->Exit)
    * This closes the program
    */
    @FXML
    private void exitApplication(ActionEvent event){
        Platform.exit();
        
    }
    /*openNewFile()
    * FMXL
    * Users then allowed to only choose image files (jpeg,png,tiff,pdf)
    * If the file is not corrupted, the file will open
    * Note to self: This needs to be wrapped in a try catch
    */
    @FXML
    private void openNewFile(ActionEvent event){
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Please Select an Image");
        
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("All Files", "*.jpeg", "*.jpg",
                "*.png", "*.tiff", "*.tif", "*.pdf", "*.JPEG", "*.JPG","*.PNG",
                "*.TIFF","*.TIF", "*.PDF"),
            new FileChooser.ExtensionFilter("JPEG", "*.jpeg", "*.jpg",
                "*.JPEG", "*.JPG"),
            new FileChooser.ExtensionFilter("PNG", "*.png", "*.PNG"),
            new FileChooser.ExtensionFilter("TIFF", "*.tiff", "*.tif",
                "*.TIFF", "*.tif"),
            new FileChooser.ExtensionFilter("PDF", "*.pdf", "*.PDF")
            );
        File file = fileChooser.showOpenDialog(new Stage());
        //change so it opens in program, but progress
        if(file!=null){
            openFile(file);
        }      
    }
    /*handleSaveAs() (needs a rename)
    * This has not been finished yet
    */
    @FXML
    private void handleSaveAs(ActionEvent event){
        System.out.println("save as pressed");
        
    }
    /*handleSave() (needs a rename)
    * This has not been finished yet
    */
    @FXML
    private void handleSave(ActionEvent event){
        System.out.println("save as pressed");
        
    }
    @FXML
    private void handleCloseButton(ActionEvent event){
       /* This should close the current stage open, 
        * as opposed to the whole program
        * Close whole program : Platform.exit();
       */
       Node source = (Node) event.getSource();
       Stage stage = (Stage) source.getScene().getWindow();
       
       stage.close();
    }
    /*openFile()
    * Controller Function
    * Tries opening the file using desktop object
    * Catches IOException and logs it if the opening fails
    */
    private void openFile(File file){
        try {
            desktop.open(file);
        } 
        catch (IOException ex) {
                Logger.getLogger(
                FXMLPaintController.class.getName()).log(
                    Level.SEVERE, null, ex
                );
        }
    }
}  
