package com.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.*;
import javafx.scene.shape.SVGPath;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import java.sql.*;

public class EstacionamientoController {

    @FXML private GridPane gridAutos;
    @FXML private GridPane gridMotos;
    @FXML private Label lblTitulo;
    @FXML private Label lblDispCoches;
    @FXML private Label lblDispMotos;

    private String tipoVehiculo;
    private String matriculaPendiente;
    private String placaPendiente;

    private final String DB_URL = "jdbc:mysql://localhost:3306/seguridad";
    private final String DB_USER = "root";
    private final String DB_PASS = "";

    private static final int COLUMNAS = 8;

    public void setDatosTransaccion(String tipo, String matricula, String placa) {

        this.tipoVehiculo = (tipo == null) ? "CONSULTA" : tipo;
        this.matriculaPendiente = matricula;
        this.placaPendiente = placa;

        if (matriculaPendiente != null && !matriculaPendiente.isEmpty()) {
            lblTitulo.setText("ASIGNAR LUGAR A: " + placaPendiente);
        } else {
            lblTitulo.setText("ESTADO DEL ESTACIONAMIENTO");
        }

        cargarMapa();
    }

    private void cargarMapa() {

        gridAutos.getChildren().clear();
        gridMotos.getChildren().clear();

        String sql = "SELECT * FROM estacionamiento_3 ORDER BY lugar ASC";

        int dispAutos = 0;
        int dispMotos = 0;

        int indexAutos = 0;
        int indexMotos = 0;

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                int numero = rs.getInt("lugar");
                String estado = rs.getString("estado");
                String tipoBD = rs.getString("tipo");

                if ("Disponible".equalsIgnoreCase(estado)) {
                    if ("AUTO".equalsIgnoreCase(tipoBD)) dispAutos++;
                    if ("MOTO".equalsIgnoreCase(tipoBD)) dispMotos++;
                }

                Button btn = crearBoton(numero, estado, tipoBD);

                if ("AUTO".equalsIgnoreCase(tipoBD)) {

                    int col = indexAutos % COLUMNAS;
                    int row = indexAutos / COLUMNAS;

                    gridAutos.add(btn, col, row);
                    indexAutos++;

                } else {

                    int col = indexMotos % COLUMNAS;
                    int row = indexMotos / COLUMNAS;

                    gridMotos.add(btn, col, row);
                    indexMotos++;
                }
            }

            lblDispCoches.setText("Coches Libres: " + dispAutos);
            lblDispMotos.setText("Motos Libres: " + dispMotos);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

private Button crearBoton(int numero, String estado, String tipoBD) {

    Button btn = new Button();
    btn.setPrefSize(110, 70);

    VBox contenido = new VBox(5);
    contenido.setStyle("-fx-alignment: center;");

    Label lblNumero = new Label(String.valueOf(numero));
    lblNumero.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");

    if ("Disponible".equalsIgnoreCase(estado)) {

        Label lblDisponible = new Label("Disponible");
        lblDisponible.setStyle("-fx-text-fill: white; -fx-font-size: 10px;");

        contenido.getChildren().addAll(lblNumero, lblDisponible);
        btn.setGraphic(contenido);

        String colorBase = "AUTO".equalsIgnoreCase(tipoBD)
                ? "#27ae60"
                : "#2980b9";

        btn.setStyle(
                "-fx-background-color: " + colorBase + ";" +
                "-fx-background-radius: 10;"
        );

        if (matriculaPendiente != null) {

            if (tipoVehiculo.equalsIgnoreCase(tipoBD)) {

                btn.setOnMouseEntered(e ->
                        btn.setStyle("-fx-background-color: #f1c40f; -fx-background-radius: 10;")
                );

                btn.setOnMouseExited(e ->
                        btn.setStyle("-fx-background-color: " + colorBase + "; -fx-background-radius: 10;")
                );

                btn.setOnAction(e -> confirmarAsignacion(numero));

            } else {

                btn.setOnAction(e -> {
                    Alert alert = new Alert(Alert.AlertType.WARNING,
                            "Este espacio es para " + tipoBD);
                    alert.showAndWait();
                });
            }

        } else {
            btn.setDisable(true);
            btn.setOpacity(0.7);
        }

    } else {

        
        Node icono = crearIcono(tipoBD);

        contenido.getChildren().addAll(icono, lblNumero);
        btn.setGraphic(contenido);

        btn.setStyle(
                "-fx-background-color: #8D203D;" +
                "-fx-background-radius: 10;"
        );

        btn.setDisable(true);
    }

    return btn;
}

private Node crearIcono(String tipo) {

    SVGPath svg = new SVGPath();

    if ("AUTO".equalsIgnoreCase(tipo)) {

        svg.setContent("M2 10 L4 6 H12 L14 10 H16 V14 H2 Z");

    } else {

        svg.setContent("M3 13 A2 2 0 1 0 7 13 A2 2 0 1 0 3 13 M10 13 A2 2 0 1 0 14 13 A2 2 0 1 0 10 13");

    }

    svg.setScaleX(2.0);
    svg.setScaleY(2.0);
    svg.setStyle("-fx-fill: white;");

    return svg;
}

    @FXML
    public void volverAlMenu() {

        if (matriculaPendiente != null && !matriculaPendiente.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "Debes asignar un lugar antes de salir.");
            alert.showAndWait();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Bienvenida.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) lblTitulo.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void confirmarAsignacion(int numero) {

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Asignar lugar #" + numero + "?");

        confirm.showAndWait().ifPresent(r -> {
            if (r == ButtonType.OK) ejecutarAsignacionBD(numero);
        });
    }

    private void ejecutarAsignacionBD(int numero) {

        String sql = "UPDATE estacionamiento_3 SET matricula_o_num_empleado = ?, placa_vehiculo = ?, estado = 'Ocupado' WHERE lugar = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, matriculaPendiente);
            ps.setString(2, placaPendiente);
            ps.setInt(3, numero);

            if (ps.executeUpdate() > 0) {
                this.matriculaPendiente = null;
                volverAlMenu();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}