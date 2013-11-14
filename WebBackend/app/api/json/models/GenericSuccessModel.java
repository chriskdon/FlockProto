package api.json.models;

/**
 * Created by chriskellendonk on 11/14/2013.
 */
public class GenericSuccessModel extends JsonModelBase {
    private String message;

    /**
     * Error response without message
     */
    public GenericSuccessModel() {
        this(null);
    }

    /**
     * Error response with message.
     *
     * @param message Message to send.
     */
    public GenericSuccessModel(String message) {
        setMessage(message);
    }

    public String getMessage() { return message; }
    public void setMessage(String value) { message = value; };

    @Override
    public String toJsonString() {
        try {
            return mapper.writeValueAsString(this);
        } catch (Exception ex) {
            return "{\"status\":" + STATUS_OKAY + ",\"message\":null}";
        }
    }
}
