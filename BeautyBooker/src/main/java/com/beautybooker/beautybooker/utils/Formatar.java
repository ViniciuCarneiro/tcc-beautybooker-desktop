package com.beautybooker.beautybooker.utils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Formatar {
    public static String formatar(double decimal, String formato)
    {
        String valor = new DecimalFormat(formato + "00").format(decimal);

        int penultima = Integer.parseInt(valor.substring(valor.length() - 2, valor.length() - 1));

        if (penultima < 5)
        {
            valor = valor.substring(0, valor.length() - 2) + "1";
        }
        else
        {
            valor = valor.substring(0, valor.length() - 2) + "9";
        }

        return new DecimalFormat(formato).format(Double.parseDouble(valor.replaceAll("[.]", "").replaceAll("[,]", ".")));
    }

    public static String formatarData(String dataString, String formatoAntigo, String formatoNovo) {
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

    public static String formatarData(String dataString) {
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

    public static double converterStringEmDouble(String string)
    {
        String ValorDouble = string.replace(",", "/");

        ValorDouble = ValorDouble.replace(".", "/");

        String[] teste = ValorDouble.split("/");

        String valor = "";
        int i = 0;

        while( i < teste.length - 1 ) {
            valor = valor + teste[i];

            i ++;
        }

        valor = valor + "." + teste[teste.length - 1];

        double stringConvertida = Double.parseDouble(valor);

        return stringConvertida;
    }
}
