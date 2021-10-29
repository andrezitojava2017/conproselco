/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comproselco.controler;

import com.jfoenix.controls.JFXButton;
import comproselco.dao.AprovadosDao;
import comproselco.models.Aprovados;
import comproselco.models.Candidato;
import comproselco.models.Cargos;
import comproselco.models.Processo;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author andre
 */
public class FXMLLocalizarCandidatosController implements Initializable {

    private List<Aprovados> listaAprovados;
    private Processo idProcesso;
    @FXML
    private TableView<Aprovados> tableListaCandidatos;
    @FXML
    private TableColumn<Aprovados, Integer> colClassificacao;
    @FXML
    private TableColumn<Aprovados, String> colInscricao;
    @FXML
    private TableColumn<Aprovados, Candidato> colNomeCandidato;
    @FXML
    private TableColumn<Aprovados, Cargos> colCargo;
    @FXML
    private TableColumn<Aprovados, Cargos> colLotacao;
    @FXML
    private JFXButton btnEditarCandidato;
    @FXML
    private JFXButton btnSair;

    
    /**
     * define o idProcesso para buscar todos candidatos aprovados na base de dados
     * @param numProcesso 
     */
    public void setProcesso(String numProcesso){
        idProcesso = new Processo();
        idProcesso.setIdProcesso(numProcesso);
        
        // define as colunas
        setColunas();
        
        // carrega lista de candidatos de um determinad processo
        AprovadosDao dao = new AprovadosDao();
        this.listaAprovados = dao.getAprovadoPorProcesso(idProcesso.getIdProcesso());
        carregarListaCandidatos();
        
    }
    /**
     * metodo que faz o preenchimento da tabela com os dados recuperados 
     * da base de dados
     * @param processo 
     */
    private void carregarListaCandidatos(){
        tableListaCandidatos.setItems(FXCollections.observableArrayList(listaAprovados));
        TableView.TableViewSelectionModel<Aprovados> model = tableListaCandidatos.getSelectionModel();
    }
    
    /**
     * Define as colunas com os atributos da classe
     */
    private void setColunas(){
        colClassificacao.setCellValueFactory(new PropertyValueFactory<>("posicaoClassificao"));
        colInscricao.setCellValueFactory(new PropertyValueFactory<>("inscricao"));
        //colNomeCandidato.setCellValueFactory(new PropertyValueFactory<>("nomeCandidato"));
        colNomeCandidato.setCellValueFactory((param) -> new SimpleObjectProperty(param.getValue().getCandidato().getNomeCandidato()));
        colCargo.setCellValueFactory((param) -> new SimpleObjectProperty(param.getValue().getCargo().getNomeCargo()));
        colLotacao.setCellValueFactory((param) -> new SimpleObjectProperty(param.getValue().getCargo().getLotacao()));
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
