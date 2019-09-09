/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;
import java.awt.Desktop;
import javafx.scene.input.MouseEvent;
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
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import javafx.scene.paint.Color;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import java.text.NumberFormat;
import java.util.Optional;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ToggleButton;
import javafx.scene.shape.Line;

/**
 * <p>
 * FXMLPaintController is the base class for the whole program.
 * This class is the controller for pain(t). It implements all functions needed to create a functioning
 * version of MS Paint. 
 * This class references all tags created in the .fxml file and creates local variables to store data in.
 * This class also references all actions created in the .fxml file. 
 * </p>
 * @author dylan
 * @version 2.1
 * @since 1.0
 */
public class FXMLPaintController implements Initializable {
    //intialize FMXL and JavaFX vars

    @FXML public BorderPane borderPane;
    
    @FXML private Slider slider;
    @FXML private TextField sliderText;
    
    @FXML private ToggleButton drawButton;
    @FXML private ToggleButton lineButton;
    @FXML private ToggleButton fillButton;
    @FXML private ToggleButton eraseButton;
    @FXML private ToggleButton rectButton;
    @FXML private ToggleButton ovalButton;
    @FXML private ToggleButton textButton;
    @FXML private ToggleButton zoomButton;
    
    @FXML private ColorPicker colorPicker;
   
    @FXML public Canvas imageCanvas;
    private GraphicsContext gcImage;
    
    private String imageFile;
    FileChooser fileChooser = new FileChooser();
    File file;
    
    Line line = new Line();
    
    /**
     * This function currently sets things that need to be controlled after the FXML has been loaded into the program.
     * Sets GraphicsContext gc for imageCanvas.
     * Sets slider value and also uses a text field to control it.
     * The text field function still uses doubles, instead of integers
     * @param location this is necessary for the initialize function, but it is not currently used.
     * @param resources this is necessary for the initialize function, but it is not currently used.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources){
        gcImage = imageCanvas.getGraphicsContext2D();
        slider.setValue(50);
        sliderText.setText(Integer.toString(50));
        sliderText.textProperty().bindBidirectional(slider.valueProperty(), NumberFormat.getNumberInstance());

    }
    /**
    * FMXL Function to close application from File-&gt;Exit.
    * If a save location is set, this function asks you if you would like to save
    * before exiting. If yes, the closeStage() method is called. Otherwise, 
    * the program exits.
    * @see closeStage()
    */
    @FXML
    private void exitApplication(){
        // change this to check file modification
        if(file!=null){
            closeStage();
        }
        else{
            Platform.exit();
        }
        
    }
    /**
    * FXML Function from File-&gt;Open.
    * This function calls configureFileChooser() to configure the file chooser 
    * with the correct extentions and title. It then shows the open dialog. It 
    * then loads the file if one has been chosen. 
    * @see configureFileChooser(FileChooser,String)
    * @see loadFile(File)
     */
    @FXML
    private void openNewFile(){
        configureFileChooser(fileChooser, "Please Select an Image:");
        File loadFile = fileChooser.showOpenDialog(borderPane.getScene().getWindow());
        if(loadFile!=null){
            loadFile(loadFile);
        } 
    }
    /**
     * FXML Function from File-&gt;Save As.
     * This function calls configureFileChooser() to configure the file chooser 
     * with the correct extentions and title. It then shows the save dialog. It 
     * then saves the file if one has been chosen. 
     * @see configureFileChooser(FileChooser,String)
     * @see setImagePath()
     * @see saveImage(File)
     */
    @FXML
    private void handleSaveAs(){
        configureFileChooser(fileChooser, "Save File: ");
        setImagePath();
        if(file != null){
                saveImage(file);
        }
    }
    /**
     * FXML Function from File-&gt;Save.
     * This function calls configureFileChooser() to configure the file chooser 
     * with the correct extentions and title. It then checks if a file has already
     * been saved. If it has, it will save it to the same location. Otherwise,
     * It then shows the save dialog and save it to the path chosen.
     * @see configureFileChooser(FileChooser,String)
     * @see setImagePath()
     * @see saveImage(File)
     */
    @FXML
    private void handleSave(){ 
        configureFileChooser(fileChooser, "Save File: ");
        if(file != null){
            saveImage(file);
        }
        else{
            setImagePath();
            saveImage(file);
        }
    }
    /**
     * FXML Function from Help-&gt;About.
     * This function sends an information dialog to the user. The dialog
     * informs the user of the program.
     */
    @FXML
    private void handleAbout(){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("About PAIN(T) by Dylan Brown");
        alert.setContentText("This program attempts (poorly) to "
                + "recreate MS Paint.\nPray for me");
        alert.showAndWait();
    }
    /**
     * FXML from Help-&gt;Release Notes.
     * This function opens the release notes for the user on their default .txt
     * editor. 
     * @throws IOException if file cannot be read.
     */
    @FXML
    private void handleReleaseNotes() throws IOException{
        File releaseNotes = new File("releasenotes.txt");
        Desktop.getDesktop().open(releaseNotes);
    }
    /**
     * FMXL Function to close application from close button.
     * If a save location is set, this function asks you if you would like to save
     * before exiting. If yes, the closeStage() method is called. Otherwise, 
     * the program exits.
     * @see closeStage() 
     */
    @FXML
    private void handleCloseButton(){
        // change this to check file modification
        if(file!=null){
            closeStage();
        }
        else{
            Platform.exit();
        }
    }
    /** 
     * Will JavaDoc later.
     * @param e 
     */
    @FXML 
    private void setOnMousePressed(MouseEvent e){
        if(drawButton.isSelected()){
            
        }
        else if(lineButton.isSelected()){
            gcImage.setStroke(colorPicker.getValue());
            gcImage.setLineWidth(2.0);
            line.setStartX(e.getX());
            line.setStartY(e.getY());
        }
        else if(fillButton.isSelected()){
            
        }
        else if(eraseButton.isSelected()){
            
        }
        else if(rectButton.isSelected()){
            
        }
        else if(ovalButton.isSelected()){
            
        }
        else if(textButton.isSelected()){
            
        }
        else if(zoomButton.isSelected()){
            
        }
    }
    /**
     * Will JavaDoc later.
     * @param e 
     */
    @FXML
    private void setOnMouseDragged(MouseEvent e){
        
    }
    /**
     * Will JavaDoc later.
     * @param e 
     */
    @FXML
    private void setOnMouseReleased(MouseEvent e){
        if(drawButton.isSelected()){
            
        }
        else if(lineButton.isSelected()){
            line.setEndX(e.getX());
            line.setEndY(e.getY());
            
            gcImage.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
        }
        else if(fillButton.isSelected()){
            
        }
        else if(eraseButton.isSelected()){
            
        }
        else if(rectButton.isSelected()){
            
        }
        else if(ovalButton.isSelected()){
            
        }
        else if(textButton.isSelected()){
            
        }
        else if(zoomButton.isSelected()){
            
        }  
    }
    /**
     * Controller function to load a new file into the canvas.
     * This functions takes the file parameter and converts it to a string. 
     * The function then creates an image using the file path, and outputs its
     * height and width for debugging. The canvas's width and height are set
     * to the images width and height, and outputs the canvas's height and 
     * width for debugging. The GraphicsContext for the canvas draws the image
     * onto the canvas. The function catches any exceptions and prints them to
     * the console, this needs to be logged to a file at a later date.
     * @param file file to be loaded onto canvas
     * Called from {@link openNewFile()}
     */
    private void loadFile(File file){
        
        try {
            imageFile = file.toURI().toURL().toString();
            //System.out.println("file:"+fpath);    
            Image image = new Image(imageFile);
            System.out.println("height:"+image.getHeight()+"\nWidth:"+image.getWidth());
            imageCanvas.setWidth(image.getWidth());
            imageCanvas.setHeight(image.getHeight());
            System.out.println("height:"+imageCanvas.getHeight()+"\nWidth:"+imageCanvas.getWidth());
            gcImage.drawImage(image,0,0);
            
        } 
        catch (Exception ex) {
                System.out.println("An Error Has Occured While Loading Image");
        }
    }
    
    /**
    * configureFileChooser()
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
    /**
    * get source node
    * get the current stage for that node
    * close the stage
    * TODO: Do I even need this?
    */
    private void closeStage() {
        /* This needs to be modified, but it is not required for now
        * Need to somehow check if the canvas has been modified since last save
        if(file != null){
            Platform.exit();
        }
        else{
        setCloseAlerts();
        }
        */ 
        setCloseAlerts();
    }
    /**
     * saveFile()
    * Called from handleSaveAs(), handleSave()
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
            if(file!=null){
                WritableImage writableImage = new WritableImage((int)imageCanvas.getWidth(),(int)imageCanvas.getHeight());
                SnapshotParameters params = new SnapshotParameters();
                params.setFill(Color.TRANSPARENT);
                imageCanvas.snapshot(params, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(renderedImage, "png", file);
            }
            else{
                return;
            }
            
        } 
        catch (IOException ex) {
            Logger.getLogger(FXMLPaintController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
    saveFile()
    * Called from handleSaveAs(), handleSave()
    * Creates WritableImage object 
    * Takes a snapshot of the canvas and writes it to writableImage
    * Converts writableImage to RenderedImage
    * Writes file out
    * TODO: Explore "png" and why that only seems to work
    */
    private void setImagePath(){
        try {
            file = fileChooser.showSaveDialog(borderPane.getScene().getWindow());
        } 
        catch (Exception ex) {
            Logger.getLogger(FXMLPaintController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void setCloseAlerts(){
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Warning!");
        alert.setHeaderText("Your file is not currently saved. Would you "
                + "like to save it?");
        alert.setContentText("Please choose an option");
        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");
        ButtonType cancelButton = new ButtonType("Cancel");
        
        alert.getButtonTypes().setAll(yesButton,noButton,cancelButton);
        
        Optional<ButtonType> result = alert.showAndWait();
            if(result.get()==yesButton){
                if(file != null){
                    saveImage(file);
                }
                else{
                    setImagePath();
                    saveImage(file);
                }    
            } 
            else if(result.get()==noButton){
                Platform.exit();
            }
            else if(result.get() == cancelButton){
            }
    }
}  
