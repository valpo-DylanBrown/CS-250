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
public class Star extends Polygon {
    public Star(double x, double y) {
        super(x,y);
    }
    @Override
    public void draw(GraphicsContext gc, int sides){
        boolean xPositive = x1 - x0 >= 0;
        boolean yPositive = y1 - y0 >= 0;   
        final double angleStep = Math.PI*2/5;
        double angle = 0;
        double radiusDistance = Math.sqrt(Math.pow((x1-x0),2)+Math.pow((y1-y0),2));
        double[] xPoints = new double[5];
        double[] yPoints = new double[5];
        for(int i=0; i < 5; i++, angle+=angleStep){
            xPoints[i] = Math.sin(angle*3+Math.PI)*radiusDistance+x0;
            yPoints[i] = Math.cos(angle*3+Math.PI)*radiusDistance+y0;
        }
        gc.fillPolygon(xPoints, yPoints, 5);
        gc.strokePolygon(xPoints, yPoints, 5);
        
    }
}
