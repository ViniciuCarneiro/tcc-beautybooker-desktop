package com.beautybooker.beautybooker.controllers;

import com.beautybooker.beautybooker.Application;
import com.beautybooker.beautybooker.config.ConfigAPI;
import com.beautybooker.beautybooker.models.bean.CadastroProfissionalResponse;
import com.beautybooker.beautybooker.models.bean.Cliente;
import com.beautybooker.beautybooker.utils.Mascaras;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class CadastroClientesController implements Initializable {

    @FXML
    private JFXButton bot_fechar;

    @FXML
    private TextField text_nome;

    @FXML
    private TextField text_sobrenome;

    @FXML
    private TextField txt_cpf;

    @FXML
    private TextField text_email;

    @FXML
    private TextField txt_whatsapp;

    @FXML
    private TextField text_datanascimento;

    @FXML
    private TextField txt_endereco;

    @FXML
    private TextField txt_usuario;

    @FXML
    private PasswordField txt_senha;

    @FXML
    private JFXButton bot_salvar;

    private static String token;

    private static Cliente clienteEdit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
    }

    private void init() {

        Mascaras.dateField(text_datanascimento);
        Mascaras.Cpf(txt_cpf);
        Mascaras.Telefone(txt_whatsapp);

        if (clienteEdit != null) {
            text_nome.setText(clienteEdit.getNome());
            text_sobrenome.setText(clienteEdit.getSobrenome());
            txt_cpf.setText(clienteEdit.getCpf());
            text_email.setText(clienteEdit.getEmail());
            txt_whatsapp.setText(clienteEdit.getWhatsapp());
            text_datanascimento.setText(clienteEdit.getDatanascimento());
            txt_endereco.setText(clienteEdit.getEndereco());
            txt_usuario.setText(clienteEdit.getUsuario());
            txt_senha.setText(clienteEdit.getSenha());
        }

        setOnAction();
    }

    public static void openScreen(String acessToken, Cliente cliente)
    {
        try
        {
            token = acessToken;
            clienteEdit = cliente;

            Stage primaryStage = new Stage();
            primaryStage.setScene(Application.getSceneCadastroCliente());
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
                jsonRequest.addProperty("sobrenome", text_sobrenome.getText());
                jsonRequest.addProperty("cpf", txt_cpf.getText());
                jsonRequest.addProperty("email", text_email.getText());
                jsonRequest.addProperty("datanascimento", formatarData(text_datanascimento.getText(), "dd/MM/yyyy", "yyyy-MM-dd"));
                jsonRequest.addProperty("whatsapp", txt_whatsapp.getText());
                jsonRequest.addProperty("usuario", txt_usuario.getText());
                jsonRequest.addProperty("senha", txt_senha.getText());
                jsonRequest.addProperty("endereco", txt_endereco.getText());
                jsonRequest.addProperty("ativo", 1);

                String apiUrl;

                HttpURLConnection connection;

                if (clienteEdit != null) {
                    apiUrl = ConfigAPI.BASE_URL.concat(ConfigAPI.PATH_CLIENTES).concat("/").concat(String.valueOf(clienteEdit.getId()));
                    URL url = new URL(apiUrl);
                    connection = ConfigAPI.sendRequest(url,ConfigAPI.PUT, token, jsonRequest);
                } else {
                    apiUrl = ConfigAPI.BASE_URL.concat(ConfigAPI.PATH_CLIENTES);
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

    private String formatarData(String dataString, String formatoAntigo, String formatoNovo) {
        SimpleDateFormat formatoEntrada = new SimpleDateFormat(formatoAntigo);
        SimpleDateFormat formatoSaida = new SimpleDateFormat(formatoNovo);

        try {
            Date data = formatoEntrada.parse(dataString);
            return formatoSaida.format(data);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean validarDados() {

        if (text_nome.getText().isBlank() || text_nome.getText().isEmpty()) {
            return false;
        }

        if (text_sobrenome.getText().isBlank() || text_sobrenome.getText().isEmpty()) {
            return false;
        }

        if (txt_cpf.getText().isBlank() || txt_cpf.getText().isEmpty()) {
            return false;
        }

        if (text_email.getText().isBlank() || text_email.getText().isEmpty()) {
            return false;
        }

        if (txt_whatsapp.getText().isBlank() || txt_whatsapp.getText().isEmpty()) {
            return false;
        }

        if (text_datanascimento.getText().isBlank() || text_datanascimento.getText().isEmpty()) {
            return false;
        }

        if (txt_endereco.getText().isBlank() || txt_endereco.getText().isEmpty()) {
            return false;
        }

        if (txt_usuario.getText().isBlank() || txt_usuario.getText().isEmpty()) {
            return false;
        }

        if (txt_senha.getText().isBlank() || txt_senha.getText().isEmpty()) {
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
