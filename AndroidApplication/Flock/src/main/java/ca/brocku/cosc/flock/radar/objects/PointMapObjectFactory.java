package ca.brocku.cosc.flock.radar.objects;

import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

/**
 * Factory for drawing current user.
 *
 * - CircleOptions can't be extended.
 *
 * Author: Chris Kellendonk
 * Student #: 4810800
 * Date: 11/23/2013
 */
public class PointMapObjectFactory {
    private static CircleOptions newBaseCircle() {
        return new CircleOptions()
                .strokeColor(RadarObjectConstants.COLOR_STROKE)
                .radius(RadarObjectConstants.RADIUS_CIRCLE)
                .strokeWidth(RadarObjectConstants.STROKE_WIDTH);
    }

    public static CircleOptions newCurrentUser(LatLng centre) {
        return newBaseCircle()
                .fillColor(RadarObjectConstants.COLOR_CURRENT_USER)
                .center(centre);
    }
}
