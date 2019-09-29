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
 *
 * @author dylan
 */
public class Rectangle extends PaintShape {
    public Rectangle(double startX, double startY, double endX, double endY) {
        super(startX, startY, endX, endY);
    }
    public Rectangle(double x, double y) {
        super(x,y,x,y);
    }
    @Override
    public void draw(GraphicsContext gc){
        boolean xPositive = x1 - x0 >= 0;
        boolean yPositive = y1 - y0 >= 0;
        gc.fillRect(xPositive ? x0 : x1, yPositive ? y0 : y1, xPositive ? x1-x0 : x0-x1, yPositive ? y1-y0 : y0-y1);
        gc.strokeRect(xPositive ? x0 : x1, yPositive ? y0 : y1, xPositive ? x1-x0 : x0-x1, yPositive ? y1-y0 : y0-y1);
    }
    @Override
    public void draw(GraphicsContext gc, int sides){}
    
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
