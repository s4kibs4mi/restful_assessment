package ninja.sakib.restfulassessment.daos;

import com.mongodb.MongoClient;
import ninja.sakib.restfulassessment.models.KeyValue;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import java.util.Iterator;

public class MongoDao {
    private static MongoDao mongoDao = new MongoDao();

    public static MongoDao getInstance() {
        return mongoDao;
    }

    private Datastore datastore;

    private MongoDao() {
        init();
    }

    private void init() {
        Morphia morphia = new Morphia();
        morphia.mapPackage("ninja.sakib.restfulassessment.models");
        datastore = morphia.createDatastore(new MongoClient("cloud.sakib.ninja", 27717), "keyStoreDB");
    }

    public void add(KeyValue value) {
        datastore.save(value);
    }

    public void update(KeyValue value) {
        Query<KeyValue> query = datastore.createQuery(KeyValue.class)
                .field("key")
                .equal(value.getKey());
        UpdateOperations<KeyValue> updateOperations = datastore.createUpdateOperations(KeyValue.class);
        updateOperations.set("value", value.getValue());
        datastore.update(query, updateOperations);
    }

    public void remove(KeyValue value) {
        Query<KeyValue> query = datastore.createQuery(KeyValue.class)
                .field("key")
                .equal(value.getKey());
        datastore.delete(query);
    }

    public Iterator<KeyValue> find() {
        return datastore.find(KeyValue.class)
                .iterator();
    }

    public KeyValue find(String key) {
        return datastore.createQuery(KeyValue.class)
                .field("key")
                .equal(key)
                .get();
    }

    public boolean isExists(String key) {
        return find(key) != null;
    }
}
