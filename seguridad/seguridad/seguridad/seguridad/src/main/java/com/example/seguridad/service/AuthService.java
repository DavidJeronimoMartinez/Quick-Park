package com.example.seguridad.service;

import com.example.seguridad.entity.RegistroPersonal;
import com.example.seguridad.repository.RegistroPersonalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private RegistroPersonalRepository repo;

    public boolean login(int numEmpleado, String contrasena) {

        RegistroPersonal usuario = repo.findById(numEmpleado).orElse(null);
        

        if (usuario != null && usuario.getContrasena().equals(contrasena)) {
            return true;
        }
        
        return false;
    }
}