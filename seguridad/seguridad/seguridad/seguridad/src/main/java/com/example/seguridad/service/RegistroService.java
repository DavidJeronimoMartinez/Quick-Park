package com.example.seguridad.service;

import com.example.seguridad.entity.RegistroPersonal;
import com.example.seguridad.repository.RegistroPersonalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistroService {

    @Autowired
    private RegistroPersonalRepository repo;

    public String registrar(RegistroPersonal personal) {
        // 1. Bloqueo por ID (Número de empleado)
        if (repo.existsById(personal.getNumEmpleado())) {
            return "Error: El numero de empleado ya existe";
        }

        // 2. Bloqueo por Contraseña
        if (repo.existsByContrasena(personal.getContrasena())) {
            return "Error: La contraseña ya esta en uso";
        }

        // 3. Si no hay duplicados, guardamos
        repo.save(personal);
        return "Usuario guardado exitosamente";
    }
}