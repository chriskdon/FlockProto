package ca.brocku.cosc.flock.data.api.json.models;

/**
 * Error response JSON POJO.
 */
public class GenericErrorModel extends JsonModelBase {
    public String message;

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
        this.status = STATUS_ERROR;
        this.message = message;
    }

    @Override
    public String toJsonString() {
        try {
            return mapper.writeValueAsString(this);
        } catch (Exception ex) {
            return "{\"status\":" + STATUS_ERROR + ",\"message\":null}";
        }
    }
}
