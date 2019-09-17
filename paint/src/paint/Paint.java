package paint;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
    static final double DEFAULT_WIDTH = 1024;
    static final double DEFAULT_HEIGHT = 760;
    
    
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
        Parent root = FXMLLoader.load(FXMLPaintController.class.getResource("fmxl_paint.fxml"));
        
        Scene scene = new Scene(root, DEFAULT_WIDTH , DEFAULT_HEIGHT);
        stage.setTitle(PROGRAM_NAME);
        stage.setScene(scene);
        stage.setMinWidth(MIN_PROGRAM_WIDTH);
        stage.setMinHeight(MIN_PROGRAM_HEIGHT);
        /* If you need to disable the windows Min, Max, and Close
         * do this
         * stage.initStyle(StageStyle.UNDECORATED);
        */
        Image icon = new Image(getClass().getResourceAsStream("icon.png"));
        stage.getIcons().add(icon);
        stage.setMaximized(true);
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
