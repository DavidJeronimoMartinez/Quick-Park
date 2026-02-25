package com.example.controllers;

import com.example.vistas.LoginView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BienvenidaController {

    @FXML private Label lblMensaje;
    @FXML private TextField txtMatricula;
    @FXML private ComboBox<String> comboRol;
    @FXML private TextField txtPlaca;
    @FXML private TextField txtMarca;
    @FXML private TextField txtModelo;

    private Stage stage;
    private final HttpClient client = HttpClient.newHttpClient();

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setMensaje(int numEmpleado) {
        if (lblMensaje != null) {
            lblMensaje.setText("Bienvenido, Guardia #" + numEmpleado);
        }
    }

    @FXML
    public void initialize() {
        comboRol.getItems().addAll("Estudiante", "Docente");
    }

    @FXML
    private void guardarDatos() {
        String matricula = txtMatricula.getText();
        String rol = comboRol.getValue();
        String placa = txtPlaca.getText();
        String marca = txtMarca.getText();
        String modelo = txtModelo.getText();

        if (matricula.isEmpty() || rol == null || placa.isEmpty() || marca.isEmpty() || modelo.isEmpty()) {
            mostrarMensaje("Por favor llena todos los campos.", true);
            return;
        }

        try {
            // JSON combinado conductor + vehículo
            String json = """
                    {
                        "conductor": {
                            "matricula": "%s",
                            "rol": "%s"
                        },
                        "vehiculo": {
                            "placa": "%s",
                            "marca": "%s",
                            "modelo": "%s"
                        }
                    }
                    """.formatted(matricula, rol, placa, marca, modelo);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/registro-completo"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 400) {
                mostrarMensaje(response.body(), true);
            } else if (response.statusCode() == 200 || response.statusCode() == 201) {
                mostrarMensaje("Conductor y vehículo guardados correctamente.", false);
                limpiarCampos();
            } else {
                mostrarMensaje("Error al guardar. Código: " + response.statusCode(), true);
            }

        } catch (Exception e) {
            mostrarMensaje("Error de conexión con el servidor.", true);
            e.printStackTrace();
        }
    }

    private void mostrarMensaje(String mensaje, boolean esError) {
        lblMensaje.setText(mensaje);
        lblMensaje.setStyle(esError ? "-fx-text-fill: red;" : "-fx-text-fill: green;");
    }

    private void limpiarCampos() {
        txtMatricula.clear();
        comboRol.setValue(null);
        txtPlaca.clear();
        txtMarca.clear();
        txtModelo.clear();
    }

    @FXML
    private void cerrarSesion() {
        try {
            if (this.stage == null) {
                this.stage = (Stage) lblMensaje.getScene().getWindow();
            }
            LoginView login = new LoginView();
            login.mostrar(this.stage);
        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensaje("No se pudo cerrar sesión.", true);
        }
    }

  @FXML
private void abrirRegistros() {
    try {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/Registros.fxml")
        );

        Scene scene = new Scene(loader.load());

        Stage stage = (Stage) lblMensaje.getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    } catch (IOException e) {
        e.printStackTrace();
    }
}


}
