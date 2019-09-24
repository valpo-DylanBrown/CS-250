package paint_overhaul.other;

//BIG TODO SPLIT THINGS UP INTO FUNCTIONS AND DIFFERENT FILES

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import paint_overhaul.constant.*;
import paint_overhaul.alerts.*;
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
    @FXML private ReleaseNotesAlert releaseNotesAlert;
    @FXML private AboutAlert aboutAlert;
    @FXML private SmartSaveAlert smartSaveAlert;
    @FXML private ResizeCanvasAlert resizeCanvasAlert;
    private PaintCanvas paintCanvas;
    
    private BetterFileChooser openFileChooser;
    private BetterFileChooser saveFileChooser;
    
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
    @FXML private ToggleButton eraserButton;
    @FXML private ToggleButton rectButton;
    @FXML private ToggleButton squareButton;
    @FXML private ToggleButton ovalButton;
    @FXML private ToggleButton circleButton;
    @FXML private ToggleButton triangleButton;
    @FXML private ToggleButton polygonButton;
    @FXML private ToggleButton starButton;
    @FXML private ToggleButton textButton;
    @FXML private ToggleButton eyedropperButton;
    @FXML private ToggleButton selectionRectangle;
    
    @FXML private Button zoomInButton;
    @FXML private Button zoomOutButton;
    @FXML private Label zoomLabel;
    @FXML private Button swapColors;
    @FXML private Button closeButton;
    @FXML private Button undoButton;
    @FXML private Button redoButton;
    
    @FXML private MenuItem saveAsMenu;
    
    @FXML private ToolBar toolBar;
    @FXML private ComboBox<String> fontChooser;
    @FXML private Spinner fontSizeSpinner;
    @FXML private TextField userText;
    
    @FXML private Spinner polygonSpinner;
    @FXML private Spinner widthSpinner;
    
    public FXMLPaintController(){
        Main.paintController = this;
    }
    public PaintCanvas getPaintCanvas(){
        return paintCanvas;
    }
    @FXML
    public void handleOpen(){
        
        File fileToOpen = openFileChooser.getFileChooser().showOpenDialog(borderPane.getScene().getWindow());
        if(fileToOpen == null){
            return;
        }
        paintCanvas.loadImageFromFille(fileToOpen);
        zoomInButton.setDisable(false);
        zoomOutButton.setDisable(false);
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
        File file = saveFileChooser.getFileChooser().showSaveDialog(borderPane.getScene().getWindow());
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
        aboutAlert.createAboutAlert();
    }
    @FXML 
    public void handleReleaseNotes(){
        releaseNotesAlert.createAlert();
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
        resizeCanvasAlert.createResizeDialog();
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
    public void handleFontChooser(){
        paintCanvas.setFont(new Font(fontChooser.getValue(), (int)fontSizeSpinner.getValue()));
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
        if(paintCanvas.getHasBeenModified()==true){
            smartSaveAlert.setSmartSaveAlert();
        }
        else{
            Platform.exit();
        }
    }
    @FXML
    public void handleZoomInButton(){
        paintCanvas.zoomIn();
        zoomOutButton.setDisable(false);
        updateZoomLabel();
    }
    @FXML
    public void handleZoomOutButton(){
        paintCanvas.zoomOut();
        if(paintCanvas.getIsZoomedOut() == true){
            zoomOutButton.setDisable(true);
        }
        updateZoomLabel();
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
        updateZoomLabel();
    }
    @FXML
    public void handleZoom50Menu(){
        paintCanvas.zoomToX(50);
        updateZoomLabel();
    }
    @FXML
    public void handleZoom75Menu(){
        paintCanvas.zoomToX(75);
        updateZoomLabel();
    }
    @FXML
    public void handleZoom100Menu(){
        paintCanvas.zoomToX(100);
        updateZoomLabel();
    }
    @FXML
    public void handleZoom200Menu(){
        paintCanvas.zoomToX(200);
        updateZoomLabel();
    }
    @FXML
    public void handleZoom250Menu(){
        paintCanvas.zoomToX(250);
        updateZoomLabel();
    }
    @FXML
    public void handleZoom300Menu(){
        paintCanvas.zoomToX(300);
        updateZoomLabel();
    }
    public void updateZoomLabel(){
        zoomLabel.setText(Math.round(paintCanvas.getCurrentZoom()*100)+"% ");
    }
    @FXML
    public void handleDrawToggle(){
        if(drawButton.isSelected()){
            paintCanvas.setDrawingTool(DrawingTools.PENCIL);
        }
        else{
            paintCanvas.setDrawingTool(null);
        }
    }
    @FXML
    public void handleStarButton(){
        if(starButton.isSelected()){
            paintCanvas.setDrawingTool(DrawingTools.STAR);
        }
        else{
            paintCanvas.setDrawingTool(null);
        }
    }
    @FXML
    public void handleLineToggle(){
        if(lineButton.isSelected()){
            paintCanvas.setDrawingTool(DrawingTools.LINE);
        }
        else{
            paintCanvas.setDrawingTool(null);
        }
    }
    public void handleEraserToggle(){
        if(eraserButton.isSelected()){
            paintCanvas.setDrawingTool(DrawingTools.ERASER);
        }
        else{
            paintCanvas.setDrawingTool(null);
        }
    }
    @FXML
    public void handleRectToggle(){
        if(rectButton.isSelected()){
            paintCanvas.setDrawingTool(DrawingTools.RECTANGLE);
        }
        else{
            paintCanvas.setDrawingTool(null);
        }
    }
    @FXML
    public void handleCircleToggle(){
       if(circleButton.isSelected()){
            paintCanvas.setDrawingTool(DrawingTools.CIRCLE);
        }
        else{
            paintCanvas.setDrawingTool(null);
        }
    }
    @FXML
    public void handleSquareToggle(){
        if(squareButton.isSelected()){
            paintCanvas.setDrawingTool(DrawingTools.SQUARE);
        }
        else{
            paintCanvas.setDrawingTool(null);
        }
    }
    @FXML
    public void handleOvalToggle(){
        if(ovalButton.isSelected()){
            paintCanvas.setDrawingTool(DrawingTools.ELLIPSE);
        }
        else{
            paintCanvas.setDrawingTool(null);
        }
    }
    @FXML
    public void handleTriangleToggle(){
        if(triangleButton.isSelected()){
            paintCanvas.setDrawingTool(DrawingTools.TRIANGLE);
        }
        else{
            paintCanvas.setDrawingTool(null);
        }
    }
    @FXML
    public void handlePolygonButton(){
        if(polygonButton.isSelected()){
            paintCanvas.setDrawingTool(DrawingTools.POLYGON);
        }
        else{
            paintCanvas.setDrawingTool(null);
        }
    }
    @FXML
    public void handleTextToggle(){
        if(textButton.isSelected()){
            paintCanvas.setDrawingTool(DrawingTools.TEXT);
        }
        else{
            paintCanvas.setDrawingTool(null);
        }
    }
    @FXML
    public void handleEDToggle(){
        if(eyedropperButton.isSelected()){
            paintCanvas.setDrawingTool(DrawingTools.EYEDROPPER);
        }
        else{
            paintCanvas.setDrawingTool(null);
        }
    }
    public void handleSelectionRectangle(){
        if(selectionRectangle.isSelected()){
            //paintCanvas.setDrawingMode(DrawingMode.SELECT);
            paintCanvas.setDrawingTool(DrawingTools.SELECTIONRECTANGLE);
        }
        else{
            paintCanvas.setDrawingTool(null);
        }
    }
    
    public BorderPane getBorderPane(){
        return borderPane;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //slider.valueProperty().addListener((e) -> paintCanvas.setLineWidth(slider.getValue()));
        configurePolygonSpinner(polygonSpinner);
        configureWidthSpinner(widthSpinner);
        configureFontSizeSpinner(fontSizeSpinner);
        
        fontChooser.getItems().setAll(Font.getFamilies());
        fontChooser.getSelectionModel().selectFirst();
        userText.textProperty().addListener((observable, oldValue, newValue) -> {
            paintCanvas.setUserText(newValue);
        });
        openFileChooser = new BetterFileChooser("Open Image: ");
        saveFileChooser = new BetterFileChooser("Save Canvas: ");
        paintCanvas = new PaintCanvas(canvas);
        releaseNotesAlert = new ReleaseNotesAlert();
        aboutAlert = new AboutAlert();
        smartSaveAlert = new SmartSaveAlert(paintCanvas, saveFileChooser);
        resizeCanvasAlert = new ResizeCanvasAlert(paintCanvas);
    }
    public void configureWidthSpinner(Spinner spinner) throws NumberFormatException{
        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 100));
        TextFormatter formatter = new TextFormatter(spinner.getValueFactory().getConverter(), spinner.getValueFactory().getValue());
        spinner.getEditor().setTextFormatter(formatter);
        // bidi-bind the values
        spinner.getValueFactory().valueProperty().bindBidirectional(formatter.valueProperty());
        spinner.valueProperty().addListener((e) -> paintCanvas.setLineWidth((int) spinner.getValue()));
    }
    public void configurePolygonSpinner(Spinner spinner) throws NumberFormatException{
        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 100));
        TextFormatter formatter = new TextFormatter(spinner.getValueFactory().getConverter(), spinner.getValueFactory().getValue());
        spinner.getEditor().setTextFormatter(formatter);
        // bidi-bind the values
        spinner.getValueFactory().valueProperty().bindBidirectional(formatter.valueProperty());
        spinner.valueProperty().addListener((e) -> paintCanvas.setNumSides((int) spinner.getValue()));
    }
    public void configureFontSizeSpinner(Spinner spinner) throws NumberFormatException{
        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100));
        TextFormatter formatter = new TextFormatter(spinner.getValueFactory().getConverter(), spinner.getValueFactory().getValue());
        spinner.getEditor().setTextFormatter(formatter);
        // bidi-bind the values
        spinner.getValueFactory().valueProperty().bindBidirectional(formatter.valueProperty());
        spinner.valueProperty().addListener((e) -> paintCanvas.setFont(new Font(fontChooser.getValue(),(int) spinner.getValue())));
    }
}  