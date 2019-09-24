/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint_overhaul.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 *
 * @author dylan
 */
public class SelectionRectangle extends Rectangle {
    private WritableImage image;
    private double origX, origY;
    private double endX, endY;
    
    public SelectionRectangle(double startX, double startY, double endX, double endY) {
        super(startX, startY, endX, endY);
    }
    public SelectionRectangle(double x, double y) {
        super(x,y,x,y);
        origX = x;
        origY = y;
    }
    
    @Override
    public void draw(GraphicsContext gc){
        boolean xPositive = x1 - x0 >= 0;
        boolean yPositive = y1 - y0 >= 0;
        gc.setFill(Color.TRANSPARENT);
        gc.setStroke(Color.BLACK);
        gc.setLineDashes(4);
        gc.setLineWidth(2);
        gc.fillRect(xPositive ? x0 : x1, yPositive ? y0 : y1, xPositive ? x1-x0 : x0-x1, yPositive ? y1-y0 : y0-y1);
        gc.strokeRect(xPositive ? x0 : x1, yPositive ? y0 : y1, xPositive ? x1-x0 : x0-x1, yPositive ? y1-y0 : y0-y1);
    }
    @Override
    public void draw(GraphicsContext gc, int sides){}
    @Override
    public void setEnd(double x, double y) {
        this.x1 = x;
        this.y1 = y;
        this.endX = x;
        this.endY = y;
    }
    public double getOrigX(){
        return origX;
    }
    public double getOrigY(){
        return origY;
    }
    public double getEndX(){
        return endX;
    }
    public double getEndY(){
        return endY;
    }
    public WritableImage getImage(){
        return image;
    }
    public void setImage(WritableImage image){
        this.image = image;
    }
    
}
