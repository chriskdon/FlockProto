package controllers.api;


import authentication.FlockAuthentication;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import play.mvc.Controller;

public abstract class ApiControllerBase extends Controller {
    protected static final FlockAuthentication auth = FlockAuthentication.getInstance();
    protected static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Return the body as JSON object
     * @return
     */
    protected static JsonNode jsonBody() {
        return request().body().asJson();
    }
}
