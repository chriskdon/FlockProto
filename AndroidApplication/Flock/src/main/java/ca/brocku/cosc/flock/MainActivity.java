package ca.brocku.cosc.flock;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import ca.brocku.cosc.flock.data.exceptions.NoUserSecretException;
import ca.brocku.cosc.flock.data.settings.UserDataManager;
import ca.brocku.cosc.flock.gcm.*;
import android.support.v4.app.Fragment;

public class MainActivity extends FragmentActivity {
    private static final int PLAY_REQUEST = 9000;

    private String secret;                      // User's secret
    private static final int NUM_PAGES = 3;     // Number of view pager pages
    private ViewPager pager;                    // The view pager

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Is play services installed?
        if(doesUserHavePlayServicesInstalled()) {
            // TODO: Don't crash on no internet

            UserDataManager udm = new UserDataManager(this);

            //go to the Registration Activity if there is no secret set on this device
            try {
                secret = udm.getUserSecret();
            } catch (NoUserSecretException e) {
                finish();
                startActivity(new Intent(this, RegisterActivity.class));
            }

            setContentView(R.layout.activity_main);
            getActionBar().hide();

            pager = (ViewPager) findViewById(R.id.main_pager);
            pager.setOffscreenPageLimit(2);
            MainPagerAdapter pagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
            pager.setAdapter(pagerAdapter);
            pager.setOnPageChangeListener(pagerAdapter);
            pager.setCurrentItem(1);

            // Register for GCM
            // TODO: Check if they have a google account
            if(udm.getGCMRegistrationID().isEmpty()) {
                GCMManager.getRegistrationIDAsync(this);
            }
        }
    }

    @Override
    protected  void onResume() {
        super.onResume();
        doesUserHavePlayServicesInstalled();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Check if Google Play Services are installed
     * @return
     */
    protected boolean doesUserHavePlayServicesInstalled() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_REQUEST).show();
            } else {
                finish();
            }

            return false; // Not Installed
        }

        return true; // Installed
    }

    /**
     * An adapter for the ViewPager. It populates the different pages that can be swiped through.
     */
    private static class MainPagerAdapter extends FragmentPagerAdapter
                                            implements ViewPager.OnPageChangeListener {

        PageFragment[] pages;

        public MainPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);

            // Create pages
            pages = new PageFragment[NUM_PAGES];
            pages[0] = new NotificationsFragment();
            pages[1] = new RadarFragment();
            pages[2] = new FriendsFragment();
        }

        @Override
        public Fragment getItem(int position) {
            return pages[position];
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        @Override
        public void onPageScrolled(int i, float v, int i2) {
            pages[i].onPageVisible();
        }

        @Override
        public void onPageSelected(int i) {}

        @Override
        public void onPageScrollStateChanged(int i) {}
    }

}
