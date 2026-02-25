package com.example.services;

import com.example.api.ApiClient;

public class AuthService {
    
    public static boolean login(int numEmpleado, String contrasena) {
        // Aseguramos que el JSON lleve los nombres exactos que espera el Backend
        String json = "{\"numEmpleado\":" + numEmpleado + ",\"contrasena\":\"" + contrasena + "\"}";
        String respuesta = ApiClient.login(json);
        
        System.out.println("Respuesta Servidor Login: " + respuesta);
        
        return respuesta != null && respuesta.toLowerCase().contains("login correcto");
    }

    public static boolean registrarUsuario(int num, String nom, String ap, String am, String pass) {
        String json = String.format(
            "{\"numEmpleado\":%d,\"nombre\":\"%s\",\"apellidoPaterno\":\"%s\",\"apellidoMaterno\":\"%s\",\"contrasena\":\"%s\"}",
            num, nom, ap, am, pass
        );
        
        String respuesta = ApiClient.registrar(json); 
        System.out.println("Respuesta Servidor Registro: " + respuesta);

        if (respuesta == null) return false;

        String resMin = respuesta.toLowerCase();
        return resMin.contains("exitosamente") || resMin.contains("guardado");
    }
}