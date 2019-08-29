package jlab1b;
 
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.geometry.*; 
import javafx.scene.control.Alert.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
 
public class Jlab1b extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        //grid
        GridPane grid = new GridPane();
        //sets padding for a more user-friendly GUI
        grid.setPadding(new Insets(5,5,5,5));
        
        //x information 
        Label xLabel = new Label("x:        ");
        //add label to Grid C:0, R:1
        grid.add(xLabel,0,1);
        TextField xText = new TextField();
        //set width of text field box to clean up GUI
        xText.setMaxWidth(80);
        //add textfield to Grid C:2, R:1
        grid.add(xText,2,1);
        
         //y information
        Label yLabel = new Label("y:        ");
        //add label to Grid C:0, R:2
        grid.add(yLabel,0,2);
        TextField yText = new TextField();
        //set width of text field box to clean up GUI
        yText.setMaxWidth(80);
        //add label to Grid C:2, R:2
        grid.add(yText,2,2);
        
        Label mult = new Label("x*y:        ");
        //add label to Grid C:0, R:3
        grid.add(mult,0,3);
        Label rslt = new Label("0");
        //add label to Grid C:2, R:3
        grid.add(rslt,2,3);
        
        Button compute = new Button();
        compute.setText("Compute!");
        //sets min width to clean up GUI
        compute.setMinWidth(200);
        compute.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                //System.out.println("Hello World!");
                //System.out.println(xText.getText());
                try{
                //ty parsing the strings to ints and multiplying them
                  int x = Integer.parseInt(xText.getText());
                    int y = Integer.parseInt(yText.getText());
                    rslt.setText(String.valueOf(x*y));  
                }
                catch(Exception e){
                /*catch general error and throw an alert
                * this can be improved by using more specific exceptions
                * but it works for now.
                */
                  Alert alert = new Alert(AlertType.INFORMATION);
                  alert.setTitle("ERROR");
                  alert.setHeaderText(null);
                  alert.setContentText("Only Integers Allowed. Please Try Again.");
                  
                  alert.showAndWait();
                  
                }
            }
        });
        //add button to Grid C:0, R:5, CSPAN:3, RSPAN: 1
        grid.add(compute,0,5,3,1);
        
        

 Scene scene = new Scene(grid, 210, 110);
 
        primaryStage.setTitle("Multiplier");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
 public static void main(String[] args) {
        launch(args);
    }
}