package com.example.seguridad.service;

import com.example.seguridad.entity.Conductor;
import com.example.seguridad.entity.Vehiculo;
import com.example.seguridad.repository.ConductorRepository;
import com.example.seguridad.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistroCompletoService {

    @Autowired
    private ConductorRepository conductorRepo;

    @Autowired
    private VehiculoRepository vehiculoRepo;

    @Transactional
    public String guardarConductorYVehiculo(Conductor conductor, Vehiculo vehiculo) {
        // Validar duplicados
        if (conductorRepo.existsByMatricula(conductor.getMatricula())) {
            return "Error: Ya existe un conductor con esa matrícula";
        }

        if (vehiculoRepo.existsByPlaca(vehiculo.getPlaca())) {
            return "Error: Ya existe un vehículo con esa placa";
        }

        // Guardar ambos solo si no hay duplicados
        conductorRepo.save(conductor);
        vehiculoRepo.save(vehiculo);

        return "Conductor y vehículo guardados correctamente";
    }
}
