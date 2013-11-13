package api.json.models;

import java.io.IOException;

/**
 * Error response JSON POJO.
 */
public class ErrorResponseModel extends JsonModelBase {
    private String message;

    /**
     * Error response without message
     */
    public ErrorResponseModel() {
        this(null);
    }

    /**
     * Error response with message.
     *
     * @param message Message to send.
     */
    public ErrorResponseModel(String message) {
        setStatus(STATUS_ERROR);
        setMessage(message);
    }

    public String getMessage() { return message; }
    public void setMessage(String value) { message = value; };

    @Override
    public String toJsonString() {
        try {
            return mapper.writeValueAsString(this);
        } catch (Exception ex) {
            return "{\"status\":-1,\"message\":null}";
        }
    }
}
