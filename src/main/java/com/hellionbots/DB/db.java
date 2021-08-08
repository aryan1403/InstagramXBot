package com.hellionbots.DB;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

public class db {
    private final String MongoUrl = "mongodb://localhost:27017";
    private final String dbName = "test";
    private final String collName = "testing";
    
    public boolean add(Document document){
        try {
            MongoClient client = MongoClients.create(MongoUrl);
            MongoDatabase database = client.getDatabase(dbName);
            MongoCollection<Document> doc = database.getCollection(collName);

            doc.insertOne(document);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("ERROR Occured Inserting Data..");
            return false;
        }
    }

    public void alreadyPresent(String id){
        try {
            MongoClient client = MongoClients.create(MongoUrl);
            MongoDatabase database = client.getDatabase(dbName);
            MongoCollection<Document> doc = database.getCollection(collName);

            Document d = doc.find(Filters.eq("key", id)).first();
        
            if(d.isEmpty() != true){
                doc.deleteOne(d);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("ERROR Occured Deleting Data..");
        }
    }

    public String findUsername(String id){
        try {
            MongoClient client = MongoClients.create(MongoUrl);
            MongoDatabase database = client.getDatabase(dbName);
            MongoCollection<Document> doc = database.getCollection(collName);

            Document d = doc.find(Filters.eq("key", id)).first();
            String username = d.get("username").toString();
            return username;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("ERROR Occured Finding Username..");
            return null;
        }
    }

    public String findPassword(String id){
        try {
            MongoClient client = MongoClients.create(MongoUrl);
            MongoDatabase database = client.getDatabase(dbName);
            MongoCollection<Document> doc = database.getCollection(collName);

            Document d = doc.find(Filters.eq("key", id)).first();
            String password = d.get("password").toString();
            return password;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("ERROR Occured Finding Password..");
            return null;
        }
    }

    public Document get(String id){
        try {
            MongoClient client = MongoClients.create(MongoUrl);
            MongoDatabase database = client.getDatabase(dbName);
            MongoCollection<Document> doc = database.getCollection(collName);

            return doc.find(Filters.eq("key", id)).first();
        } catch (Exception e) {
            System.out.println("ERROR Occured Retreiving Data..");
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
