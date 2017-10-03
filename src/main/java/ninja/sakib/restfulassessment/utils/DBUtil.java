package ninja.sakib.restfulassessment.utils;

import ninja.sakib.restfulassessment.daos.MongoDao;
import ninja.sakib.restfulassessment.models.KeyValue;
import org.joda.time.DateTime;

import java.util.Iterator;

public class DBUtil {

    public static void dbCleaner() {
        new Thread(() -> {
            while (true) {
                Iterator<KeyValue> iterator = MongoDao.getInstance().find();
                while (iterator.hasNext()) {
                    KeyValue value = iterator.next();
                    DateTime time = new DateTime(value.getTime()).plusMinutes(5);
                    if (DateTime.now().isAfter(time)) {
                        MongoDao.getInstance().remove(value);
                    }
                }
                try {
                    Thread.sleep(10 * 1000);
                } catch (Exception ex) {

                }
            }
        }).start();
    }
}
