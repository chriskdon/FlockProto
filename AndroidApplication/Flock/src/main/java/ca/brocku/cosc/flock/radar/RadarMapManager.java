package ca.brocku.cosc.flock.radar;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ca.brocku.cosc.flock.data.api.APIResponseHandler;
import ca.brocku.cosc.flock.data.api.actions.LocationAPIAction;
import ca.brocku.cosc.flock.data.api.json.models.ErrorModel;
import ca.brocku.cosc.flock.data.api.json.models.GenericSuccessModel;
import ca.brocku.cosc.flock.data.api.json.models.location.FriendLocationsListResponseModel;
import ca.brocku.cosc.flock.data.api.json.models.location.UserLocationModel;
import ca.brocku.cosc.flock.data.exceptions.NoUserSecretException;
import ca.brocku.cosc.flock.data.settings.UserDataManager;
import ca.brocku.cosc.flock.gcm.GCMActionEvent;
import ca.brocku.cosc.flock.gcm.GCMMessanger;
import ca.brocku.cosc.flock.radar.callbacks.RadarConnected;
import ca.brocku.cosc.flock.radar.markers.MarkerBitmapFactory;
import ca.brocku.cosc.flock.radar.markers.MarkerFactory;
import ca.brocku.cosc.flock.utils.TryCallback;

/**
 * Handles rendering of objects on the map, and any
 * events associated with it.
 * <p/>
 * Author: Chris Kellendonk
 * Student #: 4810800
 * Date: 11/23/2013
 */
public class RadarMapManager implements GooglePlayServicesClient.OnConnectionFailedListener,
        GooglePlayServicesClient.ConnectionCallbacks,
        LocationListener {

    public static final int DEFAULT_ZOOM_LEVEL = 15;
    private static final int DEFAULT_UPDATE_INTERVAL = 5000; // 5 Seconds
    private RadarConnected connectedCallback; // Fired when the radar connects
    private GoogleMap map;
    private Activity activity;
    private Marker currentUserMarker;
    private List<Marker> friendMarkers;
    private int currentZoomLevel; // Current zoom level
    private LocationClient locationClient;
    private LocationRequest locationRequest;
    private boolean isVisible;
    private GCMActionEvent friendLocationPushHandler;

    /**
     * Instantiate a manger for a map.
     *
     * @param activity // The activity the map is in.
     * @param map      // The map to manage as a radar instance.
     */
    public RadarMapManager(Activity activity, GoogleMap map) {
        this.activity = activity;
        this.map = map;

        currentZoomLevel = DEFAULT_ZOOM_LEVEL;

        friendMarkers = Collections.synchronizedList(new ArrayList<Marker>());

        // Configure Map
        map.getUiSettings().setZoomControlsEnabled(false);
        map.getUiSettings().setAllGesturesEnabled(false); // Manually handled
        map.setMyLocationEnabled(false);

        // Setup Location Services
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(DEFAULT_UPDATE_INTERVAL);
        locationRequest.setFastestInterval(DEFAULT_UPDATE_INTERVAL);

        locationClient = new LocationClient(this.activity, this, this);

        // Update friends when location is pushed to them
        friendLocationPushHandler = new GCMActionEvent() {
            @Override
            public void onEvent() {
                updateAllFriendPositions();
            }
        };
    }

    /**
     * Set the event handler for when the location services is connected
     *
     * @param onConnected Callback
     */
    public void setOnConnected(RadarConnected onConnected) {
        this.connectedCallback = onConnected;
    }

    /**
     * Stop the the Radar manager from responding and updating.
     * Should be used when the application is backgrounded, or stopped.
     */
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
     * Public interface to set the visibility of the marker
     *
     * @param visible
     */
    public void setVisibility(boolean visible) {
        UserDataManager udm = new UserDataManager(activity);

        if (visible) {
            udm.setUserVisibility(true, new TryCallback() {
                @Override
                public void success() {
                    isVisible = true;
                    updateLocationOnServer(locationClient.getLastLocation());
                }
            });
        } else {
            udm.setUserVisibility(false, new TryCallback() {
                @Override
                public void success() {
                    isVisible = false;
                    getUserMarker().setIcon(BitmapDescriptorFactory.fromBitmap(MarkerBitmapFactory.currentUserInvisible()));
                }
            });
        }
    }

    /**
     * Update a users location on the server.
     *
     * @param location The location to update to. If null the user will be set invisible
     */
    protected void updateLocationOnServer(Location location) {
        if (isVisible) {
            // Update on server end
            try {
                String secret = new UserDataManager(activity).getUserSecret();

                // Update location on server so friends can find it.
                LocationAPIAction.setLocation(secret, location, new APIResponseHandler<GenericSuccessModel>() {
                    /**
                     * Fires when a response has completed.
                     *
                     * @param genericSuccessModel The result data from the server.
                     */
                    @Override
                    public void onResponse(GenericSuccessModel genericSuccessModel) {
                        // Change the marker to indicate their visibility status has changed
                        getUserMarker().setIcon(BitmapDescriptorFactory.fromBitmap(MarkerBitmapFactory.currentUserVisible()));
                    }

                    @Override
                    public void onError(ErrorModel result) {

                    }
                });
            } catch (NoUserSecretException e) {

            }
        }
    }

    /**
     * Add a marker to the map indicating a friends position.
     *
     * @param position   The position of the friend marker.
     * @param id         A unique ID for this marker.
     * @param friendName The name of the friend to appear when the marker is clicked on.
     */
    public void setFriendMarker(LatLng position, long id, String friendName) {
        friendMarkers.add(map.addMarker(MarkerFactory.friendMarker(position, friendName)));
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
     *
     * @return
     */
    private Marker getUserMarker() {
        if (currentUserMarker == null) {
            Location location = locationClient.getLastLocation();
            LatLng point = new LatLng(location.getLatitude(), location.getLongitude());
            currentUserMarker = map.addMarker(MarkerFactory.currentUserInvisibleMarker(point));
            return currentUserMarker;
        } else {
            return currentUserMarker;
        }
    }

    /**
     * Update the location of the user and marker position
     *
     * @param animate
     */
    public void updateUserLocation(boolean animate) {
        // Move Camera
        if (animate) {
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
     *
     * @param location
     */
    private void animateToLocation(Location location) {
        map.animateCamera(CameraUpdateFactory.newLatLng(
                new LatLng(location.getLatitude(), location.getLongitude())));
    }

    /**
     * Quickly move to a location.
     *
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

    /**
     * Is the location service connected
     *
     * @return
     */
    public boolean isConnected() {
        return locationClient.isConnected();
    }

    /**
     * Get the position of ALL friends.
     */
    public void updateAllFriendPositions() {
        try {
            String secret = new UserDataManager(activity).getUserSecret();

            LocationAPIAction.friendLocations(secret, new APIResponseHandler<FriendLocationsListResponseModel>() {
                /**
                 * Fires when a response has completed.
                 *
                 * @param response The result data from the server.
                 */
                @Override
                public void onResponse(FriendLocationsListResponseModel response) {
                    for (Marker old : friendMarkers) {
                        old.remove();
                    }

                    for (UserLocationModel f : response.friendLocations) {
                        setFriendMarker(new LatLng(f.latitude, f.longitude), f.userID, f.username);
                        Log.e("FLOCK_PUSH", "EVENT");
                    }
                }

                @Override
                public void onError(ErrorModel result) {
                    Log.e("FLOCK_PUSH", "ERROR");
                }
            });
        } catch (NoUserSecretException ex) {

        }
    }

    // -------------------------------------------------
    // Location Services
    // -------------------------------------------------

    /**
     * The location manager was connected. We can now get locations.
     *
     * @param bundle
     */
    @Override
    public void onConnected(Bundle bundle) {
        locationClient.requestLocationUpdates(locationRequest, this);

        if (locationClient.getLastLocation() != null) {
            if (connectedCallback != null) {
                connectedCallback.onConnected();
            }

            // Make sure visibility is set
            setVisibility(new UserDataManager(activity).getUserVisibility());

            // Show Current User position instantly and zoom to them
            updateUserLocation(false);
            zoomToUser();

            // Update
            updateAllFriendPositions();

            // Register for friend location push updates
            GCMMessanger.getInstance().register(GCMMessanger.ACTION_LOCATION_UPDATE, friendLocationPushHandler);
        } else {
            Toast.makeText(activity, "Location Not Enabled", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDisconnected() {
        GCMMessanger.getInstance().unregister(GCMMessanger.ACTION_LOCATION_UPDATE, friendLocationPushHandler);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    /**
     * The user has moved and the location has been automatically updated.
     * Update the user's location on the map.
     *
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {
        updateUserLocation(false); // Drawing
        updateLocationOnServer(location);
    }
}
