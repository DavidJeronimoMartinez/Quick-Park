package com.example.seguridad.dto;

import com.example.seguridad.entity.Conductor;
import com.example.seguridad.entity.Vehiculo;
import com.example.seguridad.entity.Moto;

public class RegistroCompletoDTO {

    private Conductor conductor;
    private Vehiculo vehiculo;
    private Moto moto;
    private String tipo; 

   
    public Conductor getConductor() {
        return conductor;
    }

    public void setConductor(Conductor conductor) {
        this.conductor = conductor;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Moto getMoto() {
        return moto;
    }

    public void setMoto(Moto moto) {
        this.moto = moto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}