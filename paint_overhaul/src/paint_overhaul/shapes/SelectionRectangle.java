/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint_overhaul.shapes;

import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
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
    
    public void setImage(GraphicsContext gc){
        boolean xPositive = endX - origX >= 0;
        boolean yPositive = endY - origY >= 0;
        SnapshotParameters params = new SnapshotParameters();
        params.setViewport(new Rectangle2D(xPositive ? origX : endX, yPositive ? origY : endY, xPositive ? endX-origX : origX-endX, yPositive ? endY-origY : origY-endY));
        params.setFill(Color.TRANSPARENT);
        image = new WritableImage(xPositive ? (int)endX-(int)origX : (int)origX-(int)endX, yPositive ? (int)endY-(int)origY : (int)origY-(int)endY);
        paintCanvas.getCanvas().snapshot(null,null);
        //paintCanvas.loadImage(image);
        imgView = new ImageView(image);
        /*
        gc.setFill(Color.TRANSPARENT);
        gc.setStroke(Color.BLACK);
        gc.setLineDashes(4);
        gc.setLineWidth(2);
        gc.fillRect(xPositive ? x0 : x1, yPositive ? y0 : y1, xPositive ? x1-x0 : x0-x1, yPositive ? y1-y0 : y0-y1);
        gc.strokeRect(xPositive ? x0 : x1, yPositive ? y0 : y1, xPositive ? x1-x0 : x0-x1, yPositive ? y1-y0 : y0-y1);
        */
    }
    public void selectionSetup(){
        //handle movements
        //handle other stuff
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
