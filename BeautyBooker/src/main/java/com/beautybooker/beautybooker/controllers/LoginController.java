package com.beautybooker.beautybooker.controllers;

import com.beautybooker.beautybooker.Application;
import com.beautybooker.beautybooker.config.ConfigAPI;
import com.beautybooker.beautybooker.models.bean.LoginResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private Stage primaryStage;

    @FXML
    private TextField txt_usuario;

    @FXML
    private PasswordField txt_senha;

    @FXML
    private JFXButton btn_entrar;

    @FXML
    private Label btn_cadastro;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private void init() {
        setAction();
    }

    private void auth() {

        String usuario = txt_usuario.getText();
        String senha = txt_senha.getText();

        if (validateCredentials(usuario, senha)) {

            try {
                JsonObject jsonRequest = new JsonObject();
                jsonRequest.addProperty("usuario", usuario);
                jsonRequest.addProperty("senha", senha);

                String apiUrl = ConfigAPI.BASE_URL.concat(ConfigAPI.PATH_PROFISSIONAIS_AUTH);
                URL url = new URL(apiUrl);

                HttpURLConnection connection = ConfigAPI.sendRequest(url, jsonRequest);

                int responseCode = connection.getResponseCode();

                if (responseCode == 200) {
                    String responseBody = ConfigAPI.readResponse(connection);
                    LoginResponse loginResponse = new Gson().fromJson(responseBody, LoginResponse.class);
                    HomeController.token = loginResponse.getToken();
                    opeHome();
                } else {
                    System.out.println("Falha na autenticação.");
                    showErrorAlert("usuário ou senha inválido");
                }

                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void opeHome() throws IOException {

        Stage newStage = new Stage();
        newStage.setTitle("BeautyBooker | Login");
        newStage.initStyle(StageStyle.DECORATED);
        newStage.setResizable(true);
        newStage.setMaximized(true);
        newStage.setScene(Application.getSceneHome());
        newStage.show();
        primaryStage.close();
    }

    private boolean validateCredentials(String usuario, String senha) {
        if (usuario.isEmpty() || senha.isEmpty()) {
            System.out.println("Preencha todos os campos.");
            return false;
        }

        return true;
    }

    private void setAction(){
        btn_entrar.setOnAction(event -> {
            auth();
        });

        btn_cadastro.setOnMouseClicked(event -> {
            Application.loadCadastroScene(primaryStage);
        });

        txt_senha.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    auth();
                }
            }
        });
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}