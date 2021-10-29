/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comproselco.conexaoBD;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import javafx.scene.control.Alert;

/**
 *
 * @author andre
 */
public class ConexaoMongo {

    /**
     * Estabelece a conexao com a base de dados mongoDb - conproselco
     *
     * @return MongoDataBase database
     */
    public MongoDatabase ConectarMongo() {

        try {

            ConnectionString connectionString = new ConnectionString("");
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connectionString)
                    .build();
            MongoClient mongoClient = MongoClients.create(settings);
            MongoDatabase database = mongoClient.getDatabase("conproselco");

            // conectando ao servidor
           // MongoClient mongo = new MongoClient("localhost", 27017);

            // acessando database
           // MongoDatabase database = mongo.getDatabase("conproselco");
            return database;

        } catch (Exception e) {
            Alert msg = new Alert(Alert.AlertType.ERROR);
            msg.setTitle("Erro de conexão");
            msg.setContentText(e.getMessage());
            msg.showAndWait();

            // System.out.println("Erro \n " + e.getStackTrace());
            return null;
        }

    }

}
