/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comproselco.models;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author andre
 */
public class Edital {
    private int idEdital;
    private String numeroEdital;
    private LocalDate dataConvocacao;
    private LocalDate dataPrazo;
    private Processo idProcesso;
    private List<Aprovados> convocados;

    public List<Aprovados> getConvocados() {
        return convocados;
    }

    public void setConvocados(List<Aprovados> convocados) {
        this.convocados = convocados;
    }

    public String getNumeroEdital() {
        return numeroEdital;
    }

    public void setNumeroEdital(String numeroEdital) {
        this.numeroEdital = numeroEdital;
    }

    public LocalDate getDataConvocacao() {
        return dataConvocacao;
    }

    public void setDataConvocacao(LocalDate dataConvocacao) {
        this.dataConvocacao = dataConvocacao;
    }

    public LocalDate getDataPrazo() {
        return dataPrazo;
    }

    public void setDataPrazo(LocalDate dataPrazo) {
        this.dataPrazo = dataPrazo;
    }

    
    
    public int getIdEdital() {
        return idEdital;
    }

    public void setIdEdital(int idEdital) {
        this.idEdital = idEdital;
    }

    public Processo getIdProcesso() {
        return idProcesso;
    }

    public void setIdProcesso(Processo idProcesso) {
        this.idProcesso = idProcesso;
    }
}
