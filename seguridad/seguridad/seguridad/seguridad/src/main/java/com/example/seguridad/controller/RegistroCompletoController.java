package com.example.seguridad.controller;

import com.example.seguridad.dto.RegistroCompletoDTO;
import com.example.seguridad.entity.Conductor;
import com.example.seguridad.entity.Vehiculo;
import com.example.seguridad.service.ConductorService;
import com.example.seguridad.service.RegistroVehicularService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registro-completo")
public class RegistroCompletoController {

    @Autowired
    private ConductorService conductorService;

    @Autowired
    private RegistroVehicularService vehiculoService;

    @PostMapping
    public ResponseEntity<String> guardar(@RequestBody RegistroCompletoDTO registro) {

        Conductor c = registro.getConductor();
        Vehiculo v = registro.getVehiculo();

        // Verificar duplicados antes de guardar
        if (conductorService.existePorMatricula(c.getMatricula())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Ya existe un conductor con esa matrícula");
        }

        if (vehiculoService.existePorPlaca(v.getPlaca())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Ya existe un vehículo con esa placa");
        }

        // Guardar ambos registros
        conductorService.guardar(c);
        v.setMatricula(c.getMatricula()); // asegurar relación
        vehiculoService.guardar(v);

        return ResponseEntity.ok("Conductor y vehículo guardados correctamente");
    }
}
