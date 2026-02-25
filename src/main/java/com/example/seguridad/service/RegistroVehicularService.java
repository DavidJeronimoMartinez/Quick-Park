package com.example.seguridad.service;

import com.example.seguridad.entity.Vehiculo;
import com.example.seguridad.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistroVehicularService {

    @Autowired
    private VehiculoRepository vehiculoRepo;

    public String guardar(Vehiculo vehiculo) {
        if (vehiculoRepo.existsByPlaca(vehiculo.getPlaca())) {
            return "Error: Ya existe un vehículo con esa placa";
        }
        vehiculoRepo.save(vehiculo);
        return "Vehículo guardado correctamente";
    }
    public boolean existePorPlaca(String placa) {
    return vehiculoRepo.existsByPlaca(placa);
}

}
