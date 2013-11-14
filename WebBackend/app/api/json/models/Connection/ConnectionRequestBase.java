package api.json.models.Connection;

import api.json.models.JsonModelBase;

/**
 * All connection questions need a user secret
 */
public abstract class ConnectionRequestBase extends JsonModelBase {
    private String secret;

    public String getSecret() { return secret; }
    public void setSecret(String value) { secret = value; }
}
