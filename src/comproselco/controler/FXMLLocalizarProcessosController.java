/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comproselco.controler;

import com.jfoenix.controls.JFXButton;
import comproselco.dao.ProcessoDao;
import comproselco.models.Processo;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author andre
 */
public class FXMLLocalizarProcessosController implements Initializable {

    private Processo processo;
    private List<Processo> listaProcesso;
    @FXML
    private TableView<Processo> tableListaProcessos;
    @FXML
    private TableColumn<Processo, Integer> colNumProcesso;
    @FXML
    private TableColumn<Processo, Integer> colAnoProcesso;
    @FXML
    private TableColumn<Processo, String> colDescricao;
    @FXML
    private JFXButton btnConfirmar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // ao abrir o fomrulario sera setado as colunas
        // dpois e feito a busca na base de dados dos processos ja cadastrados
        // logo é preenchido na tabela os dados retornados da colecao
        setColunasTabela();

        ProcessoDao dao = new ProcessoDao();
        listaProcesso = dao.getListarProcessos();

        preencherTabelaListaProcessos();

    }

    /**
     * seta os atributos da classe Processo as colunas da tabela
     */
    private void setColunasTabela() {
        colNumProcesso.setCellValueFactory(new PropertyValueFactory<>("numeroProcesso"));
        colAnoProcesso.setCellValueFactory(new PropertyValueFactory<>("anoProcesso"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
    }

    /**
     * preenche a tabela com os dados dos processos seletivos/concursos
     * cadastrados na base de dados
     */
    private void preencherTabelaListaProcessos() {
        tableListaProcessos.setItems(FXCollections.observableArrayList(listaProcesso));
        TableView.TableViewSelectionModel<Processo> model = tableListaProcessos.getSelectionModel();
        model.select(0);
        processo = model.getSelectedItem();

    }

    /**
     * Define o ID do processo selecionado para usar no cadasto de aprovados
     */
    @FXML
    private void setIdProcesso() {
        TableView.TableViewSelectionModel<Processo> model = tableListaProcessos.getSelectionModel();

        Processo proc = model.getSelectedItem(); // recupera o objeto da linha selecionada
        this.processo = proc;
        fecharJanelaLocalizarProcesso();

    }

    @FXML
    private void habilitarBotaoConfirmar() {

        if (btnConfirmar.isDisable()) {
            btnConfirmar.setDisable(false);
        }
    }

    /**
     * recupera o ID do processo
     *
     * @return String
     */
    public Processo getIdProcesso() {
        return this.processo;
    }

    private void fecharJanelaLocalizarProcesso() {
        Stage stage = (Stage) btnConfirmar.getScene().getWindow();
        stage.close();
    }
}
