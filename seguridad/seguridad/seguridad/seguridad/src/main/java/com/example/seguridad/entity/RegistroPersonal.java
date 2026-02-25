package com.example.seguridad.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "registro_personal") 
public class RegistroPersonal {

    @Id 
    @Column(name = "num_empleado") 
    private int numEmpleado;

    private String nombre;
    
    @Column(name = "apellido_paterno")
    private String apellidoPaterno;
    
    @Column(name = "apellido_materno")
    private String apellidoMaterno;

    @Column(name = "contrasena", unique = true, nullable = false)
    private String contrasena;

    public RegistroPersonal() {}

    // Getters y Setters
    public int getNumEmpleado() { return numEmpleado; }
    public void setNumEmpleado(int numEmpleado) { this.numEmpleado = numEmpleado; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellidoPaterno() { return apellidoPaterno; }
    public void setApellidoPaterno(String apellidoPaterno) { this.apellidoPaterno = apellidoPaterno; }
    public String getApellidoMaterno() { return apellidoMaterno; }
    public void setApellidoMaterno(String apellidoMaterno) { this.apellidoMaterno = apellidoMaterno; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
}