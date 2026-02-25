package com.example.seguridad.controller;

import com.example.seguridad.entity.Conductor;
import com.example.seguridad.service.ConductorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/conductores")
public class ConductorController {

    @Autowired
    private ConductorService service;

    @PostMapping
    public ResponseEntity<String> guardar(@RequestBody Conductor conductor) {
        String resultado = service.guardar(conductor);
        if (resultado.startsWith("Error")) {
            return ResponseEntity.badRequest().body(resultado);
        }
        return ResponseEntity.ok(resultado);
    }
}
