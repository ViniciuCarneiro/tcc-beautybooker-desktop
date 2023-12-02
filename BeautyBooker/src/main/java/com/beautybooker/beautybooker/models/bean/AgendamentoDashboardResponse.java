package com.beautybooker.beautybooker.models.bean;

import java.util.List;

public class AgendamentoDashboardResponse {
    private AgendamentoDashboard agendamento;
    private List<AgendamentoDashboard> agendamentos;

    public AgendamentoDashboardResponse() {
    }

    public AgendamentoDashboardResponse(AgendamentoDashboard agendamento) {
        this.agendamento = agendamento;
    }

    public AgendamentoDashboard getAgendamento() {
        return agendamento;
    }

    public void setAgendamento(AgendamentoDashboard agendamento) {
        this.agendamento = agendamento;
    }

    public List<AgendamentoDashboard> getAgendamentos() {
        return agendamentos;
    }

    public void setAgendamentos(List<AgendamentoDashboard> agendamentos) {
        this.agendamentos = agendamentos;
    }
}
