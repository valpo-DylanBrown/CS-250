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
 * @author Dylan
 */
public class Eraser extends Pencil {
    public Eraser(double x, double y){
        super(x,y);
    }
    
    @Override
    public void draw(GraphicsContext gc){
        Paint colorBeforeErase = gc.getStroke();
        double lineWidthBefore = gc.getLineWidth();
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(lineWidthBefore*2);
        super.draw(gc);
        gc.setStroke(colorBeforeErase);
        gc.setLineWidth(lineWidthBefore);
    }
}
