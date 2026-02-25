package com.example.seguridad.dto;

import com.example.seguridad.entity.Conductor;
import com.example.seguridad.entity.Vehiculo;

public class RegistroCompletoDTO {
    private Conductor conductor;
    private Vehiculo vehiculo;

    // Getters y Setters
    public Conductor getConductor() { return conductor; }
    public void setConductor(Conductor conductor) { this.conductor = conductor; }

    public Vehiculo getVehiculo() { return vehiculo; }
    public void setVehiculo(Vehiculo vehiculo) { this.vehiculo = vehiculo; }
}
