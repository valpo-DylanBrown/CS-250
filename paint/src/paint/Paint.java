package paint;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



/**
 * Constructor for MS PAIN(t) program.
 * This class creates the program 
 * It has constants to set the smallest the window should go at (400 x 150) 
 * It also sets a constant for the program name. 
 * @author dylan
 * @version 2.0
 * @since 1.0
 * 
 */
public class Paint extends Application {
    static final double MIN_PROGRAM_WIDTH = 400;
    static final double MIN_PROGRAM_HEIGHT = 150;
    static final String PROGRAM_NAME = "PAIN(t)";
    
    /**
     * This function initializes the root and scene for the program.
     * The function first loads the FXML document into the parent. It then uses
     * GraphicsDevice to grab the screen resolution for the computer it is being
     * run on. It then sets the root, width, and height of the scene. Lastly, it
     * titles the program, sets the minimum width and height, and shows it to
     * to the user
     * @param stage top level container
     * @throws Exception if FXML file cannot be found or loaded 
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("fmxl_paint.fxml"));
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        Scene scene = new Scene(root, width , height);
        stage.setTitle(PROGRAM_NAME);
        stage.setScene(scene);
        stage.setMinWidth(MIN_PROGRAM_WIDTH);
        stage.setMinHeight(MIN_PROGRAM_HEIGHT);
        //stage.setMaximized(true);
        stage.show();
    }

    /**
     * This function launches the arguments
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
