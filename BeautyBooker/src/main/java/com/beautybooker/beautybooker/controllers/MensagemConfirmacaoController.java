package com.beautybooker.beautybooker.controllers;

import com.beautybooker.beautybooker.Application;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ResourceBundle;

public class MensagemConfirmacaoController implements Initializable {

    @FXML
    private JFXButton bot_fechar;

    @FXML
    private Text text_contador;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
    }

    public static void openScreen()
    {
        try
        {
            Stage primaryStage = new Stage();
            primaryStage.setScene(Application.getSceneMensagemConfirmacao());
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

        Task<Void> sleeper = new Task<Void>()
        {
            @Override
            protected Void call() throws Exception
            {

                for (int i = 10; i >= 0; i--) {
                    int finalI = i;
                    Platform.runLater(() -> text_contador.setText(String.valueOf(finalI)));
                    Thread.sleep(1000);
                }

                return null;
            }
        };

        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>()
        {
            @Override
            public void handle(WorkerStateEvent event)
            {
                close();
            }
        });

        new Thread(sleeper).start();

        setOnAction();
    }

    private void close() {
        Stage stage = (Stage) bot_fechar.getScene().getWindow();
        stage.close();
    }

    private void setOnAction() {
        bot_fechar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                close();
            }
        });
    }
}
