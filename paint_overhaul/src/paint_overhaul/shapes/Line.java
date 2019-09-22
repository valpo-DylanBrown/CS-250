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
public class Line extends PaintShape {
    
    public Line(double startX, double startY, double endX, double endY) {
        super(startX, startY, endX, endY);
    }
    public Line(double x, double y) {
        super(x,y,x,y);
    }
    @Override
    public void draw(GraphicsContext gc){
        gc.strokeLine(x0, y0, x1, y1);
    }
    @Override
    public void draw(GraphicsContext gc, int sides){}

}
