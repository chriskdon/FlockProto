package controllers.api;

import models.User;
import org.codehaus.jackson.JsonNode;
import play.mvc.*;

public class UserController extends Controller {
    public static Result register() {
        Http.RequestBody body = request().body();

        JsonNode json = body.asJson();


        User u = new User("ebean","f","l","s","s","E");


        u.save();

        return ok("Saved");
    }
}
