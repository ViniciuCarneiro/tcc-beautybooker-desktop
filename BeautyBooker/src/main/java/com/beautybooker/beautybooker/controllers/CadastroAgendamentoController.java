package com.beautybooker.beautybooker.controllers;

import com.beautybooker.beautybooker.Application;
import com.beautybooker.beautybooker.config.ConfigAPI;
import com.beautybooker.beautybooker.models.bean.*;
import com.beautybooker.beautybooker.utils.Formatar;
import com.beautybooker.beautybooker.utils.Mascaras;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class CadastroAgendamentoController implements Initializable {
    @FXML
    private TextField busca_cliente;

    @FXML
    private TextField busca_servico;

    @FXML
    private TextField busca_profissional;

    @FXML
    private ListView<Profissional> lista_profissional;
    @FXML
    private ListView<Cliente> lista_clientes;
    @FXML
    private ListView<Servicos> lista_servicos;

    @FXML
    private JFXButton bot_proximo;

    @FXML
    private JFXButton bot_fechar;

    @FXML
    private AnchorPane paneFinal;

    @FXML
    private TextField text_horarioAgendado;

    @FXML
    private TextField text_dataAgendada;

    @FXML
    private TextField text_cliente;

    @FXML
    private TextField text_servico;

    @FXML
    private TextField text_profissional;

    @FXML
    private TextField text_horarioTermino;

    @FXML
    private JFXButton bot_verificarDisponibilidade;

    @FXML
    private TextField text_valorServico;

    @FXML
    private TextField text_valorDesconto;

    @FXML
    private TextField text_valorFinal;

    @FXML
    private JFXButton bot_salvarAgendamento;
    private static String token;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
    }

    public static void openScreen(String acessToken)
    {
        try
        {
            token = acessToken;

            Stage primaryStage = new Stage();
            primaryStage.setScene(Application.getSceneCadastroAgendamento());
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

        Mascaras.dateField(text_dataAgendada);
        Mascaras.horaFild(text_horarioAgendado);
        Mascaras.monetaryField(text_valorDesconto);
    }

    private ClientesResponse buscarClientes() {
        try {
            String apiUrl;

            apiUrl = ConfigAPI.BASE_URL.concat(ConfigAPI.PATH_CLIENTES).concat("?page=1&pageSize=100");

            URL url = new URL(apiUrl);

            HttpURLConnection connection = ConfigAPI.sendRequest(url,ConfigAPI.GET, token);

            int responseCode = connection.getResponseCode();

            if (responseCode == 200) {
                String responseBody = ConfigAPI.readResponse(connection);
                ClientesResponse clientesResponse = new Gson().fromJson(responseBody, ClientesResponse.class);
                return clientesResponse;
            } else {
                System.out.println("Falha na autenticação.");
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private ServicosResponse buscarServicos() {
        try {
            String apiUrl;

            apiUrl = ConfigAPI.BASE_URL.concat(ConfigAPI.PATH_SERVICOS).concat("?page=1&pageSize=100");

            URL url = new URL(apiUrl);

            HttpURLConnection connection = ConfigAPI.sendRequest(url,ConfigAPI.GET, token);

            int responseCode = connection.getResponseCode();

            if (responseCode == 200) {
                String responseBody = ConfigAPI.readResponse(connection);
                ServicosResponse servicosResponse = new Gson().fromJson(responseBody, ServicosResponse.class);
                return servicosResponse;
            } else {
                System.out.println("Falha na autenticação.");
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    private ProfissionalResponse buscarProfissional() {
        try {
            String apiUrl;

            apiUrl = ConfigAPI.BASE_URL.concat(ConfigAPI.PATH_PROFISSIONAIS).concat("?page=1&pageSize=100");

            URL url = new URL(apiUrl);

            HttpURLConnection connection = ConfigAPI.sendRequest(url,ConfigAPI.GET, token);

            int responseCode = connection.getResponseCode();

            if (responseCode == 200) {
                String responseBody = ConfigAPI.readResponse(connection);
                ProfissionalResponse profissionalResponse = new Gson().fromJson(responseBody, ProfissionalResponse.class);
                return profissionalResponse;
            } else {
                System.out.println("Falha na autenticação.");
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private boolean verificarDisponibilidade(int profissional) {
        try {
            String apiUrl;

            apiUrl = ConfigAPI.BASE_URL.concat(ConfigAPI.PATH_AGENDAMENTOS).concat("/verify").concat("?data="+Formatar.formatarData(text_dataAgendada.getText(), "dd/MM/yyyy", "yyyy-MM-dd")+"&horarioInicio="+text_horarioAgendado.getText()+"&&horarioTermino="+text_horarioTermino.getText()+"&&profissional="+profissional);

            URL url = new URL(apiUrl);

            HttpURLConnection connection = ConfigAPI.sendRequest(url,ConfigAPI.GET, token);

            int responseCode = connection.getResponseCode();

            if (responseCode == 200) {
                return true;
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    private String calcularHorarioFinal(String horarioInicialStr, String tempoDeServicoStr) {
        LocalTime horarioInicial = LocalTime.parse(horarioInicialStr, DateTimeFormatter.ofPattern("HH:mm"));

        LocalTime tempoDeServico = LocalTime.parse(tempoDeServicoStr, DateTimeFormatter.ofPattern("HH:mm:ss"));

        LocalTime horarioFinal = horarioInicial.plusHours(tempoDeServico.getHour())
                .plusMinutes(tempoDeServico.getMinute())
                .plusSeconds(tempoDeServico.getSecond());

        return horarioFinal.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    private void setOnAction() {
        bot_fechar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) bot_fechar.getScene().getWindow();
                stage.close();
            }
        });

        bot_proximo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                paneFinal.setVisible(true);
            }
        });

        busca_cliente.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    lista_clientes.getItems().clear();
                    lista_clientes.getItems().addAll(buscarClientes().getClientes().stream()
                            .filter(cliente -> cliente.getNome().toLowerCase().contains(busca_cliente.getText().toLowerCase()))
                            .collect(Collectors.toList()));
                }
            }
        });

        busca_servico.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    lista_servicos.getItems().clear();
                    lista_servicos.getItems().addAll(buscarServicos().getServicos().stream()
                            .filter(servico -> servico.getNome().toLowerCase().contains(busca_servico.getText().toLowerCase()))
                            .collect(Collectors.toList()));
                }
            }
        });

        busca_profissional.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    lista_profissional.getItems().clear();
                    lista_profissional.getItems().addAll(buscarProfissional().getProfissional().stream()
                            .filter(servico -> servico.getNome().toLowerCase().contains(busca_profissional.getText().toLowerCase()))
                            .collect(Collectors.toList()));
                }
            }
        });

        bot_proximo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Cliente cliente = lista_clientes.getSelectionModel().getSelectedItem();
                Servicos servicos = lista_servicos.getSelectionModel().getSelectedItem();
                Profissional profissional = lista_profissional.getSelectionModel().getSelectedItem();

                if (cliente != null && servicos != null && profissional != null) {
                    text_cliente.setText(cliente.toString());
                    text_servico.setText(servicos.toString());
                    text_profissional.setText(profissional.toString());
                    text_valorServico.setText(Formatar.formatar(servicos.getValor(), "#0.00"));
                    text_valorFinal.setText(Formatar.formatar((servicos.getValor()), "#0.00"));
                    paneFinal.setVisible(true);
                } else {
                    showErrorAlert("Preencha todos os campos para prosseguuir");
                }
            }
        });

        bot_verificarDisponibilidade.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (text_dataAgendada.getText().isEmpty() || text_dataAgendada.getText().isBlank() || text_horarioAgendado.getText().isBlank() || text_horarioAgendado.getText().isEmpty()) {
                    showErrorAlert("Preencha todos os campos para prosseguuir");
                    return;
                }

                Profissional profissional = lista_profissional.getSelectionModel().getSelectedItem();
                Servicos servicos = lista_servicos.getSelectionModel().getSelectedItem();

                text_horarioTermino.setText(calcularHorarioFinal(text_horarioAgendado.getText(), servicos.getTempo()));

                if (profissional != null) {
                    boolean disponivel = verificarDisponibilidade(profissional.getId());

                    if (!disponivel) {
                        showErrorAlert("Horário e data informada não está disponivel, tente outro horário ou data");
                    }
                }
            }
        });

        text_valorDesconto.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                if (text_valorDesconto.getText().isEmpty() || text_valorDesconto.getText().isBlank()) {
                    return;
                }

                double valorDesconto = Formatar.converterStringEmDouble(text_valorDesconto.getText());

                Servicos servicos = lista_servicos.getSelectionModel().getSelectedItem();

                if (servicos != null) {
                    double valorFinal = servicos.getValor();
                    valorFinal = valorFinal - valorDesconto;

                    text_valorFinal.setText(Formatar.formatar(valorFinal, "#0.00"));
                }
            }
        });

        bot_salvarAgendamento.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    JsonObject jsonRequest = new JsonObject();
                    jsonRequest.addProperty("cliente_id", lista_clientes.getSelectionModel().getSelectedItem().getId());
                    jsonRequest.addProperty("servico_id", lista_servicos.getSelectionModel().getSelectedItem().getId());
                    jsonRequest.addProperty("profissional_id", lista_profissional.getSelectionModel().getSelectedItem().getId());
                    jsonRequest.addProperty("dataagendada", Formatar.formatarData(text_dataAgendada.getText(), "dd/MM/yyyy", "yyyy-MM-dd"));
                    jsonRequest.addProperty("horaagendada", text_horarioAgendado.getText());
                    jsonRequest.addProperty("valordesconto", (text_valorDesconto.getText().isBlank() || text_valorDesconto.getText().isEmpty()) ? 0 : Formatar.converterStringEmDouble(text_valorDesconto.getText()));
                    jsonRequest.addProperty("valorfinal", Formatar.converterStringEmDouble(text_valorFinal.getText()));
                    jsonRequest.addProperty("cancelado", 0);
                    jsonRequest.addProperty("concluido", 0);

                    String apiUrl;

                    HttpURLConnection connection;

                    apiUrl = ConfigAPI.BASE_URL.concat(ConfigAPI.PATH_AGENDAMENTOS);
                    URL url = new URL(apiUrl);
                    connection = ConfigAPI.sendRequest(url,ConfigAPI.POST, token, jsonRequest);

                    int responseCode = connection.getResponseCode();

                    if (responseCode == 201 || responseCode == 200) {
                        MensagemConfirmacaoController.openScreen();
                        Stage stage = (Stage) bot_fechar.getScene().getWindow();
                        stage.close();
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
