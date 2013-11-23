package ca.brocku.cosc.flock;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

import ca.brocku.cosc.flock.radar.RadarMapManager;

/**
 * Created by kubasub on 11/13/2013.
 */
public class RadarFragment extends Fragment {
    private ImageView settings;
    private GoogleMap map;
    private RadarMapManager radarMapManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_radar, container, false);

        // Bind Controls
        settings = (ImageView) v.findViewById(R.id.settings_icon);
        map = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        // Bind Listener
        settings.setOnClickListener(new SettingsIntentHandler());

        // Setup Radar Manager
        radarMapManager = new RadarMapManager(getActivity(), map);

        return v;
    }

    private class SettingsIntentHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getActivity(), SettingsActivity.class));
        }
    }
}