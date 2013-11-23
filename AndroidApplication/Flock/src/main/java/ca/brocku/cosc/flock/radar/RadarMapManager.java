package ca.brocku.cosc.flock.radar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import ca.brocku.cosc.flock.radar.objects.MarkerFactory;
import ca.brocku.cosc.flock.radar.objects.RadarObjectConstants;

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

    private int currentZoomLevel; // Current zoom level

    public RadarMapManager(Context context, GoogleMap map) {
        this.context = context;
        this.map = map;

        this.currentZoomLevel = DEFAULT_ZOOM_LEVEL;

        // Configure Map
        map.getUiSettings().setZoomControlsEnabled(false);
        map.getUiSettings().setScrollGesturesEnabled(false);
        map.setMyLocationEnabled(false);

        // Draw Current User
        zoomToUser();
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
            currentUserMarker = map.addMarker(MarkerFactory.currentUserMarker(getCurrentUserPosition()));
        }
    }

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
