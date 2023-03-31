package com.allthingsfuchsia;

import com.mongodb.*;
import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.ListCollectionsIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDBUtil {

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
        // Create a new client and connect to the server
        try (
                MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase database = mongoClient.getDatabase("admin");
            for(String s :mongoClient.listDatabaseNames())
            {
                System.out.println(s);
            }
            try {
                // Send a ping to confirm a successful connection
                Bson command = new BsonDocument("ping", new BsonInt64(1));
                Document commandResult = database.runCommand(command);
                for (Document c : database.listCollections()){
                    System.out.println(c.toJson());
                }
                
                System.out.println("Pinged your deployment. You successfully connected to MongoDB!");


            } catch (MongoException me) {
                System.err.println(me);
            }
        }

    }
}
