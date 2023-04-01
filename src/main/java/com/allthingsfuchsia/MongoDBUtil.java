package com.allthingsfuchsia;

import com.mongodb.*;

import java.util.ArrayList;

import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDBUtil implements DBUtilInterface {

    MongoClient mongoClient;

    public MongoDBUtil(DBInfo dbInfo) {

        String uri = dbInfo.getURL();
        // Construct a ServerApi instance using the ServerApi.builder() method
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(uri))
                .serverApi(serverApi)
                .build();

        this.mongoClient = MongoClients.create(settings);

    }

    @Override
    public String changePassword() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'changePassword'");
    }

    @Override
    public void checkHeartbeat() {
        MongoDatabase database = this.mongoClient.getDatabase("admin");
        try {
            // Send a ping to confirm a successful connection
            Bson command = new BsonDocument("ping", new BsonInt64(1));
            Document commandResult = database.runCommand(command);

            System.out.println("Pinged your deployment. You successfully connected to MongoDB!");

        } catch (MongoException me) {
            System.err.println(me);
        }
    }

    @Override
    public void showTableMetaData(String Schema, String tableName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showTableMetaData'");
    }

    @Override
    public ArrayList<String> getTablesList() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTablesList'");
    }

    @Override
    public void executeQuery(String query) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'executeQuery'");
    }

    @Override
    public void listAllTables() {
        for (String databaseName : this.mongoClient.listDatabaseNames()) {
            MongoDatabase database = this.mongoClient.getDatabase(databaseName);
            for (Document c : database.listCollections()) {
                System.out.println(c.toJson());
            }

        }
    }
}
