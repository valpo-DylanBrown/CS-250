/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint_overhaul.other;

import java.io.File;
import java.util.Stack;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import paint_overhaul.constant.DrawingTools;
import shapes.*;
/**
 *
 * @author dylan
 */
public class PaintCanvas {
    private Canvas canvas;
    private GraphicsContext gc;
    private Color strokeColor;
    private Color fillColor;
    private Image redrawnImage;
    private File openedFile;
    private DrawingTools drawTools;
    private PaintShape currentShape;
    private boolean hasBeenModified;
    private Stack<Image> undoHistory;
    private Stack<Image> redoHistory;
    
    PaintCanvas(Canvas canvas){
        undoHistory = new Stack<>();
        redoHistory = new Stack<>();
        this.canvas = canvas;
        strokeColor = Color.BLACK;
        fillColor = Color.TRANSPARENT;
        this.gc = canvas.getGraphicsContext2D();
        canvasSetup();
        redrawnImage = canvas.snapshot(null,null);
        //redrawCanvas();
    }
    private void canvasSetup(){
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, e->{
            if(drawTools == null){return;}
            redrawnImage = canvas.snapshot(null,null);
            switch(drawTools){
                case PENCIL:
                    currentShape = new Pencil(e.getX(), e.getY());
                    break;
                case LINE:
                    currentShape = new Line(e.getX(), e.getY());
                    break;
                case RECTANGLE:
                    currentShape = new Rectangle(e.getX(), e.getY());
                    break;
                case SQUARE:
                    currentShape = new Square(e.getX(), e.getY());
                    break;
                case CIRCLE:
                    currentShape = new Circle(e.getX(), e.getY());
                    break;
                case ELLIPSE:
                    currentShape = new Ellipse(e.getX(), e.getY());
                    break;
                case EYEDROPPER:
                    Color color = redrawnImage.getPixelReader().getColor((int)e.getX(), (int)e.getY());
                    
            }
            });
    }
    
    
            
}
