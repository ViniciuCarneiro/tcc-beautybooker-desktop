package com.beautybooker.beautybooker.config;

import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConfigAPI {
    public static String BASE_URL = "http://localhost:3000/";
    public static String PATH_PROFISSIONAIS_AUTH = "profissionais/login";
    public static String PATH_PROFISSIONAIS = "profissionais";

    public static String PATH_AGENDAMENTO_DASHBOARD = "agendamentos/dashboard";
    public static String PATH_AGENDAMENTO = "agendamentos/busca?page=1&pageSize=50";
    public static String PATH_AGENDAMENTOS = "agendamentos";
    public static String PATH_HORARIOS_FUNCIONAMENTO = "empresa/funcionamento";
    public static String PATH_CLIENTES = "clientes";
    public static String PATH_SERVICOS = "servicos";

    public static String POST = "POST";
    public static String PUT = "PUT";
    public static String GET = "GET";
    public static String DELETE = "DELETE";

    public static HttpURLConnection sendRequest(URL url, JsonObject jsonRequest) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");

        connection.setDoOutput(true);
        connection.getOutputStream().write(jsonRequest.toString().getBytes("UTF-8"));

        return connection;
    }

    public static HttpURLConnection sendRequest(URL url, JsonObject jsonRequest, String method) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod(method);
        connection.setRequestProperty("Content-Type", "application/json");

        connection.setDoOutput(true);
        connection.getOutputStream().write(jsonRequest.toString().getBytes("UTF-8"));

        return connection;
    }

    public static HttpURLConnection sendRequest(URL url, String method, String authToken) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod(method);
        connection.setRequestProperty("Content-Type", "application/json");

        // Adicione o cabeçalho de autorização Bearer
        if (authToken != null && !authToken.isEmpty()) {
            connection.setRequestProperty("Authorization", "Bearer " + authToken);
        }

        return connection;
    }

    public static HttpURLConnection sendRequest(URL url, String method, String authToken, JsonObject jsonRequest) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod(method);
        connection.setRequestProperty("Content-Type", "application/json");

        if (authToken != null && !authToken.isEmpty()) {
            connection.setRequestProperty("Authorization", "Bearer " + authToken);
        }

        connection.setDoOutput(true);
        connection.getOutputStream().write(jsonRequest.toString().getBytes("UTF-8"));

        return connection;
    }

    public static String readResponse(HttpURLConnection connection) throws IOException {
        try {
            StringBuilder response = new StringBuilder();
            String line;
            int responseCode = connection.getResponseCode();

            if (responseCode == 200 || responseCode == 201) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                }
            } else {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                }
            }

            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
