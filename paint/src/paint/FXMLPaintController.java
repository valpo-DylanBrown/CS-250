package paint;


//clean this up possibly/ask if this is good/bad practice
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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import javafx.scene.paint.Color;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import java.util.Optional;
import javafx.geometry.Insets;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.util.Pair;

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
    
    @FXML private ToggleButton drawButton;
    @FXML private ToggleButton lineButton;
    @FXML private ToggleButton fillButton;
    @FXML private ToggleButton eraseButton;
    @FXML private ToggleButton rectButton;
    @FXML private ToggleButton squareButton;
    @FXML private ToggleButton ovalButton;
    @FXML private ToggleButton circleButton;
    @FXML private ToggleButton textButton;
    @FXML private ToggleButton zoomButton;
    
    @FXML private ColorPicker fillColorPicker;
    @FXML private ColorPicker strokeColorPicker;
   
    @FXML public Canvas imageCanvas;
    private GraphicsContext gcImage;
    
    private String imageFile;
    FileChooser fileChooser = new FileChooser();
    File file;
    
    private boolean hasBeenModified = false;
    
    Line line = new Line();
    Rectangle rect = new Rectangle();
    Rectangle sqr = new Rectangle();
    Circle circ = new Circle();
    Ellipse ellipse = new Ellipse();
    
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
        //slider.setValue(3.0);
        //sliderText.setText(Integer.toString(50));
        //sliderText.textProperty().bindBidirectional(slider.valueProperty(), NumberFormat.getNumberInstance());

    }
    /**
    * FMXL Function to close application from File-&gt;Exit.
    * If a save location is set, this function asks you if you would like to save
    * before exiting. If yes, the closeStage() method is called. Otherwise, 
    * the program exits.
    * @see #closeStage()
    */
    @FXML
    private void exitApplication(){
        // change this to check file modification
        if(hasBeenModified==true){
            setCloseAlerts();
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
    * @see #configureFileChooser(FileChooser,String)
    * @see #loadFile(File)
     */
    @FXML
    private void openNewFile(){
        configureFileChooser(fileChooser, "Please Select an Image:");
        File loadFile = fileChooser.showOpenDialog(borderPane.getScene().getWindow());
        if(loadFile!=null){
            loadFile(loadFile);
        } 
        hasBeenModified = true;
    }
    /**
     * FXML Function from File-&gt;Save As.
     * This function calls configureFileChooser() to configure the file chooser 
     * with the correct extentions and title. It then shows the save dialog. It 
     * then saves the file if one has been chosen. 
     * @see #configureFileChooser(FileChooser,String)
     * @see #setImagePath()
     * @see #saveImage(File)
     */
    @FXML
    private void handleSaveAs(){
        configureFileChooser(fileChooser, "Save File: ");
        setImagePath();
        if(file != null){
                saveImage(file);
                hasBeenModified = false;
        }
    }
    /**
     * FXML Function from File-&gt;Save.
     * This function calls configureFileChooser() to configure the file chooser 
     * with the correct extentions and title. It then checks if a file has already
     * been saved. If it has, it will save it to the same location. Otherwise,
     * It then shows the save dialog and save it to the path chosen.
     * @see #configureFileChooser(FileChooser,String)
     * @see #setImagePath()
     * @see #saveImage(File)
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
        hasBeenModified = false;
    }
    
    @FXML
    private void handleResizeCanvas(){
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Resize Canvas");

        // Set the button types.
        ButtonType okButtonType = new ButtonType("OK", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        TextField width = new TextField();
        width.setPromptText("Width:");
        TextField height = new TextField();
        height.setPromptText("Height");

        gridPane.add(new Label("Width:"), 0, 0);
        gridPane.add(width, 1, 0);
        gridPane.add(new Label("Height"), 2, 0);
        gridPane.add(height, 3, 0);

        dialog.getDialogPane().setContent(gridPane);

        // Request focus on the username field by default.
        Platform.runLater(() -> width.requestFocus());
        /*double dw = Double.parseDouble(width.toString());
        double dh = Double.parseDouble(height.toString());
        imageCanvas.setWidth(dw);
        imageCanvas.setHeight(dh);*/
        
        dialog.showAndWait();
        
        
        double dw = Double.parseDouble(width.toString());
        double dh = Double.parseDouble(height.toString());
        imageCanvas.setWidth(dw);
        imageCanvas.setHeight(dh);
        //Optional<Pair<String, String>> result = dialog.showAndWait();

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
     * @see #closeStage() 
     */
    @FXML
    private void handleCloseButton(){
        // change this to check file modification
        if(hasBeenModified==true){
            setCloseAlerts();
        }
        else{
            Platform.exit();
        }
    }
    /** 
     * Will JavaDoc later.
     * @param event to do
     */
    @FXML 
    private void setOnMousePressed(MouseEvent event){
        if(drawButton.isSelected()){
            gcImage.setStroke(fillColorPicker.getValue());
            gcImage.setLineWidth(slider.getValue());
        
            gcImage.beginPath();
            gcImage.lineTo(event.getX(),event.getY()); 
        }
        else if(lineButton.isSelected()){
            gcImage.setStroke(fillColorPicker.getValue());
            gcImage.setLineWidth(slider.getValue());
            
            line.setStartX(event.getX());
            line.setStartY(event.getY());
        }
        else if(fillButton.isSelected()){
            
        }
        else if(eraseButton.isSelected()){
            
        }
        else if(rectButton.isSelected()){
            gcImage.setStroke(strokeColorPicker.getValue());
            gcImage.setLineWidth(slider.getValue());
            gcImage.setFill(fillColorPicker.getValue());
            
            rect.setX(event.getX());                
            rect.setY(event.getY());
        }
        else if(squareButton.isSelected()){
            gcImage.setStroke(strokeColorPicker.getValue());
            gcImage.setLineWidth(slider.getValue());
            gcImage.setFill(fillColorPicker.getValue());
            
            sqr.setX(event.getX());                
            sqr.setY(event.getY());
        }
        else if(circleButton.isSelected()){
            gcImage.setStroke(strokeColorPicker.getValue());
            gcImage.setLineWidth(slider.getValue());
            gcImage.setFill(fillColorPicker.getValue());
            
            circ.setCenterX(event.getX());
            circ.setCenterY(event.getY());
        }
        else if(ovalButton.isSelected()){
            gcImage.setStroke(strokeColorPicker.getValue());
            gcImage.setLineWidth(slider.getValue());
            gcImage.setFill(fillColorPicker.getValue());
            
            ellipse.setCenterX(event.getX());
            ellipse.setCenterY(event.getY());
        }
        
        else if(textButton.isSelected()){
            
        }
        else if(zoomButton.isSelected()){
            
        }
    }
    /**
     * Will JavaDoc later.
     * @param event to do
     */
    @FXML
    private void setOnMouseDragged(MouseEvent event){
        if(drawButton.isSelected()){
            gcImage.lineTo(event.getX(), event.getY());
            gcImage.stroke();
        }
    }
    /**
     * Will JavaDoc later.
     * @param event to do
     */
    @FXML
    private void setOnMouseReleased(MouseEvent event){
        if(drawButton.isSelected()){
            gcImage.lineTo(event.getX(), event.getY());
            gcImage.stroke();
            
            gcImage.closePath();
        }
        else if(lineButton.isSelected()){
            line.setEndX(event.getX());
            line.setEndY(event.getY());
            
            gcImage.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
        }
        else if(fillButton.isSelected()){
            
        }
        else if(eraseButton.isSelected()){
            
        }
        else if(rectButton.isSelected()){
            rect.setWidth(Math.abs((event.getX() - rect.getX())));
            rect.setHeight(Math.abs((event.getY() - rect.getY())));
            if(rect.getX() > event.getX()) {
                rect.setX(event.getX());
            }
            if(rect.getY() > event.getY()) {
                rect.setY(event.getY());
            }

            gcImage.fillRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
            gcImage.strokeRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        }
        else if(squareButton.isSelected()){
            sqr.setWidth(Math.abs((event.getX() - sqr.getX())));
            sqr.setHeight(sqr.getWidth());
            if(sqr.getX() > event.getX()) {
                sqr.setX(event.getX());
            }
            if(sqr.getY() > event.getY()) {
                sqr.setY(event.getY());
            }

            gcImage.fillRect(sqr.getX(), sqr.getY(), sqr.getWidth(), sqr.getHeight());
            gcImage.strokeRect(sqr.getX(), sqr.getY(), sqr.getWidth(), sqr.getHeight());
        }
        else if(circleButton.isSelected()){
            circ.setRadius((Math.abs(event.getX() - circ.getCenterX()) + Math.abs(event.getY() - circ.getCenterY())) / 2);

            if(circ.getCenterX() > event.getX()) {
                circ.setCenterX(event.getX());
            }
            if(circ.getCenterY() > event.getY()) {
                circ.setCenterY(event.getY());
            }

            gcImage.fillOval(circ.getCenterX(), circ.getCenterY(), circ.getRadius(), circ.getRadius());
            gcImage.strokeOval(circ.getCenterX(), circ.getCenterY(), circ.getRadius(), circ.getRadius());
        }
        else if(ovalButton.isSelected()){
            ellipse.setRadiusX((Math.abs(event.getX() - ellipse.getCenterX())));
            ellipse.setRadiusY((Math.abs(event.getY() - ellipse.getCenterY())));
            if(ellipse.getCenterX() > event.getX()) {
                ellipse.setCenterX(event.getX());
            }
            if(ellipse.getCenterY() > event.getY()) {
                ellipse.setCenterY(event.getY());
            }

            gcImage.fillOval(ellipse.getCenterX(), ellipse.getCenterY(), ellipse.getRadiusX(), ellipse.getRadiusY());
             gcImage.strokeOval(ellipse.getCenterX(), ellipse.getCenterY(), ellipse.getRadiusX(), ellipse.getRadiusY());
        }
        else if(textButton.isSelected()){
            
        }
        else if(zoomButton.isSelected()){
            
        } 
        else{}
        hasBeenModified = true;
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
     * Called from {@link #openNewFile()}
     * @param file file to be loaded onto canvas
     * 
     */
    private void loadFile(File file){
        
        try {
            imageFile = file.toURI().toURL().toString();
            //System.out.println("file:"+fpath);    
            Image image = new Image(imageFile);
            //System.out.println("height:"+image.getHeight()+"\nWidth:"+image.getWidth());
            imageCanvas.setWidth(image.getWidth());
            imageCanvas.setHeight(image.getHeight());
            //System.out.println("height:"+imageCanvas.getHeight()+"\nWidth:"+imageCanvas.getWidth());
            gcImage.drawImage(image,0,0);
            
        } 
        catch (Exception ex) {
                System.out.println("An Error Has Occured While Loading Image");
        }
    }
    
    /**
     * Configures the File Chooser.
     * This function configures the file chooser. The function passes the string
     * to the title of the window and adds extension filters to filter images in.
     * Called from {@link #openNewFile()}, {@link #handleSaveAs()}, and
     * {@link #handleSave()} 
     * @param fileChooser  file chooser to edit
     * @param title string to set title of the window
     * 
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
     * Function to close the program.
     * This function calls setCloseAlerts() before closing the application 
     * 
     * TODO: This function still needs to implement checking if a file has been
     * modified. If the file has been modified since last save, it will call
     * setCloseAlerts(), otherwise it will call an exit. 
     * 
     * Called from {@link #handleCloseButton()} and {@link #exitApplication()}
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
     * Function that writes image to file out.
     * This function creates a writable image with the dimensions of the canvas.
     * It then sets snapshot parameters to set the canvas snapshot to transparent.
     * Then, the function takes a snapshot of the canvas and writes it to
     * a WritableImage. It converts the writable image to a rendered image,
     * and writes the image to a file.
     * Called from {@link #handleSaveAs()} and {@link #handleSave()}
     * @param file location of desired save
     * 
     * 
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
     * Function to set path of desired save location. 
     * This function opens the save dialog and sets the location under file.
     * 
     * Called from {@link #handleSaveAs()} and {@link #handleSave()}
     */
    private void setImagePath(){
        try {
            file = fileChooser.showSaveDialog(borderPane.getScene().getWindow());
        } 
        catch (Exception ex) {
            Logger.getLogger(FXMLPaintController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Function to create alerts before closing the window with an unsaved file.
     * This function creates a new warning dialog. The window prompts the user
     * if they would like to save their file. It then shows the user the options,
     * saves the image if they would like, closes the program, or closes the
     * dialog window. 
     * 
     * Called from {@link #exitApplication()}, {@link #handleCloseButton()}
     */
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