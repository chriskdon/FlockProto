package ca.brocku.cosc.flock.radar.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

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
    private static Bitmap CURRENT_USER_BITMAP; // Current user icon

    /**
     * Draw a basic circle.
     *
     * @param radius
     * @param strokeWidth
     * @param fillColor
     * @param strokeColor
     * @return
     */
    private static Bitmap drawCircle(int radius, int strokeWidth, int fillColor, int strokeColor) {
        int diameter = radius*2;

        Bitmap icon = Bitmap.createBitmap(diameter, diameter, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(icon);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

        // Draw Stroke
        p.setColor(strokeColor);
        c.drawOval(new RectF(0, 0, diameter, diameter), p);

        // Draw Fill
        p.setColor(fillColor);
        c.drawOval(new RectF(strokeWidth, strokeWidth, diameter - strokeWidth, diameter - strokeWidth), p);

        return icon;
    }

    public static MarkerOptions currentUserMarker(LatLng position) {
        // Lazy Load Current User Image - Don't redraw on every call
        if(CURRENT_USER_BITMAP == null) {
            CURRENT_USER_BITMAP = drawCircle(RadarObjectConstants.RADIUS_CIRCLE,
                    RadarObjectConstants.STROKE_WIDTH,
                    RadarObjectConstants.COLOR_CURRENT_USER_VISIBLE,
                    RadarObjectConstants.COLOR_STROKE);
        }

        return new MarkerOptions()
                .position(position)
                .anchor((float)0.5, (float)0.5)
                .alpha((float)0.8)
                .title("You")
                .icon(BitmapDescriptorFactory.fromBitmap(CURRENT_USER_BITMAP));
    }
}
