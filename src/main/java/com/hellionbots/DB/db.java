package com.hellionbots.DB;

import com.hellionbots.Helpers.configuration;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import org.bson.Document;

public class db {
    private final String connection = "mongodb://localhost:27017";
    private final String dbName = "test";
    private final String collName = "testing";
    
    public boolean add(Document document){
        try {
            /*
            ConnectionString connectionString = new ConnectionString(connection);
            MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
                */
            //MongoCredential credential = MongoCredential.createCredential(configuration.username, "database", configuration.password.toCharArray());
            //MongoClient client = new MongoClient(new ServerAddress("localhost", 27017), Arrays.asList(credential));
            MongoClient client = MongoClients.create(connection);
            
            MongoDatabase database = client.getDatabase(dbName);
            MongoCollection<Document> doc = database.getCollection(collName);

            doc.insertOne(document);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void alreadyPresent(String id){
        try {
            /*
            ConnectionString connectionString = new ConnectionString(connection);
            MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
                */
            MongoClient client = MongoClients.create(connection);
            //MongoCredential credential = MongoCredential.createCredential("user", "database", "passwd".toCharArray());
            //MongoClient client = new MongoClient(new ServerAddress("localhost", 27017), Arrays.asList(credential));
            MongoDatabase database = client.getDatabase(dbName);
            MongoCollection<Document> doc = database.getCollection(collName);

            Document d = doc.find(Filters.eq("key", id)).first();
        
            if(d.isEmpty() != true){
                doc.deleteOne(d);
            }
        } catch (Exception e) {
        }
    }

    public String findUsername(String id){
        try {
            /*
            ConnectionString connectionString = new ConnectionString(connection);
            MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
               */
            MongoClient client = MongoClients.create(connection);
            //MongoCredential credential = MongoCredential.createCredential("user", "database", "passwd".toCharArray());
            //MongoClient client = new MongoClient(new ServerAddress("localhost", 27017), Arrays.asList(credential));
            MongoDatabase database = client.getDatabase(dbName);
            MongoCollection<Document> doc = database.getCollection(collName);

            Document d = doc.find(Filters.eq("key", id)).first();

            if(d == null) return null;

            String username = d.get("username").toString();
            return username;
        } catch (Exception e) {
            return null;
        }
    }

    public String findPassword(String id){
        try {
            /*
            ConnectionString connectionString = new ConnectionString(connection);
            MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
                */
            MongoClient client = MongoClients.create(connection);
            //MongoCredential credential = MongoCredential.createCredential("user", "database", "passwd".toCharArray());
            //MongoClient client = new MongoClient(new ServerAddress("localhost", 27017), Arrays.asList(credential));
            MongoDatabase database = client.getDatabase(dbName);
            MongoCollection<Document> doc = database.getCollection(collName);

            Document d = doc.find(Filters.eq("key", id)).first();

            if(d == null) return null;

            String password = d.get("password").toString();
            return password;
        } catch (Exception e) {
            return null;
        }
    }

    public Document get(String id){
        try {
            /*
            ConnectionString connectionString = new ConnectionString(connection);
            MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
                */
               
            MongoClient client = MongoClients.create(connection);
            //MongoCredential credential = MongoCredential.createCredential("user", "database", "passwd".toCharArray());
            //MongoClient client = new MongoClient(new ServerAddress("localhost", 27017), Arrays.asList(credential));
            MongoDatabase database = client.getDatabase(dbName);
            MongoCollection<Document> doc = database.getCollection(collName);

            return doc.find(Filters.eq("key", id)).first();
        } catch (Exception e) {
            return null;
        }
    }

    /*
    public boolean delete(String key) {
        try {
            MongoClient client = MongoClients.create("mongodb://localhost:27017");
            MongoDatabase database = client.getDatabase(dbName);
            MongoCollection<Document> doc = database.getCollection(collName);

            for(Document d : doc.find()){
                doc.deleteOne(Filters.eq("key", key));
                d.toJson();
            }
            return true;
        } catch (Exception e) {
            System.out.println("ERROR Occured Deleting Data..\nPlease Enter correct key");
            return false;
        }
    }
    */
    
}
