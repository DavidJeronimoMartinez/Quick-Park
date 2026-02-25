package com.example.controllers;

import com.example.services.AuthService;
import com.example.vistas.BienvenidaView;
import com.example.vistas.RegistroView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
    @FXML private TextField txtNum;
    @FXML private PasswordField txtPass;
    @FXML private Label mensaje;
    private Stage stage;

    public void setStage(Stage stage) { this.stage = stage; }

    @FXML
private void login() {
    System.out.println("--- INTENTANDO LOGIN ---");
    try {
        // VALIDACIÓN 1: Verificar que los campos de la interfaz existan
        if (txtNum == null || txtPass == null) {
            System.err.println("ERROR: Los campos FXML no están conectados. Revisa los fx:id en Scene Builder.");
            return;
        }

        if (txtNum.getText().isEmpty() || txtPass.getText().isEmpty()) {
            mensaje.setText("Completa los campos");
            return;
        }

        int num = Integer.parseInt(txtNum.getText());
        String password = txtPass.getText();

        // VALIDACIÓN 2: Llamada al servicio
        if (AuthService.login(num, password)) {
            System.out.println("Login exitoso en Backend. Cambiando de ventana...");

            // REPARACIÓN DEL STAGE: Si stage es null, lo obtenemos del botón/campo
            if (this.stage == null) {
                this.stage = (Stage) txtNum.getScene().getWindow();
            }

            // VALIDACIÓN 3: Abrir Bienvenida
            BienvenidaView bienvenida = new BienvenidaView();
            bienvenida.mostrar(this.stage, num); 
            
        } else {
            mensaje.setText("Número o contraseña incorrectos");
            mensaje.setStyle("-fx-text-fill: red;");
        }

    } catch (NumberFormatException e) {
        mensaje.setText("El número debe ser numérico");
    } catch (NullPointerException e) {
        System.err.println("ERROR: Se detectó un valor nulo. Imprimiendo detalles:");
        e.printStackTrace();
    } catch (Exception e) {
        System.err.println("Error inesperado:");
        e.printStackTrace();
    }
}

    @FXML
    private void irRegistro() {
        if (this.stage == null) this.stage = (Stage) txtNum.getScene().getWindow();
        RegistroView registro = new RegistroView();
        registro.mostrar(this.stage);
    }
}