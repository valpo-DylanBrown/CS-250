/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shapes;

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
    public void setEnd(double x, double y) {
        super.setEnd(x,y);
        path.add(new Point2D(x,y));
    }
    @Override
    public void draw(GraphicsContext gc){
        gc.strokeLine(x0, y0, x1, y1);
    }

}
