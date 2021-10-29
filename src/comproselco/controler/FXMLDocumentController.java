/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comproselco.controler;



import comproselco.models.Emails;
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
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author andre
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private MenuBar barraMenu;
    @FXML
    private MenuItem itemMenuCadProcesso;
    @FXML
    private MenuItem itemMenuCadEdital;
    @FXML
    private MenuItem itemMenuCadContratos;
    @FXML
    private ImageView imagemBrasao;
    @FXML
    private Label labelNomeDepartamento;
    @FXML
    private MenuItem itemMenuCadAprovados;
    @FXML
    private MenuItem menuConsultaProcessos;
    @FXML
    private MenuItem menuConsultaEdital;
    @FXML
    private MenuItem menuConsultaContratos;
    
    @FXML
    public void formularioNovoProcesso() throws IOException {
        
        Parent nProcesso = FXMLLoader.load(getClass().getResource("/comproselco/view/FXMLProcesso.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(nProcesso));
        stage.setTitle("Cadastro de Novos Processos");
        stage.setResizable(false);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.showAndWait();
        //System.out.println("<<<<<<< " + cont.getTeste() + " >>>>>>>>>>");
    }
    
    

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // define configuraão para envio de email
        Emails email = new Emails();
        email.lerArquivoConfigEmail();
        //System.out.println(">>> " + email.getEmailMensagem());
    }    

    @FXML
    private void formularioCadastroAprovados(ActionEvent event) {
        
        try {
            Parent cadAprovado = FXMLLoader.load(getClass().getResource("/comproselco/view/FXMLAprovados.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(cadAprovado));
            stage.setTitle("Cadastro de candidatos aprovados");
            stage.setResizable(false);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();
        } catch (IOException ex) {
            Alert msg = new Alert(Alert.AlertType.ERROR);
            msg.setTitle("Erro");
            msg.setHeaderText("Erro na exibição do formulario");
            msg.setContentText( ex.getMessage());
            msg.showAndWait();
        }
        
    }
    
    /*
    private void lerJsonDados() {
        JSONParser parser = new JSONParser();
        
        List<Aprovados> lst = new ArrayList<>();
        
        String cargos[] = {"Auxiliar serviços Gerais", "Guarda", "Dentista", "Auxiliar Administrativo", "Operador Maquinas"};
        String lotacao[] = {"SEDE - ADMINISTRACAO", "DISTRITO - EDUCACAO", "SEDE - EDUCACAO", "DISTRITO - SAUDE", "SEDE - CRAS"};
        String processo[] = {"6116ef283cfb332674e6db05", "611c5865e185ae6254179e17", "6116eea13cfb332674e6db03"};
        try(FileReader reader = new FileReader("data.json")){
            
            Object obj = parser.parse(reader);
            JSONArray array = (JSONArray) obj;
            Random ran = new Random();
            
            
            array.forEach(p ->{
                JSONObject o = (JSONObject)p;
                Aprovados ap = new Aprovados();
                
                String nomeCargo = cargos[ran.nextInt(5)];
                String lot = lotacao[ran.nextInt(5)];
                String proc = processo[ran.nextInt(3)];
                
                ap.setIdProcesso(new Processo(proc));
                ap.setInscricao(o.get("senha").toString());
                ap.setPosicaoClassificao(Integer.parseInt(o.get("idade").toString()));
                ap.setSituacao("Aprovado");
                
                ap.setCargo(new Cargos(
                        nomeCargo,
                        lot
                ));
                
                ap.setCandidatos(new Candidato(
                        o.get("nome").toString(),
                        o.get("rg").toString(),
                        o.get("cpf").toString(),
                        o.get("email").toString(),
                        o.get("pai").toString(),
                        o.get("mae").toString()
                ));
                
                //System.out.println(">>> " + ap.getCandidato().getNomeCandidato() + " " + ap.getIdProcesso().getIdProcesso() + " " + ap.getCargo().getNomeCargo());
                AprovadosDao dao = new AprovadosDao();
                ObjectId id = dao.inserirNovoCandidato(ap);
                System.out.println(">>> id " + id.toString());
        });

            
        } catch (FileNotFoundException ex ) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
    }
    */

    @FXML
    private void formularioEditalConvocacao(ActionEvent event) {
        
        try {
            
            Parent cadAprovado = FXMLLoader.load(getClass().getResource("/comproselco/view/FXMLEditalConvocacao.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(cadAprovado));
            stage.setTitle("Cadastro de candidatos aprovados");
            stage.setResizable(false);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void consultarProcessos(ActionEvent event) {
        
        try {
            Parent consultaProc = FXMLLoader.load(getClass().getResource("/comproselco/view/FXMLLocalizarProcessos.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(consultaProc));
            stage.setTitle("Consultar Processos");
            stage.setResizable(false);
            stage.showAndWait();
            
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void consultarEditalConvocacao(ActionEvent event) {
        
        try {
            Parent parentConsultaEdital = FXMLLoader.load(getClass().getResource("/comproselco/view/FXMLConsultaEdital.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(parentConsultaEdital));
            stage.setTitle("Consulta de Editais por processo");
            stage.showAndWait();
        } catch (IOException ex) {
            Alert msg = new Alert(Alert.AlertType.ERROR);
            msg.setTitle("Mensagem de Error");
            msg.setContentText("Aconteceu um erro!!\n" + ex);
            msg.showAndWait();
        }
        
    }

    @FXML
    private void consultarContratos(ActionEvent event) {
    }


}
