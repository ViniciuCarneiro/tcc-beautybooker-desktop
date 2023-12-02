package com.beautybooker.beautybooker.models.bean;

public class Horarios {
    private int id;
    private int dia;
    private String nome;
    private String horarioabertura;
    private String horariofechamento;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getHorarioabertura() {
        return horarioabertura;
    }

    public void setHorarioabertura(String horarioabertura) {
        this.horarioabertura = horarioabertura;
    }

    public String getHorariofechamento() {
        return horariofechamento;
    }

    public void setHorariofechamento(String horariofechamento) {
        this.horariofechamento = horariofechamento;
    }
}
