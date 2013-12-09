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

import ca.brocku.cosc.flock.data.api.actions.LocationAPIAction;
import ca.brocku.cosc.flock.data.settings.UserDataManager;
import ca.brocku.cosc.flock.radar.RadarMapManager;
import ca.brocku.cosc.flock.radar.callbacks.RadarConnected;

/**
 * Created by kubasub on 11/13/2013.
 */
public class RadarFragment extends PageFragment {
    private static final int REQUEST_CODE_SETTINGS = 1;
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

        // Check visibility
        radarMapManager.setOnConnected(new RadarConnectedHandler());

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(!radarMapManager.isConnected()) {
            radarMapManager.start();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_SETTINGS) { // Coming back from settings
            checkRadarVisibility();
        }
    }

    /**
     * Check the user preferences to see if the radar visibility setting has changed.
     */
    protected void checkRadarVisibility() {
        radarMapManager.setVisibility(new UserDataManager(getActivity()).getUserVisibility());
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
            startActivityForResult(new Intent(getActivity(), SettingsActivity.class),
                    REQUEST_CODE_SETTINGS);
        }
    }

    /**
     * When connecting check the current status of the visibility setting.
     */
    private class RadarConnectedHandler implements RadarConnected {
        @Override
        public void onConnected() {
            checkRadarVisibility();
        }
    }
}