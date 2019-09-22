/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint_overhaul.shapes;

import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author dylan
 */
public class Ellipse extends PaintShape {
    public Ellipse(double startX, double startY, double endX, double endY) {
        super(startX, startY, endX, endY);
    }
    public Ellipse(double x, double y) {
        super(x,y,x,y);
    }
    @Override
    public void draw(GraphicsContext gc){
        boolean xPositive = x1 - x0 >= 0;
        boolean yPositive = y1 - y0 >= 0;
        gc.fillOval(xPositive ? x0 : x1, yPositive ? y0 : y1, xPositive ? x1-x0 : x0-x1, yPositive ? y1-y0 : y0-y1);
        gc.strokeOval(xPositive ? x0 : x1, yPositive ? y0 : y1, xPositive ? x1-x0 : x0-x1, yPositive ? y1-y0 : y0-y1);
    }
    @Override
    public void draw(GraphicsContext gc, int sides){}
}
