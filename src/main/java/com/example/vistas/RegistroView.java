package com.example.vistas;

import com.example.controllers.RegistrosController; 
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RegistroView {
    public void mostrar(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Registros.fxml")); 
            Parent root = loader.load();
            
            RegistrosController controller = loader.getController();
            
            stage.setTitle("Seguridad - Vista de Conductores");
            stage.setScene(new Scene(root, 1100, 700)); 
            stage.show();
        } catch (Exception e) {
            System.err.println("Error al cargar la tabla de registros: " + e.getMessage());
            e.printStackTrace();
        }
    }
}