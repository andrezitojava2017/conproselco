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
public class Cargos {

    private String nomeCargo;
    private int qtdVagasReserva;
    private int qtdVagasImediatas;
    private double remuneracao;
    private String lotacao;


    
    public Cargos(String cargo, String lotacao){
        this.nomeCargo = cargo;
        this.lotacao = lotacao;
    }
    
    public String getLotacao() {
        return lotacao;
    }

    public void setLotacao(String lotacao) {
        this.lotacao = lotacao;
    }

    public Cargos() {
    }

     
    public String getNomeCargo() {
        return nomeCargo;
    }

    public void setNomeCargo(String nomeCargo) {
        this.nomeCargo = nomeCargo;
    }

    public int getQtdVagasReserva() {
        return qtdVagasReserva;
    }

    public void setQtdVagasReserva(int qtdVagasReserva) {
        this.qtdVagasReserva = qtdVagasReserva;
    }

    public int getQtdVagasImediatas() {
        return qtdVagasImediatas;
    }

    public void setQtdVagasImediatas(int qtdVagasImediatas) {
        this.qtdVagasImediatas = qtdVagasImediatas;
    }

    public double getRemuneracao() {
        return remuneracao;
    }

    public void setRemuneracao(double remuneracao) {
        this.remuneracao = remuneracao;
    }
    
    
}
