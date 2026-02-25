package com.example.api;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiClient {

    private static String ruta = "http://localhost:8080/api/personal";

    public static String registrar(String json) {
        return enviar(ruta + "/registro", json);
    }

    public static String login(String json) {    
        return enviar(ruta + "/login", json);
    }

    private static String enviar(String url, String datos) {
        try {
            HttpClient cliente = HttpClient.newHttpClient();
            HttpRequest peticion = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(datos))
                    .build();

            HttpResponse<String> respuesta = cliente.send(peticion, HttpResponse.BodyHandlers.ofString());
            
            // Si el status es 200 (OK) o 401 (Error de datos), devolvemos el cuerpo
            return respuesta.body();

        } catch (Exception e) {
            System.err.println("Error de conexión: " + e.getMessage());
            return null;
        }
    }

    public static String guardarVehiculo(String json) {
    return enviar("http://localhost:8080/api/vehiculos", json);
}

}