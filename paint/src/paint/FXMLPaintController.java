/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;
import java.awt.image.RenderedImage;
import javafx.scene.image.WritableImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

/**
 *
 * @author dylan
 */
public class FXMLPaintController implements Initializable {
    //intialize FMXL and JavaFX vars
    @FXML private AnchorPane anchorPane;
            
    @FXML public Canvas imageCanvas;
    private GraphicsContext gcImage;
    
    private String imageFile;
    FileChooser fileChooser = new FileChooser();
    
    @Override
    public void initialize(URL location, ResourceBundle resources){
        gcImage = imageCanvas.getGraphicsContext2D();
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
    * onEvent --> Menu->Open clicked
    * configures fileChooser usig configureFileChooser()
    * opens dialog from fileChooser
    * if file is not null, calls loadFile
    * TODO: Add alert if file is null
    */
    @FXML
    private void openNewFile(ActionEvent event) throws IOException{
        configureFileChooser(fileChooser, "Please Select an Image:");
        File file = fileChooser.showOpenDialog(anchorPane.getScene().getWindow());
        if(file!=null){
            loadFile(file);
        } 
    }
    /*handleSaveAs()
    * onEvent --> Menu->Save As clicked
    * configures fileChooser usig configureFileChooser()
    * opens dialog from fileChooser
    * if file is not null, calls saveFile
    * TODO: Add alert if file is null
    */
    @FXML
    private void handleSaveAs(ActionEvent event) throws IOException{
        configureFileChooser(fileChooser, "Save File: ");
        File file = fileChooser.showSaveDialog(anchorPane.getScene().getWindow());
        if(file != null){
                saveImage(file);
        }
    }
    /*handleSave()
    * onEvent --> Menu->Save clicked
    * configures fileChooser usig configureFileChooser()
    * opens dialog from fileChooser
    * if file is not null, calls saveFile
    * TODO: Add alert if file is null, change this so it works like a typical save
    */
    @FXML
    private void handleSave(ActionEvent event){ //THIS NEEDS TO GET CHANGED TO ACT AS A REGULAR SAVE
        configureFileChooser(fileChooser, "Save File: ");
        File file = fileChooser.showSaveDialog(anchorPane.getScene().getWindow());
        if(file != null){
            try{
                saveImage(file);
            }
            catch(Exception e){
               System.out.println("An Error has Occured While Saving.");
            }
        }
    }
    /*handleCloseButton() 
    * closes program
    */
    @FXML
    private void handleCloseButton(ActionEvent event){
       closeStage(event);
    }
    /*loadFile()
    * Called from openNewFile()
    * Get string for filepath
    * create an image off of the file path
    * Requests 850x760, size of current canvas, keeps ratio, and smooths
    * Print out width and height for debug
    * Calcualte midpoint to place picture
    * Draw picture onto imageCanvas in the middle
    * TODO: 
    */
    private void loadFile(File file){
        
        try {
            imageFile = file.toURI().toURL().toString();
            //System.out.println("file:"+fpath);    
            Image image = new Image(imageFile, 850, 760, true, true);
            System.out.println("height:"+image.getHeight()+"\nWidth:"+image.getWidth());
            double x = (double) imageCanvas.getWidth()/2 - image.getWidth()/2;
            double y = (double) imageCanvas.getHeight()/2 - image.getHeight()/2;
            gcImage.drawImage(image,x,y);
            
        } 
        catch (Exception ex) {
                System.out.println("An Error Has Occured While Loading Image");
        }
    }
    
    /*configureFileChooser()
    * Called from multiple functions()
    * Sets Title to String passed
    * Sets Extension Filters
    * TODO: Add BMP and GIF, possibly take away PDF
    */
    private void configureFileChooser(FileChooser fileChooser, String title){
        fileChooser.setTitle(title);
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
    }
    /*closeStage()
    * get source node
    * get the current stage for that node
    * close the stage
    * TODO: Do I even need this?
    */
    private void closeStage(ActionEvent event){
       Node source = (Node) event.getSource();
       Stage stage = (Stage) source.getScene().getWindow();
       
       stage.close();
    }
    /*saveFile()
    * Called from handleSaveAs() & handleSave()
    * Creates WritableImage object 
    * Takes a snapshot of the canvas and writes it to writableImage
    * Converts writableImage to RenderedImage
    * Writes file out
    * TODO: Explore "png" and why that only seems to work
    */
    private void saveImage(File file){
        /*
            String name = file.getName();
            String extension = name.substring(1+name.lastIndexOf(".")).toLowerCase();
            System.out.println("name: "+ name +"\nextension:"+extension);
        */
        try {
            WritableImage writableImage = new WritableImage(850,760);
            imageCanvas.snapshot(null, writableImage);
            RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
            ImageIO.write(renderedImage, "png", file);
        } 
        catch (IOException ex) {
            Logger.getLogger(FXMLPaintController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}  
