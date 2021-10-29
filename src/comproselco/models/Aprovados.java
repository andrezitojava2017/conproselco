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
public class Aprovados {
    private int idAprovado;
    private String inscricao;
    private String situacao;
    private Processo idProcesso;
    private Cargos cargo;
    private int posicaoClassificao;
    private String lotacao;
    private Candidato candidato;

    public Aprovados() {
    }

    public Aprovados(int idAprovado, Processo idProcesso, Cargos cargo, int posicaoClassificao, String lotacao, Candidato candidato) {
        this.idAprovado = idAprovado;
        this.idProcesso = idProcesso;
        this.cargo = cargo;
        this.posicaoClassificao = posicaoClassificao;
        this.lotacao = lotacao;
        this.candidato = candidato;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    
    public String getInscricao() {
        return inscricao;
    }

    public void setInscricao(String inscricao) {
        this.inscricao = inscricao;
    }
        

    public String getLotacao() {
        return lotacao;
    }

    public void setLotacao(String lotacao) {
        this.lotacao = lotacao;
    }


    public int getIdAprovado() {
        return idAprovado;
    }

    public void setIdAprovado(int idAprovado) {
        this.idAprovado = idAprovado;
    }

    public Processo getIdProcesso() {
        return idProcesso;
    }

    public void setIdProcesso(Processo idProcesso) {
        this.idProcesso = idProcesso;
    }

    public Cargos getCargo() {
        return cargo;
    }

    public void setCargo(Cargos cargo) {
        this.cargo = cargo;
    }

    public int getPosicaoClassificao() {
        return posicaoClassificao;
    }

    public void setPosicaoClassificao(int posicaoClassificao) {
        this.posicaoClassificao = posicaoClassificao;
    }

    public Candidato getCandidato() {
        return candidato;
    }

    public void setCandidatos(Candidato candidato) {
        this.candidato = candidato;
    }

    
    
}
