/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint_overhaul.shapes;


import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;


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
    /*private WritableImage image;
    double origX, origY;
    double endX, endY;
    boolean copyMode = false;
    boolean pasteMode = false;
    private ImageView currentSelection = null;
    
    public SelectionRectangle(double x0, double y0) {
        super(x0, y0, x0, y0);
    }
    
    @Override
    public void draw(GraphicsContext gc) {
        boolean xPositive = x1 - x0 >= 0;
        boolean yPositive = y1 - y0 >= 0;
        
        Paint beforeStrokeColor = gc.getStroke();
        Paint beforeFillColor = gc.getFill();
        double beforeLineWidth = gc.getLineWidth();
        
        gc.setFill(Color.TRANSPARENT);
        gc.setStroke(Color.BLACK);
        gc.setLineDashes(4);
        gc.setLineWidth(1);
        gc.fillRect(xPositive ? x0 : x1, yPositive ? y0 : y1, xPositive ? x1-x0 : x0-x1, yPositive ? y1-y0 : y0-y1);
        gc.strokeRect(xPositive ? x0 : x1, yPositive ? y0 : y1, xPositive ? x1-x0 : x0-x1, yPositive ? y1-y0 : y0-y1);
        gc.setStroke(beforeStrokeColor);
        gc.setFill(beforeFillColor);
        gc.setLineDashes(0);
        gc.setLineWidth(beforeLineWidth);  
    }
    public void setImage(Canvas canvas, GraphicsContext gc){
        
        selectionSetup(canvas, gc);
       
        Paint colorBeforeErase = gc.getFill();
        currentSelection.setX(origX);
        currentSelection.setY(origY);
        
        
        currentSelection.setOnMousePressed(event -> {
            if(!copyMode){
                gc.setFill(Color.WHITE);
                gc.fillRect(origX, origY, Math.abs(endX - origX), Math.abs(endY - origY));
            }
            origX = (event.getX()- currentSelection.getX());
            origY = (event.getY()- currentSelection.getY());
        });
        currentSelection.setOnMouseDragged(event -> {
            currentSelection.setX(event.getX()- origX);
            currentSelection.setY(event.getY()- origY);
            event.consume();
        });
        currentSelection.setOnMouseReleased(event -> {
            //selection.setIsDragging(false);
            Main.paintController.getStaticPane().getChildren().remove(currentSelection);
            
            gc.drawImage(currentSelection.getImage(),currentSelection.getX(),currentSelection.getY());
            currentSelection = null;
            
            copyMode = false;
            gc.setFill(colorBeforeErase); 
        });
        
        Main.paintController.getStaticPane().getChildren().add(currentSelection);
        
        gc.setFill(colorBeforeErase); 

    }
    private void selectionSetup(Canvas canvas, GraphicsContext gc){
        
        WritableImage oldImg = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
        Main.paintController.getPaintCanvas().redoLast();
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
    public void setCopyMode(boolean bool){
        this.copyMode = bool;
    }
    public ImageView getCurrentSelection(){
        return currentSelection;
    }
    @Override
    public void draw(GraphicsContext gc, int sides) {}*/
    
    private WritableImage image;
    private PaintCanvas paintCanvas;
    double origX, origY;
    double endX, endY;
    boolean copyMode = false;
    boolean pasteMode = false;
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
            //Main.paintController.getStaticPane().getChildren().remove(Main.paintController.getPaintCanvas().getRectangle().getRect());
            if(!copyMode){
                gc.setFill(Color.WHITE);
                gc.fillRect(origX, origY, Math.abs(endX - origX), Math.abs(endY - origY));
            }
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
            Main.paintController.getPaintCanvas().setCurrentSelectionNulll();
            setCopyMode(false);
            gc.setFill(colorBeforeErase);
            
        });
        //double width = selection.getEndX() - selection.getOrigX();
        //double height = selection.getEndY() - selection.getOrigY();
        //gc.drawImage(currentSelection.getImage(), selection.getEndX(),selection.getEndY());
        Main.paintController.getStaticPane().getChildren().add(currentSelection);
        
        gc.setFill(colorBeforeErase); 

    }
    private void selectionSetup(Canvas canvas, GraphicsContext gc){
        
        WritableImage oldImg = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
        Main.paintController.getPaintCanvas().redoLast();
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
    public void setCopyMode(boolean bool){
        this.copyMode = bool;
    }
    public ImageView getCurrentSelection(){
            return currentSelection;
        }
    
    
}
