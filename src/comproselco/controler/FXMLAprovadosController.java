/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comproselco.controler;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import comproselco.dao.AprovadosDao;
import comproselco.models.Aprovados;
import comproselco.models.Candidato;
import comproselco.models.Cargos;
import comproselco.models.Processo;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.bson.types.ObjectId;

/**
 * FXML Controller class
 *
 * @author andre
 */
public class FXMLAprovadosController implements Initializable {

    private Processo idProcesso;

    @FXML
    private JFXTextField textFieldNumeroProcesso;
    @FXML
    private JFXButton btnBuscarProcesso;
    @FXML
    private JFXTextField textFieldNomeCandidato;
    @FXML
    private JFXTextField textFieldCartIdentidade;
    @FXML
    private JFXTextField textFieldCpf;
    @FXML
    private JFXTextField textFieldContato1;
    @FXML
    private JFXTextField textFieldContato2;
    @FXML
    private JFXTextField textFieldEmail;
    @FXML
    private JFXTextField textFieldNomePai;
    @FXML
    private JFXTextField textFieldNomeMae;
    @FXML
    private JFXTextField textFieldSituacao;
    @FXML
    private JFXTextField textFieldClassificacao;
    @FXML
    private JFXTextField textFieldNumeroInscricao;
    @FXML
    private JFXTextField textFieldCargo;
    @FXML
    private JFXTextField textFieldLotacao;
    @FXML
    private JFXButton btnInserirCandidato;
    @FXML
    private JFXButton btnLocalizarCandidato;
    @FXML
    private JFXButton btnAtualizarCandidato;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //idProcesso = new Processo();
    }

    @FXML
    private void localizarProcesso(ActionEvent event) {
        try {
            // iremos chamar a view de busca de processos
            // para que retorne o idProcesso, para que depois de selecionar o processo,
            // possamos vincular o candidato q sera inscrito, ou buscar um candidato
            FXMLLoader viewLocalProcesso = new FXMLLoader();
            viewLocalProcesso.setLocation(getClass().getResource("/comproselco/view/FXMLLocalizarProcessos.fxml"));
            Parent localizar = viewLocalProcesso.load();
            FXMLLocalizarProcessosController controler = viewLocalProcesso.getController();

            Stage stage = new Stage();
            stage.setScene(new Scene(localizar));
            stage.setTitle("Listar Processos");
            stage.setResizable(false);

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            // preenche o campo numero de processo
            setNumeroProcesso(controler.getIdProcesso());

        } catch (IOException ex) {
            Alert msg = new Alert(Alert.AlertType.ERROR);
            msg.setTitle("Error");
            msg.setContentText(ex.getMessage());
        }
    }

    @FXML
    private void inserirCandidato(ActionEvent event) {
        // verificamos se os campos estao prenchidos
        if (textFieldNomeCandidato.getText().isEmpty()
                || textFieldCartIdentidade.getText().isEmpty()
                || textFieldCpf.getText().isEmpty()
                || textFieldContato1.getText().isEmpty()
                || textFieldContato2.getText().isEmpty()
                || textFieldEmail.getText().isEmpty()
                || textFieldNomePai.getText().isEmpty()
                || textFieldNomeMae.getText().isEmpty()
                || textFieldNumeroInscricao.getText().isEmpty()
                || textFieldSituacao.getText().isEmpty()
                || textFieldClassificacao.getText().isEmpty()
                || textFieldCargo.getText().isEmpty()
                || textFieldLotacao.getText().isEmpty()) {
            Alert msg = new Alert(Alert.AlertType.WARNING);
            msg.setTitle("Atenção");
            msg.setContentText("Preencha todos os campos!!");
            msg.showAndWait();
        } else {

            // aqui verificamos se o usuario vinculou um processo ao candidado
            // precisa escolher processo na view ao clicar em buscar processo
            if (idProcesso == null) {
                Alert msg = new Alert(Alert.AlertType.WARNING);
                msg.setTitle("Atenção");
                msg.setContentText("Selecione um Processo para vincularao candidato!!");
                msg.showAndWait();
            } else {
                Aprovados ap = new Aprovados();

                ap.setCandidatos(new Candidato(textFieldNomeCandidato.getText().toUpperCase(),
                        textFieldCartIdentidade.getText(),
                        textFieldCpf.getText(),
                        textFieldEmail.getText(),
                        textFieldNomePai.getText().toUpperCase(),
                        textFieldNomeMae.getText().toUpperCase()));

                ap.setCargo(new Cargos(textFieldCargo.getText().toUpperCase(), textFieldLotacao.getText().toUpperCase()));
                ap.setPosicaoClassificao(Integer.parseInt(textFieldClassificacao.getText()));
                ap.setInscricao(textFieldNumeroInscricao.getText());
                ap.setSituacao(textFieldSituacao.getText().toUpperCase());
                ap.setIdProcesso(new Processo(idProcesso.getIdProcesso()));

                
                
                // chama metodo que faz a insercao na colecao
                AprovadosDao dao = new AprovadosDao();
                ObjectId idCndt = dao.inserirNovoCandidato(ap);
                
                // msg de confirmação
                if (idCndt != null) {
                    Alert msg = new Alert(Alert.AlertType.INFORMATION);
                    msg.setTitle("Mensagem...");
                    msg.setContentText("Candidato salvo com sucesso!!!");
                    msg.showAndWait();
                }
            }

        }

    }

    @FXML
    private void localizarCandidato(ActionEvent event) {

        if (textFieldNumeroProcesso.getText().isEmpty()
                || textFieldNumeroProcesso.getText() == "") {
            Alert msg = new Alert(Alert.AlertType.WARNING);
            msg.setTitle("Atenção");
            msg.setContentText("Selecione primeiro um processo, para depois buscarmos seus candidatos!!!");
            msg.showAndWait();

        } else {
            try {

                FXMLLoader viewLocalizar = new FXMLLoader();
                viewLocalizar.setLocation(getClass().getResource("/comproselco/view/FXMLLocalizarCandidatos.fxml"));
                Parent localizar = viewLocalizar.load();
                FXMLLocalizarCandidatosController controler = viewLocalizar.getController();

                // definimos de qual processo queremos buscar os candidatos
                controler.setProcesso(this.idProcesso.getIdProcesso());

                Stage stage = new Stage();
                stage.setScene(new Scene(localizar));
                stage.setTitle("Localizar candidatos");
                stage.setResizable(false);
                stage.initModality(Modality.WINDOW_MODAL);
                stage.showAndWait();

                // apos selecionar o candidato na lista, preenchemos os campos com os dados do candidato
            } catch (IOException ex) {
                Alert msg = new Alert(Alert.AlertType.ERROR);
                msg.setTitle("Error");
                msg.setContentText(ex.getMessage());
            }
        }

    }

    /**
     * define o idProcesso, tanto para busca, qto para inserir novo candidato
     *
     * @return int idProcesso
     */
    private void setNumeroProcesso(Processo proc) {

        this.idProcesso = proc;
        textFieldNumeroProcesso.setText(proc.getNumeroProcesso() + "-" + proc.getAnoProcesso());
        btnLocalizarCandidato.setDisable(false);
    }

    @FXML
    private void atualizarDadosCandidato(ActionEvent event) {
    }

}
