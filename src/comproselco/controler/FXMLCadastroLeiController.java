/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comproselco.controler;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import comproselco.models.Cargos;
import comproselco.models.FormatarValor;
import comproselco.models.Lei;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author andre
 */
public class FXMLCadastroLeiController implements Initializable {

    Lei lei;
    List<Cargos> cargos;
    private double remun;
    private int indiceItemLista;

    @FXML
    private JFXTextField textFieldNumeroLei;
    @FXML
    private JFXTextField textFieldAnoLei;
    @FXML
    private JFXDatePicker DatePickerDataLei;
    @FXML
    private TableView<Cargos> tableListCargos;
    @FXML
    private JFXTextField textFieldNomeCargo;
    @FXML
    private JFXTextField textFieldQtdVagasImediatas;
    @FXML
    private JFXTextField textFieldQtdVagasReservas;
    @FXML
    private JFXTextField textFieldRemuneracao;
    @FXML
    private JFXButton btnSalvarCargo;
    @FXML
    private TableColumn<Cargos, String> colNomeCargo;
    @FXML
    private TableColumn<Cargos, Integer> colQtdImediata;
    @FXML
    private TableColumn<Cargos, Integer> colQtdreserva;
    @FXML
    private TableColumn<Cargos, Double> colRemuneracao;
    @FXML
    private JFXButton btnExcluirItemLista;
    @FXML
    private JFXButton btnConfirmarLei;
    @FXML
    private JFXTextField textFieldLotacao;
    @FXML
    private TableColumn<?, ?> colLotacao;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // instancia objeto para prencher com dados dos cargos
        this.cargos = new ArrayList<>();
        this.lei = new Lei();

        // setar colunas das tabela
        setColunasTabela();
    }

    /**
     * seta as colunas aos atributos da classe Cargos
     */
    private void setColunasTabela() {
        colNomeCargo.setCellValueFactory(new PropertyValueFactory<>("nomeCargo"));
        colQtdImediata.setCellValueFactory(new PropertyValueFactory<>("qtdVagasImediatas"));
        colQtdreserva.setCellValueFactory(new PropertyValueFactory<>("qtdVagasReserva"));
        colRemuneracao.setCellValueFactory(new PropertyValueFactory<>("remuneracao"));
        colLotacao.setCellValueFactory(new PropertyValueFactory<>("lotacao"));
    }

    /**
     * Add elementos na lista de cargos para aprsentar na tabelaCargos
     *
     * @param cg Cargos
     */
    private void addElementosListaCargos(Cargos cg) {
        cargos.add(cg);
    }

    /**
     * Remove um item da lista de cargos
     *
     * @param indice
     */
    private void removerItemListaCargos(int indice) {

        cargos.remove(indice); // remove o item selecionado

        btnExcluirItemLista.setDisable(true); // desabilita o botao excluir
        preencherTabelaCargos(); // atualiza a tabela
    }

    /**
     * Preenche a tabela com os dados dos cargos
     */
    private void preencherTabelaCargos() {

        tableListCargos.setItems(FXCollections.observableArrayList(cargos));

    }

    @FXML
    private void capturarDadosCargo() {

        Cargos cg = new Cargos();

        if (textFieldNomeCargo.getText().isEmpty()
                || textFieldQtdVagasImediatas.getText().isEmpty()
                || textFieldQtdVagasReservas.getText().isEmpty()
                || textFieldRemuneracao.getText().isEmpty()
                || textFieldLotacao.getText().isEmpty()) {
            Alert msg = new Alert(Alert.AlertType.INFORMATION);
            msg.setTitle("Error");
            msg.setContentText("Atenção preencha todos os campos do cargo!!");
            msg.showAndWait();
        } else {
            cg.setNomeCargo(textFieldNomeCargo.getText().toUpperCase());
            cg.setQtdVagasImediatas(Integer.parseInt(textFieldQtdVagasImediatas.getText()));
            cg.setQtdVagasReserva(Integer.parseInt(textFieldQtdVagasReservas.getText()));
            cg.setLotacao(textFieldLotacao.getText().toUpperCase());
            cg.setRemuneracao(this.remun);

            addElementosListaCargos(cg);
            preencherTabelaCargos();

            limparCampos();

        }

    }

    /**
     * Limpa os campos de cargos
     */
    private void limparCampos() {
        textFieldNomeCargo.setText("");
        textFieldQtdVagasImediatas.setText("");
        textFieldQtdVagasReservas.setText("");
        textFieldRemuneracao.setText("");
        textFieldLotacao.setText("");
    }

    @FXML
    private void formatarValor(KeyEvent event) {

        if (textFieldRemuneracao.getText().isEmpty()) {
            textFieldRemuneracao.setText("0");
        }
        FormatarValor formtar = new FormatarValor();

        remun = formtar.formatarValor(textFieldRemuneracao.getText());
        // System.out.println("FORMATO REMUNERACAO: >>> " + format.format(v));
    }

    @FXML
    private void recuperaItemLista() {
        TableView.TableViewSelectionModel<Cargos> model = tableListCargos.getSelectionModel();
        //Cargos table = model.getSelectedItem(); // RECUPERA O CARGO SELECIONADO NA TABELA
        this.indiceItemLista = model.getFocusedIndex(); // RECUPERA O INDICE DA LINHA

        btnExcluirItemLista.setDisable(false);
    }

    @FXML
    private void removerItemListaCargos(ActionEvent event) {
        removerItemListaCargos(indiceItemLista);
    }

    @FXML
    private void capturarLei() {

        if (textFieldNumeroLei.getText().isEmpty()
                || textFieldAnoLei.getText().isEmpty()
                || DatePickerDataLei.getValue() == null) {
            Alert msg = new Alert(Alert.AlertType.INFORMATION);
            msg.setTitle("Erro");
            msg.setContentText("Atenção Preencha todos os campos!!");
        } else {

            lei.setAnolei(Integer.parseInt(textFieldAnoLei.getText()));
            lei.setNumeroLei(Integer.parseInt(textFieldNumeroLei.getText()));
            lei.setDataLei(DatePickerDataLei.getValue());
            lei.setListaCargos(cargos);
            
            // fecha a janela de cadastro
            fecharJanelaCadastroLei();
        }
    }

    private void fecharJanelaCadastroLei() {

        Stage stage = (Stage) btnConfirmarLei.getScene().getWindow();
        stage.close();
    }
}
