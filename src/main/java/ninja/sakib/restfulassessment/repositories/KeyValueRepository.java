package ninja.sakib.restfulassessment.repositories;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import ninja.sakib.restfulassessment.caches.CacheManager;
import ninja.sakib.restfulassessment.daos.MongoDao;
import ninja.sakib.restfulassessment.models.KeyValue;
import ninja.sakib.restfulassessment.utils.LogUtil;
import org.joda.time.DateTime;
import spark.Request;
import spark.Response;

import java.util.Iterator;

public class KeyValueRepository {

    public static String getValues(Request req, Response resp) {
        resp.header("content-type", "application/json");

        JsonObject result = new JsonObject();
        try {
            String params = req.queryParams("keys");
            String cachePathInfo = req.pathInfo() + params;
            String cacheValue = CacheManager.get().get(cachePathInfo);
            if (cacheValue == null || cacheValue.trim().isEmpty()) {
                String keys[] = params.split(",");
                if (params != null && !params.trim().isEmpty()) {
                    for (String key : keys) {
                        KeyValue value = MongoDao.getInstance().find(key);
                        if (value != null) {
                            result.add(value.getKey(), value.getValue());
                        }
                    }
                } else {
                    Iterator<KeyValue> iterator = MongoDao.getInstance().find();
                    while (iterator.hasNext()) {
                        KeyValue value = iterator.next();
                        result.add(value.getKey(), value.getValue());
                    }
                }
                CacheManager.get().set(cachePathInfo, result.toString(), 10);    // Cache available time 10s
                LogUtil.log("From Database");
            } else {
                result = Json.parse(cacheValue).asObject();
                LogUtil.log("From cache");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result.toString();
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
                    value.setTime(DateTime.now().toDate());

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
