/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint_overhaul.other;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 *
 * @author Dylan
 */
public class DefaultController implements Initializable {
    protected PaintCanvas paintCanvas;
    
    
    @Override
    public void initialize(URL url, ResourceBundle resources){
        paintCanvas = Main.paintController.getPaintCanvas();
    }
}
