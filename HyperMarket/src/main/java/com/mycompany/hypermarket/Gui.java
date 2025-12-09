package com.mycompany.hypermarket;

import javafx.application.Application ;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 *
 * @author user
 */
public class Gui extends Application {

    public static void main(String[]args){
       launch();
    }
  @Override
    public void start (Stage stage) throws Exception{
        stage.setTitle("welcome");
        stage.setScene(new Scene(new Button("click")));
        stage.show();
    }
}