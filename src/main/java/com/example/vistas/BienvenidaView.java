package com.example.vistas;

import com.example.controllers.BienvenidaController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.URL;

public class BienvenidaView {
    public void mostrar(Stage stage, int num) {
        try {
            URL fxmlLocation = getClass().getResource("/bienvenida.fxml");

            if (fxmlLocation == null) {
                System.err.println(" ERROR: No se encontró 'bienvenida.fxml' en la carpeta resources.");
                return;
            }

            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent root = loader.load(); 

            BienvenidaController controller = loader.getController();
            if (controller != null) {
                controller.setStage(stage);
                controller.setMensaje(num); 
            }

            stage.setScene(new Scene(root));
            stage.setTitle("Bienvenido Guardia: " + num);
            stage.centerOnScreen();
            stage.show();
            
            System.out.println("Ventana de bienvenida cargada desde resources.");

        } catch (Exception e) {
            System.err.println(" Error al cargar bienvenida.fxml:");
            e.printStackTrace();
        }
    }
}