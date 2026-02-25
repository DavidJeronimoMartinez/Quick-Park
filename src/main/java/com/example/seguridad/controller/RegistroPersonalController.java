package com.example.seguridad.controller;

import com.example.seguridad.entity.RegistroPersonal;
import com.example.seguridad.service.AuthService;
import com.example.seguridad.service.RegistroService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/personal")
public class RegistroPersonalController {

    @Autowired
    private AuthService authService;

    @Autowired
    private RegistroService registroService; 

    @PostMapping("/registro")
    public ResponseEntity<String> registrar(@Valid @RequestBody RegistroPersonal personal) {

        String resultado = registroService.registrar(personal);

        if (resultado.equals("Usuario guardado exitosamente")) {
            return ResponseEntity.ok(resultado);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(resultado);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody RegistroPersonal datos) {

        if (authService.login(datos.getNumEmpleado(), datos.getContrasena())) {
            return ResponseEntity.ok("Login correcto");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error en los datos");
        }
    }
}