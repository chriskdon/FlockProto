package ca.brocku.cosc.flock.radar;

import android.content.Context;
import android.location.Location;
import com.google.android.gms.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.HashMap;
import java.util.Map;

import ca.brocku.cosc.flock.radar.markers.MarkerBitmapFactory;
import ca.brocku.cosc.flock.radar.markers.MarkerFactory;

/**
 * Handles rendering of objects on the map, and any
 * events associated with it.
 *
 * Author: Chris Kellendonk
 * Student #: 4810800
 * Date: 11/23/2013
 */
public class RadarMapManager implements GooglePlayServicesClient.OnConnectionFailedListener,
                                        GooglePlayServicesClient.ConnectionCallbacks,
                                        LocationListener {

    public static final int DEFAULT_ZOOM_LEVEL = 15;
    private static final int DEFAULT_UPDATE_INTERVAL = 5000;

    private GoogleMap map;
    private Context context;
    private Marker currentUserMarker;

    private Map<String, Marker> friendMarkers;

    private int currentZoomLevel; // Current zoom level

    private LocationClient locationClient;
    private LocationRequest locationRequest;

    private boolean isVisible;  // Current status of the users visibility

    /**
     * Instantiate a manger for a map.
     *
     * @param context   // The context the map is in.
     * @param map       // The map to manage as a radar instance.
     */
    public RadarMapManager(Context context, GoogleMap map) {
        this.context = context;
        this.map = map;

        isVisible = true;

        currentZoomLevel = DEFAULT_ZOOM_LEVEL;

        friendMarkers = new HashMap<String, Marker>();

        // Configure Map
        map.getUiSettings().setZoomControlsEnabled(false);
        map.getUiSettings().setAllGesturesEnabled(false); // Manually handled
        map.setMyLocationEnabled(false);

        // Setup Location Services
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(DEFAULT_UPDATE_INTERVAL);
        locationRequest.setFastestInterval(DEFAULT_UPDATE_INTERVAL);

        locationClient = new LocationClient(context, this, this);

    }

    /**
     * Stop the the Radar manager from responding and updating.
     * Should be used when the application is backgrounded, or stopped.
     */
    // TODO: Call this from the radar activity
    public void stop() {
        locationClient.disconnect();
    }

    /**
     * Start listening for location updates and drawing the radar.
     */
    public void start() {
        locationClient.connect();
    }

    /**
     * Set the visibility of the marker
     * @param visible
     */
    public void setVisibility(boolean visible) {
        isVisible = visible;

        // Update marker
        if(visible) {
            getUserMarker().setIcon(BitmapDescriptorFactory.fromBitmap(MarkerBitmapFactory.currentUserInvisible()));
        } else {
            getUserMarker().setIcon(BitmapDescriptorFactory.fromBitmap(MarkerBitmapFactory.currentUserVisible()));
        }
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
        updateUserLocation(true);
        map.animateCamera(CameraUpdateFactory.zoomTo(currentZoomLevel));
    }

    /**
     * Get the current instance of the user marker. If one has not been created yet, create one.
     * @return
     */
    private Marker getUserMarker() {
        if(currentUserMarker == null) {
            Location location = locationClient.getLastLocation();
            LatLng point = new LatLng(location.getLatitude(), location.getLongitude());
            currentUserMarker = map.addMarker(MarkerFactory.currentUserVisibleMarker(point));
            return currentUserMarker;
        } else {
            return currentUserMarker;
        }
    }

    /**
     * Update the location of the user and marker position
     * @param animate
     */
    public void updateUserLocation(boolean animate) {
        // Move Camera
        if(animate) {
            animateToLocation(locationClient.getLastLocation());
        } else {
            moveToLocation(locationClient.getLastLocation());
        }

        // Update Marker
        Location location = locationClient.getLastLocation();
        LatLng point = new LatLng(location.getLatitude(), location.getLongitude());
        getUserMarker().setPosition(point);
    }

    /**
     * Animate the camera to a location on the map
     * @param location
     */
    private void animateToLocation(Location location) {
        map.animateCamera(CameraUpdateFactory.newLatLng(
                new LatLng(location.getLatitude(), location.getLongitude())));
    }

    /**
     * Quickly move to a location.
     * @param location
     */
    private void moveToLocation(Location location) {
        map.moveCamera(CameraUpdateFactory.newLatLng(
                new LatLng(location.getLatitude(), location.getLongitude())));
    }

    /**
     * Set the zoom level of the map on the user.
     *
     * @param zoom Zoom level.
     */
    public void setZoomOnUser(int zoom) {
        currentZoomLevel = zoom;

        zoomToUser();
    }

    // -------------------------------------------------
    // Location Services
    // -------------------------------------------------

    /**
     * The location manager was connected. We can now get locations.
     * @param bundle
     */
    @Override
    public void onConnected(Bundle bundle) {
        locationClient.requestLocationUpdates(locationRequest, this);


        if(locationClient.getLastLocation() != null) {
            // Show Current User position instantly and zoom to them
            updateUserLocation(false);
            zoomToUser();
        } else {
            // TODO: Replace with failed callback
            Toast.makeText(context, "Location Not Enabled", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDisconnected() {
        // TODO: Notify User
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // TODO: Notify User
    }

    /**
     * The user has moved and the location has been automatically updated.
     * Update the user's location on the map.
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {
        updateUserLocation(false);
    }
}
