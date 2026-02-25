package com.example.controllers;

public class Registro {
    private String matricula, rol, placa, marca, modelo, tipo;

    public Registro(String matricula, String rol, String placa, String marca, String modelo, String tipo) {
        this.matricula = matricula;
        this.rol = rol;
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.tipo = tipo;
    }

    
    public String getMatricula() { return matricula; }
    public String getRol() { return rol; }
    public String getPlaca() { return placa; }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public String getTipo() { return tipo; }
}