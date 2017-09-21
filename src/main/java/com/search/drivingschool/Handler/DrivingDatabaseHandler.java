package com.search.drivingschool.Handler;

/**
 * Created by abc on 9/10/2017.
 */
import com.search.drivingschool.data.Items;
import org.bson.*;
import com.mongodb.*;
import com.mongodb.client.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DrivingDatabaseHandler {

    public List getDatabaseResult(String suburb) {
        String mongoUri = "mongodb://drivingschool:Welcome1@ds117913.mlab.com:17913/drivingschool";

        List items = new ArrayList();
        MongoClientURI connStr = new MongoClientURI(mongoUri);
        MongoClient mongoClient = new MongoClient(connStr);
        try {
            // Use the database named "someonedb"
            MongoDatabase database = mongoClient.getDatabase("drivingschool");
            // Get the handle of the collection/table "someonetable"
            MongoCollection<Document> collection = database.getCollection("ds_main");

            Document findQuery = new Document("suburb", java.util.regex.Pattern.compile(suburb));
            Document orderBy = new Document("decade", 1);

            MongoCursor<Document> cursor = collection.find(findQuery).iterator();


                while (cursor.hasNext()) {
                    Document doc = cursor.next();

                    Items dbval = new Items();
                    dbval.setSchoolId(doc.get("school_id").toString());
                    dbval.setTitle(doc.get("school_name").toString());
                    dbval.setSnippet(doc.get("description").toString());
                    dbval.setContactNumber(doc.get("contact_number").toString());
                    dbval.setEmailId(doc.get("email_id").toString());
                    dbval.setPrice(doc.get("price").toString());
                    dbval.setRating(doc.get("rating").toString());
                    dbval.setTestRoute(doc.get("test_route").toString());
                    dbval.setStatus(doc.get("status").toString());
                    dbval.setSuburb(doc.get("suburb").toString());
                    dbval.setState(doc.get("state").toString());
                    dbval.setCountry(doc.get("country").toString());
                    dbval.setPostcode(doc.get("post_code").toString());
                    dbval.setStartDate(doc.get("start_date").toString());
                    dbval.setEndDate(doc.get("end_date").toString());
                    dbval.setSocialLink(doc.get("social_network_link").toString());
                    dbval.setLink(doc.get("website").toString());//cursor.next().get("website").toString());
                    //dbval.setImage(doc.get("profile_thumbnail").toString());
                    //dbval.setSnippet(doc.get("suburb").toString());//cursor.next().get("suburb").toString());
                    //dbval.setTitle(doc.get("school_name").toString());//cursor.next().get("school_name").toString());
                    //System.out.println("find document: " + cursor.next());
                    items.add(dbval);
                }

            } finally {
                //Close the connection
                mongoClient.close();
            }
            return items;
        }
    }

