package org.tweetwallfx.vdz;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class TestMain extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
    System.out.println("Visual bounds");
    System.out.println("min x \t" + visualBounds.getMinX());
    System.out.println("min y \t" + visualBounds.getMinY());
    System.out.println("width \t" + visualBounds.getWidth());
    System.out.println("height \t" + visualBounds.getHeight());

    System.out.println();

    Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
    System.out.println("Bounds");
    System.out.println("min x \t" + bounds.getMinX());
    System.out.println("min y \t" + bounds.getMinY());
    System.out.println("width \t" + bounds.getWidth());
    System.out.println("height \t" + bounds.getHeight());

    Scene scene = new Scene(new Group(), 1920, 1080);
    scene.setFill(Color.YELLOW);
    primaryStage.setHeight(1080);
    primaryStage.setWidth(1920);
    primaryStage.setTitle("Test");
    primaryStage.setScene(scene);
    primaryStage.show();
    primaryStage.setFullScreen(true);
  }

  public static void main(String[] args) {
    launch(args);
  }
}
