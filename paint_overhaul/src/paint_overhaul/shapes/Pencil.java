/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint_overhaul.shapes;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author dylan
 */
public class Pencil extends PaintShape {
    private List<Point2D> path;

    public Pencil(double x, double y) {
        super(x,y,x,y);
        path = new ArrayList<>();
        path.add(new Point2D(x,y));
    }
    @Override
    public void setEnd(double x1, double y1) {
        super.setEnd(x1,y1);
        path.add(new Point2D(x1,y1));
    }
    @Override
    public void draw(GraphicsContext gc){
        for(int i = 1; i < path.size(); i++) {
            Point2D from = path.get(i-1);
            Point2D to = path.get(i);
            gc.strokeLine(from.getX(), from.getY(), to.getX(), to.getY());
        }

    }
    @Override
    public void draw(GraphicsContext gc, int sides){}

}
