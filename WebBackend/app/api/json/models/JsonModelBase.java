package api.json.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

@JsonIgnoreProperties({"mapper", "STATUS_OKAY", "STATUS_ERROR"})
public abstract class JsonModelBase {
    public static final int STATUS_OKAY = 1;
    public static final int STATUS_ERROR = -1;

    protected static final ObjectMapper mapper = new ObjectMapper();

    public int status = STATUS_OKAY;

    public String toJsonString() throws IOException {
        return mapper.writeValueAsString(this);
    }
}
