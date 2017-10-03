package ninja.sakib.restfulassessment.repositories;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import ninja.sakib.restfulassessment.daos.MongoDao;
import ninja.sakib.restfulassessment.models.KeyValue;
import spark.Request;
import spark.Response;

public class KeyValueRepository {

    public static String getValues(Request req, Response resp) {
        return "";
    }

    public static String createOrUpdateValues(Request req, Response resp) {
        resp.header("content-type", "application/json");

        JsonObject result = new JsonObject();
        try {
            JsonObject keyValues = Json.parse(req.body()).asObject();
            if (keyValues != null) {
                for (String key : keyValues.names()) {
                    KeyValue value = new KeyValue();
                    value.setKey(key);
                    value.setValue(keyValues.getString(key, ""));

                    if (!value.getKey().trim().isEmpty() && !value.getValue().trim().isEmpty()) {
                        if (!MongoDao.getInstance().isExists(value.getKey())) {
                            MongoDao.getInstance().add(value);
                        } else {
                            MongoDao.getInstance().update(value);
                        }
                    }
                }
            }
            result.add("code", 200);
        } catch (Exception ex) {
            result.add("code", 201);
        }
        return result.toString();
    }
}
