package ca.brocku.cosc.flock.radar;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import ca.brocku.cosc.flock.radar.objects.MarkerFactory;

/**
 * Handles rendering of objects on the map, and any
 * events associated with it.
 *
 * Author: Chris Kellendonk
 * Student #: 4810800
 * Date: 11/23/2013
 */
public class RadarMapManager {
    public static final int DEFAULT_ZOOM_LEVEL = 15;

    private GoogleMap map;
    private Context context;
    private Marker currentUserMarker;

    private Map<String, Marker> friendMarkers;

    private int currentZoomLevel; // Current zoom level

    /**
     * Instantiate a manger for a map.
     *
     * @param context   // The context the map is in.
     * @param map       // The map to manage as a radar instance.
     */
    public RadarMapManager(Context context, GoogleMap map) {
        this.context = context;
        this.map = map;

        currentZoomLevel = DEFAULT_ZOOM_LEVEL;

        friendMarkers = new HashMap<String, Marker>();

        // Configure Map
        map.getUiSettings().setZoomControlsEnabled(false);
        map.getUiSettings().setScrollGesturesEnabled(false);
        map.setMyLocationEnabled(false);

        // Show Current User
        zoomToUser();
    }

    /**
     * Add a marker to the map indicating a friends position.
     * @param position      The position of the friend marker.
     * @param id            A unique ID for this marker.
     * @param friendName    The name of the friend to appear when the marker is clicked on.
     */
    public void setFriendMarker(LatLng position, String id, String friendName) {
        Marker friendMarker = map.addMarker(MarkerFactory.friendMarker(position, friendName));

        Marker old = friendMarkers.put(id, friendMarker);

        // Was there already a marker for that friend
        if(old != null) {
            old.remove();
        }
    }

    /**
     * Zoom to the position of the current user on the map.
     */
    public void zoomToUser() {
        LatLng currentPosition = getCurrentUserPosition();

        CameraUpdate moveToUser = CameraUpdateFactory.newLatLng(currentPosition);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(currentZoomLevel);

        map.moveCamera(moveToUser);
        map.animateCamera(zoom);

        if(currentUserMarker == null) {
            currentUserMarker = map.addMarker(MarkerFactory.currentUserVisibleMarker(getCurrentUserPosition()));
        }
    }

    /**
     * Set the zoom level of the map.
     *
     * @param zoom Zoom level.
     */
    public void setZoom(int zoom) {
        currentZoomLevel = zoom;

        zoomToUser();
    }

    /**
     * @return The current position of the user.
     */
    private LatLng getCurrentUserPosition() {
        // TODO: Handle case where we can't get a user's position.

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        String provider = locationManager.getBestProvider(new Criteria(), true);
        Location location = locationManager.getLastKnownLocation(provider);

        return new LatLng(location.getLatitude(), location.getLongitude());
    }
}
