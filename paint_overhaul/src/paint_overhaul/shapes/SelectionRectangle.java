/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint_overhaul.shapes;

import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import paint_overhaul.other.Main;
import paint_overhaul.other.PaintCanvas;

/**
 * SelectionRectangle object. This class needs to be overhauled and is only 
 * partially functional. The code to handle the select and move can be see
 * in {@link PaintCanvas#canvasSetup()}
 * @author dylan
 */
public class SelectionRectangle {
    private WritableImage image;
    private ImageView imgView;
    private PaintCanvas paintCanvas;
    double origX, origY;
    double endX, endY;
    boolean isDragging = false;
    private ImageView currentSelection;
    public SelectionRectangle(double startX, double startY, double endX, double endY) {
        origX = startX;
        origY = startY;
        this.endX = endX;
        this.endY = endY;
    }
    public SelectionRectangle(double x, double y) {
        origX = x;
        origY = y;
        endX = x;
        endY = y;
    }
    
    public void setEnd(double x, double y){
        endX = x;
        endY = y;
    }
    
    public void setImage(Canvas canvas, GraphicsContext gc){
        selectionSetup(canvas, gc);
        Paint colorBeforeErase = gc.getFill();
        currentSelection.setX(origX);
        currentSelection.setY(origY);

        currentSelection.setOnMousePressed(event -> {
            gc.setFill(Color.WHITE);
            gc.fillRect(origX, origY, Math.abs(endX - origX), Math.abs(endY - origY));
            origX = (event.getX()- currentSelection.getX());
            origY = (event.getY()- currentSelection.getY());
        });
        currentSelection.setOnMouseDragged(event -> {
            //System.out.println("DRAGGED1");
            currentSelection.setX(event.getX()- origX);
            currentSelection.setY(event.getY()- origY);
            event.consume();
            //System.out.println("DRAGGED");
            //selection.setEndX(currentSelection.getX());
            //selection.setEndY(currentSelection.getY());
            //selection.setIsDragging(true);
        });
        currentSelection.setOnMouseReleased(event -> {
            //selection.setIsDragging(false);
            Main.paintController.getStaticPane().getChildren().remove(currentSelection);
            gc.drawImage(currentSelection.getImage(),currentSelection.getX(),currentSelection.getY());
            //selection.getOrigX(), selection.getOrigY(), currentSelection.getX()-selection.getOrigX(), currentSelection.getY()-selection.getOrigY()
            currentSelection = null;
        });
        //double width = selection.getEndX() - selection.getOrigX();
        //double height = selection.getEndY() - selection.getOrigY();
        //gc.drawImage(currentSelection.getImage(), selection.getEndX(),selection.getEndY());
        Main.paintController.getStaticPane().getChildren().add(currentSelection);
        gc.setFill(colorBeforeErase); 

    }
    private void selectionSetup(Canvas canvas, GraphicsContext gc){
        WritableImage oldImg = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
        canvas.snapshot(null, oldImg);
        
        if(origX > endX){
            double temp = endX;
            endX = origX;
            origX = temp; 
        }
        if(origY > endY){
            double temp = endY;
            endY = origY;
            origY = temp;
        }
        WritableImage newImage = new WritableImage(oldImg.getPixelReader(), (int)origX, (int)origY, (int)Math.abs(origX-endX), (int)Math.abs(origY-endY));
        //selectedImage = newImage;
        
        currentSelection = new ImageView(newImage);
    }
    public Image getImage(){
        return imgView.getImage();
    }
    public double getOrigX(){
        return origX;
    }
    public double getEndX(){
        return endX;
    }
    public double getOrigY(){
        return origY;
    }
    public double getEndY(){
        return endY;
    }
    public void setOrigX(double origX){
        this.origX = origX;
    }
    public void setEndX(double endX){
        this.endX = endX;
    }
    public void setOrigY(double origY){
        this.origY = origY;
    }
    public void setEndY(double endY){
        this.endY = endY;
    }
    public void setIsDragging(boolean bool){
        this.isDragging = bool;
    }
    public boolean getIsDragging(){
        return isDragging;
                
    }
}
