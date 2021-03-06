package ca.brocku.cosc.flock.data.api.json.models.connection;

import java.util.List;

import ca.brocku.cosc.flock.data.api.json.models.JsonModelBase;

/**
 * Created by Chris Kellendonk
 * Student #: 4810800
 * Date: 12/6/2013.
 */
public class ConnectionListResponse extends JsonModelBase {
    public List<Connection> connections;

    public ConnectionListResponse() {}

    public ConnectionListResponse(List<Connection> list) {
        this.connections = list;
    }
}
