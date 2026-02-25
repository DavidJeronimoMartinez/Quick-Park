package com.example.controllers;

import com.example.vistas.LoginView;
import com.example.vistas.RegistroView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.function.UnaryOperator;

public class BienvenidaController {

    @FXML private Label lblMensaje;
    @FXML private TextField txtMatricula, txtPlaca, txtMarca, txtModelo;
    @FXML private ComboBox<String> comboRol, comboTipo;
    @FXML private Button btnVistaEstacionamiento;

    private final String URL = "jdbc:mysql://localhost:3306/seguridad";
    private final String USER = "root";
    private final String PASS = "";

    public void setStage(Stage stage) { }

    @FXML
    public void initialize() {
        if (comboRol != null) comboRol.getItems().setAll("Estudiante", "Empleado");
        if (comboTipo != null) comboTipo.getItems().setAll("AUTO", "MOTO");
        configurarValidaciones();
    }

    private void configurarValidaciones() {
        txtMatricula.setTextFormatter(new TextFormatter<>(change ->
                (change.getControlNewText().matches("\\d*") &&
                        change.getControlNewText().length() <= 10) ? change : null));

        txtPlaca.setTextFormatter(new TextFormatter<>(change -> {
            change.setText(change.getText().toUpperCase());
            return (change.getControlNewText().matches("[A-Z0-9-]*") &&
                    change.getControlNewText().length() <= 8) ? change : null;
        }));

        UnaryOperator<TextFormatter.Change> filtroLetras = change ->
                (change.getControlNewText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*") &&
                        change.getControlNewText().length() <= 20) ? change : null;

        txtMarca.setTextFormatter(new TextFormatter<>(filtroLetras));
        txtModelo.setTextFormatter(new TextFormatter<>(filtroLetras));
    }

    @FXML
    private void guardarDatos() {
        String matricula = txtMatricula.getText().trim();
        String placa = txtPlaca.getText().trim();
        String marca = txtMarca.getText().trim();
        String modelo = txtModelo.getText().trim();
        String rol = comboRol.getValue();
        String tipo = comboTipo.getValue();

        if (matricula.isEmpty() || placa.isEmpty() || marca.isEmpty() || modelo.isEmpty() || rol == null || tipo == null) {
            mostrarAlerta(Alert.AlertType.ERROR, "Campos Incompletos", "Debe llenar todos los campos.");
            return;
        }

        String tabla = tipo.equals("AUTO") ? "vehiculo" : "moto";
        String colPlaca = tipo.equals("AUTO") ? "placa_coche" : "placa_moto";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {

            conn.setAutoCommit(false);

            String sqlC = "INSERT IGNORE INTO conductor (matricula_o_num_empleado, rol) VALUES (?, ?)";
            String sqlV = "INSERT INTO " + tabla +
                    " (matricula_o_num_empleado, " + colPlaca + ", marca, modelo) VALUES (?, ?, ?, ?)";

            try (PreparedStatement ps1 = conn.prepareStatement(sqlC);
                 PreparedStatement ps2 = conn.prepareStatement(sqlV)) {

                ps1.setString(1, matricula);
                ps1.setString(2, rol);
                ps1.executeUpdate();

                ps2.setString(1, matricula);
                ps2.setString(2, placa);
                ps2.setString(3, marca);
                ps2.setString(4, modelo);
                ps2.executeUpdate();

                conn.commit();

                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Vehículo registrado correctamente.");

                abrirVistaEstacionamiento(tipo, matricula, placa);

            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }

        } catch (SQLException | IOException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo procesar: " + e.getMessage());
        }
    }

    @FXML
    private void irAVistaEstacionamiento() {
        try {
            String tipoDefault = (comboTipo.getValue() != null)
                    ? comboTipo.getValue()
                    : "AUTO";

            abrirVistaEstacionamiento(tipoDefault, null, null);

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo abrir el mapa.");
        }
    }

    private void abrirVistaEstacionamiento(String tipo,
                                           String matricula,
                                           String placa) throws IOException {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/estacionamiento.fxml")
        );

        Parent root = loader.load();

        EstacionamientoController controller = loader.getController();

        if (controller == null) {
            throw new IOException("No se pudo obtener el controller del mapa.");
        }

        controller.setDatosTransaccion(tipo, matricula, placa);

        Stage stage = (Stage) txtMatricula.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    private void abrirRegistros() {
        try {
            new RegistroView().mostrar((Stage) txtMatricula.getScene().getWindow());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cerrarSesion() {
        new LoginView().mostrar((Stage) txtMatricula.getScene().getWindow());
    }

    private void mostrarAlerta(Alert.AlertType tipo,
                               String titulo,
                               String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void setMensaje(int numEmpleado) {
        if (lblMensaje != null)
            lblMensaje.setText("Bienvenido, Guardia #" + numEmpleado);
    }
}