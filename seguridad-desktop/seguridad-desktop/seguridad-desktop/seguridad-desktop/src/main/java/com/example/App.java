package com.example;

import com.example.vistas.LoginView;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage ventana) {
        LoginView login = new LoginView();
        login.mostrar(ventana);
    }

    public static void main(String[] args) {
        launch(args);
    }
}