/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint_overhaul.alerts;

import java.io.File;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Dylan
 */
public class ReleaseNotesAlert {
    public String content;
    //public TextArea area;
    String filePath  = "src/paint_overhaul/other/releasenotes.txt";
    String iconFilePath = "src/paint_overhaul/icons/alertIcon.png";
    public void createAlert(){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Release Notes");
        alert.setHeaderText("Release Notes for Pain(t)");

        
        
        content = this.setContent(filePath);
        TextArea area = new TextArea(content);
        area.setWrapText(true);
        area.setEditable(false);
        area.setPrefWidth(500);
        area.setPrefHeight(600);
        alert.getDialogPane().setContent(area);
        alert.setResizable(true);
        File file = new File(iconFilePath);
        Image image = new Image(file.toURI().toString(), 50, 50, true, true);
        ImageView icon = new ImageView(image);
        alert.getDialogPane().setGraphic(icon);
        alert.showAndWait();
    }
    private String setContent(String filePath)
    {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8))
        {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        }
        catch (IOException e)
        {
            System.out.println("error");
        }
        return contentBuilder.toString();
    }
}
