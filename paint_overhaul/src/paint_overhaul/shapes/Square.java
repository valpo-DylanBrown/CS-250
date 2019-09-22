package paint_overhaul.shapes;

import javafx.scene.canvas.GraphicsContext;

public class Square extends Rectangle {

    public Square(double x0, double y0, double x1, double y1) {
        super(x0, y0, x1, y1);
    }

    public Square(double x, double y) {
        this(x, y, x, y);
    }

    @Override
    public void draw(GraphicsContext gc) {
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
