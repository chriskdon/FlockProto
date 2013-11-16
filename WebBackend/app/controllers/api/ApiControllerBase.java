package controllers.api;


import authentication.FlockAuthentication;
import org.codehaus.jackson.map.ObjectMapper;
import play.mvc.Controller;

public abstract class ApiControllerBase extends Controller {
    protected static final FlockAuthentication auth = FlockAuthentication.getInstance();
    protected static final ObjectMapper mapper = new ObjectMapper();
}
