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
public class Lei {
 
    private int numeroLei;
    private int anolei;
    private LocalDate dataLei;
    private String descricao;
    private List<Cargos> listaCargos;

    public List<Cargos> getListaCargos() {
        return listaCargos;
    }

    public void setListaCargos(List<Cargos> listaCargos) {
        this.listaCargos = listaCargos;
    }

    public int getAnolei() {
        return anolei;
    }

    public void setAnolei(int anolei) {
        this.anolei = anolei;
    }

    public int getNumeroLei() {
        return numeroLei;
    }

    public void setNumeroLei(int numeroLei) {
        this.numeroLei = numeroLei;
    }

    public LocalDate getDataLei() {
        return dataLei;
    }

    public void setDataLei(LocalDate dataLei) {
        this.dataLei = dataLei;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Lei() {
    }
    
}
