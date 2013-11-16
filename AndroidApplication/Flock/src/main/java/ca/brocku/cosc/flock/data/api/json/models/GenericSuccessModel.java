package ca.brocku.cosc.flock.data.api.json.models;

/**
 * Created by chriskellendonk on 11/14/2013.
 */
public class GenericSuccessModel extends JsonModelBase {
    public String message;

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
        this.message = message;
    }

    @Override
    public String toJsonString() {
        try {
            return mapper.writeValueAsString(this);
        } catch (Exception ex) {
            return "{\"status\":" + STATUS_OKAY + ",\"message\":null}";
        }
    }
}
