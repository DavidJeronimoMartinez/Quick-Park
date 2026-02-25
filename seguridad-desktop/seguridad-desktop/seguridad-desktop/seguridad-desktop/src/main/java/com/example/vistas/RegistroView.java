package com.example.vistas;

import com.example.controllers.RegistroController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RegistroView {
    public void mostrar(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/registro.fxml"));
            Parent root = loader.load();
            
            RegistroController controller = loader.getController();
            controller.setStage(stage);
            
            stage.setTitle("Seguridad - Crear Cuenta");
            stage.setScene(new Scene(root, 400, 500));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}