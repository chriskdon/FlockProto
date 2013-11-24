package ca.brocku.cosc.flock.radar.markers;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Factory for drawing current user.
 *
 * - CircleOptions can't be extended.
 *
 * Author: Chris Kellendonk
 * Student #: 4810800
 * Date: 11/23/2013
 */
public class MarkerFactory {
    /**
     * Base settings for a marker on the map.
     * @param position  Position of the marker.
     * @return The marker settings.
     */
    private static MarkerOptions baseMarkerNoIcon(LatLng position) {
        return new MarkerOptions()
                .position(position)
                .anchor((float)0.5, (float)0.5)
                .alpha((float)0.8);
    }

    /**
     * Base marker for the current user but with no icon set.
     * @param position Position of the current user.
     * @return The marker.
     */
    private static MarkerOptions currentUserNoIcon(LatLng position) {
        return baseMarkerNoIcon(position).title("You");
    }

    /**
     * Marker for when the current user is visible.
     * @param position Position of the current user.
     * @return The marker.
     */
    public static MarkerOptions currentUserVisibleMarker(LatLng position) {
        return currentUserNoIcon(position)
                .icon(BitmapDescriptorFactory.fromBitmap(MarkerBitmapFactory.currentUserVisible()));
    }

    /**
     * Marker for when the current user is invisible.
     * @param position Position of the current user.
     * @return The marker options.
     */
    public static MarkerOptions currentUserInvisibleMarker(LatLng position) {
        return currentUserNoIcon(position)
                .icon(BitmapDescriptorFactory.fromBitmap(MarkerBitmapFactory.currentUserInvisible()));
    }

    /**
     * Maker for a friend on the map.
     * @param position      The position of the friend.
     * @param friendName    The name of the friend to appear when the marker is clicked on.
     * @return The marker options.
     */
    public static MarkerOptions friendMarker(LatLng position, String friendName) {
        return baseMarkerNoIcon(position)
                .title(friendName)
                .icon(BitmapDescriptorFactory.fromBitmap(MarkerBitmapFactory.friend()));
    }
}
