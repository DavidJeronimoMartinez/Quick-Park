package com.example.seguridad.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "registro_personal")
public class RegistroPersonal {

    @Id
    @Column(name = "num_empleado")
    @NotBlank(message = "El número de empleado es obligatorio")
    @Pattern(
        regexp = "^[0-9]{4}$",
        message = "El número de empleado debe contener exactamente 4 números"
    )
    private String numEmpleado;

    @NotBlank(message = "El nombre es obligatorio")
    @Pattern(
        regexp = "^[A-ZÁÉÍÓÚÑ][a-zA-ZÁÉÍÓÚÑáéíóúñÑ ]*$",
        message = "El nombre debe iniciar con mayúscula y no contener números"
    )
    private String nombre;

    @Column(name = "apellido_paterno")
    @NotBlank(message = "El apellido paterno es obligatorio")
    @Pattern(
        regexp = "^[A-ZÁÉÍÓÚÑ][a-zA-ZÁÉÍÓÚÑáéíóúñÑ ]*$",
        message = "El apellido paterno debe iniciar con mayúscula y no contener números"
    )
    private String apellidoPaterno;

    @Column(name = "apellido_materno")
    @NotBlank(message = "El apellido materno es obligatorio")
    @Pattern(
        regexp = "^[A-ZÁÉÍÓÚÑ][a-zA-ZÁÉÍÓÚÑáéíóúñÑ ]*$",
        message = "El apellido materno debe iniciar con mayúscula y no contener números"
    )
    private String apellidoMaterno;

    @Column(name = "contrasena", unique = true, nullable = false)
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 7, max = 10, message = "La contraseña debe tener entre 7 y 10 caracteres")
    private String contrasena;

    public RegistroPersonal() {}

    public String getNumEmpleado() { return numEmpleado; }
    public void setNumEmpleado(String numEmpleado) { this.numEmpleado = numEmpleado; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidoPaterno() { return apellidoPaterno; }
    public void setApellidoPaterno(String apellidoPaterno) { this.apellidoPaterno = apellidoPaterno; }

    public String getApellidoMaterno() { return apellidoMaterno; }
    public void setApellidoMaterno(String apellidoMaterno) { this.apellidoMaterno = apellidoMaterno; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
}