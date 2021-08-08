package com.hellionbots.DB;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;

public class db {
    private final String MongoUrl = "mongodb://localhost:27017";
    private final String dbName = "test";
    private final String collName = "testing";
    
    public boolean add(String key, Document document){
        try {
            MongoClient client = MongoClients.create(MongoUrl);
            MongoDatabase database = client.getDatabase(dbName);
            MongoCollection<Document> doc = database.getCollection(collName);

            doc.insertOne(document);
            return true;
        } catch (Exception e) {
            System.out.println("ERROR Occured Inserting Data..");
            return false;
        }
    }

    public List<Document> get(){
        try {
            MongoClient client = MongoClients.create(MongoUrl);
            MongoDatabase database = client.getDatabase(dbName);
            MongoCollection<Document> doc = database.getCollection(collName);

            List<Document> list = new ArrayList<>();

            for (Document d : doc.find()) {
                list.add(d);
            }
            return list;
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
