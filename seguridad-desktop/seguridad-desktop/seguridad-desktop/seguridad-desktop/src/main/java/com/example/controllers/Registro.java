package com.example.controllers;

public class Registro {

    private String matricula;
    private String rol;
    private String placa;
    private String marca;
    private String modelo;

    public Registro(String matricula, String rol, String placa, String marca, String modelo) {
        this.matricula = matricula;
        this.rol = rol;
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
    }

    public String getMatricula() { return matricula; }
    public String getRol() { return rol; }
    public String getPlaca() { return placa; }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
}
