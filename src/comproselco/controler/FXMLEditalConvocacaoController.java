/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comproselco.controler;

import com.jfoenix.controls.JFXDatePicker;
import comproselco.dao.AprovadosDao;
import comproselco.dao.EditalConvocacaoDao;
import comproselco.models.Aprovados;
import comproselco.models.Candidato;
import comproselco.models.Cargos;
import comproselco.models.Edital;
import comproselco.models.Emails;
import comproselco.models.Processo;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFComment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.bson.types.ObjectId;

/**
 * FXML Controller class
 *
 * @author andre
 */
public class FXMLEditalConvocacaoController implements Initializable {

    private Edital edital;
    private List<Aprovados> listaCandidatos;
    private Processo processoSelecionado;
    private List<Aprovados> candidatosSelecionados;
    private Aprovados candSelecionadoPesquisa;
    @FXML
    private TextField campoNumeroProcesso;
    @FXML
    private Button btnBuscarProcesso;
    @FXML
    private TextField campoNumeroEditalConvocacao;
    @FXML
    private TextField campoPesqNomeCandidato;
    @FXML
    private Button btnPesqConfirmar;
    @FXML
    private TableView<Aprovados> tabelaListaCandidatos;
    @FXML
    private TableColumn<Aprovados, Integer> colunCalssificacao;
    @FXML
    private TableColumn<Aprovados, Candidato> colunCandidato;
    @FXML
    private TableColumn<Aprovados, Cargos> colunCargo;
    @FXML
    private TableColumn<Aprovados, Cargos> colunLotacao;
    @FXML
    private TextField campoPesqCargo;
    @FXML
    private Button btnPesCargoConfirmar;
    @FXML
    private TableView<Aprovados> tabelaListaCandidatosPorCargo;
    @FXML
    private TableColumn<Aprovados, Integer> colClassificacao2;
    @FXML
    private TableColumn<Aprovados, Candidato> colCandidato2;
    @FXML
    private TableColumn<Aprovados, Cargos> colCargo2;
    @FXML
    private TableColumn<Aprovados, Cargos> colLotacao2;
    @FXML
    private TableColumn<Aprovados, Integer> colTabCandSelecClassificacao;
    @FXML
    private TableColumn<Aprovados, Candidato> colTabCandSelecNome;
    @FXML
    private TableColumn<Aprovados, Cargos> colTabCandSelecCargo;
    @FXML
    private TableColumn<Aprovados, Cargos> colTabCandSelecLotacao;
    @FXML
    private TableView<Aprovados> tabelaCandidatosSelecionados;
    @FXML
    private Button btnExcluirCandSelecionado;
    @FXML
    private Button btnSalvarEdital;
    @FXML
    private Button btnGerarDocEdital;
    @FXML
    private Button btnSair;
    @FXML
    private JFXDatePicker dataEditalConvocacao;
    @FXML
    private JFXDatePicker dataPrazoComparecer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        this.listaCandidatos = new ArrayList<>();
        this.processoSelecionado = new Processo();
        this.candSelecionadoPesquisa = new Aprovados();
        this.candidatosSelecionados = new ArrayList<>();
        setColunasTabela();
        setColTabPesqPorCargo();
        setColTabCandidatosSelecionados();
        
    }

    @FXML
    private void buscarProcessos(ActionEvent event) {

        try {
            FXMLLoader processos = new FXMLLoader();
            processos.setLocation(getClass().getResource("/comproselco/view/FXMLLocalizarProcessos.fxml"));
            Parent localizar = processos.load();

            FXMLLocalizarProcessosController controler = processos.getController();

            Stage stage = new Stage();
            stage.setScene(new Scene(localizar));
            stage.setTitle("Listar Processos");
            stage.setResizable(false);

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            // define o id do processo q sera feito a busca
            setNumeroProcesso(controler.getIdProcesso());

            // recuperamos os candidatos do processo selecionado
            List<Aprovados> listCandidatos = getListaAprovados(this.processoSelecionado.getIdProcesso());

            // preenchemos a tabela com os dados dos candidatos
            preencherTabelaListaCandidatos(listCandidatos);

            // preenche a segunda tabela de pesquisa por cargo
            preencherTabListaCandidatosPorCargo(listCandidatos);

        } catch (IOException ex) {
            Alert msg = new Alert(Alert.AlertType.ERROR);
            msg.setTitle("Error");
            msg.setContentText(ex.getMessage());
            msg.showAndWait();
        }

    }

    /**
     * Add candidatos a lista de selecionados para convocação
     *
     * @param event
     */
    @FXML
    private void addCandidatoListaConvocacao(ActionEvent event
    ) {

        if (this.candSelecionadoPesquisa != null) {
            candidatosSelecionados.add(candSelecionadoPesquisa);

            // preenche a tabela de candidatos selecionados
            preencherTabListaCandidatosSelecionados(candidatosSelecionados);
        }

    }

    /**
     * Define o processo que iremos buscar os candidatos a serem convocados
     *
     * @param proc
     */
    private void setNumeroProcesso(Processo proc) {
        this.processoSelecionado = proc;
        campoNumeroProcesso.setText(proc.getNumeroProcesso() + "-" + proc.getAnoProcesso());

    }

    /**
     * Define as colunas da tabela de pesquisa POR NOME
     */
    private void setColunasTabela() {
        colunCalssificacao.setCellValueFactory(new PropertyValueFactory<>("posicaoClassificao"));
        colunCandidato.setCellValueFactory((param) -> new SimpleObjectProperty(param.getValue().getCandidato().getNomeCandidato()));
        colunCargo.setCellValueFactory((param) -> new SimpleObjectProperty(param.getValue().getCargo().getNomeCargo()));
        colunLotacao.setCellValueFactory((param) -> new SimpleObjectProperty(param.getValue().getCargo().getLotacao()));
    }

    /**
     * Define as colunas da tabela de pesquisa por CARGO
     */
    private void setColTabPesqPorCargo() {
        colClassificacao2.setCellValueFactory(new PropertyValueFactory<>("posicaoClassificao"));
        colCandidato2.setCellValueFactory((param) -> new SimpleObjectProperty(param.getValue().getCandidato().getNomeCandidato()));
        colCargo2.setCellValueFactory((param) -> new SimpleObjectProperty(param.getValue().getCargo().getNomeCargo()));
        colLotacao2.setCellValueFactory((param) -> new SimpleObjectProperty(param.getValue().getCargo().getLotacao()));
    }

    /**
     * Define as colunas da tabelas dos candidato que foram selecionados para
     * convocação
     */
    private void setColTabCandidatosSelecionados() {
        colTabCandSelecClassificacao.setCellValueFactory(new PropertyValueFactory<>("posicaoClassificao"));
        colTabCandSelecNome.setCellValueFactory((param) -> new SimpleObjectProperty(param.getValue().getCandidato().getNomeCandidato()));
        colTabCandSelecCargo.setCellValueFactory((param) -> new SimpleObjectProperty(param.getValue().getCargo().getNomeCargo()));
        colTabCandSelecLotacao.setCellValueFactory((param) -> new SimpleObjectProperty(param.getValue().getCargo().getLotacao()));
    }

    /**
     * Preenche a tabela com dados de uma lista passada com parametro com os
     * dados do candidato Pesquisa POR NOME
     *
     * @param listAprovados
     */
    private void preencherTabelaListaCandidatos(List<Aprovados> listAprovados) {
        tabelaListaCandidatos.setItems(FXCollections.observableArrayList(listAprovados));
        TableView.TableViewSelectionModel<Aprovados> model = tabelaListaCandidatos.getSelectionModel();

    }

    /**
     * Preenche a tabela de candidatos pequisa por CARGO com dados de candidatos
     * aprovados no processo selecionado
     *
     * @param listAprovados
     */
    private void preencherTabListaCandidatosPorCargo(List<Aprovados> listAprovados) {
        tabelaListaCandidatosPorCargo.setItems(FXCollections.observableArrayList(listAprovados));
        TableView.TableViewSelectionModel<Aprovados> model = tabelaListaCandidatosPorCargo.getSelectionModel();
    }

    /**
     * Preenche a tabela dos candidatos que irão ser convocados no edital
     *
     * @param listSelecionados
     */
    private void preencherTabListaCandidatosSelecionados(List<Aprovados> listSelecionados) {
        tabelaCandidatosSelecionados.setItems(FXCollections.observableArrayList(listSelecionados));
        TableView.TableViewSelectionModel<Aprovados> model = tabelaCandidatosSelecionados.getSelectionModel();
    }

    /**
     * Recupera uma lista de candidatos de um detemrinado processo para exibir
     * na tabela
     *
     * @param idProcesso
     * @return List<Aprovados>
     */
    private List<Aprovados> getListaAprovados(String idProcesso) {
        List<Aprovados> lista;

        AprovadosDao dao = new AprovadosDao();
        lista = dao.getAprovadoPorProcesso(idProcesso);

        // define valor ao atributo global da classe
        // para acessarmos nos metodos de busca por nomes/cargos
        this.listaCandidatos = lista;

        return lista;
    }

    /**
     * Faz a busca de determinado candidato na lista de candidatos recuperados
     */
    @FXML
    private void buscaCandidatoPorNome() {

        //String candidato = campoPesqNomeCandidato.getText();
        List<Aprovados> selecao = new ArrayList<>();

        if (listaCandidatos.isEmpty() || listaCandidatos == null) {
            Alert msg = new Alert(Alert.AlertType.INFORMATION);
            msg.setTitle("Atenção");
            msg.setContentText("Selecione um Processo para buscar os candidatos");
            msg.showAndWait();
        } else {
            listaCandidatos.stream()
                    .filter(e -> e.getCandidato().getNomeCandidato().contains(campoPesqNomeCandidato.getText()))
                    .forEach((Aprovados e) -> {
                        Aprovados cand = new Aprovados();
                        cand = e;
                        selecao.add(cand);
                        //System.out.println(e.getCandidato().getNomeCandidato());
                    });

            preencherTabelaListaCandidatos(selecao);
        }

    }

    /**
     * Faz a busca de determinado cargo na lista
     *
     * @param event
     */
    @FXML
    private void buscarCandidatoPorCargo(KeyEvent event) {
        //String candidato = campoPesqNomeCandidato.getText();
        List<Aprovados> selecao = new ArrayList<>();

        if (listaCandidatos.isEmpty() || listaCandidatos == null) {
            Alert msg = new Alert(Alert.AlertType.INFORMATION);
            msg.setTitle("Atenção");
            msg.setContentText("Selecione um Processo para buscar os candidatos");
            msg.showAndWait();
        } else {
            listaCandidatos.stream()
                    .filter(e -> e.getCargo().getNomeCargo().contains(campoPesqCargo.getText()))
                    .forEach(e -> {
                        Aprovados cand = new Aprovados();
                        cand = e;
                        selecao.add(cand);
                        //System.out.println(e.getCandidato().getNomeCandidato());
                    });

            preencherTabListaCandidatosPorCargo(selecao);
        }
    }

    /**
     * Captura objeto selecionado na tabela de pesquisa por NOME, e define valor
     * para atributo global da classe
     *
     * @param event
     */
    @FXML
    private void selecionarPesqCandidatoPorNome(MouseEvent event) {

        TableView.TableViewFocusModel<Aprovados> focusModel = tabelaListaCandidatos.getFocusModel();
        this.candSelecionadoPesquisa = focusModel.getFocusedItem();

        // habilita o btn para inserir na lista de candidatos que seraão convocados
        // exibir na tabela que foram selecionados
        btnPesqConfirmar.setDisable(false);

    }

    @FXML
    private void selecionarCandPorCargo(MouseEvent event) {
        TableView.TableViewFocusModel<Aprovados> focus = tabelaListaCandidatosPorCargo.getFocusModel();
        this.candSelecionadoPesquisa = focus.getFocusedItem();

        // habilita btn para inserir na lista de candidatos que serão convocados
        btnPesCargoConfirmar.setDisable(false);
    }

    /**
     * Exlui candidato da lista de convocação
     *
     * @param event
     */
    @FXML
    private void excluirCandSelecionadoLista(ActionEvent event) {
        TableView.TableViewFocusModel<Aprovados> focus = tabelaCandidatosSelecionados.getFocusModel();
        int indice = focus.getFocusedIndex();

        this.candidatosSelecionados.remove(indice);

        // preenche novamente a tabela com os dados atualizados
        preencherTabListaCandidatosSelecionados(candidatosSelecionados);
    }

    /**
     * Habilita btn de exclusao de candidato que fora selecionado excluindo da
     * lista de candidatos a serem convocados
     */
    @FXML
    private void ativarBtnExclusaoCandSelecionado(MouseEvent event) {
        if (btnExcluirCandSelecionado.isDisable()) {
            btnExcluirCandSelecionado.setDisable(false);
        }
    }

    /**
     *
     * @param event
     */
    @FXML
    private void salvarEditalConvocacao(ActionEvent event) {

        //System.out.println(dataEditalConvocacao.getValue());
        if (campoNumeroEditalConvocacao.getText().isEmpty()
                || campoNumeroEditalConvocacao.getText() == ""
                || dataEditalConvocacao.getValue() == null
                || dataPrazoComparecer.getValue() == null
                || candidatosSelecionados.isEmpty()) {
            Alert msg = new Alert(Alert.AlertType.INFORMATION);
            msg.setTitle("Atenção");
            msg.setContentText("Informe o numero e ano do edital, data de convocação e prazo comparecimento\nEx: 05/2021");
            msg.showAndWait();
        } else {

            edital = new Edital();
            edital.setNumeroEdital(campoNumeroEditalConvocacao.getText().replace("/", "-"));
            edital.setIdProcesso(processoSelecionado);
            edital.setConvocados(candidatosSelecionados);
            edital.setDataConvocacao(dataEditalConvocacao.getValue());
            edital.setDataPrazo(dataPrazoComparecer.getValue());

            EditalConvocacaoDao dao = new EditalConvocacaoDao();
            ObjectId idEdital = dao.gravarEditalConvocacao(edital);

            if (idEdital != null) {
                Alert msg = new Alert(Alert.AlertType.INFORMATION);
                msg.setTitle("Mensagem...");
                msg.setContentText("Edital salvo com sucesso!!!");
                msg.showAndWait();

                //ativa btnGerarDoc
                btnGerarDocEdital.setDisable(false);
                //desativa btnSalvarEdital
                btnSalvarEdital.setDisable(true);
            }

        }

    }

    @FXML
    private void gerarEditalDoc(ActionEvent event) {

        try {
            File f = new File("c:\\conproselco\\modelo\\teste.docx");

            // codigo copiado de link https://www.ti-enxame.com/pt/java/substituindo-um-texto-no-apache-poi-xwpf/1044872970/
            XWPFDocument doc = new XWPFDocument(new FileInputStream(f));
            List<XWPFParagraph> paragraphs = doc.getParagraphs();

            paragraphs.forEach((XWPFParagraph pr) -> {
                pr.getRuns().forEach(run -> {

                    String txt = run.getText(0);

                    if (txt != null && txt.contains("<numeroEdital>")) {
                        txt = txt.replace("<numeroEdital>", edital.getNumeroEdital());
                        run.setText(txt, 0);
                    }
                    if (txt != null && txt.contains("<dataEdital>")) {
                        txt = txt.replace("<dataEdital>", formatarData(edital.getDataConvocacao()));
                        run.setText(txt, 0);
                    }
                    if (txt != null && txt.contains("<processo>")) {
                        String proc = edital.getIdProcesso().getNumeroProcesso() + "/" + edital.getIdProcesso().getAnoProcesso();
                        txt = txt.replace("<processo>", proc);
                        run.setText(txt, 0);
                    }
                    if (txt != null && txt.contains("<prazo>")) {

                        txt = txt.replace("<prazo>", formatarData(edital.getDataPrazo()));
                        run.setText(txt, 0);
                    }

                });
            });

            //XWPFDocument doc2 = new XWPFDocument();
            doc = criarTabelaCandidatosSelecionados(candidatosSelecionados, doc);

            doc.write(new FileOutputStream(new File("c:\\conproselco\\edital\\" + edital.getNumeroEdital() + ".docx")));
            doc.close();
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Documento gerado com sucesso!!");
            alert.showAndWait();

        } catch (IOException ex) {
            Logger.getLogger(FXMLEditalConvocacaoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void fecharTela(ActionEvent event) {

        Stage st = (Stage) btnSair.getScene().getWindow();
        st.close();
    }

    /**
     * Cria tabela no documento .docx com os candidatos que foram selecionados
     * para convocação
     *
     * @param selecionados
     * @param doc
     * @return XWPFDocument
     */
    private XWPFDocument criarTabelaCandidatosSelecionados(List<Aprovados> selecionados, XWPFDocument doc) {

        XWPFDocument docT = doc;

        XWPFTable table = docT.createTable();
        XWPFTableRow cabecalho = table.createRow();
        cabecalho.getCell(0).setText("INSCRIÇÃO");
        cabecalho.addNewTableCell().setText("CLASSIFICACAO");
        cabecalho.addNewTableCell().setText("CANDIDATO");
        cabecalho.addNewTableCell().setText("CARGO");
        cabecalho.addNewTableCell().setText("LOTACAO");

        selecionados.forEach((aprovados) -> {
            XWPFTableRow linhas = table.createRow();

            linhas.addNewTableCell().setText(aprovados.getInscricao());
            linhas.addNewTableCell().setText(String.valueOf(aprovados.getPosicaoClassificao()));
            linhas.addNewTableCell().setText(aprovados.getCandidato().getNomeCandidato());
            linhas.addNewTableCell().setText(aprovados.getCargo().getNomeCargo());
            linhas.addNewTableCell().setText(aprovados.getCargo().getLotacao());
          
            table.addRow(linhas);

        });
        return docT;
    }

    private String formatarData(LocalDate dt) {
        DateTimeFormatter fr = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        //dt.format(fr);
        return dt.format(fr);
    }
}
