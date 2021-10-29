/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comproselco.models;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

/**
 *
 * @author andre
 */
public class Emails {

    private String emailDestino;
    private static String emailRemetente;
    private static String emailMensagem;
    private static String emailSenha;
    private static String hostName;
    private static int smtpPort;

    
    

    public String getEmailDestino() {
        return emailDestino;
    }

    public void setEmailDestino(String emailDestino) {
        this.emailDestino = emailDestino;
    }

    public String getEmailRemetente() {
        return emailRemetente;
    }

    public void setEmailRemetente(String emailRemetente) {
        Emails.emailRemetente = emailRemetente;
    }

    public String getEmailMensagem() {
        return Emails.emailMensagem;
    }

    public void setEmailMensagem(String emailMensagem) {
        Emails.emailMensagem = emailMensagem;
    }

    public String getEmailSenha() {
        return emailSenha;
    }

    public void setEmailSenha(String emailSenha) {
        Emails.emailSenha = emailSenha;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        Emails.hostName = hostName;
    }

    public int getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(int smtpPort) {
        Emails.smtpPort = smtpPort;
    }

    /**
     * le arquivo txt que tem as configuraçoes para envio de mail SMPT, PORT e
     * HOSTNAME
     */
    public void lerArquivoConfigEmail() {

        Emails configEmail = new Emails();
        try {
            Path caminho = Paths.get("C:\\conproselco\\config\\email.txt");
            List<String> linha = Files.readAllLines(caminho);

            linha.stream().forEach(l -> {

                String[] split = l.split("=");

                if (split[0].equalsIgnoreCase("hostName")) {
                    configEmail.setHostName(split[1]);
                }
                if (split[0].equalsIgnoreCase("smtpPort")) {
                    configEmail.setSmtpPort(Integer.parseInt(split[1]));
                }
                if (split[0].equalsIgnoreCase("from")) {
                    configEmail.setEmailRemetente(split[1]);
                }
                if (split[0].equalsIgnoreCase("pass")) {
                    configEmail.setEmailSenha(split[1]);
                }
                if (split[0].equalsIgnoreCase("mensagem")){
                    configEmail.setEmailMensagem(split[1]);
                }

            });

        } catch (IOException ex) {
            Alert msg = new Alert(Alert.AlertType.NONE);
            msg.setTitle("Erro ao carregar arquivo");
            msg.setContentText("Houve um erro ao tentar abrir arquivo de configuração email.txt\n" + ex.getMessage());
            msg.showAndWait();
        }

    }

    /**
     * Envia e-mail de comunicado aos candidatos que foram selecionados em determinado
     * edital de convocação
     * @param lista
     * @return 
     */
    public boolean enviarEmailCandidatosConvocados(List<Candidato> lista) {

        String[] emails = new String[lista.size()]; // definimos a qtd de emails no array
        
        // percorremos a lista e inserimos os emails no array
        for (int i = 0; i < lista.size(); i++) {
            emails[i] = lista.get(i).getEmail();
        }
        
        try {
            HtmlEmail send = new HtmlEmail();
            String html = getEmailMensagem();

            send.setHostName(hostName);
            send.setSmtpPort(smtpPort);
            send.setAuthenticator(new DefaultAuthenticator(emailRemetente, emailSenha));
            send.setHtmlMsg(html);
            send.addTo(emails);
            send.setFrom(emailRemetente, "RH XINGU");
            send.setSubject("COMUNICADO DE CONVOCAÇÃO" );
            //send.setMsg("envio de mensagem de teste pelo java");
            send.send();
            return true;
        } catch (EmailException ex) {
            Logger.getLogger(Emails.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
