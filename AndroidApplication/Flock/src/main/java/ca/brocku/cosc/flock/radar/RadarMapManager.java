package ca.brocku.cosc.flock.radar;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;

import ca.brocku.cosc.flock.radar.objects.PointMapObjectFactory;

/**
 * Handles rendering of objects on the map, and any
 * events associated with it.
 *
 * Author: Chris Kellendonk
 * Student #: 4810800
 * Date: 11/23/2013
 */
public class RadarMapManager {
    private GoogleMap map;
    private Context context;

    private Circle currentUserCircle;

    public RadarMapManager(Context context, GoogleMap map) {
        this.context = context;
        this.map = map;

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
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);

        map.moveCamera(moveToUser);
        map.animateCamera(zoom);

        if(currentUserCircle == null) {
            currentUserCircle = map.addCircle(PointMapObjectFactory.newCurrentUser(getCurrentUserPosition()));
        } else {
            currentUserCircle.setCenter(getCurrentUserPosition());
        }
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
