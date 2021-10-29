/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comproselco.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import comproselco.conexaoBD.ConexaoMongo;
import comproselco.models.Aprovados;
import comproselco.models.Candidato;
import comproselco.models.Cargos;
import comproselco.models.Edital;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author andre
 */
public class EditalConvocacaoDao {

    private MongoDatabase base;

    public EditalConvocacaoDao() {
        base = new ConexaoMongo().ConectarMongo();
    }

    public ObjectId gravarEditalConvocacao(Edital editalConvocacao) {
        ObjectId id;

        MongoCollection<Document> colecaoEdital = base.getCollection("EditalConvocacao");

        Document edital = new Document("idProcesso", editalConvocacao.getIdProcesso().getIdProcesso())
                .append("numeroEdital", editalConvocacao.getNumeroEdital())
                .append("dataConvocacao", editalConvocacao.getDataConvocacao())
                .append("dataPrazo", editalConvocacao.getDataPrazo())
                .append("convocados", listaConvocados(editalConvocacao.getConvocados()));

        colecaoEdital.insertOne(edital);
        id = edital.getObjectId("_id");
        return id;
    }

    private List<Document> listaConvocados(List<Aprovados> candidatos) {
        List<Document> convocados = new ArrayList<>();

        candidatos.stream().forEach((Aprovados e) -> {
            Document dc = new Document("nomeCandidato", e.getCandidato().getNomeCandidato())
                    .append("classificacao", e.getPosicaoClassificao())
                    .append("cargo", e.getCargo().getNomeCargo())
                    .append("lotacao", e.getCargo().getLotacao());
            convocados.add(dc);
        });
        return convocados;
    }

    /**
     * recupera uma lista de editais de um determinado processo utilizado para
     * consulta de cancidados de um determinado edital
     *
     * @param idProcesso
     */
    public List<Edital> getListaEditalConvocacao(String idProcesso) {

        List<Edital> listaEdital = new ArrayList<>();
        
        MongoCollection<Document> collectionEdital = base.getCollection("EditalConvocacao");
        MongoCursor<Document> find = collectionEdital.find(eq("idProcesso", idProcesso)).iterator();

        while (find.hasNext()) {
            Document dc = find.next();
            Edital edital = new Edital();
            edital.setNumeroEdital(dc.get("numeroEdital").toString());

            //System.out.println(">>>" + dc.get("numeroEdital"));
            
            List<Aprovados> aprovados = new ArrayList<>();
            
            List<Document> candidatos = (List<Document>) dc.get("convocados");
            candidatos.stream().forEach(ap -> {
                
                Aprovados candAprovado = new Aprovados();
                
                candAprovado.setCandidatos(new Candidato(ap.get("nomeCandidato").toString()));
                candAprovado.setCargo(new Cargos(ap.get("cargo").toString(), ap.get("lotacao").toString() ));
                candAprovado.setPosicaoClassificao(Integer.parseInt(ap.get("classificacao").toString()));
               
                aprovados.add(candAprovado);
                
               // System.out.println(">>>> processo: " + idProcesso + " - " + ap.get("nomeCandidato"));

            });
            edital.setConvocados(aprovados);
            listaEdital.add(edital);

        }
        
        return listaEdital;

    }
}
