/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint_overhaul.shapes;

import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author Dylan
 */
public class Text extends PaintShape{
    private String text;

    public Text(double x, double y, String text) {
        super(x, y, x, y);
        this.text = text;
    }
    @Override
    public void draw(GraphicsContext gc){
        gc.fillText(text, x1, y1);
        //gc.strokeText(text, x1, y1);
    }
    @Override
    public void draw(GraphicsContext gc, int sides){}

}
