package edu.uchicago.tiang.repos;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Updates.*;
import edu.uchicago.tiang.models.FavoriteItem;
import org.bson.Document;
import com.google.gson.Gson;
import com.github.javafaker.Faker;
import org.bson.conversions.Bson;
import static com.mongodb.client.model.Filters.eq;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import io.quarkus.runtime.StartupEvent;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
public class FavsRepository {

    @Inject
    MongoClient mongoClient;

    public static final int PAGE_SIZE = 20;

    public static  final String DEFAULT_EMAIL = "default@default.com";

    private Gson gson = new Gson();

    void onStart(@Observes StartupEvent ev) {

        long collectionSize = getCollection().countDocuments();
        if (collectionSize > 0) return;

        Faker faker = new Faker();
        getCollection().insertMany(
                Stream.generate(
                                () -> new FavoriteItem(
                                        UUID.randomUUID().toString(),
                                        DEFAULT_EMAIL,
                                        faker.beer().name(),
                                        faker.company().name(),
                                        faker.company().buzzword(),
                                        faker.beer().name(),
                                        faker.internet().domainName(),
                                        faker.internet().domainName())
                        )
                        .peek(fav -> System.out.println(fav))
                        .map(fav -> item2doc(fav))
                        .limit(1000).collect(Collectors.toList())

        );

    }

    public FavoriteItem add(FavoriteItem favoriteItem){

        getCollection().insertOne(item2doc(favoriteItem));
        return  favoriteItem;
    }

    public FavoriteItem get(String id){

        BasicDBObject query = new BasicDBObject();
        query.put("id", id);

        FindIterable<Document> documents = getCollection().find(query);


        List<FavoriteItem> items = new ArrayList<>();
        for (Document document : documents) {
            items.add(doc2item(document));
        }

        //this will produce a 404 not found
        if (items.size() != 1) return null;

        return items.get(0);
    }

    public List<FavoriteItem> getAll(){

        BasicDBObject query = new BasicDBObject();

        FindIterable<Document> documents = getCollection().find(query);


        List<FavoriteItem> items = new ArrayList<>();
        for (Document document : documents) {
            items.add(doc2item(document));
        }

        //this will produce a 404 not found
        if (items.size() == 0) return null;

        return items;
    }

    public FavoriteItem delete(String id){

        BasicDBObject query = new BasicDBObject();
        query.put("id", id);

        FindIterable<Document> documents = getCollection().find(query);

        Document firstDocument = null;
        for (Document document : documents) {
            firstDocument = document;
            break;
        }
        getCollection().deleteOne(firstDocument);
        return doc2item(firstDocument);
    }

    public List<FavoriteItem> paged(int page){
        BasicDBObject query = new BasicDBObject();
        List<FavoriteItem> favs = new ArrayList<>();
        try {
            MongoCursor<Document> cursor =
                    getCollection().find(query).skip(PAGE_SIZE * (page - 1)).limit(PAGE_SIZE).iterator();
            while (cursor.hasNext()) {
                Document document = cursor.next();
                favs.add(doc2item(document));
            }
            cursor.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return favs;
    }

    public FavoriteItem update(FavoriteItem item, String id){
        Bson filter = eq("id", id);
        Bson newValue = new Document("authorName", item.getAuthorName()).append("userEmail", item.getUserEmail())
                .append("title",item.getTitle()).append("year", item.getYear()).append("description",item.getDescription())
                .append("link",item.getLink()).append("preview",item.getPreview());
        Bson updateDocument = new Document("$set", newValue);
        getCollection().updateOne(filter,updateDocument);
        return get(id);
    }

    private MongoCollection getCollection() {
        return mongoClient.getDatabase("favs_db").getCollection("favs_collection");
    }

    //local transform ops
    private FavoriteItem doc2item(Document document) {
        if (document != null && !document.isEmpty()) {
            return gson.fromJson(document.toJson(), FavoriteItem.class);
        }
        return null;

    }

    private Document item2doc(FavoriteItem item) {
        if (item != null) {
            String json = gson.toJson(item);
            return Document.parse(json);
        }
        return null;
    }

}
