/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comproselco.dao;

import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.model.Projections;
import comproselco.conexaoBD.ConexaoMongo;
import comproselco.models.Aprovados;
import comproselco.models.Cargos;
import comproselco.models.Processo;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

/**
 *
 * @author andre
 */
public class ProcessoDao {

    private MongoDatabase base;

    public ProcessoDao() {
        ConexaoMongo con = new ConexaoMongo();
        base = con.ConectarMongo();

    }

    /**
     * faz a insercao na colecao processos com todos os dados de Lei, Cargos e
     * quantidades
     *
     * @param processo
     * @return ObjectId
     */
    public ObjectId inserirProcesso(Processo processo) {

        ObjectId id;
        try {
            MongoCollection<Document> colecaoProcesso = base.getCollection("Processos");
            Document doc = new Document("descricaoProcesso", processo.getDescricao());
            doc.append("numeroProcesso", processo.getNumeroProcesso());
            doc.append("anoProcesso", processo.getAnoProcesso());
            doc.append("dataValidade", processo.getDataValidade());
            doc.append("dataHomologacao", processo.getDataHomologacao());
            doc.append("tipoProcesso", processo.getTipoProcesso());
            doc.append("leiAutorizativa",
                    new Document("numeroLei", processo.getLeiAutorizativa().getNumeroLei())
                            .append("anolei", processo.getLeiAutorizativa().getAnolei())
                            .append("data", processo.getLeiAutorizativa().getDataLei()));

            doc.append("cargos", listarCargos(processo));

            colecaoProcesso.insertOne(doc);
            id = doc.getObjectId("_id");

            return id;

        } catch (MongoException e) {
            Alert msg = new Alert(Alert.AlertType.ERROR);
            msg.setTitle("Erro de conexao");
            msg.setContentText(e.getMessage());
            msg.showAndWait();
            //System.out.println("errro>>>>>>> \n" + e.getMessage());
        }
        return null;
    }

    /**
     * cria uma lista de documents para cargos
     *
     * @param proc
     * @return List<Document>
     */
    private List<Document> listarCargos(Processo proc) {
        List<Document> listdc = new ArrayList<>();
        for (Cargos procc : proc.getLeiAutorizativa().getListaCargos()) {
            Document dc = new Document("nomeCargo", procc.getNomeCargo());
            dc.append("lotacao", procc.getLotacao());
            dc.append("qtdVagasImediatas", procc.getQtdVagasImediatas());
            dc.append("qtdVagasReserva", procc.getQtdVagasReserva());
            dc.append("remuneracao", procc.getRemuneracao());
            listdc.add(dc);
        }
        return listdc;

    }

    /**
     * recupera a lista de processos cadastrados na base de dados
     *
     * @return List<Processo>
     */
    public List<Processo> getListarProcessos() {

        List<Processo> listaProc = new ArrayList<>();

        MongoCollection<Document> colecaoProcesso = base.getCollection("Processos");

        Bson fields = Projections.fields(
                Projections.include("_id", "descricaoProcesso", "numeroProcesso", "anoProcesso")
        );

        MongoCursor<Document> cursor = colecaoProcesso.find().projection(fields).iterator();

        while (cursor.hasNext()) {
            Document doc = cursor.next();
            Processo proc = new Processo();
            proc.setIdProcesso(doc.get("_id").toString());
            proc.setDescricao(doc.get("descricaoProcesso").toString());
            proc.setNumeroProcesso(Integer.parseInt(doc.get("numeroProcesso").toString()));
            proc.setAnoProcesso(Integer.parseInt(doc.get("anoProcesso").toString()));

            listaProc.add(proc);

        }
        return listaProc;
    }

    public void testeConsulta() {

        try {
            MongoCollection<Document> proc = base.getCollection("Aprovados");
            MongoCursor<Document> find = proc.find(eq("idProcesso", "6116eea13cfb332674e6db03")).iterator();

            while (find.hasNext()) {
                Document dc = find.next();
                Aprovados ap = new Aprovados();

                ap.setLotacao(dc.get("lotacao").toString());
                System.out.println(">>> " + ap.getLotacao());
            }

        } catch (MongoException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }
}
