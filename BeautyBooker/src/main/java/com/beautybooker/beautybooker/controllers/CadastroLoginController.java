package com.beautybooker.beautybooker.controllers;

import com.beautybooker.beautybooker.Application;
import com.beautybooker.beautybooker.config.ConfigAPI;
import com.beautybooker.beautybooker.models.bean.CadastroProfissionalResponse;
import com.beautybooker.beautybooker.models.bean.LoginResponse;
import com.beautybooker.beautybooker.utils.Formatar;
import com.beautybooker.beautybooker.utils.Mascaras;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CadastroLoginController implements Initializable {
    @FXML
    private TextField txt_nome;

    @FXML
    private TextField txt_sobrenome;

    @FXML
    private TextField txt_cpf;

    @FXML
    private TextField txt_email;

    @FXML
    private JFXButton btn_proximo;

    @FXML
    private Label txt_login;

    @FXML
    private TextField txt_data_nascimento;

    @FXML
    private TextField txt_usuario;

    @FXML
    private Pane pane_proximo;

    @FXML
    private JFXButton btn_cadastrar;

    @FXML
    private TableView<?> tabela;

    @FXML
    private TableColumn<?, ?> coluna_check;

    @FXML
    private TableColumn<?, ?> coluna_habilidades;

    @FXML
    private ImageView btn_voltar;

    @FXML
    private PasswordField txt_senha;

    @FXML
    private PasswordField txt_confirmarSenha;

    @FXML
    private JFXCheckBox check_admin;

    private Stage primaryStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void init() {
        setOnAction();

        Mascaras.Cpf(txt_cpf);
        Mascaras.dateField(txt_data_nascimento);
    }

    private void cadastrar() throws IOException {
        if (validar()) {

            JsonArray jsonArray = new JsonArray();
            JsonObject habilidades = new JsonObject();
            habilidades.addProperty("servico_id", 1);
            jsonArray.add(habilidades);

            JsonObject jsonRequest = new JsonObject();
            jsonRequest.addProperty("nome", txt_nome.getText());
            jsonRequest.addProperty("sobrenome", txt_sobrenome.getText());
            jsonRequest.addProperty("cpf", txt_cpf.getText());
            jsonRequest.addProperty("email", txt_email.getText());
            jsonRequest.addProperty("datanascimento", Formatar.formatarData(txt_data_nascimento.getText(), "dd/MM/yyyy", "yyyy-mm-dd"));
            jsonRequest.addProperty("admin", (check_admin.isSelected() ? 1 : 0));
            jsonRequest.addProperty("usuario", txt_usuario.getText());
            jsonRequest.addProperty("senha", txt_senha.getText());
            jsonRequest.addProperty("ativo", 1);
            jsonRequest.add("habilidades", jsonArray);

            String apiUrl = ConfigAPI.BASE_URL.concat(ConfigAPI.PATH_PROFISSIONAIS);
            URL url = new URL(apiUrl);

            HttpURLConnection connection = ConfigAPI.sendRequest(url, jsonRequest, ConfigAPI.POST);

            int responseCode = connection.getResponseCode();

            if (responseCode == 201) {
                String responseBody = ConfigAPI.readResponse(connection);
                System.out.println(responseBody);
                CadastroProfissionalResponse cadastroProfissionalResponse = new Gson().fromJson(responseBody, CadastroProfissionalResponse.class);

                pane_proximo.setVisible(false);
                txt_nome.setText("");
                txt_sobrenome.setText("");
                txt_cpf.setText("");
                txt_email.setText("");
                txt_senha.setText("");
                txt_confirmarSenha.setText("");
                txt_data_nascimento.setText("");
                txt_senha.setText("");
                check_admin.setSelected(false);
                Application.loadLoginScene(primaryStage);
            }

        } else {
            showErrorAlert("Preencha todos os campos para prosseguuir");
        }
    }

    private boolean validar() {
        if (txt_nome.getText().isEmpty() || txt_nome.getText().isBlank()) {
            return false;
        }

        if (txt_sobrenome.getText().isEmpty() || txt_sobrenome.getText().isBlank()) {
            return false;
        }

        if (txt_cpf.getText().isEmpty() || txt_cpf.getText().isBlank()) {
            return false;
        }

        if (txt_email.getText().isEmpty() || txt_email.getText().isBlank()) {
            return false;
        }

        if (txt_senha.getText().isEmpty() || txt_senha.getText().isBlank()) {
            return false;
        }

        if (txt_confirmarSenha.getText().isEmpty() || txt_confirmarSenha.getText().isBlank()) {
            return false;
        }

        if (txt_data_nascimento.getText().isEmpty() || txt_data_nascimento.getText().isBlank()) {
            return false;
        }

        if (!txt_senha.getText().equals(txt_confirmarSenha.getText())) {
            showErrorAlert("As senhas não coincidem");
            return false;
        }

        return true;
    }

    private void setOnAction() {
        txt_login.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Application.loadLoginScene(primaryStage);
            }
        });

        btn_proximo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pane_proximo.setVisible(true);
            }
        });

        btn_cadastrar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    cadastrar();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        
        btn_voltar.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                pane_proximo.setVisible(false);
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
