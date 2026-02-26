package com.example.seguridad.service;

import com.example.seguridad.entity.Conductor;
import com.example.seguridad.repository.ConductorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConductorService {

    @Autowired
    private ConductorRepository conductorRepo;

    public String guardar(Conductor conductor) {
        if (conductorRepo.existsByMatricula(conductor.getMatricula())) {
            return "Error: Ya existe un conductor con esa matrícula";
        }
        conductorRepo.save(conductor);
        return "Conductor guardado correctamente";
    }
    public boolean existePorMatricula(String matricula) {
    return conductorRepo.existsByMatricula(matricula);
}

}
