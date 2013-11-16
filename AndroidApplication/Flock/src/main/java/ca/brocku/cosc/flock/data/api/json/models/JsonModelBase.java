package ca.brocku.cosc.flock.data.api.json.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

@JsonIgnoreProperties({"mapper", "STATUS_OKAY", "STATUS_ERROR"})
public abstract class JsonModelBase {
    public final int STATUS_OKAY = 1;
    public final int STATUS_ERROR = -1;

    protected static final ObjectMapper mapper = new ObjectMapper();

    public int status = STATUS_OKAY;

    public String toJsonString() throws IOException {
        return mapper.writeValueAsString(this);
    }
}
