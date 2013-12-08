package controllers.api;

import api.json.models.ErrorModel;
import api.json.models.ErrorTypes;
import api.json.models.GenericSuccessModel;
import api.json.models.gcm.GCMRegistrationRequest;
import com.avaje.ebean.Ebean;
import gcm.GcmManager;
import models.User;
import org.codehaus.jackson.node.ObjectNode;
import play.libs.F;
import play.libs.WS;
import play.mvc.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris Kellendonk
 * Student #: 4810800
 * Date: 12/7/2013.
 */
public class GCMController extends ApiControllerBase {
    /**
     * Register a Google Cloud Messaging registration ID to a user for
     * push notifications.
     * @return
     */
    public static Result registerGCMID() {
        try {
            GCMRegistrationRequest request = mapper.readValue(jsonBody(), GCMRegistrationRequest.class);

            User user = User.findBySecret(request.secret);

            if(user == null || request.gcmRegistrationID.isEmpty()) {
                return ok((new ErrorModel("Invalid Request Data", ErrorTypes.ERROR_TYPE_FATAL)).toJsonString());
            }

            user.gcmRegistrationID = request.gcmRegistrationID;
            user.update();
            Ebean.save(user);

            return ok((new GenericSuccessModel()).toJsonString());
        } catch(Exception ex) {
            return ok((new ErrorModel(ex.getMessage(), ErrorTypes.ERROR_TYPE_FATAL)).toJsonString());
        }
    }

    public static Result test() {
        ArrayList<String> test = new ArrayList<String>();
        test.add("APA91bHZn-GlFl6G77CUVIgyQlhUa3y5R-dhkx69JKgA2kdhr4a7I12TbylO4vftN1ow0Ic82qLc1HR0WKNBrb9nHBxiiI9-i8a_r0DPU4TUI1QvvNoKunbLwjkuiGlUByww7RYkbOY8y2PcQCoH0j6gnxk-GVP60Q");

        ObjectNode node = mapper.createObjectNode();
        node.put("HelloWorld", "Hello Android, from Flock Server");

        return async(GcmManager.send(test, node).map(new F.Function<WS.Response, Result>() {
            @Override
            public Result apply(WS.Response response) throws Throwable {
                return ok("STATUS: " + response.getBody());
            }
        }));
    }
}
