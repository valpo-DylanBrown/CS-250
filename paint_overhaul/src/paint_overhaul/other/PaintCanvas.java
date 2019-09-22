/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint_overhaul.other;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Stack;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javax.imageio.ImageIO;
import paint_overhaul.constant.DrawingTools;
import paint_overhaul.shapes.*;

/**
 *
 * @author dylan
 */
public class PaintCanvas {
    private Canvas canvas;
    private GraphicsContext gc;
    private Color strokeColor;
    private Color fillColor;
    private Image redrawnImage;
    private File openedFile;
    private File savedFile;
    private DrawingTools drawTools;
    private PaintShape currentShape;
    private boolean hasBeenModified;
    private Stack<Image> undoHistory;
    private Stack<Image> redoHistory;
    private double currentZoom = 1;
    private Scale zoomScale;
    
    PaintCanvas(Canvas canvas){
        undoHistory = new Stack<>();
        redoHistory = new Stack<>();
        this.canvas = canvas;
        strokeColor = Color.BLACK;
        fillColor = Color.TRANSPARENT;
        this.gc = canvas.getGraphicsContext2D();
        canvasSetup();
        redrawnImage = canvas.snapshot(null,null);
        redrawCanvas();
    }
    private void canvasSetup(){
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, e->{
            if(drawTools == null){
                System.out.println("Draw Tools NULL");
                return;
            }
            redrawnImage = canvas.snapshot(null,null);
            switch(drawTools){
                case PENCIL:
                    currentShape = new Pencil(e.getX(), e.getY());
                    break;
                case LINE:
                    currentShape = new Line(e.getX(), e.getY());
                    break;
                case RECTANGLE:
                    currentShape = new Rectangle(e.getX(), e.getY());
                    break;
                case SQUARE:
                    currentShape = new Square(e.getX(), e.getY());
                    break;
                case CIRCLE:
                    currentShape = new Circle(e.getX(), e.getY());
                    break;
                case ELLIPSE:
                    currentShape = new Ellipse(e.getX(), e.getY());
                    break;
                case EYEDROPPER:
                    Color color = redrawnImage.getPixelReader().getColor((int)e.getX(), (int)e.getY());
                    Main.paintController.getFillColorPicker().setValue(color);
                    setFillColor(color);
                    break;
            }
            hasBeenModified = true;
            });
         canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, e->{
                if(drawTools == null || drawTools == DrawingTools.EYEDROPPER){
                    return;
                }
                redrawCanvas();
                currentShape.setEnd(e.getX(), e.getY());
                currentShape.draw(gc);
                hasBeenModified = true;
            });
            canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, e->{
                if(drawTools == null || drawTools == DrawingTools.EYEDROPPER){
                    return;
                }
                undoHistory.add(redrawnImage);
                redrawCanvas();
                currentShape.setEnd(e.getX(), e.getY());
                currentShape.draw(gc);
                redrawnImage = canvas.snapshot(null,null);
                hasBeenModified = true;
            });
    }
    public void setDrawingToolMode(DrawingTools type){
        this.drawTools=type;
    }
    public void setLineWidth(double width){
        gc.setLineWidth(width);
    }
    public void setFillColor(Color color){
        this.fillColor = color;
        gc.setFill(color);
    }
    public void setStrokeColor(Color color){
        this.strokeColor = color;
        gc.setStroke(color);
    }
    public void undoLast(){
        if(undoHistory.isEmpty()){
            return;
        }
        redoHistory.push(redrawnImage);
        redrawnImage = undoHistory.pop();
        redrawCanvas();
    }
    public void redoLast(){
        if(redoHistory.isEmpty()){
            return;
        }
        undoHistory.push(redrawnImage);
        redrawnImage = redoHistory.pop();
        redrawCanvas();
    }
    private void redrawCanvas(){
        gc.drawImage(redrawnImage, 0, 0, redrawnImage.getWidth(), redrawnImage.getHeight());
    }
    public Canvas getCanvas(){
        return canvas;
    }
    public File getOpenedFile(){
        return openedFile;
    }
    public File getSavedFile(){
        return savedFile;
    }
    public boolean isHasBeenModified(){
        return hasBeenModified;                
    }
    public void loadImageFromFille(File imageFile){
        Image image = null;
        try{
            image = new Image(new FileInputStream(imageFile.getAbsolutePath()));
  
        }
        catch(FileNotFoundException e){
            System.out.println("file not found");
        }
        openedFile = imageFile;
        loadImage(image);
    }
    public void loadImage(Image image){
        canvas.setWidth(image.getWidth());
        canvas.setHeight(image.getHeight());
        gc.drawImage(image,0,0,image.getWidth(),image.getHeight());
    }
    public void saveCanvasToFile(File file) {
        savedFile = file;
        WritableImage writableImage = snapshotCurrentCanvas();
        RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
        try{
            ImageIO.write(renderedImage, "png", file);
        }
        catch(IOException e){
            System.out.println("File not found");
        }
    }
    public WritableImage snapshotCurrentCanvas(){
        WritableImage writableImage = new WritableImage((int)canvas.getWidth(),(int)canvas.getHeight());
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        canvas.snapshot(params, writableImage);
        return writableImage;
    }
    public GraphicsContext getGraphicsContext(){
        return gc;
    }
    public double getCurrentZoom(){
        return currentZoom;
    }
    public void zoomIn(){
        Parent parent = canvas.getParent();
        parent.getTransforms().remove(zoomScale);
        currentZoom += 0.05;
        applyZoom(parent, currentZoom);
        //zoomLabel.setText(Math.round(currentZoom*100) + "%");
    }
    private void applyZoom(Parent parent, double zoom){
        zoomScale = new Scale(zoom, zoom,0,0);
        parent.getTransforms().add(zoomScale);
    }
    public void zoomOut(){
        Parent parent = canvas.getParent();
        parent.getTransforms().remove(zoomScale);
        currentZoom -= 0.05;
        applyZoom(parent, currentZoom);
    }
    public void zoomToX(double zoom){
        Parent parent = canvas.getParent();
        zoom/=100;
        parent.getTransforms().remove(zoomScale);
        currentZoom = zoom;
        applyZoom(parent, currentZoom);
        //zoomLabel.setText(Math.round(currentZoom*100) + "%");
        
    }
}