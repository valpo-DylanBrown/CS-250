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
public class Circle extends Ellipse {
    public Circle(double startX, double startY, double endX, double endY) {
        super(startX, startY, endX, endY);
    }
    public Circle(double x, double y) {
        super(x,y,x,y);
    }
    @Override
    public void draw(GraphicsContext gc){
        double maxWidth = Math.min(Math.abs(x1-x0), Math.abs(y1-y0));
        if(Math.abs(y1-y0) > maxWidth)
            if(y1 < y0)
                y1 = y0 - maxWidth;
            else
                y1 = y0 + maxWidth;
        if(Math.abs(x1-x0) > maxWidth)
            if(x1 < x0)
                x1 = x0 - maxWidth;
            else
                x1 = x0 + maxWidth;
        super.draw(gc);
    }
}
