package org.tweetwallfx.vdz;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TestMain extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    BorderPane borderPane = new BorderPane();
    Scene scene = new Scene(borderPane, 1920, 1080);
    borderPane.getStyleClass().add("splash");

    ImageView background =
        new ImageView(getClass().getResource("/vdz18-background-single.png").toString());
    borderPane.setCenter(background);

    primaryStage.setTitle("Test");
    primaryStage.setScene(scene);
    primaryStage.show();
    primaryStage.setFullScreen(!Boolean.getBoolean("org.tweetwallfx.disable-full-screen"));
  }

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    launch(args);
  }

}
