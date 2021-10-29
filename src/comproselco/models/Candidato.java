/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comproselco.models;

/**
 *
 * @author andre
 */
public class Candidato {
    private String nomeCandidato;
    private String identidade;
    private String cpf;
    private String contatos[];
    private String email;
    private String nomePai;
    private String nomeMae;
    
    
    public Candidato(String nomeCandidato, String identidade, String cpf, String email, String nomePai, String nomeMae) {
        this.nomeCandidato = nomeCandidato;
        this.identidade = identidade;
        this.cpf = cpf;
        this.email = email;
        this.nomePai = nomePai;
        this.nomeMae = nomeMae;
    }

    public Candidato(String nomeCandidato) {
        this.nomeCandidato = nomeCandidato;
    }

    
    
    
    
    
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String[] getContatos() {
        return contatos;
    }

    public void setContatos(String[] contatos) {
        this.contatos = contatos;
    }

  

    public String getNomeCandidato() {
        return nomeCandidato;
    }

    public void setNomeCandidato(String nomeCandidato) {
        this.nomeCandidato = nomeCandidato;
    }

    public String getIdentidade() {
        return identidade;
    }

    public void setIdentidade(String identidade) {
        this.identidade = identidade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNomePai() {
        return nomePai;
    }

    public void setNomePai(String nomePai) {
        this.nomePai = nomePai;
    }

    public String getNomeMae() {
        return nomeMae;
    }

    public void setNomeMae(String nomeMae) {
        this.nomeMae = nomeMae;
    }
}
