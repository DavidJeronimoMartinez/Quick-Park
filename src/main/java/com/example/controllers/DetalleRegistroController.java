package com.example.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class DetalleRegistroController {
    @FXML private TextField txtMatricula, txtPlaca, txtMarca, txtModelo;
    @FXML private ComboBox<String> comboRol, comboTipo;
    @FXML private Button btnGuardar, btnModificar, btnEliminar, btnRegresar, btnCancelar;

    private final String DB_URL = "jdbc:mysql://localhost:3306/seguridad";

    @FXML
    public void initialize() {
        if (comboRol != null) comboRol.getItems().setAll("Estudiante", "Empleado");
        if (comboTipo != null) comboTipo.getItems().setAll("AUTO", "MOTO");
        bloquearCampos();
    }

    public void setRegistro(com.example.controllers.Registro reg) {
        if (reg != null) {
            txtMatricula.setText(reg.getMatricula());
            comboRol.setValue(reg.getRol());
            comboTipo.setValue(reg.getTipo());
            txtPlaca.setText(reg.getPlaca());
            txtMarca.setText(reg.getMarca());
            txtModelo.setText(reg.getModelo());
        }
    }

    @FXML
    private void activarEdicion() {
        toggleCampos(true);
        btnModificar.setDisable(true);
        btnEliminar.setDisable(true);
        btnRegresar.setDisable(true);
        btnGuardar.setVisible(true);
        btnGuardar.setDisable(false);
        if (btnCancelar != null) btnCancelar.setVisible(true);
    }

    @FXML
    private void cancelarEdicion() {
        bloquearCampos();
    }

    @FXML
    private void guardarCambios() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "¿Deseas guardar los cambios?", ButtonType.YES, ButtonType.NO);
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) ejecutarUpdate();
        });
    }

    private void ejecutarUpdate() {
        String tabla = comboTipo.getValue().equals("AUTO") ? "vehiculo" : "moto";
        String colPlaca = comboTipo.getValue().equals("AUTO") ? "placa_coche" : "placa_moto";
        String sql = "UPDATE " + tabla + " SET " + colPlaca + " = ?, marca = ?, modelo = ? WHERE matricula_o_num_empleado = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, "root", "");
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, txtPlaca.getText().trim());
            ps.setString(2, txtMarca.getText().trim());
            ps.setString(3, txtModelo.getText().trim());
            ps.setString(4, txtMatricula.getText().trim());

            if (ps.executeUpdate() > 0) {
                mostrarAlerta("Éxito", "Datos actualizados correctamente.");
                volver();
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void bloquearCampos() {
        toggleCampos(false);
        btnModificar.setDisable(false);
        btnEliminar.setDisable(false);
        btnRegresar.setDisable(false);
        btnGuardar.setVisible(false);
        if (btnCancelar != null) btnCancelar.setVisible(false);
    }

    private void toggleCampos(boolean editable) {
        txtPlaca.setEditable(editable);
        txtMarca.setEditable(editable);
        txtModelo.setEditable(editable);
        txtPlaca.setOpacity(editable ? 1.0 : 0.5);
        txtMarca.setOpacity(editable ? 1.0 : 0.5);
        txtModelo.setOpacity(editable ? 1.0 : 0.5);
        txtMatricula.setOpacity(0.5);
    }

    @FXML
    private void eliminarRegistro() { }

   @FXML
private void volver() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Registros.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

        
        Stage currentStage = (Stage) txtMatricula.getScene().getWindow();
        currentStage.close();

    } catch (IOException e) {
        e.printStackTrace();
    }
}

    private void mostrarAlerta(String t, String m) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(t); a.setContentText(m); a.showAndWait();
    }
}