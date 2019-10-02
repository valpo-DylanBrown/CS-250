/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint_overhaul.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Rectangle object. This extends {@link PaintShape}.
 * @author dylan
 * @since 4.0
 */
public class Rectangle extends PaintShape {
    /**
     * 4-input constructor for Rectangle object. 
     * @param startX Starting X location. 
     * @param startY Starting Y location. 
     * @param endX Ending X location. 
     * @param endY Ending Y location. 
     */
    public Rectangle(double startX, double startY, double endX, double endY) {
        super(startX, startY, endX, endY);
    }
    /**
     * 2-input constructor for Ellipse. 
     * @param x X location.
     * @param y Y location. 
     */
    public Rectangle(double x, double y) {
        super(x,y,x,y);
    }
    /**
     * Override for draw in PaintCanvas. This function handles users drawing 
     * rectangles in any direction. It then strokes and fills the rectangle.
     * @param gc GraphicsContext for the PaintCanvas. 
     */
    @Override
    public void draw(GraphicsContext gc){
        boolean xPositive = x1 - x0 >= 0;
        boolean yPositive = y1 - y0 >= 0;
        gc.fillRect(xPositive ? x0 : x1, yPositive ? y0 : y1, xPositive ? x1-x0 : x0-x1, yPositive ? y1-y0 : y0-y1);
        gc.strokeRect(xPositive ? x0 : x1, yPositive ? y0 : y1, xPositive ? x1-x0 : x0-x1, yPositive ? y1-y0 : y0-y1);
    }
    /**
     * Drawing function for polygon shapes. 
     * @param gc GraphicsContext for the PaintCanvas.
     * @param sides Number of sides desired.
     * @deprecated  
     */
    @Override
    public void draw(GraphicsContext gc, int sides){}
    /**
     * Special draw when PaintCanvas needs to draw a dashed rectangle. This 
     * gets the original characteristics, and resets them after drawing. 
     * @param gc GraphicsContext for the PaintCanvas. 
     */
    public void drawSelection(GraphicsContext gc){
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

}
