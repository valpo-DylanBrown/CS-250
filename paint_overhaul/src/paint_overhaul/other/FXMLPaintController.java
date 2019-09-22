package paint_overhaul.other;

//BIG TODO SPLIT THINGS UP INTO FUNCTIONS AND DIFFERENT FILES

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.util.Pair;
import paint_overhaul.constant.*;



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
public class FXMLPaintController extends DefaultController {
    @SuppressWarnings("LeakingThisInConstructor")
    
    @FXML private BorderPane borderPane;
    @FXML private Canvas canvas;
    @FXML private MenuBar menuBar;
    
    private PaintCanvas paintCanvas;
    
    private FileChooser fileChooser;
    
    @FXML ColorPicker strokeColorPicker;
    @FXML ColorPicker fillColorPicker;
    public ColorPicker getStrokeColorPicker(){
        return strokeColorPicker;
    }
    public ColorPicker getFillColorPicker(){
        return fillColorPicker;
    }
    @FXML private ToggleButton drawButton;
    @FXML private ToggleButton lineButton;
    @FXML private ToggleButton fillButton;
    @FXML private ToggleButton eraseButton;
    @FXML private ToggleButton rectButton;
    @FXML private ToggleButton squareButton;
    @FXML private ToggleButton ovalButton;
    @FXML private ToggleButton circleButton;
    @FXML private ToggleButton textButton;
    @FXML private ToggleButton eyedropperButton;
    
    @FXML private Button zoomInButton;
    @FXML private Button zoomOutButton;
    @FXML private Label zoomLabel;
    @FXML private Button swapColors;
    @FXML private Button closeButton;
    @FXML private Button undoButton;
    @FXML private Button redoButton;
    
    @FXML private MenuItem saveAsMenu;
    
    @FXML private ToolBar toolBar;
    
    @FXML private Slider slider;
    
    public FXMLPaintController(){
        Main.paintController = this;
    }
    public PaintCanvas getPaintCanvas(){
        return paintCanvas;
    }
    
    @FXML
    public void handleOpen(){
        configureFileChooser(fileChooser, "Open File: ");
        File fileToOpen = fileChooser.showOpenDialog(borderPane.getScene().getWindow());
        if(fileToOpen == null){
            return;
        }
        paintCanvas.loadImageFromFille(fileToOpen);
    }
    @FXML
    public void handleSave(){
        if(paintCanvas.getSavedFile() == null){
            saveAsMenu.fire();
            return;
        }
        paintCanvas.saveCanvasToFile(paintCanvas.getSavedFile()); 
    }
    @FXML
    public void handleSaveAs(){
        configureFileChooser(fileChooser, "Save File: ");
        File file = fileChooser.showSaveDialog(borderPane.getScene().getWindow());
        if(file==null){
            return;
        }
        paintCanvas.saveCanvasToFile(file); 
    }
    @FXML
    public void handleExit(){
        closeButton.fire();
    }
    @FXML
    public void handleAbout(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("About PAIN(T) by Dylan Brown");
        alert.setContentText("This program attempts (poorly) to "
                + "recreate MS Paint.\nPray for me");
        alert.showAndWait();
    }
    @FXML 
    public void handleReleaseNotes(){
        File releaseNotes = new File("../paint_overhaul/other/releasenotes.txt");
        try{
            Desktop.getDesktop().open(releaseNotes);
        }
        catch(IOException e){
            System.out.println("unable to open release notes");
        }  
    }
    @FXML
    public void handleUndoMenuItem(){
        undoButton.fire();
    }
    @FXML
    public void handleRedoMenuItem(){
        redoButton.fire();
    }
    @FXML
    public void handleResizeCanvas(){
        createResizeDialog();
    }
    @FXML
    public void handleUndoButton(){
        paintCanvas.undoLast();
    }
    @FXML
    public void handleRedoButton(){
        paintCanvas.redoLast();
    }
    @FXML
    public void handleStrokeColorPicker(){
        paintCanvas.setStrokeColor(strokeColorPicker.getValue());
    }
    @FXML
    public void handleFillColorPicker(){
        paintCanvas.setFillColor(fillColorPicker.getValue());
    }
    @FXML
    private void handleSwapColorButton(){
        Color tempColor = fillColorPicker.getValue();
        fillColorPicker.setValue(strokeColorPicker.getValue());
        paintCanvas.setFillColor(fillColorPicker.getValue());
        strokeColorPicker.setValue(tempColor);
        paintCanvas.setStrokeColor(strokeColorPicker.getValue());
        tempColor = null; //destroy tempColor to save memory
    }
    @FXML
    private void handleCloseButton(){
        if(paintCanvas.isHasBeenModified()==true){
            setCloseAlerts();
        }
        else{
            Platform.exit();
        }
    }
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
                if(paintCanvas.getSavedFile() != null){
                    handleSave();
                }
                else{
                    handleSaveAs();
                }    
            } 
            else if(result.get()==noButton){
                Platform.exit();
            }
            else if(result.get() == cancelButton){
            }
    }
    
    @FXML
    public void handleZoomInButton(){
        paintCanvas.zoomIn();
    }
    @FXML
    public void handleZoomOutButton(){
        paintCanvas.zoomOut();
    }
    @FXML
    public void handleZoomInMenu(){
        handleZoomInButton();
    }
    @FXML
    public void handleZoomOutMenu(){
        handleZoomOutButton();
    }
    @FXML
    public void handleZoom25Menu(){
        paintCanvas.zoomToX(25);
    }
    @FXML
    public void handleZoom50Menu(){
        paintCanvas.zoomToX(50);
    }
    @FXML
    public void handleZoom75Menu(){
        paintCanvas.zoomToX(75);
    }
    @FXML
    public void handleZoom100Menu(){
        paintCanvas.zoomToX(100);
    }
    @FXML
    public void handleZoom200Menu(){
        paintCanvas.zoomToX(200);
    }
    @FXML
    public void handleZoom250Menu(){
        paintCanvas.zoomToX(250);
    }
    @FXML
    public void handleZoom300Menu(){
        paintCanvas.zoomToX(300);
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
    
    public void createResizeDialog(){
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Resize Canvas");

        // Set the button types.
        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
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
        
        
        WritableImage beforeResizeImage = paintCanvas.snapshotCurrentCanvas();
        PixelReader pixelReader = beforeResizeImage.getPixelReader();
        if(pixelReader == null){
            return;
        }
        
        if(width.getText().isEmpty() || height.getText().isEmpty()){
            return;
        }
        
        int beforeResizeWidth = (int)paintCanvas.getCanvas().getWidth();
        int beforeResizeHeight = (int)paintCanvas.getCanvas().getHeight();
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
        paintCanvas.getCanvas().setWidth(afterResizeImage.getWidth());
        paintCanvas.getCanvas().setHeight(afterResizeImage.getHeight());
        paintCanvas.getGraphicsContext().drawImage(afterResizeImage,0,0);        
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        slider.valueProperty().addListener((e) -> paintCanvas.setLineWidth(slider.getValue()));
        fileChooser = new FileChooser();
        
        paintCanvas = new PaintCanvas(canvas);
        
    }
}  