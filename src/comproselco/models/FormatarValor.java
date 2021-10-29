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
public class FormatarValor {
    
    
   
    /**
     * Formata a valor que sera aceito na base de dados
     * @param v
     * @return double
     */
    public Double formatarValor(String v){
       
        Double retornoValor = Double.parseDouble(v.replace(",", "."));
        return retornoValor;
    }
    
}
