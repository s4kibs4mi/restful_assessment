package ninja.sakib.restfulassessment;

import ninja.sakib.restfulassessment.controllers.KeyValueController;
import ninja.sakib.restfulassessment.utils.DBUtil;

public class Run {

    // Application starts from here
    public static void main(String args[]) {
        KeyValueController.init();
        DBUtil.dbCleaner();
    }
}
