package api.json.models.connection;

import api.json.models.JsonModelBase;
import api.json.models.location.UserLocationModel;

import java.util.List;

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
