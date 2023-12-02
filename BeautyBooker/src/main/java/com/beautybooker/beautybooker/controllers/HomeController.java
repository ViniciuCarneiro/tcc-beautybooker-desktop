package com.beautybooker.beautybooker.controllers;

import com.beautybooker.beautybooker.Application;
import com.beautybooker.beautybooker.config.ConfigAPI;
import com.beautybooker.beautybooker.config.JwtDecoder;
import com.beautybooker.beautybooker.interfaces.HomeInterface;
import com.beautybooker.beautybooker.models.bean.*;
import com.beautybooker.beautybooker.utils.Formatar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import com.jfoenix.controls.JFXButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class HomeController implements Initializable {
    @FXML
    private JFXButton btn_dashboardMenu;

    @FXML
    private TextField text_buscaMenu;

    @FXML
    private JFXButton btn_clientesMenu;

    @FXML
    private JFXButton btn_servicosMenu;

    @FXML
    private JFXButton btnProfissionaisMenu;

    @FXML
    private JFXButton btn_configuracoesMenu;

    @FXML
    private Label txt_saudacoes;

    @FXML
    private Label txt_dataatual;

    @FXML
    private Label txt_agendamentosConcluidos;

    @FXML
    private Label txt_agendamentosTotal;

    @FXML
    private Label txt_agendamentosConcluido2;

    @FXML
    private Label txt_agendamentosTotal2;

    @FXML
    private Label txt_filtrosemana;

    @FXML
    private VBox vbox_proximosAgendamentos;

    @FXML
    private ImageView btn_reload;

    @FXML
    private HBox pane_grafico;
    @FXML
    private HBox hbox_horarios;
    @FXML
    private HBox hbox_segunda;

    @FXML
    private HBox hbox_terca;

    @FXML
    private HBox hbox_quarta;

    @FXML
    private HBox hbox_quinta;

    @FXML
    private HBox hbox_sexta;

    @FXML
    private HBox hbox_sabado;

    @FXML
    private HBox hbox_domingo;

    public static String token;

    private PieChart pieChart;

    @FXML
    private JFXButton btn_semanaAnterior;

    @FXML
    private JFXButton btn_proximaSemana;

    @FXML
    private JFXButton bot_novoAgendamento;
    @FXML
    private AnchorPane pane_clientes;
    @FXML
    private TableView<Cliente> table_clientes;

    @FXML
    private TableColumn<String, Cliente> column_nomeCliente;

    @FXML
    private TableColumn<String, Cliente> column_sobrenomeCliente;

    @FXML
    private TableColumn<String, Cliente> column_dataNascimentoCliente;

    @FXML
    private TableColumn<String, Cliente> column_cpfCliente;

    @FXML
    private TableColumn<String, Cliente> column_emailCliente;

    @FXML
    private TableColumn<String, Cliente> column_whatsappCliente;

    @FXML
    private TableColumn<String, Cliente> column_enderecoCliente;


    @FXML
    private JFXButton bot_excluirClientes;

    @FXML
    private JFXButton bot_atualizarClientes;

    @FXML
    private JFXButton bot_novoClientes;

    @FXML
    private HBox hbox_paginacaoClientes;

    @FXML
    private HBox hbox_registrosClientes;

    @FXML
    private ComboBox<String> cb_registrosPorPagina;

    @FXML
    private AnchorPane pane_Servicos;

    @FXML
    private TableView<Servicos> table_servicos;

    @FXML
    private TableColumn<String, Servico> column_nomeServicos;

    @FXML
    private TableColumn<String, Servico> column_tempoServicos;

    @FXML
    private TableColumn<String, Servico> column_valorServicos;

    @FXML
    private JFXButton bot_excluirServicos;

    @FXML
    private JFXButton bot_atualizarServicos;

    @FXML
    private JFXButton bot_novoServicos;

    @FXML
    private HBox hbox_paginacaoSerrvicos;

    @FXML
    private ComboBox<String> cb_registrosPorPaginaServicos;

    @FXML
    private AnchorPane pane_profissionais;

    @FXML
    private TableView<Profissional> table_profissional;

    @FXML
    private TableColumn<String, Profissional> column_nomeProfissional;

    @FXML
    private TableColumn<String, Profissional> column_sobrenomeProfissional;

    @FXML
    private TableColumn<String, Profissional> column_emailProfissional;

    @FXML
    private TableColumn<String, Profissional> column_dataNascimentoProfissional;

    @FXML
    private Label txt_saudacoes111;

    @FXML
    private JFXButton bot_excluirProfissional;

    @FXML
    private HBox hbox_paginacaProfissional;

    @FXML
    private HBox hbox_registrosClientes11;

    @FXML
    private ComboBox<String> cb_registrosPorPaginaProfissional;


    private String dataSemanaReferencia;

    private int idUsuarioLogado;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
    }

    public void init() {

        dataSemanaReferencia = getDataAtualBanco();

        pieChart = new PieChart();

        AcessToken acessToken = JwtDecoder.getTokenDecoded(token);

        idUsuarioLogado = acessToken.getProfissional().getId();

        txt_saudacoes.setText("Olá, "+acessToken.getProfissional().getNome()+ " " + acessToken.getProfissional().getSobrenome()+"!");
        txt_dataatual.setText(getDataAtual());

        btn_dashboardMenu.setStyle("-fx-background-color: #656ed3; -fx-text-fill: #ffffff;");

        ObservableList<String> opcoes = FXCollections.observableArrayList("05", "10", "30", "50", "80", "100");
        cb_registrosPorPagina.setItems(opcoes);
        cb_registrosPorPagina.setValue("05");

        ObservableList<String> opcoesServico = FXCollections.observableArrayList("05", "10", "30", "50", "80", "100");
        cb_registrosPorPaginaServicos.setItems(opcoesServico);
        cb_registrosPorPaginaServicos.setValue("05");

        ObservableList<String> opcoesProfissional = FXCollections.observableArrayList("05", "10", "30", "50", "80", "100");
        cb_registrosPorPaginaProfissional.setItems(opcoesProfissional);
        cb_registrosPorPaginaProfissional.setValue("05");

        getAgendamentosDashboard();
        getProximosAgendamentos();
        inicializarHBoxHorarios();
        setOnAction();
    }

    private void getAgendamentosDashboard() {
        try {

            txt_filtrosemana.setText(formatarData(getDataInicioSemana(dataSemanaReferencia), "yyyy-MM-dd", "dd/MM/yyyy") + " - " + formatarData(getDataFimSemana(dataSemanaReferencia), "yyyy-MM-dd", "dd/MM/yyyy"));

            String apiUrl = ConfigAPI.BASE_URL.concat(ConfigAPI.PATH_AGENDAMENTO_DASHBOARD).concat("?dataInicio="+getDataInicioSemana(dataSemanaReferencia)+"&dataFim="+getDataFimSemana(dataSemanaReferencia));
            URL url = new URL(apiUrl);

            HttpURLConnection connection = ConfigAPI.sendRequest(url,ConfigAPI.GET, token);

            int responseCode = connection.getResponseCode();

            if (responseCode == 200) {
                String responseBody = ConfigAPI.readResponse(connection);
                AgendamentoDashboardResponse agendamentoDashboardResponse = new Gson().fromJson(responseBody, AgendamentoDashboardResponse.class);

                txt_agendamentosConcluidos.setText(agendamentoDashboardResponse.getAgendamento().getConcluido() + " concluidos");
                txt_agendamentosConcluido2.setText(agendamentoDashboardResponse.getAgendamento().getConcluido() + "");
                txt_agendamentosTotal.setText("de "+agendamentoDashboardResponse.getAgendamento().getTotal()+" agendamentos");
                txt_agendamentosTotal2.setText(agendamentoDashboardResponse.getAgendamento().getTotal()+"");

                criarGraficoDashboard(agendamentoDashboardResponse.getAgendamento().getConcluido(), agendamentoDashboardResponse.getAgendamento().getTotal());
            } else {
                System.out.println("Falha na autenticação.");
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getProximosAgendamentos() {
        try {

            limparDashboard();

            String apiUrl = ConfigAPI.BASE_URL.concat(ConfigAPI.PATH_AGENDAMENTO);
            URL url = new URL(apiUrl);



            JsonObject jsonRequest = new JsonObject();

            JsonObject searchParams = new JsonObject();
            searchParams.addProperty("dataagendadastart", getDataInicioSemana(dataSemanaReferencia));
            searchParams.addProperty("dataagendadaend", getDataFimSemana(dataSemanaReferencia));
            searchParams.addProperty("concluido", 0);
            searchParams.addProperty("cancelado", 0);

            jsonRequest.add("searchParams", searchParams);

            HttpURLConnection connection = ConfigAPI.sendRequest(url,ConfigAPI.POST, token, jsonRequest);

            int responseCode = connection.getResponseCode();

            if (responseCode == 200) {
                String responseBody = ConfigAPI.readResponse(connection);
                AgendamentoDashboardResponse agendamentoDashboardResponse = new Gson().fromJson(responseBody, AgendamentoDashboardResponse.class);

                LocalDate dataAgendada;

                for (int i = 0; i < agendamentoDashboardResponse.getAgendamentos().size(); i++) {

                    vbox_proximosAgendamentos.getChildren().add(criarPaneAgendamentosProximos(agendamentoDashboardResponse.getAgendamentos().get(i)));

                    dataAgendada = LocalDate.parse(agendamentoDashboardResponse.getAgendamentos().get(i).getDataagendada().substring(0, 10), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    DayOfWeek diaSemana = dataAgendada.getDayOfWeek();

                    switch (diaSemana) {
                        case MONDAY:
                            criarCardAgendamento(agendamentoDashboardResponse.getAgendamentos().get(i).getCliente().getNome().concat(" ").concat(agendamentoDashboardResponse.getAgendamentos().get(i).getCliente().getSobrenome()), agendamentoDashboardResponse.getAgendamentos().get(i).getServico().getNome(), agendamentoDashboardResponse.getAgendamentos().get(i).getServico().getTempo(), agendamentoDashboardResponse.getAgendamentos().get(i).getHoraagendada(), i == 0 ? "" : agendamentoDashboardResponse.getAgendamentos().get(i -1).getHoraaTermino(), i == 0, hbox_segunda);
                            break;
                        case TUESDAY:
                            criarCardAgendamento(agendamentoDashboardResponse.getAgendamentos().get(i).getCliente().getNome().concat(" ").concat(agendamentoDashboardResponse.getAgendamentos().get(i).getCliente().getSobrenome()), agendamentoDashboardResponse.getAgendamentos().get(i).getServico().getNome(), agendamentoDashboardResponse.getAgendamentos().get(i).getServico().getTempo(), agendamentoDashboardResponse.getAgendamentos().get(i).getHoraagendada(), i == 0 ? "" : agendamentoDashboardResponse.getAgendamentos().get(i -1).getHoraaTermino(), i == 0, hbox_terca);
                            break;
                        case WEDNESDAY:
                            criarCardAgendamento(agendamentoDashboardResponse.getAgendamentos().get(i).getCliente().getNome().concat(" ").concat(agendamentoDashboardResponse.getAgendamentos().get(i).getCliente().getSobrenome()), agendamentoDashboardResponse.getAgendamentos().get(i).getServico().getNome(), agendamentoDashboardResponse.getAgendamentos().get(i).getServico().getTempo(), agendamentoDashboardResponse.getAgendamentos().get(i).getHoraagendada(), i == 0 ? "" : agendamentoDashboardResponse.getAgendamentos().get(i -1).getHoraaTermino(), i == 0, hbox_quarta);
                            break;
                        case THURSDAY:
                            criarCardAgendamento(agendamentoDashboardResponse.getAgendamentos().get(i).getCliente().getNome().concat(" ").concat(agendamentoDashboardResponse.getAgendamentos().get(i).getCliente().getSobrenome()), agendamentoDashboardResponse.getAgendamentos().get(i).getServico().getNome(), agendamentoDashboardResponse.getAgendamentos().get(i).getServico().getTempo(), agendamentoDashboardResponse.getAgendamentos().get(i).getHoraagendada(), i == 0 ? "" : agendamentoDashboardResponse.getAgendamentos().get(i -1).getHoraaTermino(), i == 0, hbox_quinta);
                            break;
                        case FRIDAY:
                            criarCardAgendamento(agendamentoDashboardResponse.getAgendamentos().get(i).getCliente().getNome().concat(" ").concat(agendamentoDashboardResponse.getAgendamentos().get(i).getCliente().getSobrenome()), agendamentoDashboardResponse.getAgendamentos().get(i).getServico().getNome(), agendamentoDashboardResponse.getAgendamentos().get(i).getServico().getTempo(), agendamentoDashboardResponse.getAgendamentos().get(i).getHoraagendada(), i == 0 ? "" : agendamentoDashboardResponse.getAgendamentos().get(i -1).getHoraaTermino(), i == 0, hbox_sexta);
                            break;
                        case SATURDAY:
                            criarCardAgendamento(agendamentoDashboardResponse.getAgendamentos().get(i).getCliente().getNome().concat(" ").concat(agendamentoDashboardResponse.getAgendamentos().get(i).getCliente().getSobrenome()), agendamentoDashboardResponse.getAgendamentos().get(i).getServico().getNome(), agendamentoDashboardResponse.getAgendamentos().get(i).getServico().getTempo(), agendamentoDashboardResponse.getAgendamentos().get(i).getHoraagendada(), i == 0 ? "" : agendamentoDashboardResponse.getAgendamentos().get(i -1).getHoraaTermino(), i == 0, hbox_sabado);
                            break;
                        case SUNDAY:
                            criarCardAgendamento(agendamentoDashboardResponse.getAgendamentos().get(i).getCliente().getNome().concat(" ").concat(agendamentoDashboardResponse.getAgendamentos().get(i).getCliente().getSobrenome()), agendamentoDashboardResponse.getAgendamentos().get(i).getServico().getNome(), agendamentoDashboardResponse.getAgendamentos().get(i).getServico().getTempo(), agendamentoDashboardResponse.getAgendamentos().get(i).getHoraagendada(), i == 0 ? "" : agendamentoDashboardResponse.getAgendamentos().get(i -1).getHoraaTermino(), i == 0, hbox_domingo);
                            break;
                    }
                }

            } else {
                System.out.println("Falha na autenticação.");
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void limparDashboard() {
        vbox_proximosAgendamentos.getChildren().clear();
        hbox_segunda.getChildren().clear();
        hbox_terca.getChildren().clear();
        hbox_quarta.getChildren().clear();
        hbox_quinta.getChildren().clear();
        hbox_sexta.getChildren().clear();
        hbox_sabado.getChildren().clear();
        hbox_domingo.getChildren().clear();
    }

    private String getDataInicioSemana(String data) {
        try {
            // Converta a string de data para um objeto Date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dataConvertida = sdf.parse(data);

            Calendar cal = Calendar.getInstance();

            // Defina a data de início da semana como a data fornecida
            cal.setTime(dataConvertida);

            // Defina o primeiro dia da semana como segunda-feira
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

            // Obtenha a data de início da semana
            Date dataInicioSemana = cal.getTime();

            // Adicione 6 dias para obter a data de término da semana (domingo)
            cal.add(Calendar.DATE, 6);
            Date dataFimSemana = cal.getTime();

            // Formate as datas conforme necessário
            String dataInicioFormatada = sdf.format(dataInicioSemana);

            return dataInicioFormatada;
        } catch (Exception e) {
            e.printStackTrace(); // Trate a exceção conforme necessário
            return null;
        }
    }

    private String getDataFimSemana(String data) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dataConvertida = sdf.parse(data);

            Calendar cal = Calendar.getInstance();

            cal.setTime(dataConvertida);

            // Defina o primeiro dia da semana como segunda-feira
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

            // Obtenha a data de início da semana
            Date dataInicioSemana = cal.getTime();

            // Adicione 6 dias para obter a data de término da semana (domingo)
            cal.add(Calendar.DATE, 6);
            Date dataFimSemana = cal.getTime();

            // Formate as datas conforme necessário
            String dataFimFormatada = sdf.format(dataFimSemana);

            return dataFimFormatada;
        } catch (Exception e) {
            e.printStackTrace(); // Trate a exceção conforme necessário
            return null;
        }
    }
    private void criarGraficoDashboard(int concluidos, int total) {
        PieChart.Data slice1 = new PieChart.Data(null, total);
        PieChart.Data slice2 = new PieChart.Data(null, concluidos);

        pieChart.getData().clear();
        pieChart.getData().addAll(slice1, slice2);

        pane_grafico.getChildren().clear();
        pane_grafico.getChildren().add(pieChart);
    }

    private String getDataAtual(){
        Date dataAtual = new Date();

        SimpleDateFormat formato = new SimpleDateFormat("EEEE, dd MMMM yyyy");

        String dataFormatada = formato.format(dataAtual);

        return "Hoje é " + dataFormatada;
    }

    private String formatarData(String dataString) {
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat formatoSaida = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date data = formatoEntrada.parse(dataString);
            return formatoSaida.format(data);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
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

    private String formatarHora(String hora) {
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat formatoSaida = new SimpleDateFormat("H'H'm'M'");

        try {
            Date data = formatoEntrada.parse(hora);
            return formatoSaida.format(data);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    private String getDataAtualBanco(){
        Date dataAtual = new Date();

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

        String dataFormatada = formato.format(dataAtual);

        return dataFormatada;
    }

    private AnchorPane criarPaneAgendamentosProximos(AgendamentoDashboard agendamento) {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefHeight(108.0);
        anchorPane.setPrefWidth(294.0);
        anchorPane.setStyle("-fx-background-color: #FFFF; -fx-background-radius: 15;");

        Label labelCliente = new Label("Cliente:");
        labelCliente.setLayoutX(11.0);
        labelCliente.setLayoutY(32.0);
        labelCliente.setFont(new Font("Arial Bold", 14.0));

        Label labelNomeCliente = new Label(agendamento.getCliente().getNome().concat(" ").concat(agendamento.getCliente().getSobrenome()));
        labelNomeCliente.setLayoutX(67.0);
        labelNomeCliente.setLayoutY(33.0);
        labelNomeCliente.setFont(new Font("Arial", 14.0));

        Label labelTempo = new Label("  " + formatarHora(agendamento.getServico().getTempo()));
        labelTempo.setLayoutX(260.0);
        labelTempo.setLayoutY(13.0);
        labelTempo.setPrefHeight(21.0);
        labelTempo.setPrefWidth(42.0);
        labelTempo.setStyle("-fx-background-color: #656ed3; -fx-background-radius: 10;");
        labelTempo.setTextFill(javafx.scene.paint.Color.WHITE);
        labelTempo.setFont(new Font("Arial Bold Italic", 10.0));

        Label labelServico = new Label("Serviço:");
        labelServico.setLayoutX(11.0);
        labelServico.setLayoutY(52.0);
        labelServico.setFont(new Font("Arial Bold", 14.0));

        Label labelNomeServico = new Label(agendamento.getServico().getNome());
        labelNomeServico.setLayoutX(71.0);
        labelNomeServico.setLayoutY(53.0);
        labelNomeServico.setFont(new Font("Arial", 14.0));

        Label labelProfissional = new Label("Profissional:");
        labelProfissional.setLayoutX(11.0);
        labelProfissional.setLayoutY(72.0);
        labelProfissional.setFont(new Font("Arial Bold", 14.0));

        Label labelNomeProfissional = new Label(agendamento.getProfissional().getNome().concat(" ").concat(agendamento.getProfissional().getSobrenome()));
        labelNomeProfissional.setLayoutX(102.0);
        labelNomeProfissional.setLayoutY(73.0);
        labelNomeProfissional.setFont(new Font("Arial", 14.0));

        Label labelData = new Label(formatarData(agendamento.getDataagendada()));
        labelData.setLayoutX(10.0);
        labelData.setLayoutY(4.0);
        labelData.setFont(new Font("Arial Bold", 14.0));

        Label labelHora = new Label(agendamento.getHoraagendada() + " - " + agendamento.getHoraaTermino());
        labelHora.setLayoutX(100.0);
        labelHora.setLayoutY(4.0);
        labelHora.setFont(new Font("Arial Bold", 14.0));

        String url = Application.getURLImagemInfo();

        ImageView infoIcon = new ImageView(new Image(url));
        infoIcon.setFitHeight(34.0);
        infoIcon.setFitWidth(27.0);
        infoIcon.setLayoutX(245.0);
        infoIcon.setLayoutY(68.0);
        infoIcon.setPickOnBounds(true);
        infoIcon.setPreserveRatio(true);
        infoIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                InformacoesAgendamentoController.openScreen(agendamento, token);
                vbox_proximosAgendamentos.getChildren().clear();
                getAgendamentosDashboard();
                getProximosAgendamentos();
            }
        });

        ImageView concluirIcon = new ImageView(new Image(Application.getURLImagemAgendamentoConcluir()));
        concluirIcon.setFitHeight(34.0);
        concluirIcon.setFitWidth(27.0);
        concluirIcon.setLayoutX(280.0);
        concluirIcon.setLayoutY(68.0);
        concluirIcon.setPickOnBounds(true);
        concluirIcon.setPreserveRatio(true);
        concluirIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

            }
        });

        anchorPane.getChildren().addAll(
                labelCliente, labelNomeCliente, labelTempo,
                labelServico, labelNomeServico, labelProfissional,
                labelNomeProfissional, labelData, labelHora, infoIcon,
                concluirIcon
        );

        return anchorPane;
    }

    private void criarCardAgendamento(String nome, String categoria, String tempo, String horaInicio, String horarioTermino, boolean primeiro, HBox hboxDiaSemana) {
        Pane pane = new Pane();

        double largura = tempo.equals("00:30:00") ? 150 : calcularLargura(tempo);
        pane.setPrefHeight(46.0);
        pane.setPrefWidth(largura);
        pane.setStyle("-fx-background-color: #656ed3;");

        Rectangle barraVertical = new Rectangle(0, 0, 5, 46);
        barraVertical.setFill(Color.web("#444184"));

        Label nameLabel = new Label(nome);
        nameLabel.setLayoutX(12.0);
        nameLabel.setLayoutY(6.0);
        nameLabel.setTextFill(javafx.scene.paint.Color.WHITE);
        nameLabel.setFont(Font.font("System Bold", 14.0));

        Label categoryLabel = new Label(categoria);
        categoryLabel.setLayoutX(12.0);
        categoryLabel.setLayoutY(22.0);
        categoryLabel.setTextFill(javafx.scene.paint.Color.WHITE);
        categoryLabel.setFont(Font.font(14.0));

        pane.getChildren().addAll(barraVertical, nameLabel, categoryLabel);

        int periodosVazios = calcularPeriodos((primeiro ? "07:00:00" : horarioTermino), horaInicio);

        if (periodosVazios > 0) {
            for (int i = 0; i < periodosVazios; i++) {
                Pane paneVazio = new Pane();
                paneVazio.setPrefHeight(46.0);
                paneVazio.setPrefWidth(120.0);
                hboxDiaSemana.getChildren().add(paneVazio);
            }
        }

        hboxDiaSemana.getChildren().add(pane);
    }

    private void inicializarHBoxHorarios() {
        hbox_horarios.setSpacing(200);

        List<Horarios> horarios = getHorariosFuncionamento().getHorarios();

        if (horarios != null && !horarios.isEmpty()) {
            String menorHorario = calcularMenorHorario(horarios);
            String maiorHorario = calcularMaiorHorario(horarios);

            adicionarLabelsAoHBox(menorHorario, maiorHorario);
        } else {
            System.out.println("Lista de horários vazia ou nula.");
        }
    }

    private String calcularMenorHorario(List<Horarios> horarios) {
        String menorHorario = horarios.get(0).getHorarioabertura();

        for (Horarios horario : horarios) {
            if (horario.getHorarioabertura().compareTo(menorHorario) < 0) {
                menorHorario = horario.getHorarioabertura();
            }
        }

        return menorHorario;
    }

    private String calcularMaiorHorario(List<Horarios> horarios) {
        String maiorHorario = horarios.get(0).getHorariofechamento();

        for (Horarios horario : horarios) {
            if (horario.getHorariofechamento().compareTo(maiorHorario) > 0) {
                maiorHorario = horario.getHorariofechamento();
            }
        }

        return maiorHorario;
    }

    private void adicionarLabelsAoHBox(String menorHorario, String maiorHorario) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime menor = LocalTime.parse(menorHorario, formatter);
        LocalTime maior = LocalTime.parse(maiorHorario, formatter);

        while (!menor.isAfter(maior)) {
            String horarioFormatado = menor.format(DateTimeFormatter.ofPattern("HH:mm"));
            adicionarLabelAoHBox(horarioFormatado);
            menor = menor.plusHours(1);
        }
    }

    private void adicionarLabelAoHBox(String texto) {
        Label label = new Label(texto);
        Font fonte = new Font("System Bold", 13.0);
        label.setFont(fonte);
        hbox_horarios.getChildren().add(label);
    }

    private HorarioFuncionamentoResponse getHorariosFuncionamento() {
        try {
            String apiUrl = ConfigAPI.BASE_URL.concat(ConfigAPI.PATH_HORARIOS_FUNCIONAMENTO);
            URL url = new URL(apiUrl);

            HttpURLConnection connection = ConfigAPI.sendRequest(url,ConfigAPI.GET, token);

            int responseCode = connection.getResponseCode();

            if (responseCode == 200) {
                String responseBody = ConfigAPI.readResponse(connection);
                HorarioFuncionamentoResponse horarioFuncionamentoResponse = new Gson().fromJson(responseBody, HorarioFuncionamentoResponse.class);
                return horarioFuncionamentoResponse;
            } else {
                System.out.println("Falha na autenticação.");
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private int calcularPeriodos(String horarioInicio, String horario){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            Date horaLimite = sdf.parse(horarioInicio);
            Date horarioFornecido = sdf.parse(horario);

            long diferencaEmMilissegundos = horarioFornecido.getTime() - horaLimite.getTime();
            long diferencaEmMinutos = diferencaEmMilissegundos / (60 * 1000);

            return (int) (diferencaEmMinutos / 30);
        }
        catch (Exception e ){
            e.printStackTrace();
        }

        return 0;
    }

    private double calcularLargura(String tempo) {
        String[] partes = tempo.split(":");
        int horas = Integer.parseInt(partes[0]);
        int minutos = Integer.parseInt(partes[1]);
        int segundos = Integer.parseInt(partes[2]);
        int totalMinutos = (int) (horas * 60 + minutos + Math.round(segundos / 60.0));

        double larguraBase = 40; // Largura base para 30 minutos
        double larguraPorHora = 230.0; // Aumento de largura por hora
        return larguraBase + (totalMinutos / 60.0) * larguraPorHora;
    }

    public static Date obterDataSemanaAnterior(Date data) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        calendar.add(Calendar.DAY_OF_WEEK, -7);

        return calendar.getTime();
    }

    public static Date obterDataProximaSemana(Date data) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        calendar.add(Calendar.DAY_OF_WEEK, +7);

        return calendar.getTime();
    }

    private ClientesResponse buscarClientes(int page) {
        try {
            String apiUrl;

            if (page > 0) {
                apiUrl = ConfigAPI.BASE_URL.concat(ConfigAPI.PATH_CLIENTES).concat("?page="+page+"&pageSize="+cb_registrosPorPagina.getSelectionModel().getSelectedItem());
            } else {
                apiUrl = ConfigAPI.BASE_URL.concat(ConfigAPI.PATH_CLIENTES).concat("?page=1&pageSize="+cb_registrosPorPagina.getSelectionModel().getSelectedItem());
            }

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
    private ServicosResponse buscarServicos(int page) {
        try {
            String apiUrl;

            if (page > 0) {
                apiUrl = ConfigAPI.BASE_URL.concat(ConfigAPI.PATH_SERVICOS).concat("?page="+page+"&pageSize="+cb_registrosPorPaginaServicos.getSelectionModel().getSelectedItem());
            } else {
                apiUrl = ConfigAPI.BASE_URL.concat(ConfigAPI.PATH_SERVICOS).concat("?page=1&pageSize="+cb_registrosPorPaginaServicos.getSelectionModel().getSelectedItem());
            }

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
   private ProfissionalResponse buscarProfissional(int page) {
        try {
            String apiUrl;

            if (page > 0) {
                apiUrl = ConfigAPI.BASE_URL.concat(ConfigAPI.PATH_PROFISSIONAIS).concat("?page="+page+"&pageSize="+cb_registrosPorPaginaProfissional.getSelectionModel().getSelectedItem());
            } else {
                apiUrl = ConfigAPI.BASE_URL.concat(ConfigAPI.PATH_PROFISSIONAIS).concat("?page=1&pageSize="+cb_registrosPorPaginaProfissional.getSelectionModel().getSelectedItem());
            }

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

    private void exibirClientes(int page) {

        ClientesResponse clientesResponse;

        if (page > 0){
            clientesResponse = buscarClientes(page);
        }
        else {
            clientesResponse = buscarClientes(0);
        }

        if (clientesResponse != null) {

            criarPaginacao(clientesResponse.getTotalPages(), clientesResponse.getPage());

            List<Cliente> clientesList = clientesResponse.getClientes();

            ObservableList<Cliente> observableClientesList = FXCollections.observableArrayList(clientesList);

            for (Cliente c: observableClientesList) {
                c.setDatanascimento(formatarData(c.getDatanascimento()));
            }

            column_nomeCliente.setCellValueFactory(new PropertyValueFactory<>("nome"));
            column_sobrenomeCliente.setCellValueFactory(new PropertyValueFactory<>("sobrenome"));
            column_dataNascimentoCliente.setCellValueFactory(new PropertyValueFactory<>("datanascimento"));
            column_cpfCliente.setCellValueFactory(new PropertyValueFactory<>("cpf"));
            column_emailCliente.setCellValueFactory(new PropertyValueFactory<>("email"));
            column_whatsappCliente.setCellValueFactory(new PropertyValueFactory<>("whatsapp"));
            column_enderecoCliente.setCellValueFactory(new PropertyValueFactory<>("endereco"));

            table_clientes.setItems(observableClientesList);
        }
    }

    private void exibirServicos(int page) {

        ServicosResponse servicosResponse;

        if (page > 0){
            servicosResponse = buscarServicos(page);
        }
        else {
            servicosResponse = buscarServicos(0);
        }

        if (servicosResponse != null) {

            criarPaginacaoServico(servicosResponse.getTotalPages(), servicosResponse.getPage());

            List<Servicos> servicoList = servicosResponse.getServicos();

            ObservableList<Servicos> observableClientesList = FXCollections.observableArrayList(servicoList);

            for (Servicos s: observableClientesList) {
                s.setValorFormatado(Formatar.formatar(s.getValor(), "#0.00"));
            }

            column_nomeServicos.setCellValueFactory(new PropertyValueFactory<>("nome"));
            column_tempoServicos.setCellValueFactory(new PropertyValueFactory<>("tempo"));
            column_valorServicos.setCellValueFactory(new PropertyValueFactory<>("valorFormatado"));

            table_servicos.setItems(observableClientesList);
        }
    }

    private void exibirProfissionais(int page) {

        ProfissionalResponse profissionalResponse;

        if (page > 0){
            profissionalResponse = buscarProfissional(page);
        }
        else {
            profissionalResponse = buscarProfissional(0);
        }

        if (profissionalResponse != null) {

            criarPaginacaoProfissional(profissionalResponse.getTotalPages(), profissionalResponse.getPage());

            List<Profissional> profissionalList = profissionalResponse.getProfissional();

            ObservableList<Profissional> observableProfissionalList = FXCollections.observableArrayList(profissionalList);

            for (Profissional s: observableProfissionalList) {
                s.setDatanascimento(Formatar.formatarData(s.getDatanascimento(), "yyyy-MM-dd", "dd/MM/yyyy"));
            }

            column_nomeProfissional.setCellValueFactory(new PropertyValueFactory<>("nome"));
            column_sobrenomeProfissional.setCellValueFactory(new PropertyValueFactory<>("sobrenome"));
            column_emailProfissional.setCellValueFactory(new PropertyValueFactory<>("email"));
            column_dataNascimentoProfissional.setCellValueFactory(new PropertyValueFactory<>("datanascimento"));

            table_profissional.setItems(observableProfissionalList);
        }
    }

    private void criarPaginacao(int totalPages, int paginaAtual) {
        hbox_paginacaoClientes.getChildren().clear();

        JFXButton btnAnterior = new JFXButton("< Anterior");
        btnAnterior.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (paginaAtual > 0) {
                    exibirClientes(paginaAtual - 1);
                }
            }
        });

        JFXButton btnProximo = new JFXButton("Próximo >");
        btnProximo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (paginaAtual + 1 <= totalPages) {
                    exibirClientes(paginaAtual + 1);
                }
            }
        });

        hbox_paginacaoClientes.getChildren().add(btnAnterior);

        for (int i = 1; i <= totalPages; i++) {
            JFXButton btnNumero = criarBotaoNumerado(i, (i == paginaAtual));
            hbox_paginacaoClientes.getChildren().add(btnNumero);
        }

        hbox_paginacaoClientes.getChildren().add(btnProximo);
    }

    private void criarPaginacaoServico(int totalPages, int paginaAtual) {
        hbox_paginacaoSerrvicos.getChildren().clear();

        JFXButton btnAnterior = new JFXButton("< Anterior");
        btnAnterior.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (paginaAtual > 0) {
                    exibirServicos(paginaAtual - 1);
                }
            }
        });

        JFXButton btnProximo = new JFXButton("Próximo >");
        btnProximo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (paginaAtual + 1 <= totalPages) {
                    exibirServicos(paginaAtual + 1);
                }
            }
        });

        hbox_paginacaoSerrvicos.getChildren().add(btnAnterior);

        for (int i = 1; i <= totalPages; i++) {
            JFXButton btnNumero = criarBotaoNumeradoServicos(i, (i == paginaAtual));
            hbox_paginacaoSerrvicos.getChildren().add(btnNumero);
        }

        hbox_paginacaoSerrvicos.getChildren().add(btnProximo);
    }

    private void criarPaginacaoProfissional(int totalPages, int paginaAtual) {
        hbox_paginacaProfissional.getChildren().clear();

        JFXButton btnAnterior = new JFXButton("< Anterior");
        btnAnterior.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (paginaAtual > 0) {
                    exibirServicos(paginaAtual - 1);
                }
            }
        });

        JFXButton btnProximo = new JFXButton("Próximo >");
        btnProximo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (paginaAtual + 1 <= totalPages) {
                    exibirProfissionais(paginaAtual + 1);
                }
            }
        });

        hbox_paginacaProfissional.getChildren().add(btnAnterior);

        for (int i = 1; i <= totalPages; i++) {
            JFXButton btnNumero = criarBotaoNumeradoServicos(i, (i == paginaAtual));
            hbox_paginacaProfissional.getChildren().add(btnNumero);
        }

        hbox_paginacaProfissional.getChildren().add(btnProximo);
    }

    private JFXButton criarBotaoNumerado(int numero, boolean paginaAtual) {
        JFXButton btnNumero = new JFXButton(Integer.toString(numero));
        btnNumero.setStyle(paginaAtual ? "-fx-background-color: #656ed3;" : "-fx-background-color: #9da0a4;");
        btnNumero.setTextFill(javafx.scene.paint.Color.WHITE);
        btnNumero.setFont(javafx.scene.text.Font.font("System Bold", 12.0));
        btnNumero.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                exibirClientes(Integer.parseInt(btnNumero.getText()));
            }
        });
        return btnNumero;
    }

    private JFXButton criarBotaoNumeradoServicos(int numero, boolean paginaAtual) {
        JFXButton btnNumero = new JFXButton(Integer.toString(numero));
        btnNumero.setStyle(paginaAtual ? "-fx-background-color: #656ed3;" : "-fx-background-color: #9da0a4;");
        btnNumero.setTextFill(javafx.scene.paint.Color.WHITE);
        btnNumero.setFont(javafx.scene.text.Font.font("System Bold", 12.0));
        btnNumero.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                exibirServicos(Integer.parseInt(btnNumero.getText()));
            }
        });
        return btnNumero;
    }

    private void setOnAction() {
        JFXButton[] menuButtons = {btn_dashboardMenu, btn_clientesMenu, btn_servicosMenu, btnProfissionaisMenu};

        for (JFXButton button : menuButtons) {
            button.setOnAction(event -> {
                for (JFXButton b : menuButtons) {
                    b.setStyle("-fx-background-color: transparent; -fx-text-fill: #9da0a4;");
                }

                JFXButton selectedButton = (JFXButton) event.getSource();
                selectedButton.setStyle("-fx-background-color: #656ed3; -fx-text-fill: #ffffff;");

                if (selectedButton.getId().equals(btn_dashboardMenu.getId())) {
                    pane_clientes.setVisible(false);
                    pane_Servicos.setVisible(false);
                    pane_profissionais.setVisible(false);
                }

                if (selectedButton.getId().equals(btn_clientesMenu.getId())) {
                    pane_clientes.setVisible(true);
                    pane_Servicos.setVisible(false);
                    pane_profissionais.setVisible(false);

                    exibirClientes(0);
                }

                if (selectedButton.getId().equals(btn_servicosMenu.getId())) {
                    pane_clientes.setVisible(false);
                    pane_Servicos.setVisible(true);
                    pane_profissionais.setVisible(false);

                    exibirServicos(0);
                }
                if (selectedButton.getId().equals(btnProfissionaisMenu.getId())) {
                    pane_clientes.setVisible(false);
                    pane_Servicos.setVisible(false);
                    pane_profissionais.setVisible(true);

                    exibirProfissionais(0);
                }
            });
        }

        btn_reload.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                vbox_proximosAgendamentos.getChildren().clear();
                getAgendamentosDashboard();
                getProximosAgendamentos();
            }
        });

        btn_semanaAnterior.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date data = sdf.parse(dataSemanaReferencia);

                    Date semanaAnterior = obterDataSemanaAnterior(data);
                    dataSemanaReferencia = sdf.format(semanaAnterior);

                    getAgendamentosDashboard();
                    getProximosAgendamentos();

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        btn_proximaSemana.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date data = sdf.parse(dataSemanaReferencia);

                    Date semanaAnterior = obterDataProximaSemana(data);
                    dataSemanaReferencia = sdf.format(semanaAnterior);

                    getAgendamentosDashboard();
                    getProximosAgendamentos();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        cb_registrosPorPagina.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                exibirClientes(0);
            }
        });

        cb_registrosPorPaginaServicos.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                exibirServicos(0);
            }
        });

        bot_novoClientes.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CadastroClientesController.openScreen(token, null);

                exibirClientes(0);
            }
        });

        bot_atualizarClientes.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Cliente cliente = table_clientes.getSelectionModel().getSelectedItem();

                if (cliente != null) {
                    CadastroClientesController.openScreen(token, cliente);
                    exibirClientes(0);
                } else {
                    showErrorAlert("Selecione um cliente para atualizar");
                }
            }
        });

        bot_excluirClientes.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    String apiUrl;

                    Cliente cliente = table_clientes.getSelectionModel().getSelectedItem();

                    if (cliente == null) {
                        showErrorAlert("Selecione um serviço para atualizar");
                        return;
                    }

                    apiUrl = ConfigAPI.BASE_URL.concat(ConfigAPI.PATH_CLIENTES).concat("/").concat(String.valueOf(cliente.getId()));

                    URL url = new URL(apiUrl);

                    HttpURLConnection connection = ConfigAPI.sendRequest(url,ConfigAPI.DELETE, token);

                    int responseCode = connection.getResponseCode();

                    if (responseCode == 200 || responseCode == 201) {
                        MensagemConfirmacaoController.openScreen();
                        exibirClientes(0);
                    } else {
                        System.out.println("Falha na autenticação.");
                    }

                    connection.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        bot_excluirServicos.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    String apiUrl;

                    Servicos servicos = table_servicos.getSelectionModel().getSelectedItem();

                    if (servicos == null) {
                        showErrorAlert("Selecione um serviço para atualizar");
                        return;
                    }

                    apiUrl = ConfigAPI.BASE_URL.concat(ConfigAPI.PATH_SERVICOS).concat("/").concat(String.valueOf(servicos.getId()));

                    URL url = new URL(apiUrl);

                    HttpURLConnection connection = ConfigAPI.sendRequest(url,ConfigAPI.DELETE, token);

                    int responseCode = connection.getResponseCode();

                    if (responseCode == 200 || responseCode == 201) {
                        MensagemConfirmacaoController.openScreen();
                        exibirServicos(0);
                    } else {
                        System.out.println("Falha na autenticação.");
                    }

                    connection.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        bot_novoServicos.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                CadastroServicoController.openScreen(token, null);
                exibirServicos(0);
            }
        });

        bot_atualizarServicos.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Servicos servicos = table_servicos.getSelectionModel().getSelectedItem();

                if (servicos!= null) {
                    CadastroServicoController.openScreen(token, servicos);
                    exibirServicos(0);
                } else {
                    showErrorAlert("Selecione um serviço para atualizar");
                }
            }
        });

        bot_novoAgendamento.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CadastroAgendamentoController.openScreen(token);
                vbox_proximosAgendamentos.getChildren().clear();
                getAgendamentosDashboard();
                getProximosAgendamentos();
            }
        });

        bot_excluirProfissional.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Profissional profissional = table_profissional.getSelectionModel().getSelectedItem();

                if (profissional!= null) {

                    if (profissional.getId() == idUsuarioLogado) {
                        showErrorAlert("Você não pode excluir esse profissional.");
                        return;
                    }

                    if (profissional.isAdmin() == 1) {
                        try {
                            String apiUrl;

                            apiUrl = ConfigAPI.BASE_URL.concat(ConfigAPI.PATH_PROFISSIONAIS).concat("/").concat(String.valueOf(profissional.getId()));

                            URL url = new URL(apiUrl);

                            HttpURLConnection connection = ConfigAPI.sendRequest(url,ConfigAPI.DELETE, token);

                            int responseCode = connection.getResponseCode();

                            if (responseCode == 200 || responseCode == 201) {
                                MensagemConfirmacaoController.openScreen();
                                exibirProfissionais(0);
                            } else {
                                System.out.println("Falha na autenticação.");
                            }

                            connection.disconnect();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        showErrorAlert("Acesso negado!");
                    }
                } else {
                    showErrorAlert("Selecione um serviço para atualizar");
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
