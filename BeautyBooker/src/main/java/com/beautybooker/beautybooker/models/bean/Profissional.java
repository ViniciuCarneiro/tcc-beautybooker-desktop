package com.beautybooker.beautybooker.models.bean;

import java.util.Date;
import java.util.List;

public class Profissional {
    private int id;
    private String nome;
    private String sobrenome;
    private String cpf;
    private String email;
    private String datanascimento;
    private int admin;
    private String usuario;
    private String senha;
    private int ativo;
//    private Habilidades habilidades;
    private List<Habilidades> habilidades;

    public Profissional() {
    }

    public Profissional(int id,String nome, String sobrenome, String email, int admin) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.admin = admin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDatanascimento() {
        return datanascimento;
    }

    public void setDatanascimento(String datanascimento) {
        this.datanascimento = datanascimento;
    }

    public int isAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int isAtivo() {
        return ativo;
    }

    public void setAtivo(int ativo) {
        this.ativo = ativo;
    }

//    public Habilidades getHabilidades() {
//        return habilidades;
//    }
//
//    public void setHabilidades(Habilidades habilidades) {
//        this.habilidades = habilidades;
//    }


    public List<Habilidades> getHabilidades() {
        return habilidades;
    }

    public void setHabilidades(List<Habilidades> habilidades) {
        this.habilidades = habilidades;
    }

    @Override
    public String toString() {
        return nome + " " + sobrenome;
    }
}
