package com.example.controllers;

import com.example.vistas.BienvenidaView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.*;

public class RegistrosController {

    @FXML private TextField txtBuscar;
    @FXML private TableView<Registro> tablaRegistros;
    @FXML private TableColumn<Registro, String> colMatricula, colRol, colPlaca, colMarca, colModelo, colTipo;

    private ObservableList<Registro> lista = FXCollections.observableArrayList();
    private FilteredList<Registro> filtro;

    @FXML
    public void initialize() {
        colMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        colRol.setCellValueFactory(new PropertyValueFactory<>("rol"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colPlaca.setCellValueFactory(new PropertyValueFactory<>("placa"));
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));

        tablaRegistros.setRowFactory(tv -> {
            TableRow<Registro> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Registro seleccionado = row.getItem();
                    abrirDetalle(seleccionado);
                }
            });
            return row;
        });

        cargarDatos();
        filtro = new FilteredList<>(lista, p -> true);
        tablaRegistros.setItems(filtro);
    }

private void abrirDetalle(Registro reg) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/detalleRegistro.fxml"));
        Parent root = loader.load();
        
        DetalleRegistroController controller = loader.getController();
        if (controller != null) {
            controller.setRegistro(reg);
            Stage stage = (Stage) tablaRegistros.getScene().getWindow();
            stage.getScene().setRoot(root);
        }
    } catch (IOException e) {
        System.err.println("Error: No se encontró detalleRegistro.fxml en la raíz.");
        e.printStackTrace();
    }
}
    @FXML
    private void filtrarDatos() {
        String texto = txtBuscar.getText().toLowerCase();
        filtro.setPredicate(reg -> {
            if (texto.isEmpty()) return true;
            return reg.getMatricula().toLowerCase().contains(texto) || 
                   reg.getPlaca().toLowerCase().contains(texto);
        });
    }

    private void cargarDatos() {
        lista.clear();
        String url = "jdbc:mysql://localhost:3306/seguridad";
        String sql = """
            SELECT c.matricula_o_num_empleado, c.rol, v.placa_coche AS placa, v.marca, v.modelo, 'AUTO' AS tipo
            FROM conductor c JOIN vehiculo v ON c.matricula_o_num_empleado = v.matricula_o_num_empleado
            UNION ALL
            SELECT c.matricula_o_num_empleado, c.rol, m.placa_moto AS placa, m.marca, m.modelo, 'MOTO' AS tipo
            FROM conductor c JOIN moto m ON c.matricula_o_num_empleado = m.matricula_o_num_empleado
            """;
        try (Connection conn = DriverManager.getConnection(url, "root", "");
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Registro(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
            }
        } catch (SQLException e) { 
            e.printStackTrace(); 
        }
    }

    @FXML
    private void volver() {
        try {
            Stage stage = (Stage) tablaRegistros.getScene().getWindow();
            new BienvenidaView().mostrar(stage, 0); 
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
    }
}