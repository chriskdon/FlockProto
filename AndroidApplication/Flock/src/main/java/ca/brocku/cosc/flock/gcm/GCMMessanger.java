package ca.brocku.cosc.flock.gcm;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Singleton for registering event handlers to push notifications
 *
 * Author: Chris Kellendonk
 * Student #: 4810800
 * Date: 12/8/2013
 */
public class GCMMessanger {
    private static GCMMessanger instance; // Singleton

    public static final int ACTION_LOCATION_UPDATE = 1;

    private Map<Integer, ArrayList<GCMActionEvent>> events;

    public static GCMMessanger getInstance() {
        if(instance == null) {
            instance = new GCMMessanger();
        }

        return instance;
    }

    private GCMMessanger() {
        events = new HashMap<Integer, ArrayList<GCMActionEvent>>();
    }

    /**
     * Register an event to an action
     * @param action
     * @param handler
     */
    public void register(int action, GCMActionEvent handler) {
        if(!events.containsKey(action)) {
            events.put(action, new ArrayList<GCMActionEvent>());
        }

        events.get(action).add(handler);
    }

    /**
     * unregister an event from an action
     * @param action
     * @param handler
     */
    public void unregister(int action, GCMActionEvent handler) {
        if(events.containsKey(action)) {
            events.get(action).remove(handler);
        }
    }

    /**
     * Call all registered handlers when the event occurs
     * @param action
     */
    public void fireAction(int action) {
        if(events.containsKey(action)) {
            for(GCMActionEvent handler : events.get(action)) {
                handler.onEvent();
            }
        }
    }
}
