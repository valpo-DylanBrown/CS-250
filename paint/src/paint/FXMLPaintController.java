/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;
import java.awt.image.BufferedImage;
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
import javafx.scene.image.ImageView;
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
    /*grabs desktop from computer
    * used for pulling files from fileChooser
    */
    //Desktop desktop = Desktop.getDesktop();
    
    
    @FXML private ImageView imageID;
    
    @FXML private AnchorPane anchorPane;
    
    @FXML private Canvas imageCanvas;
    
    private GraphicsContext gcImage;
    
    
    
    
    private String imageFile;
    FileChooser fileChooser = new FileChooser();
    
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
    * Users then allowed to only choose image files (jpeg,png,tiff,pdf)
    * If the file is not corrupted, the file will open
    * Note to self: This needs to be wrapped in a try catch
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
    * This has not been finished yet
    */
    @FXML
    private void handleSaveAs(ActionEvent event) throws IOException{
        configureFileChooser(fileChooser, "Save File: ");
        File file = fileChooser.showSaveDialog(anchorPane.getScene().getWindow());
        if(file != null){
            try{
                String name = file.getName();
                String extension = name.substring(1+name.lastIndexOf(".")).toLowerCase();
                WritableImage writableImage = new WritableImage(843,761);
                imageCanvas.snapshot(null, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(renderedImage, extension, file);
            }
            catch(Exception e){
               System.out.println("An Error has Occured While Saving.");
            }
        }
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
       closeStage(event);
    }
    
    /*openFile()
    * Controller Function
    * Tries opening the file using desktop object
    * Catches IOException and logs it if the opening fails
    */
    /*
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
    */
    private void loadFile(File file){
        
        try {
            imageFile = file.toURI().toURL().toString();
            //System.out.println("file:"+fpath);
            Image image = new Image(imageFile);
            System.out.println("height:"+image.getHeight()+"\nWidth:"+image.getWidth());
            gcImage.drawImage(image,0,0);
            
        } 
        catch (Exception ex) {
                System.out.println("An Error Has Occured While Loading Image");
        }
    }
    private void configureFileChooser(FileChooser fileChooser, String title){
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
    }
    private void closeStage(ActionEvent event){
       Node source = (Node) event.getSource();
       Stage stage = (Stage) source.getScene().getWindow();
       
       stage.close();
    }
    private Image getImage(){
        return imageID.getImage();
    }
    private void saveImage(File file, BufferedImage bImage, Image loadedImage){
            String name = file.getName();
            String extension = name.substring(1+name.lastIndexOf(".")).toLowerCase();
            System.out.println("name: "+ name +"\nextension:"+extension);
            System.out.println("height:"+loadedImage.getHeight()+"\nWidth:"+loadedImage.getWidth());
        try {
            ImageIO.write(bImage, extension, file);
        } catch (IOException ex) {
            Logger.getLogger(FXMLPaintController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}  
