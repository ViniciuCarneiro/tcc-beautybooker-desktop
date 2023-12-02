package com.beautybooker.beautybooker.models.bean;

public class AgendamentoDashboard {

    private int id;
    private Cliente cliente;
    private Servico servico;
    private Profissional profissional;
    private String dataagendada;
    private String horaagendada;
    private String horatermino;
    private double valordesconto;
    private double valorfinal;
    private int total;
    private int concluido;

    public AgendamentoDashboard() {
    }

    public AgendamentoDashboard(int total, int concluido) {
        this.total = total;
        this.concluido = concluido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public Profissional getProfissional() {
        return profissional;
    }

    public void setProfissional(Profissional profissional) {
        this.profissional = profissional;
    }

    public String getDataagendada() {
        return dataagendada;
    }

    public void setDataagendada(String dataagendada) {
        this.dataagendada = dataagendada;
    }

    public String getHoraagendada() {
        return horaagendada;
    }

    public void setHoraagendada(String horaagendada) {
        this.horaagendada = horaagendada;
    }

    public double getValordesconto() {
        return valordesconto;
    }

    public void setValordesconto(double valordesconto) {
        this.valordesconto = valordesconto;
    }

    public double getValorfinal() {
        return valorfinal;
    }

    public void setValorfinal(double valorfinal) {
        this.valorfinal = valorfinal;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getConcluido() {
        return concluido;
    }

    public void setConcluido(int concluido) {
        this.concluido = concluido;
    }

    public String getHoraaTermino() {
        return horatermino;
    }

    public void setHoraaTermino(String horaaTermino) {
        this.horatermino = horaaTermino;
    }
}
