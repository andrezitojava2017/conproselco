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
public class Processo {
    private String idProcesso;
    private String descricao;
    private int numeroProcesso;
    private int anoProcesso;
    private LocalDate dataValidade;
    private LocalDate dataHomologacao;
    private String tipoProcesso;
    private Lei leiAutorizativa;
    private List<Cargos> cargos;

    public Processo() {
    }

    public Processo(String idProcesso) {
        this.idProcesso = idProcesso;
    }

    
    
    public String getIdProcesso() {
        return idProcesso;
    }

    public void setIdProcesso(String idProcesso) {
        this.idProcesso = idProcesso;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getNumeroProcesso() {
        return numeroProcesso;
    }

    public void setNumeroProcesso(int numeroProcesso) {
        this.numeroProcesso = numeroProcesso;
    }

    public int getAnoProcesso() {
        return anoProcesso;
    }

    public void setAnoProcesso(int anoProcesso) {
        this.anoProcesso = anoProcesso;
    }

    public LocalDate getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(LocalDate dataValidade) {
        this.dataValidade = dataValidade;
    }

    public LocalDate getDataHomologacao() {
        return dataHomologacao;
    }

    public void setDataHomologacao(LocalDate dataHomologacao) {
        this.dataHomologacao = dataHomologacao;
    }

    public String getTipoProcesso() {
        return tipoProcesso;
    }

    public void setTipoProcesso(String tipoProcesso) {
        this.tipoProcesso = tipoProcesso;
    }

    public Lei getLeiAutorizativa() {
        return leiAutorizativa;
    }

    public void setLeiAutorizativa(Lei leiAutorizativa) {
        this.leiAutorizativa = leiAutorizativa;
    }

    public List<Cargos> getCargos() {
        return cargos;
    }

    public void setCargos(List<Cargos> cargos) {
        this.cargos = cargos;
    }

    
}
