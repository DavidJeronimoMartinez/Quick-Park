package com.example.controllers;

import com.example.services.AuthService;
import com.example.vistas.BienvenidaView;
import com.example.vistas.RegistroView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController {
private Stage stage;

public void setStage(Stage stage) {
    this.stage = stage;
}
    @FXML private TextField txtNum;
    @FXML private PasswordField txtPassword;
    @FXML private Label mensaje;

    @FXML
    public void initialize() {
        if (txtNum != null) {
            txtNum.setTextFormatter(new TextFormatter<>(change -> {
                String nuevo = change.getControlNewText();
                if (nuevo.matches("\\d*") && nuevo.length() <= 4) return change;
                return null;
            }));
        }
    }

    @FXML
    private void login() {
        String numStr = txtNum.getText();
        String pass = txtPassword.getText();

        if (numStr.isEmpty() || pass.isEmpty()) {
            mostrarMensaje("Por favor, llena todos los campos", "#8D203D");
            return;
        }

        try {
            int numEmpleado = Integer.parseInt(numStr);

            if (AuthService.verificarUsuario(numEmpleado, pass)) {
                Stage currentStage = (Stage) txtNum.getScene().getWindow();
                
                new BienvenidaView().mostrar(currentStage, numEmpleado);
            } else {
                mostrarMensaje("Usuario o contraseña incorrectos", "#8D203D");
            }
        } catch (NumberFormatException e) {
            mostrarMensaje("Error: Ingresa 4 números", "#8D203D");
        } catch (Exception e) {
            mostrarMensaje("Fallo de conexión", "#8D203D");
        }
    }

@FXML
private void irARegistro() {
    try {
        Stage currentStage = (Stage) txtNum.getScene().getWindow();
        
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Registro.fxml")); 
        Parent root = loader.load();
        
        
        RegistroController controller = loader.getController();
        controller.setStage(currentStage);
        
        
        currentStage.getScene().setRoot(root);
        
    } catch (Exception e) {
        System.err.println("Error: No se encontró Registro.fxml en la raíz de resources.");
        e.printStackTrace();
    }
}

    private void mostrarMensaje(String texto, String color) {
        if (mensaje != null) {
            mensaje.setText(texto);
            mensaje.setStyle("-fx-text-fill: " + color + "; -fx-font-weight: bold;");
        }
    }
}