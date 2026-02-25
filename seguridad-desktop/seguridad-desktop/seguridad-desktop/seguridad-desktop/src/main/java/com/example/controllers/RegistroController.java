package com.example.controllers;

import com.example.services.AuthService;
import com.example.vistas.LoginView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegistroController {
    @FXML private TextField txtNum;
    @FXML private TextField txtNombre;
    @FXML private TextField txtApellidoP;
    @FXML private TextField txtApellidoM;
    @FXML private PasswordField txtPass;
    @FXML private PasswordField txtPassConfirm;
    @FXML private Label mensaje;
    private Stage stage;

    public void setStage(Stage stage) { this.stage = stage; }

@FXML
private void registrar() {
    try {
        // 1. Validaciones de campos vacíos
        if (txtNum.getText().isEmpty() || txtPass.getText().isEmpty() || txtNombre.getText().isEmpty()) {
            mensaje.setText("Completa los campos obligatorios");
            mensaje.setStyle("-fx-text-fill: orange;");
            return;
        }

        int num = Integer.parseInt(txtNum.getText());
        String nombre = txtNombre.getText();
        String ap = txtApellidoP.getText();
        String am = txtApellidoM.getText();
        String pass = txtPass.getText();
        String confirm = txtPassConfirm.getText();

        // 2. Validación de coincidencia
        if (!pass.equals(confirm)) {
            mensaje.setStyle("-fx-text-fill: red;");
            mensaje.setText("Las contraseñas no coinciden");
            return;
        }

        // 3. Intento de registro (El AuthService ahora debe validar num y pass)
        boolean exito = AuthService.registrarUsuario(num, nombre, ap, am, pass);

        if (exito) {
            mensaje.setStyle("-fx-text-fill: green;");
            mensaje.setText("Guardia registrado con éxito");
            
            // Opcional: Esperar un segundo para que el usuario lea el mensaje verde
            volverLogin(); 
        } else {
            // Este mensaje aparecerá si el servidor detecta duplicado de número O contraseña
            mensaje.setStyle("-fx-text-fill: red;");
            mensaje.setText("Datos duplicados: El número o contraseña ya existen");
        }
    } catch (NumberFormatException e) {
        mensaje.setStyle("-fx-text-fill: red;");
        mensaje.setText("El número de empleado debe ser numérico");
    }
}

// Este método sirve tanto para después del registro como para un botón de "Cancelar"
@FXML
private void volverLogin() {
    LoginView login = new LoginView();
    login.mostrar(stage);
}
}