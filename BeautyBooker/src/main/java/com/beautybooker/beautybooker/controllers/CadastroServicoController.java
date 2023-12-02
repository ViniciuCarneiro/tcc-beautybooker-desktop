package com.beautybooker.beautybooker.controllers;

import com.beautybooker.beautybooker.Application;
import com.beautybooker.beautybooker.config.ConfigAPI;
import com.beautybooker.beautybooker.models.bean.Cliente;
import com.beautybooker.beautybooker.models.bean.Servicos;
import com.beautybooker.beautybooker.utils.Formatar;
import com.beautybooker.beautybooker.utils.Mascaras;
import com.google.gson.JsonObject;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;

public class CadastroServicoController implements Initializable {

    @FXML
    private TextField text_nome;

    @FXML
    private TextField txt_tempo;

    @FXML
    private TextField text_valor;

    @FXML
    private JFXButton bot_salvar;

    @FXML
    private JFXButton bot_fechar;

    private static String token;

    private static Servicos servicosEdit;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
    }

    private void init() {

        if (servicosEdit != null){
            text_nome.setText(servicosEdit.getNome());
            txt_tempo.setText(servicosEdit.getTempo());
            text_valor.setText(Formatar.formatar(servicosEdit.getValor(), "#0.00"));
        }

        Mascaras.horaFild(txt_tempo);
        Mascaras.monetaryField(text_valor);

        setOnAction();
    }

    public static void openScreen(String acessToken, Servicos servicos)
    {
        try
        {
            token = acessToken;
            servicosEdit = servicos;

            Stage primaryStage = new Stage();
            primaryStage.setScene(Application.getSceneCadastroServicos());
            primaryStage.setResizable(false);
            primaryStage.initModality(Modality.APPLICATION_MODAL);
            primaryStage.initStyle(StageStyle.TRANSPARENT);

            primaryStage.showAndWait();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private void close() {
        Stage stage = (Stage) bot_fechar.getScene().getWindow();
        stage.close();
    }

    private void salvar() {
        if (validarDados()) {
            try {
                JsonObject jsonRequest = new JsonObject();
                jsonRequest.addProperty("nome", text_nome.getText());
                jsonRequest.addProperty("tempo", txt_tempo.getText());
                jsonRequest.addProperty("valor", Formatar.converterStringEmDouble(text_valor.getText()));
                jsonRequest.addProperty("ativo", 1);

                String apiUrl;

                HttpURLConnection connection;

                if (servicosEdit != null) {
                    apiUrl = ConfigAPI.BASE_URL.concat(ConfigAPI.PATH_SERVICOS).concat("/").concat(String.valueOf(servicosEdit.getId()));
                    URL url = new URL(apiUrl);
                    connection = ConfigAPI.sendRequest(url,ConfigAPI.PUT, token, jsonRequest);
                } else {
                    apiUrl = ConfigAPI.BASE_URL.concat(ConfigAPI.PATH_SERVICOS);
                    URL url = new URL(apiUrl);
                    connection = ConfigAPI.sendRequest(url, ConfigAPI.POST, token, jsonRequest);
                }

                int responseCode = connection.getResponseCode();

                if (responseCode == 201 || responseCode == 200) {
                    MensagemConfirmacaoController.openScreen();
                    close();
                } else {
                    showErrorAlert("Ocorreu um erro ao cadastrar o cliente, tente novamente mais tarde!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            showErrorAlert("Preencha todos os campos para prosseguuir");
        }
    }


    private boolean validarDados() {

        if (text_nome.getText().isBlank() || text_nome.getText().isEmpty()) {
            return false;
        }

        if (txt_tempo.getText().isBlank() || txt_tempo.getText().isEmpty()) {
            return false;
        }

        if (text_valor.getText().isBlank() || text_valor.getText().isEmpty()) {
            return false;
        }

        return true;
    }
    private void setOnAction() {
        bot_fechar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                close();
            }
        });

        bot_salvar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                salvar();
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
