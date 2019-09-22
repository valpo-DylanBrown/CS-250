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
public class Triangle extends PaintShape {
    
    public Triangle(double startX, double startY, double endX, double endY) {
        super(startX, startY, endX, endY);
    }
    public Triangle(double x, double y) {
        super(x,y,x,y);
    }
    @Override
    public void draw(GraphicsContext gc){
        double x2 = ((x0 + x1 + Math.sqrt(3)*(y0-y1))/2);
        double y2 = ((y0 + y1 + Math.sqrt(3)*(x1-x0))/2);
        
        gc.fillPolygon(new double[]{x0,x1,x2}, new double[]{y0,y1,y2}, 3);
        gc.strokePolygon(new double[]{x0,x1,x2}, new double[]{y0,y1,y2}, 3);
    }
    @Override
    public void draw(GraphicsContext gc, int sides){}
}
