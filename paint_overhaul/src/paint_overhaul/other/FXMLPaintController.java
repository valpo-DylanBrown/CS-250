package paint_overhaul;

//BIG TODO SPLIT THINGS UP INTO FUNCTIONS AND DIFFERENT FILES
/*KNOWN BUGS:
    * Scroll canvas is gone, jave a big scroll above border and use that one
    * Figure out undo/redo on path element
        * if in a pitch, use a line for undo and it will do the job decently for now

*/
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
import java.util.Stack;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.input.ScrollEvent;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
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
    @FXML public ScrollPane canvasScrollPane;
    //@FXML public StackPane stackPane;
    @FXML private Pane staticPane;
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
    @FXML private Button zoomInButton;
    @FXML private Button zoomOutButton;
    @FXML private Label zoomLabel;
    @FXML private Button swapColors;
    
    @FXML private Button closeButton;
    @FXML private ToggleButton eyedropperButton;
    @FXML private Button undoButton;
    @FXML private Button redoButton;
    @FXML private ColorPicker fillColorPicker;
    @FXML private ColorPicker strokeColorPicker;
    @FXML private Group group;
    

    
    @FXML public Canvas imageCanvas;
    private GraphicsContext gcImage;
    
    private File openedFile;
    private String imageFile;
    FileChooser fileChooser = new FileChooser();
    File file;
    
    private double currentZoom = 1;
    private Scale zoomScale;
    
    private boolean hasBeenModified = false;
    
    Line line = new Line();
    Rectangle rect = new Rectangle();
    Rectangle sqr = new Rectangle();
    Circle circ = new Circle();
    Ellipse ellipse = new Ellipse();
    
    Path path = new Path();
    
    Stack<Image> undoHistory = new Stack();
    Stack<Image> redoHistory = new Stack();
    
    
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
        //stage = (Stage) borderPane.getScene().getWindow();
    }
    
    /**
     * FXML Function to handle zoom in from the menu.
     * This function strictly fires the zoom in button to complete the action.
     * @see #zoomInButton()
     */
    @FXML
    private void handleZoomInMenu(){
        zoomInButton.fire();           
    }
    
    /**
     * FXML Function to handle zoom out from the menu.
     * This function strictly fires the zoom out button to complete the action.
     * @see #zoomOutButton()
     */
    @FXML
    private void handleZoomOutMenu(){
        zoomOutButton.fire();           
    }
    
    /**
     * FXML Function to set zoom to 25%.
     * @see #zoomToX()
     */
    @FXML
    private void handleZoom25Menu(){
        zoomToX(25);  
    }
    
    /**
     * FXML Function to set zoom to 50%.
     * @see #zoomToX()
     */
    @FXML
    private void handleZoom50Menu(){
        zoomToX(50);  
    }
    
    /**
     * FXML Function to set zoom to 75%.
     * @see #zoomToX()
     */
    @FXML
    private void handleZoom75Menu(){
        zoomToX(75);
    }
    
    /**
     * FXML Function to set zoom to 100%.
     * @see #zoomToX()
     */
    @FXML
    private void handleZoom100Menu(){
        zoomToX(100);  
    }
    
    /**
     * FXML Function to set zoom to 200%.
     * @see #zoomToX()
     */
    @FXML
    private void handleZoom200Menu(){
        zoomToX(200);  
    }
    
    /**
     * FXML Function to set zoom to 250%.
     * @see #zoomToX()
     */
    @FXML
    private void handleZoom250Menu(){
        zoomToX(250);  
    }
    
    /**
     * FXML Function to set zoom to 300%.
     * @see #zoomToX()
     */
    @FXML
    private void handleZoom300Menu(){
        zoomToX(300);
    }
    
    /**
    * FMXL Function to close application from File-&gt;Exit.
    * Strictly fires close button.
    * @see #handleCloseButton() 
    */
    @FXML
    private void exitApplication(){
        closeButton.fire();
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
    private void handleOpen(){
        openNewFile();
    }
    
    /**
     * FXML Function from File-&gt;Save As.
     * This function calls configureFileChooser() to configure the file chooser 
     * with the correct extentions and title. It then shows the save dialog. It 
     * then saves the file if one has been chosen. 
     * @see #configureFileChooser(FileChooser,String)
     * @see #setImagePath()
     * @see #imageSave(File)
     */
    @FXML
    private void handleSaveAs(){
        saveImageAs();
    }
    
    /**
     * FXML Function from File-&gt;Save.
     * This function calls configureFileChooser() to configure the file chooser 
     * with the correct extentions and title. It then checks if a file has already
     * been saved. If it has, it will save it to the same location. Otherwise,
     * It then shows the save dialog and save it to the path chosen.
     * @see #configureFileChooser(FileChooser,String)
     * @see #setImagePath()
     * @see #imageSave(File)
     */
    @FXML
    private void handleSave(){
        saveImage();
    }
    
    /**
     * FXML Function to resize canvas.
     * @see #createDialog() 
     */
    @FXML
    private void handleResizeCanvas(){ 
        createDialog();
    }
    
    /**
     * FXML Function from Help-&gt;About.
     * This function sends an information dialog to the user. The dialog
     * informs the user of the program.
     */
    @FXML
    private void handleAbout(){
        createAboutDialog();
    }
    
    /**
     * FXML from Help-&gt;Release Notes.
     * @see #openReleaseNotes()
     * @throws IOException if openReleaseNotes throws an exception
     */
    @FXML
    private void handleReleaseNotes() throws IOException{
        openReleaseNotes();
    }
    /**
     * FXML Function to handle Edit-&gt;Undo
     * @see #handleUndoButton()
     */
    @FXML 
    private void handleUndoMenuItem(){
        undoButton.fire();
    }
    /**
     * FXML Function to handle Edit-&gt;Redo
     * @see #handleRedoButton()
     */
    @FXML 
    private void handleRedoMenuItem(){
        redoButton.fire();
    }
    /**
     * FXML Function to handle the undo button.
     * @see #undoAction() 
     */
    @FXML 
    private void handleUndoButton(){
        undoAction();
    }
    @FXML 
    private void handleRedoButton(){
        redoAction();
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
        closeStage();
    }
    @FXML
    private void handleSwapColorButton(){
        swapColors();
    }
    
    @FXML
    private void handleZoomInButton(){
        zoomIn();
        
    }
    @FXML
    private void handleZoomOutButton(){
        zoomOut();
    }
    /** 
     * Will JavaDoc later.
     * @param event to do
     */
    @FXML 
    private void setOnMousePressed(MouseEvent event){
        WritableImage writableImage = loadCurrentCanvas();
        undoHistory.push(writableImage);
        redoHistory.clear();
        if(drawButton.isSelected()){
            gcImage.setStroke(strokeColorPicker.getValue());
            gcImage.setLineWidth(slider.getValue());
        
            gcImage.beginPath();
            gcImage.lineTo(event.getX(),event.getY());
            
        }
        else if(lineButton.isSelected()){
            gcImage.setStroke(strokeColorPicker.getValue());
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
        
        else{}
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
        
        else if(eyedropperButton.isSelected()){
            int xPosistion = new Double(event.getX()).intValue();
            int yPosistion = new Double(event.getY()).intValue();
            Image image = loadImage();
            PixelReader pixelReader = image.getPixelReader();
            Color pixelClicked = pixelReader.getColor(xPosistion, yPosistion);
            
            fillColorPicker.setValue(pixelClicked);
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
            staticPane.setPrefSize(imageCanvas.getWidth(), imageCanvas.getHeight());
            //System.out.println("height:"+imageCanvas.getHeight()+"\nWidth:"+imageCanvas.getWidth());
            gcImage.drawImage(image,0,0);
            undoHistory.clear();
            redoHistory.clear();
            
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
                "*.png", "*.tiff", "*.tif","*.bmp", "*.JPEG", "*.JPG","*.PNG",
                "*.TIFF","*.TIF", "*.BMP"),
            new FileChooser.ExtensionFilter("JPEG", "*.jpeg", "*.jpg",
                "*.JPEG", "*.JPG"),
            new FileChooser.ExtensionFilter("PNG", "*.png", "*.PNG"),
            new FileChooser.ExtensionFilter("BMP", "*.bmp", "*.BMP"),
            new FileChooser.ExtensionFilter("TIFF", "*.tiff", "*.tif",
                "*.TIFF", "*.tif")
            );
    }
        private void configureFileChooser(FileChooser fileChooser, String title, boolean save){
        fileChooser.setTitle(title);
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("All Files", "*.jpeg", "*.jpg",
                "*.png", "*.tiff", "*.tif","*.bmp", "*.JPEG", "*.JPG","*.PNG",
                "*.TIFF","*.TIF", "*.BMP"),
            new FileChooser.ExtensionFilter("JPEG", "*.jpeg", "*.jpg",
                "*.JPEG", "*.JPG"),
            new FileChooser.ExtensionFilter("PNG", "*.png", "*.PNG"),
            new FileChooser.ExtensionFilter("BMP", "*.bmp", "*.BMP"),
            new FileChooser.ExtensionFilter("TIFF", "*.tiff", "*.tif",
                "*.TIFF", "*.tif")
            );
        fileChooser.setInitialFileName("untitled.png");
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
        if(hasBeenModified==true){
            setCloseAlerts();
        }
        else{
            Platform.exit();
        }
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
    private void imageSave(File file){
        try {
            if(file!=null){
                
                WritableImage writableImage = loadCurrentCanvas();
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(renderedImage, "png", file);
            }
            else{}
            
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
        Alert alert = createCloseAlertDialog();
        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");
        ButtonType cancelButton = new ButtonType("Cancel");
                
        alert.getButtonTypes().setAll(yesButton,noButton,cancelButton);
        
        Optional<ButtonType> result = alert.showAndWait();
        handleButtonTypeLogic(result, yesButton, noButton, cancelButton);
    }
    private Alert createCloseAlertDialog(){
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Warning!");
        alert.setHeaderText("Your file is not currently saved. Would you "
                + "like to save it?");
        alert.setContentText("Please choose an option");
        return alert;
    }
    private void handleButtonTypeLogic(Optional<ButtonType> result, 
            ButtonType yesButton, ButtonType noButton, ButtonType cancelButton ){
            if(result.get()==yesButton){
                if(file != null){
                    imageSave(file);
                }
                else{
                    setImagePath();
                    imageSave(file);
                }    
            } 
            else if(result.get()==noButton){
                Platform.exit();
            }
            else if(result.get() == cancelButton){
            }
    }
    private void openNewFile(){
        configureFileChooser(fileChooser, "Please Select an Image:");
        openedFile = fileChooser.showOpenDialog(borderPane.getScene().getWindow());
        if(openedFile!=null){
            loadFile(openedFile);
        }
        zoomOutButton.setDisable(false);
        zoomInButton.setDisable(false);
    }
    private void saveImageAs(){
        configureFileChooser(fileChooser, "Save File As: ", true);
        setImagePath();
        if(file != null){
            imageSave(file);
            hasBeenModified = false;
        }
    }
    private void saveImage(){
        configureFileChooser(fileChooser, "Save File: ", true);
        if(file != null){
            imageSave(file);
        }
        else{
            setImagePath();
            imageSave(file);
        }
        hasBeenModified = false;
    }
    
    private void createDialog(){
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
        gridPane.add(new Label("Resize the canvas to your desired pixels "
                + "WARNING: This can not be undone."),4,1);

        dialog.getDialogPane().setContent(gridPane);
        // Request focus on the username field by default.
        Platform.runLater(() -> width.requestFocus());
        
        dialog.showAndWait();
        
        
        WritableImage beforeResizeImage = loadCurrentCanvas();
        PixelReader pixelReader = beforeResizeImage.getPixelReader();
        if(pixelReader == null){
            return;
        }
        
        if(width.getText().isEmpty() || height.getText().isEmpty()){
            return;
        }
        
        int beforeResizeWidth = (int)imageCanvas.getWidth();
        int beforeResizeHeight = (int)imageCanvas.getHeight();
        int afterResizeWidth = Integer.parseInt(width.getText());
        int afterResizeHeight = Integer.parseInt(height.getText());
        
        afterResizeWidth = Math.abs(afterResizeWidth);
        afterResizeHeight = Math.abs(afterResizeHeight);
        
        WritableImage afterResizeImage = new WritableImage(afterResizeWidth,afterResizeHeight);
        PixelWriter pixelWriter = afterResizeImage.getPixelWriter();
        
        for(int resizeY = 0; resizeY < afterResizeHeight; resizeY++) {
            int previousY = (int)Math.round((double)resizeY / afterResizeHeight * beforeResizeHeight);
            for(int resizeX = 0; resizeX < afterResizeWidth; resizeX++) {
                int previousX = (int)Math.round((double)resizeX / afterResizeWidth * beforeResizeWidth);
                pixelWriter.setArgb(resizeX, resizeY, pixelReader.getArgb(previousX, previousY));
            }
        }
        imageCanvas.setWidth(afterResizeImage.getWidth());
        imageCanvas.setHeight(afterResizeImage.getHeight());
        gcImage.drawImage(afterResizeImage,0,0);        
    }
    private void createAboutDialog(){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("About PAIN(T) by Dylan Brown");
        alert.setContentText("This program attempts (poorly) to "
                + "recreate MS Paint.\nPray for me");
        alert.showAndWait();
    }
    private void openReleaseNotes() throws IOException{
        File releaseNotes = new File("releasenotes.txt");
        Desktop.getDesktop().open(releaseNotes);
    }
    private Image loadImage(){
        WritableImage image = new WritableImage((int)imageCanvas.getWidth(),(int)imageCanvas.getHeight());
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        imageCanvas.snapshot(params, image);
        return (Image) image;
    }
    private WritableImage loadCurrentCanvas(){
        WritableImage writableImage = new WritableImage((int)imageCanvas.getWidth(),(int)imageCanvas.getHeight());
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        imageCanvas.snapshot(params, writableImage);
        return writableImage;
    }
    private void undoAction(){
        if(!undoHistory.empty()){
            //gcImage.clearRect(0,0,imageCanvas.getWidth(), imageCanvas.getHeight());
            WritableImage writableImage = loadCurrentCanvas();
            redoHistory.push(writableImage);
            Image undoneImage = (Image) undoHistory.pop();
            gcImage.drawImage(undoneImage,0,0);
            hasBeenModified = true;
        }
        else{
            System.out.println("nothing to undo");
        }
    }
    private void redoAction(){
        if(!redoHistory.empty()){
           WritableImage writableImage = loadCurrentCanvas();
           undoHistory.push(writableImage);
           Image redoneImage = (Image) redoHistory.pop();
           gcImage.drawImage(redoneImage,0,0);
           hasBeenModified = true;
        }
        else{
            System.out.println("nothing to redo");
        }
    }
    private void swapColors(){
        Color tempColor = fillColorPicker.getValue();
        fillColorPicker.setValue(strokeColorPicker.getValue());
        strokeColorPicker.setValue(tempColor);
        tempColor = null; //destroy tempColor to save memory
    }
    private void zoomIn(){
        if(openedFile!=null){
            zoomInButton.setDisable(false);
            zoomOutButton.setDisable(false);
            staticPane.getTransforms().remove(zoomScale);
            currentZoom += 0.05;
            applyZoom(currentZoom);
            zoomLabel.setText(Math.round(currentZoom*100) + "%");
         }
        else{
            zoomOutButton.setDisable(true);
            zoomInButton.setDisable(true);
        }
    }
    private void zoomOut(){
        staticPane.getTransforms().remove(zoomScale);
        currentZoom -= 0.05;
        applyZoom(currentZoom);
        
        if(currentZoom <= 0.05){
            zoomOutButton.setDisable(true);
        }
        zoomLabel.setText(Math.round(currentZoom*100) + "%");
    }
    private void applyZoom(double zoom){
        zoomScale = new Scale(zoom, zoom,0,0);

        Scene scene = borderPane.getScene();

        staticPane.getTransforms().add(zoomScale);
    }
    private void zoomToX(double zoom){
        zoom/=100;
        staticPane.getTransforms().remove(zoomScale);
        currentZoom = zoom;
        applyZoom(currentZoom);
        zoomLabel.setText(Math.round(currentZoom*100) + "%");
        
    }
}  