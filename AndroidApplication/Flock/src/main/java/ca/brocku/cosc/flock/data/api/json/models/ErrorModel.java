package ca.brocku.cosc.flock.data.api.json.models;

/**
 * Error response JSON POJO.
 */
public class ErrorModel extends JsonModelBase {
    public String message;  // The message for the error
    public int errorType;   // The type of error that occurred (USER/LOGIC/?)
    public int errorCode;   // Custom codes so that application can handle things differently if need be

    public ErrorModel() { this(null, ErrorTypes.ERROR_TYPE_FATAL, 0); }

    public ErrorModel(int errorType) {
        this(null, errorType, 0);
    }

    public ErrorModel(String message, int errorType) {
        this(message, errorType, 0);
    }

    public ErrorModel(String message, int errorType, int errorCode) {
        this.status = STATUS_ERROR;
        this.message = message;
        this.errorType = errorType;
        this.errorCode = errorCode;
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
