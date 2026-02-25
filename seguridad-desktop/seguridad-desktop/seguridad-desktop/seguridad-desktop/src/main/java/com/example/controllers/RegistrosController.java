package com.example.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RegistrosController {

    @FXML private TextField txtBuscar;
    @FXML private TableView<Registro> tablaRegistros;
    @FXML private TableColumn<Registro, String> colMatricula;
    @FXML private TableColumn<Registro, String> colRol;
    @FXML private TableColumn<Registro, String> colPlaca;
    @FXML private TableColumn<Registro, String> colMarca;
    @FXML private TableColumn<Registro, String> colModelo;

    private ObservableList<Registro> lista = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        colMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        colRol.setCellValueFactory(new PropertyValueFactory<>("rol"));
        colPlaca.setCellValueFactory(new PropertyValueFactory<>("placa"));
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));

        cargarDatos();
        tablaRegistros.setItems(lista);
    }

    private void cargarDatos() {

    lista.clear();

    String url = "jdbc:mysql://localhost:3306/seguridad";
    String user = "root";
    String pass = "";

    String sql = """
            SELECT c.matricula_o_num_empleado,
                   c.rol,
                   v.placa_coche,
                   v.marca,
                   v.modelo
            FROM conductor c
            JOIN vehiculo v
            ON c.matricula_o_num_empleado = v.matricula_o_num_empleado
            """;

    try (Connection conn = DriverManager.getConnection(url, user, pass)) {

        System.out.println("Conectado a: " + conn.getCatalog());

        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            System.out.println("Registro encontrado");

            lista.add(new Registro(
                    rs.getString("matricula_o_num_empleado"),
                    rs.getString("rol"),
                    rs.getString("placa_coche"),
                    rs.getString("marca"),
                    rs.getString("modelo")
            ));
        }

        System.out.println("Total registros: " + lista.size());

    } catch (Exception e) {
        e.printStackTrace();
    }
}


    @FXML
    private void filtrarDatos() {

        FilteredList<Registro> filtro = new FilteredList<>(lista, p -> true);

        filtro.setPredicate(registro -> {

            String texto = txtBuscar.getText().toLowerCase();

            if (texto.isEmpty()) return true;

            return registro.getMatricula().toLowerCase().contains(texto);
        });

        tablaRegistros.setItems(filtro);
    }

    @FXML
private void volver() {
    try {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/Bienvenida.fxml")
        );

        Scene scene = new Scene(loader.load());

        Stage stage = (Stage) tablaRegistros.getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    } catch (IOException e) {
        e.printStackTrace();
    }
}

}
