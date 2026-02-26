package com.example.models;

public class LugarEstacionamiento {
    private int lugar;
    private String matricula;
    private String placa;
    private String estado;

    public LugarEstacionamiento(int lugar, String matricula, String placa, String estado) {
        this.lugar = lugar;
        this.matricula = matricula;
        this.placa = placa;
        this.estado = estado;
    }

    public int getLugar() { return lugar; }
    public String getMatricula() { return matricula; }
    public String getPlaca() { return placa; }
    public String getEstado() { return estado; }
}