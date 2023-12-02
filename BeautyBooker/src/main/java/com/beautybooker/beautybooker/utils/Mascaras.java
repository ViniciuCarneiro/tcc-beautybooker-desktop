package com.beautybooker.beautybooker.utils;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;


public class Mascaras {


    public static void dateField(final TextField textField)
    {
        maxField(textField, 10);

        textField.lengthProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
            {
                if (newValue.intValue() < 11)
                {
                    String value = textField.getText();
                    value = value.replaceAll("[^0-9]", "");
                    value = value.replaceFirst("(\\d{2})(\\d)", "$1/$2");
                    value = value.replaceFirst("(\\d{2})\\/(\\d{2})(\\d)", "$1/$2/$3");
                    textField.setText(value);
                    positionCaret(textField);
                }
            }
        });
    }

    public static void Cpf(TextField txtCPF)
    {
        maxField(txtCPF, 14);
        txtCPF.lengthProperty().addListener((observableValue, number, number2) ->
        {
            String value = txtCPF.getText();
            value = value.replaceAll("[^0-9]", "");
            value = value.replaceFirst("(\\d{3})(\\d)", "$1.$2");
            value = value.replaceFirst("(\\d{3})(\\d)", "$1.$2");
            value = value.replaceFirst("(\\d{3})(\\d)", "$1-$2");
            try
            {
                txtCPF.setText(value);
                positionCaret(txtCPF);
            }
            catch(Exception ex)
            {
            }
        });
    }

    public static void Telefone(TextField txtTelefone)
    {
        maxField(txtTelefone, 14);
        txtTelefone.lengthProperty().addListener((observableValue, number, number2) ->
        {
            try
            {
                String value = txtTelefone.getText();
                value = value.replaceAll("[^0-9]", "");
                int tam = value.length();
                value = value.replaceFirst("(\\d{2})(\\d)", "($1)$2");
                value = value.replaceFirst("(\\d{4})(\\d)", "$1-$2");
                if (tam > 10)
                {
                    value = value.replaceAll("-", "");
                    value = value.replaceFirst("(\\d{5})(\\d)", "$1-$2");
                }
                txtTelefone.setText(value);
                positionCaret(txtTelefone);

            }
            catch(Exception ex)
            {
            }
        });
    }

    public static void maxField(TextField txtCPF, Integer length)
    {
        txtCPF.textProperty().addListener((observableValue, oldValue, newValue) ->
        {
            if (newValue == null || newValue.length() > length)
            {
                txtCPF.setText(oldValue);
            }
        });
    }

    private static void positionCaret(TextField txtCPF)
    {
        Platform.runLater(() ->
        {
            if (txtCPF.getText().length() != 0)
            {
                txtCPF.positionCaret(txtCPF.getText().length());
            }
        });
    }
    public static void horaFild(final TextField textField)
    {
        maxField(textField, 5);

        textField.lengthProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
            {
                if (newValue.intValue() < 5)
                {
                    String value = textField.getText();
                    value = value.replaceAll("[^0-9]", "");
                    value = value.replaceFirst("(\\d{2})(\\d)", "$1:$2");
                    textField.setText(value);
                    positionCaret(textField);
                }
            }
        });
    }

    public static void monetaryField(final TextField textField)
    {
        textField.setAlignment(Pos.CENTER_LEFT);
        textField.lengthProperty().addListener((observable, oldValue, newValue) ->
        {
            String value = textField.getText();
            value = value.replaceAll("[^0-9]", "");
            value = value.replaceAll("([0-9]{1})([0-9]{14})$", "$1.$2");
            value = value.replaceAll("([0-9]{1})([0-9]{11})$", "$1.$2");
            value = value.replaceAll("([0-9]{1})([0-9]{8})$", "$1.$2");
            value = value.replaceAll("([0-9]{1})([0-9]{5})$", "$1.$2");
            value = value.replaceAll("([0-9]{1})([0-9]{2})$", "$1,$2");
            textField.setText(value);
            positionCaret(textField);
            textField.textProperty().addListener((ChangeListener) new ChangeListener<String>()
            {

                public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue)
                {
                    if (newValue.length() > 17)
                    {
                        textField.setText(oldValue);
                    }
                }
            });
        });
        textField.focusedProperty().addListener((observableValue, aBoolean, fieldChange) ->
        {
            int length;
            if (!(fieldChange || (length = textField.getText().length()) <= 0 || length >= 3))
            {
                textField.setText(textField.getText() + "00");
            }
        });
    }
}
