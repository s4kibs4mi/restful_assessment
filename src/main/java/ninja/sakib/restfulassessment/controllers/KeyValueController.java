package ninja.sakib.restfulassessment.controllers;

import ninja.sakib.restfulassessment.repositories.KeyValueRepository;

import static spark.Spark.get;
import static spark.Spark.post;

public class KeyValueController {

    public static void init() {
        get("values", (req, resp) -> KeyValueRepository.getValues(req, resp));
        post("values", (req, resp) -> KeyValueRepository.createOrUpdateValues(req, resp));
    }
}
