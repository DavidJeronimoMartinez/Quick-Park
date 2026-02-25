package com.example.controllers;

import com.example.services.AuthService;
import com.example.vistas.LoginView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import java.util.function.UnaryOperator;

public class RegistroController {
    @FXML private TextField txtNum;
    @FXML private TextField txtNombre;
    @FXML private TextField txtApellidoP;
    @FXML private TextField txtApellidoM;
    @FXML private PasswordField txtPass;
    @FXML private PasswordField txtPassConfirm;
    @FXML private Label mensaje;
    private Stage stage;

    public void setStage(Stage stage) { 
        this.stage = stage; 
    }

    @FXML
    public void initialize() {
        
        UnaryOperator<TextFormatter.Change> filtroLetras = change -> {
            
            if (change.getText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*")) {
                return change;
            }
            return null;
        };

        if (txtNombre != null) txtNombre.setTextFormatter(new TextFormatter<>(filtroLetras));
        if (txtApellidoP != null) txtApellidoP.setTextFormatter(new TextFormatter<>(filtroLetras));
        if (txtApellidoM != null) txtApellidoM.setTextFormatter(new TextFormatter<>(filtroLetras));

        
        if (txtNum != null) {
            txtNum.setTextFormatter(new TextFormatter<>(change -> {
                String nuevoTexto = change.getControlNewText();
                if (nuevoTexto.matches("\\d*") && nuevoTexto.length() <= 4) {
                    return change;
                }
                return null;
            }));
        }
    }

    @FXML
    private void registrar() {
        try {

            if (txtNum.getText().trim().isEmpty() || 
                txtPass.getText().isEmpty() || 
                txtNombre.getText().trim().isEmpty() || 
                txtApellidoP.getText().trim().isEmpty()) {
                
                actualizarMensaje("Completa todos los campos obligatorios", "#C29D5B");
                return;
            }

            if (txtNum.getText().trim().length() != 4) {
                actualizarMensaje("El número de empleado debe ser de 4 dígitos", "#8D203D");
                return;
            }

            String pass = txtPass.getText();
            String confirm = txtPassConfirm.getText();

            if (!pass.equals(confirm)) {
                actualizarMensaje("Las contraseñas no coinciden", "#8D203D");
                return;
            }

            int num = Integer.parseInt(txtNum.getText().trim());
            boolean exito = AuthService.registrarUsuario(
                num, 
                txtNombre.getText().trim(), 
                txtApellidoP.getText().trim(), 
                txtApellidoM.getText().trim(), 
                pass
            );

            if (exito) {
                actualizarMensaje("Guardia registrado con éxito", "#009432");
                
                volverLogin(); 
            } else {
                actualizarMensaje("Error: El número de empleado ya existe", "#8D203D");
            }

        } catch (NumberFormatException e) {
            actualizarMensaje("El número de empleado debe ser numérico", "#8D203D");
        } catch (Exception e) {
            actualizarMensaje("Error inesperado en el sistema", "#8D203D");
            e.printStackTrace();
        }
    }

    private void actualizarMensaje(String texto, String colorHex) {
        if (mensaje != null) {
            mensaje.setText(texto);
            mensaje.setStyle("-fx-text-fill: " + colorHex + "; -fx-font-weight: bold;");
        }
    }

    @FXML
    private void volverLogin() {
        Stage currentStage = (this.stage != null) ? this.stage : (Stage) txtNum.getScene().getWindow();
        
        LoginView login = new LoginView();
        login.mostrar(currentStage);
    }
}