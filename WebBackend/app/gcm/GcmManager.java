package gcm;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import play.libs.F;
import play.libs.WS;

import java.util.List;

/**
 * Handles sending messsages to the google cloud messaging services.
 *
 * Created by Chris Kellendonk
 * Student #: 4810800
 * Date: 12/7/2013.
 */
public class GcmManager {
    private static final String GOOGLE_GCM_SERVER_ADDRESS = "http://android.googleapis.com/gcm/send";
    private static final String GCM_API_KEY = "AIzaSyA1uXWN9b5ewLKmcnOJ9QSD4ws8DmGx7jI";

    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Send the raw json request to the GCM Server
     * @param jsonMessageData
     */
    public static F.Promise<WS.Response> send(List<String> registrationIDList, JsonNode jsonMessageData) {
        // Create Registration ID List
        ArrayNode regList = mapper.createArrayNode();
        for(String id : registrationIDList) { regList.add(id); }

        // Wrapper
        ObjectNode node = mapper.createObjectNode();

        node.put("registration_ids", regList);
        node.put("data", jsonMessageData);

        return WS.url(GOOGLE_GCM_SERVER_ADDRESS)
                .setHeader("Authorization","key=" + GCM_API_KEY)
                .post(node);



    }
}
