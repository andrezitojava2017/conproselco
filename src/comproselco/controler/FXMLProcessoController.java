/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comproselco.controler;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import comproselco.dao.ProcessoDao;
import comproselco.models.Lei;
import comproselco.models.Processo;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.bson.types.ObjectId;

/**
 * FXML Controller class
 *
 * @author andre
 */
public class FXMLProcessoController implements Initializable {

    private Lei leiAutorizativa;

    private Processo processo;
    @FXML
    private VBox vbox;
    @FXML
    private CheckBox checkBoxTipoSeletivo;
    @FXML
    private CheckBox checkBoxTipoConcurso;
    @FXML
    private TextField textFieldLeiAutorizativa;
    @FXML
    private Button btnBsucarLei;
    @FXML
    private JFXButton btnSalvar;
    @FXML
    private JFXButton btnAtualizar;
    @FXML
    private JFXButton btnProcurar;
    @FXML
    private JFXButton btnSair;
    @FXML
    private JFXTextField textFieldNumeroProcesso;
    @FXML
    private JFXTextField textFieldAnoProcesso;
    @FXML
    private JFXDatePicker datePickerDataHomologacao;
    @FXML
    private JFXTextArea textAreaDescricao;
    @FXML
    private JFXDatePicker datePickerDataValidade;

    /**
     * Construtor que inicializa um objeto tipo processo
     */
    public FXMLProcessoController() {
        processo = new Processo();

    }

    @FXML
    private void checkSeletiva(ActionEvent event) {
        processo.setTipoProcesso(checkBoxTipoSeletivo.getText());

        if (checkBoxTipoConcurso.isSelected()) {
            checkBoxTipoConcurso.setSelected(false);

        }
    }

    @FXML
    private void checkConcurso(ActionEvent event) {
        processo.setTipoProcesso(checkBoxTipoConcurso.getText());

        if (checkBoxTipoSeletivo.isSelected()) {
            checkBoxTipoSeletivo.setSelected(false);

        }
    }

    @FXML
    private void salvarProcesso() {
        if (verificarCampos()) {
            processo.setNumeroProcesso(Integer.parseInt(textFieldNumeroProcesso.getText()));
            processo.setAnoProcesso(Integer.parseInt(textFieldAnoProcesso.getText()));
            processo.setDataHomologacao(datePickerDataHomologacao.getValue());
            processo.setDataValidade(datePickerDataValidade.getValue());
            processo.setDescricao(textAreaDescricao.getText());

            // passamos objeto leiautorizativa com todas as infmações capturadas na tela de cadastro de lei
            processo.setLeiAutorizativa(this.leiAutorizativa);

            ProcessoDao dao = new ProcessoDao();
            ObjectId returnId = dao.inserirProcesso(processo);

            if (returnId != null) {
                Alert msg = new Alert(Alert.AlertType.INFORMATION);
                msg.setTitle("Mensagem...");
                msg.setContentText("Processo salvo com sucesso!!!");
                msg.showAndWait();
            }

        }

    }

    /**
     * Verifica se todos os campos estão preenchidos
     *
     * @return boolean
     */
    private boolean verificarCampos() {

        if (textFieldAnoProcesso.getText().isEmpty()
                || textFieldLeiAutorizativa.getText().isEmpty()
                || textFieldNumeroProcesso.getText().isEmpty()) {
            Alert msg = new Alert(Alert.AlertType.ERROR);
            msg.setTitle("Atenção");
            msg.setContentText("Preencha todos os campos!!");
            msg.showAndWait();
            return false;
        } else {
            return true;
        }
    }

    @FXML
    private void buscarLei() {
        try {
            FXMLLoader lei = new FXMLLoader();
            lei.setLocation(getClass().getResource("/comproselco/view/FXMLCadastroLei.fxml"));
            Parent nLei = lei.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(nLei));
            stage.setTitle("Cadastro de Lei e Cargos");
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            // ao sair da tela, iremos capturar a lista de cargos q fora cadastrada
            FXMLCadastroLeiController controlerLei = lei.getController();

            // atribuimos a um novo objeto desta classe, para nao perder o objeto
            this.leiAutorizativa = controlerLei.lei;

            // atribui o valor no campo numero de Lei somente para exibição ao usuario
            textFieldLeiAutorizativa.setText(String.valueOf(leiAutorizativa.getNumeroLei()));

        } catch (IOException ex) {
            Alert msg = new Alert(Alert.AlertType.ERROR);
            msg.setTitle("Erro");
            msg.setContentText(ex.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void btnLocalizarProcessos(ActionEvent event) {

        try {
            FXMLLoader viewLocalProcesso = new FXMLLoader();
            viewLocalProcesso.setLocation(getClass().getResource("/comproselco/view/FXMLLocalizarProcessos.fxml"));
            Parent localizar = viewLocalProcesso.load();
            
            Stage stage = new Stage();
            stage.setScene(new Scene(localizar));
            stage.setTitle("Listar Processos");
            stage.setResizable(false);
            
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(FXMLProcessoController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
