package com.example.seguridad.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "moto")
public class Moto {

    @Id
    @Column(name = "placa_moto", nullable = false, length = 20)
    private String placaMoto;

    @Column(name = "matricula_o_num_empleado", nullable = false, length = 20)
    private String matricula;

    @Column(name = "marca", nullable = false, length = 50)
    private String marca;

    @Column(name = "modelo", nullable = false, length = 20)
    private String modelo;

    public String getPlacaMoto() {
        return placaMoto;
    }

    public void setPlacaMoto(String placaMoto) {
        this.placaMoto = placaMoto;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
}