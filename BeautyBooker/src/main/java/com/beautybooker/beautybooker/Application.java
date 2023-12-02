package com.beautybooker.beautybooker;

import com.beautybooker.beautybooker.controllers.CadastroLoginController;
import com.beautybooker.beautybooker.controllers.LoginController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.security.PublicKey;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginView.fxml"));
        Parent root = loader.load();

        LoginController loginController = loader.getController();
        loginController.setPrimaryStage(primaryStage);

        primaryStage.setTitle("BeautyBooker | Login");
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void loadCadastroScene(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(Application.class.getResource("/com/beautybooker/beautybooker/CadastroLoginView.fxml"));
            Parent root = loader.load();

            CadastroLoginController cadastroLoginController = loader.getController();
            cadastroLoginController.setPrimaryStage(primaryStage);

            primaryStage.setScene(new Scene(root));

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadLoginScene(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(Application.class.getResource("/com/beautybooker/beautybooker/LoginView.fxml"));
            Parent root = loader.load();

            LoginController loginController = loader.getController();
            loginController.setPrimaryStage(primaryStage);

            primaryStage.setScene(new Scene(root));

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Scene getSceneHome() throws IOException {
        FXMLLoader loader = new FXMLLoader(Application.class.getResource("/com/beautybooker/beautybooker/HomeView.fxml"));
        Parent root = loader.load();
        Scene homeScene = new Scene(root);

        return homeScene;
    }

    public static Scene getSceneCadastroCliente() throws IOException {
        FXMLLoader loader = new FXMLLoader(Application.class.getResource("/com/beautybooker/beautybooker/CadastroClientes.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        return scene;
    }
    public static Scene getSceneCadastroServicos() throws IOException {
        FXMLLoader loader = new FXMLLoader(Application.class.getResource("/com/beautybooker/beautybooker/CadastroServicos.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        return scene;
    }
    public static Scene getSceneCadastroAgendamento() throws IOException {
        FXMLLoader loader = new FXMLLoader(Application.class.getResource("/com/beautybooker/beautybooker/CadastroAgendamento.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        return scene;
    }

    public static Scene getSceneMensagemConfirmacao() throws IOException {
        FXMLLoader loader = new FXMLLoader(Application.class.getResource("/com/beautybooker/beautybooker/MensagemConfirmacao.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        return scene;
    }

    public static Scene getSceneInformacaoAgendamento() throws IOException {
        FXMLLoader loader = new FXMLLoader(Application.class.getResource("/com/beautybooker/beautybooker/InformacoesAgendamento.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        return scene;
    }

    public static String getURLImagemInfo() {
        URL imageUrl = Application.class.getResource("/com/beautybooker/beautybooker/image/icons8-information-50.png");

        if (imageUrl != null) {
            return imageUrl.toExternalForm();
        } else {
            System.out.println("nao achou");
            return null;
        }
    }

    public static String getURLImagemAgendamentoConcluir() {
        URL imageUrl = Application.class.getResource("/com/beautybooker/beautybooker/image/icons8-ok-50.png");

        if (imageUrl != null) {
            return imageUrl.toExternalForm();
        } else {
            System.out.println("nao achou");
            return null;
        }
    }

    public static void main(String[] args) {
        launch();
    }
}