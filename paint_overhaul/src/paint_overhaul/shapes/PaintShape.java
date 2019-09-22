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
public abstract class PaintShape {
    protected double x0, x1;
    protected double y0, y1;
    
    protected boolean isPolygon;
    
    public PaintShape(double x0, double y0, double x1, double y1) {
        this.x0 = x0;
        this.y0 = y0;
        this.x1 = x1;
        this.y1 = y1;
    }

    public abstract void draw(GraphicsContext gc);
    public abstract void draw(GraphicsContext gc, int sides);

    public void setEnd(double x, double y) {
        this.x1 = x;
        this.y1 = y;
    }
    public boolean getIsPolygon(){
        return isPolygon;
    }
    public void setIsPolygon(){
        isPolygon = false;
    }

}
