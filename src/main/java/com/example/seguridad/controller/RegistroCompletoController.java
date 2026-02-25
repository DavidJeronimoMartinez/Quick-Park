package com.example.seguridad.controller;

import com.example.seguridad.dto.RegistroCompletoDTO;
import com.example.seguridad.entity.Conductor;
import com.example.seguridad.entity.Vehiculo;
import com.example.seguridad.entity.Moto;
import com.example.seguridad.service.ConductorService;
import com.example.seguridad.service.RegistroVehicularService;
import com.example.seguridad.service.MotoService;

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

    @Autowired
    private MotoService motoService;

    @PostMapping
    public ResponseEntity<String> guardar(@RequestBody RegistroCompletoDTO registro) {

        Conductor c = registro.getConductor();

        
        if (conductorService.existePorMatricula(c.getMatricula())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Ya existe un conductor con esa matrícula");
        }

        if ("AUTO".equalsIgnoreCase(registro.getTipo())) {

            Vehiculo v = registro.getVehiculo();

            if (vehiculoService.existePorPlaca(v.getPlaca())) {
                return ResponseEntity
                        .badRequest()
                        .body("Error: Ya existe un vehículo con esa placa");
            }

            conductorService.guardar(c);
            v.setMatricula(c.getMatricula());
            vehiculoService.guardar(v);

            return ResponseEntity.ok("Conductor y vehículo guardados correctamente");

        } else if ("MOTO".equalsIgnoreCase(registro.getTipo())) {

            Moto m = registro.getMoto();

            if (motoService.existePorPlaca(m.getPlacaMoto())) {
                return ResponseEntity
                        .badRequest()
                        .body("Error: Ya existe una moto con esa placa");
            }

            conductorService.guardar(c);
            m.setMatricula(c.getMatricula());
            motoService.guardar(m);

            return ResponseEntity.ok("Conductor y moto guardados correctamente");
        }

        return ResponseEntity
                .badRequest()
                .body("Error: Tipo de vehículo inválido");
    }
}