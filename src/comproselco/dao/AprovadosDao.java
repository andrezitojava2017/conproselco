/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comproselco.dao;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import comproselco.conexaoBD.ConexaoMongo;
import comproselco.models.Aprovados;
import comproselco.models.Candidato;
import comproselco.models.Cargos;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author andre
 */
public class AprovadosDao {

    private MongoDatabase base;

    /**
     * Construtor que inicia a conexao com a base de dados
     */
    public AprovadosDao() {
        ConexaoMongo db = new ConexaoMongo();
        base = db.ConectarMongo();

    }

    /**
     * retorna uma lista de candidatos aprovados em determinado processo seletivo/concurso
     *
     * @param processo
     * @return List<Aprovados>
     */
    public List<Aprovados> getAprovadoPorProcesso(String processo) {
        List<Aprovados> listAprovados = new ArrayList<>();
        try {
            MongoCollection<Document> proc = base.getCollection("Aprovados");
            MongoCursor<Document> find = proc.find(eq("idProcesso", processo)).iterator();

            while (find.hasNext()) {
                Document dc = find.next();
                Document cargo = (Document) dc.get("cargo");
                Document cndt = (Document) dc.get("candidato");
                Aprovados ap = new Aprovados();

                //ap.setLotacao(dc.get("lotacao").toString());
                ap.setPosicaoClassificao(Integer.parseInt(dc.get("classificacao").toString()));
                ap.setInscricao(dc.getString("inscricao"));
                ap.setCargo(new Cargos(
                        cargo.getString("nomeCargo"), 
                        cargo.getString("lotacao")));

                ap.setCandidatos(
                        new Candidato(
                                cndt.get("nomeCandidato").toString(),
                                cndt.get("identidade").toString(),
                                cndt.get("cpf").toString(),
                                cndt.get("email").toString(),
                                cndt.get("nomePai").toString(),
                                cndt.get("nomeMae").toString()
                        )
                );

                listAprovados.add(ap);
                        
            }

        } catch (MongoException e) {
            Alert msg = new Alert(Alert.AlertType.ERROR);
            msg.setTitle("Error");
            msg.setContentText(e.getMessage());
            msg.showAndWait();
        }
        return listAprovados;
    }
    
    
    /**
     * Salva cadastro de novo candidato aprovado em determinado processo
     * Insere na colecao Aprovados
     * @param candidato
     * @return ObjectId
     */
    public ObjectId inserirNovoCandidato(Aprovados candidato){
        ObjectId id;
        
        try {
            MongoCollection<Document> colecao = base.getCollection("Aprovados");
            Document ap = new Document("idProcesso", candidato.getIdProcesso().getIdProcesso());
            ap.append("inscricao", candidato.getInscricao());
            ap.append("classificacao", candidato.getPosicaoClassificao());
            ap.append("situacao", candidato.getSituacao());
            ap.append("cargo", 
                    new Document("nomeCargo", candidato.getCargo().getNomeCargo())
                    .append("lotacao", candidato.getCargo().getLotacao()));
            ap.append("candidato", new Document("nomeCandidato", candidato.getCandidato().getNomeCandidato())
                        .append("identidade", candidato.getCandidato().getIdentidade())
                        .append("cpf", candidato.getCandidato().getCpf())
                        .append("email", candidato.getCandidato().getEmail())
                        .append("nomePai", candidato.getCandidato().getNomePai())
                        .append("nomeMae", candidato.getCandidato().getNomeMae())
            );
            
            colecao.insertOne(ap);
            id = ap.getObjectId("_id");
            return id;
            
        } catch (MongoException e) {
            Alert msg = new Alert(Alert.AlertType.ERROR);
            msg.setTitle("Erro de conexao");
            msg.setContentText(e.getMessage());
            msg.showAndWait();
        }
        return null;
    }

}
