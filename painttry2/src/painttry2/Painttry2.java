/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package painttry2;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import java.io.File;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 *
 * @author dylan
 */
public class Painttry2 extends Application {

    public Painttry2() {
        this.handleOpen = new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event) {
                FileChooser fc = new FileChooser();
                Label labeltest = new Label("test in HO");
                fc.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("All Files", "*.jpeg", "*.jpg",
                                "*.png", "*.tiff", "*.tif", "*.pdf", "*.JPEG", "*.JPG","*.PNG",
                                "*.TIFF","*.TIF", "*.PDF"),
                        new FileChooser.ExtensionFilter("JPEG", "*.jpeg", "*.jpg",
                                "*.JPEG", "*.JPG"),
                        new FileChooser.ExtensionFilter("PNG", "*.png", "*.PNG"),
                        new FileChooser.ExtensionFilter("TIFF", "*.tiff", "*.tif",
                                "*.TIFF", "*.tif"),
                        new FileChooser.ExtensionFilter("PDF", "*.pdf", "*.PDF")
                );
                File file = fc.showOpenDialog(labeltest.getScene().getWindow());
                if(file!=null){
                    try {
                        String filepath = file.toURI().toURL().toString();
                        System.out.println(filepath);
                        Image img = new Image(filepath);    
                        System.out.println("height:"+img.getHeight()+"\nWidth:"+img.getWidth());
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(Painttry2.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
            }
            
        };
    }
    
    @Override
    public void start(Stage primaryStage) {
        
        BorderPane root = new BorderPane();
        AnchorPane ap = new AnchorPane();
        Label label = new Label("test");
        Button btn = new Button("Load File");
        btn.setOnAction(handleOpen);
        ap.getChildren().add(btn);
        root.setCenter(ap);
        
  
        
        
        
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    EventHandler<ActionEvent> handleOpen;
}
