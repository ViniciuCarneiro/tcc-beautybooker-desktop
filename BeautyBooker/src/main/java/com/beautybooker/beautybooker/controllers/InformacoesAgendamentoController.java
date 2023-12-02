package com.beautybooker.beautybooker.controllers;

import com.beautybooker.beautybooker.Application;
import com.beautybooker.beautybooker.config.ConfigAPI;
import com.beautybooker.beautybooker.models.bean.AgendamentoDashboard;
import com.beautybooker.beautybooker.models.bean.AgendamentoDashboardResponse;
import com.beautybooker.beautybooker.utils.Formatar;
import com.google.gson.JsonObject;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.tools.Utils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;

public class InformacoesAgendamentoController implements Initializable {
    @FXML
    private JFXButton bot_fechar;

    @FXML
    private Label txt_nome;

    @FXML
    private Label txt_email;

    @FXML
    private Label txt_whatsapp;

    @FXML
    private Label txt_dataNascimento;

    @FXML
    private Label txt_endereco;

    @FXML
    private Label txt_servico;

    @FXML
    private Label txt_tempoServico;

    @FXML
    private Label txt_valorServico;

    @FXML
    private Label txt_dataAgendada;

    @FXML
    private Label txt_horarioAgendado;

    @FXML
    private Label txt_horarioTermino;

    @FXML
    private Label txt_valorDesconto;

    @FXML
    private Label txt_valorFinal;

    @FXML
    private JFXButton bot_cancelarAgendamento;

    private static AgendamentoDashboard agendamentoInfo;

    private static String token;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
    }

    public static void openScreen(AgendamentoDashboard agendamento, String acessToken)
    {
        try
        {
            agendamentoInfo = agendamento;
            token = acessToken;

            Stage primaryStage = new Stage();
            primaryStage.setScene(Application.getSceneInformacaoAgendamento());
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

    private void init() {
        setOnAction();

        txt_nome.setText(agendamentoInfo.getCliente().getNome().concat(" ").concat(agendamentoInfo.getCliente().getSobrenome()));
        txt_email.setText(agendamentoInfo.getCliente().getEmail());
        txt_whatsapp.setText(agendamentoInfo.getCliente().getWhatsapp());
        txt_dataNascimento.setText(Formatar.formatarData(agendamentoInfo.getCliente().getDatanascimento()));
        txt_endereco.setText(agendamentoInfo.getCliente().getEndereco());
        txt_servico.setText(agendamentoInfo.getServico().getNome());
        txt_tempoServico.setText(Formatar.formatarData(agendamentoInfo.getServico().getTempo(), "HH:mm:ss",  "HH:mm"));
        txt_valorServico.setText(Formatar.formatar(agendamentoInfo.getServico().getValor(), "#0.00"));
        txt_dataAgendada.setText(Formatar.formatarData(agendamentoInfo.getDataagendada()));
        txt_horarioAgendado.setText(Formatar.formatarData(agendamentoInfo.getHoraagendada(), "HH:mm:ss",  "HH:mm"));
        txt_horarioTermino.setText(Formatar.formatarData(agendamentoInfo.getHoraaTermino(), "HH:mm:ss",  "HH:mm"));
        txt_valorDesconto.setText(Formatar.formatar(agendamentoInfo.getValordesconto(), "#0.00"));
        txt_valorFinal.setText(Formatar.formatar(agendamentoInfo.getValorfinal(), "#0.00"));
    }

    private void setOnAction() {
        bot_fechar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) bot_fechar.getScene().getWindow();
                stage.close();
            }
        });

        bot_cancelarAgendamento.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    JsonObject jsonRequest = new JsonObject();
                    jsonRequest.addProperty("cancelado", 1);

                    String apiUrl;

                    HttpURLConnection connection;

                    apiUrl = ConfigAPI.BASE_URL.concat(ConfigAPI.PATH_AGENDAMENTOS).concat("/").concat(String.valueOf(agendamentoInfo.getId()));
                    URL url = new URL(apiUrl);
                    connection = ConfigAPI.sendRequest(url,ConfigAPI.PUT, token, jsonRequest);

                    int responseCode = connection.getResponseCode();

                    if (responseCode == 201 || responseCode == 200) {
                        MensagemConfirmacaoController.openScreen();
                        bot_cancelarAgendamento.setDisable(true);
                    } else {
                        showErrorAlert("Ocorreu um erro ao cadastrar o cliente, tente novamente mais tarde!");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
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
