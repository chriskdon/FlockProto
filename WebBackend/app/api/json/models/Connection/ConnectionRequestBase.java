package api.json.models.connection;

import api.json.models.JsonModelBase;

/**
 * All connection questions need a user secret
 */
public abstract class ConnectionRequestBase extends JsonModelBase {
    public String secret;
}
