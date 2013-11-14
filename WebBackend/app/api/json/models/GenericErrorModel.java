package api.json.models;

/**
 * Error response JSON POJO.
 */
public class GenericErrorModel extends JsonModelBase {
    private String message;

    /**
     * Error response without message
     */
    public GenericErrorModel() {
        this(null);
    }

    /**
     * Error response with message.
     *
     * @param message Message to send.
     */
    public GenericErrorModel(String message) {
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
            return "{\"status\":" + STATUS_ERROR + ",\"message\":null}";
        }
    }
}
