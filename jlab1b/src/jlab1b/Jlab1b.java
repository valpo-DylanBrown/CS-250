import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.File;
import java.net.MalformedURLException;

public class Jlab1b extends Application {

    @Override
    public void start(Stage stage) throws MalformedURLException {
        stage.setTitle("HTML");
        stage.setWidth(500);
        stage.setHeight(500);
        Scene scene = new Scene(new Group());
        VBox root = new VBox();    

        final ImageView selectedImage = new ImageView();   
        Image image1 = new Image(new File("/users/dylan/downloads/background.jpg").toURI().toURL().toExternalForm());
        //Image image1 = new Image(Jlab1b.class.getResourceAsStream("C:\\Users\\user\\Desktop\\x.jpg"));

        selectedImage.setImage(image1);

        root.getChildren().addAll(selectedImage);

        scene.setRoot(root);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}