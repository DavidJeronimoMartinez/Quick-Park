package com.example.services;

import com.example.api.ApiClient;

public class AuthService {
    
    public static boolean verificarUsuario(int numEmpleado, String contrasena) {
        String json = "{\"numEmpleado\":" + numEmpleado + ",\"contrasena\":\"" + contrasena + "\"}";
        try {
            String respuesta = ApiClient.login(json);
            System.out.println("DEBUG - Respuesta Login: " + respuesta);
            
            if (respuesta == null) return false;
            String resMin = respuesta.toLowerCase();
            return resMin.contains("login correcto") || resMin.contains("exito") || resMin.contains("true");
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean registrarUsuario(int num, String nom, String ap, String am, String pass) {
        String json = String.format(
            "{\"numEmpleado\":%d,\"nombre\":\"%s\",\"apellidoPaterno\":\"%s\",\"apellidoMaterno\":\"%s\",\"contrasena\":\"%s\"}",
            num, nom, ap, am, pass
        );
        try {
            String respuesta = ApiClient.registrar(json); 
            if (respuesta == null) return false;
            String resMin = respuesta.toLowerCase();
            return resMin.contains("exitosamente") || resMin.contains("guardado");
        } catch (Exception e) {
            return false;
        }
    }
}