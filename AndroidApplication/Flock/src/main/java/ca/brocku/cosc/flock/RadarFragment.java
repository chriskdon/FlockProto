package ca.brocku.cosc.flock;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import ca.brocku.cosc.flock.radar.RadarMapManager;

/**
 * Created by kubasub on 11/13/2013.
 */
public class RadarFragment extends Fragment {
    private GoogleMap map;
    private RadarMapManager radarMapManager;
    // Controls
    private ImageView settings;
    private SeekBar radarZoomSlider;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_radar, container, false);

        // Bind Controls
        settings = (ImageView) v.findViewById(R.id.settings_icon);
        map = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        radarZoomSlider = (SeekBar) v.findViewById(R.id.radarZoomSlider);

        // Setup Radar Manager
        radarMapManager = new RadarMapManager(getActivity(), map);

        // Configure Defaults
        radarZoomSlider.setProgress(RadarMapManager.DEFAULT_ZOOM_LEVEL);

        // Bind Listeners
        settings.setOnClickListener(new SettingsIntentHandler());
        radarZoomSlider.setOnSeekBarChangeListener(new ZoomHandler());

        // Test
        //  TODO: REMOVE -- THIS IS TEST
        radarMapManager.setFriendMarker(new LatLng(43.128439, -79.239203), "1", "Billy Bob");
        radarMapManager.setFriendMarker(new LatLng(43.129649, -79.241209), "2", "Franky Joe");
        radarMapManager.setFriendMarker(new LatLng(43.130236, -79.240673), "3", "Billy Bob");
        radarMapManager.setFriendMarker(new LatLng(43.130420, -79.239272), "4", "Jim John");
        // ------------

        return v;
    }

    /**
     * Activity visible
     */
    @Override
    public void onStart() {
        super.onStart();

        radarMapManager.start();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    /**
     * Handles zooming in and out of the map
     */
    private class ZoomHandler implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            radarMapManager.setZoomOnUser(progress);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {}

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {}
    }

    /**
     * Launches the settings view.
     */
    private class SettingsIntentHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getActivity(), SettingsActivity.class));
        }
    }
}