package ca.brocku.cosc.flock.radar.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Handles the actual storage and drawing of images for map markers.
 *
 * Author: Chris Kellendonk
 * Student #: 4810800
 * Date: 11/23/2013
 */
public class MarkerBitmapFactory {
    private static Bitmap CURRENT_USER_VISIBLE_BITMAP; // Current user icon when visible
    private static Bitmap CURRENT_USER_INVISIBLE_BITMAP; // Current user icon when invisible
    private static Bitmap FRIEND_BITMAP; // Icon for friends

    /**
     * Draw a basic circle.
     * @param radius        Radius of the circle.
     * @param strokeWidth   Width of the stroke on the circle.
     * @param fillColor     Fill color for the circle.
     * @param strokeColor   Stroke color for the circle.
     * @return The bitmap image of the circle.
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

    /**
     * Image for the current user when visible to other users.
     *
     * @return The bitmap image.
     */
    public static Bitmap currentUserVisible() {
        // Lazy Load Current User Image - Don't redraw on every call
        if(CURRENT_USER_VISIBLE_BITMAP == null) {
            CURRENT_USER_VISIBLE_BITMAP = drawCircle(RadarObjectConstants.RADIUS_CIRCLE,
                    RadarObjectConstants.STROKE_WIDTH,
                    RadarObjectConstants.COLOR_CURRENT_USER_VISIBLE,
                    RadarObjectConstants.COLOR_STROKE);
        }

        return CURRENT_USER_VISIBLE_BITMAP;
    }

    /**
     * Image for the current user when invisible to other users.
     * @return The bitmap image.
     */
    public static Bitmap currentUserInvisible() {
        // Lazy Load Current User Image - Don't redraw on every call
        if(CURRENT_USER_INVISIBLE_BITMAP == null) {
            CURRENT_USER_INVISIBLE_BITMAP = drawCircle(RadarObjectConstants.RADIUS_CIRCLE,
                    RadarObjectConstants.STROKE_WIDTH,
                    RadarObjectConstants.COLOR_CURRENT_USER_INVISIBLE,
                    RadarObjectConstants.COLOR_STROKE);
        }

        return CURRENT_USER_INVISIBLE_BITMAP;
    }

    /**
     * Image for a friend icon.
     * @return The bitmap image.
     */
    public static Bitmap friend() {
        if(FRIEND_BITMAP == null) {
            FRIEND_BITMAP = drawCircle(RadarObjectConstants.RADIUS_CIRCLE_FRIEND,
                    RadarObjectConstants.STROKE_WIDTH,
                    RadarObjectConstants.COLOR_FRIEND,
                    RadarObjectConstants.COLOR_STROKE);
        }

        return FRIEND_BITMAP;
    }
}
