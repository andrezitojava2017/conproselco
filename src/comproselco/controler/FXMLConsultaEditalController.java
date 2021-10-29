/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comproselco.controler;

import comproselco.dao.AprovadosDao;
import comproselco.dao.EditalConvocacaoDao;
import comproselco.models.Aprovados;
import comproselco.models.Candidato;
import comproselco.models.Cargos;
import comproselco.models.Edital;
import comproselco.models.Emails;
import comproselco.models.Processo;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Stream;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SortEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author andre
 */
public class FXMLConsultaEditalController implements Initializable {

    private Processo processo;
    private List<Aprovados> convocados;
    @FXML
    private TextField textNumProcesso;
    @FXML
    private Button btnBuscarProcesso;
    @FXML
    private TableView<Edital> tableListaEditais;
    @FXML
    private TableColumn<Edital, String> colNumEdital;
    @FXML
    private TableView<Aprovados> tableListaConvocadosPorEdital;
    @FXML
    private TableColumn<Aprovados, Integer> colClassificacao;
    @FXML
    private TableColumn<Aprovados, Candidato> colNomeCandidato;
    @FXML
    private TableColumn<Aprovados, Cargos> colCargo;
    @FXML
    private TableColumn<?, ?> colDataEdital;
    @FXML
    private Button btnEnviarEmail;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // confg. colunas tabela editais
        setarColunasTabelaListaEdital();
        setColunasListaCandidatos();
    }

    @FXML
    private void buscarProcessos(ActionEvent event) {

        try {
            FXMLLoader viewConsultarProcessos = new FXMLLoader();
            viewConsultarProcessos.setLocation(getClass().getResource("/comproselco/view/FXMLLocalizarProcessos.fxml"));
            Parent parentConsulta = viewConsultarProcessos.load();
            FXMLLocalizarProcessosController controler = viewConsultarProcessos.getController();

            Stage stage = new Stage();
            stage.setScene(new Scene(parentConsulta));
            stage.setTitle("Consultar editais por processo");
            stage.setResizable(false);
            stage.showAndWait();

            // recuperamos o processo que fora selecionado
            this.processo = controler.getIdProcesso();

            textNumProcesso.setText(processo.getNumeroProcesso() + "/" + processo.getAnoProcesso());

            EditalConvocacaoDao dao = new EditalConvocacaoDao();
            List<Edital> listaEdital = dao.getListaEditalConvocacao(processo.getIdProcesso());

            carregarListaEditais(listaEdital);

        } catch (IOException ex) {

            Alert msg = new Alert(Alert.AlertType.ERROR);
            msg.setTitle("Error");
            msg.setContentText("Houve um erro!!\n" + ex.getMessage());
            msg.showAndWait();

        }

    }

    private void setarColunasTabelaListaEdital() {

        colNumEdital.setCellValueFactory(new PropertyValueFactory<>("numeroEdital"));

    }

    private void setColunasListaCandidatos() {

        colClassificacao.setCellValueFactory(new PropertyValueFactory<>("posicaoClassificao"));
        colNomeCandidato.setCellValueFactory((param) -> new SimpleObjectProperty(param.getValue().getCandidato().getNomeCandidato()));
        colCargo.setCellValueFactory((param) -> new SimpleObjectProperty(param.getValue().getCargo().getNomeCargo()));

    }

    /**
     * preenche a tabela lista de editais
     *
     * @param lista
     */
    private void carregarListaEditais(List<Edital> lista) {

        tableListaEditais.setItems(FXCollections.observableArrayList(lista));
        TableView.TableViewSelectionModel<Edital> model = tableListaEditais.getSelectionModel();
        model.select(0);
        convocados = model.getSelectedItem().getConvocados();
        preencherTabelaListaCandidatosConvocados(convocados);
    }

    /**
     * preenche a tabela com dados dos candidatos convocados em um determinado
     * edital ao selecionar edital na tabela edital, a tabela de convocados e
     * preenchido
     *
     * @param convocados
     */
    private void preencherTabelaListaCandidatosConvocados(List<Aprovados> convocados) {

        tableListaConvocadosPorEdital.setItems(FXCollections.observableArrayList(convocados));
        TableView.TableViewSelectionModel<Aprovados> model = tableListaConvocadosPorEdital.getSelectionModel();

    }

    @FXML
    private void selecionarEdital(MouseEvent event) {

        TableView.TableViewSelectionModel<Edital> selectionModel = tableListaEditais.getSelectionModel();
        convocados = selectionModel.getSelectedItem().getConvocados();
        preencherTabelaListaCandidatosConvocados(convocados);
    }

    @FXML
    private void enviarEmailCandidatos(ActionEvent event) {

        List<Candidato> listaEmails = new ArrayList<>();
        AprovadosDao apDao = new AprovadosDao();
        List<Aprovados> lstCandidatos = apDao.getAprovadoPorProcesso(processo.getIdProcesso());

        // aqui percorremos a lista de candidatos convocados em um edital
        // e iremos filtrar com a lista de candidatos aprovados do processo selcionado
        // para capturarmos o email do candidato que esta cadastrado
        convocados.stream().forEach(cand -> {
            lstCandidatos.stream()
                    .filter((Aprovados ap) -> {
                        return ap.getCandidato().getNomeCandidato().equalsIgnoreCase(cand.getCandidato().getNomeCandidato());
                    })
                    .forEach(ac -> {
                        Candidato candConvocado = ac.getCandidato();
                        listaEmails.add(candConvocado);
                        //System.out.println(">> " + ac.getCandidato().getNomeCandidato() + "- email: " + ac.getCandidato().getEmail());
                    });
        });

        enviarEmails(listaEmails);
    }

    private void enviarEmails(List<Candidato> emails) {
        Alert aviso = new Alert(Alert.AlertType.CONFIRMATION);
        aviso.setTitle("Aviso");
        aviso.setContentText("Enviar E-mails de comunicação agora?");
        Optional<ButtonType> wait = aviso.showAndWait();
        ButtonType get = wait.get();

        if (get.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
            
            Emails enviar = new Emails();
            boolean retornoEmail = enviar.enviarEmailCandidatosConvocados(emails);

            if (retornoEmail) {
                Alert msg = new Alert(Alert.AlertType.INFORMATION);
                msg.setTitle("Aviso");
                msg.setContentText("E-mail enviado com sucesso!");
                msg.showAndWait();
            }
        }
    }
}
