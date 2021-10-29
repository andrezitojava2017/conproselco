/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comproselco.models;

import java.time.LocalDate;

/**
 *
 * @author andre
 */
public class Contratos {
    private int idContrato;
    private int numeroContrato;
    private LocalDate dataAssinatura;
    private  LocalDate dataEncerramento;
    private String lotacaoCandidato;
    private Processo idProcesso;
    private Aprovados Candidato;

    public int getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(int idContrato) {
        this.idContrato = idContrato;
    }

    public int getNumeroContrato() {
        return numeroContrato;
    }

    public void setNumeroContrato(int numeroContrato) {
        this.numeroContrato = numeroContrato;
    }

    public LocalDate getDataAssinatura() {
        return dataAssinatura;
    }

    public void setDataAssinatura(LocalDate dataAssinatura) {
        this.dataAssinatura = dataAssinatura;
    }

    public LocalDate getDataEncerramento() {
        return dataEncerramento;
    }

    public void setDataEncerramento(LocalDate dataEncerramento) {
        this.dataEncerramento = dataEncerramento;
    }

    public String getLotacaoCandidato() {
        return lotacaoCandidato;
    }

    public void setLotacaoCandidato(String lotacaoCandidato) {
        this.lotacaoCandidato = lotacaoCandidato;
    }

    public Processo getIdProcesso() {
        return idProcesso;
    }

    public void setIdProcesso(Processo idProcesso) {
        this.idProcesso = idProcesso;
    }

    public Aprovados getIdCandidato() {
        return Candidato;
    }

    public void setIdCandidato(Aprovados Candidato) {
        this.Candidato = Candidato;
    }
}
