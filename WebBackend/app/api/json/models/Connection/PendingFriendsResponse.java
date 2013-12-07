package api.json.models.connection;

import api.json.models.JsonModelBase;
import api.json.models.location.UserLocationModel;

import java.util.List;

/**
 * Created by Chris Kellendonk
 * Student #: 4810800
 * Date: 12/6/2013.
 */
public class PendingFriendsResponse extends JsonModelBase {
    public List<Connection> connections;

    public PendingFriendsResponse() {}

    public PendingFriendsResponse(List<Connection> list) {
        this.connections = list;
    }
}
