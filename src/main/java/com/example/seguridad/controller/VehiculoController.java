package com.example.seguridad.controller;

import com.example.seguridad.entity.Vehiculo;
import com.example.seguridad.service.RegistroVehicularService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {

    @Autowired
    private RegistroVehicularService service;

    @PostMapping
    public ResponseEntity<String> guardar(@RequestBody Vehiculo vehiculo) {
        String resultado = service.guardar(vehiculo);
        if (resultado.startsWith("Error")) {
            return ResponseEntity.badRequest().body(resultado);
        }
        return ResponseEntity.ok(resultado);
    }
}
