/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2022 TweetWallFX
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
  public void start(Stage primaryStage) {
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
