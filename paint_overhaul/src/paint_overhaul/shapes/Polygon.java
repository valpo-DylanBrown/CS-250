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
public class Polygon extends PaintShape{
    private double rotation;
    public Polygon(double x, double y) {
        super(x,y,x,y);
        setIsPolygon();
        setRotation(Math.PI);
    }
    @Override
    public void setEnd(double x1, double y1) {
        super.setEnd(x1,y1);
    }
    @Override
    public void draw(GraphicsContext gc, int sides){
        boolean xPositive = x1 - x0 >= 0;
        boolean yPositive = y1 - y0 >= 0;   
        final double angleStep = Math.PI*2/sides;
        double angle = 0;
        double radiusDistance = Math.sqrt(Math.pow((x1-x0),2)+Math.pow((y1-y0),2));
        double[] xPoints = new double[sides];
        double[] yPoints = new double[sides];
        for(int i=0; i < sides; i++, angle+=angleStep){
        /* THIS CREATES A STAR with n = 5
        xPoints[i] = Math.sin(angle*3)*radiusDistance+x0;
            yPoints[i] = Math.cos(angle*3)*radiusDistance+y0;    
        */
            xPoints[i] = Math.sin(angle+rotation)*radiusDistance+x0;
            yPoints[i] = Math.cos(angle+rotation)*radiusDistance+y0;
        }
        gc.fillPolygon(xPoints, yPoints, sides);
        gc.strokePolygon(xPoints, yPoints, sides);
        
    }
    @Override
    public void draw(GraphicsContext gc){}
    @Override
    public void setIsPolygon(){
        isPolygon = true;
    }
    public void setRotation(double num){
        rotation = num;
    }
}
