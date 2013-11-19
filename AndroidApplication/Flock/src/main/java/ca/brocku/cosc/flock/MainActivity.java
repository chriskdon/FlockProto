package ca.brocku.cosc.flock;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends FragmentActivity {
    private static final int NUM_PAGES = 3;
    private ViewPager pager;
    private MainPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar().hide();

        pagerAdapter = new MainPagerAdapter(getSupportFragmentManager());

        pager = (ViewPager) findViewById(R.id.main_pager);
        pager.setOffscreenPageLimit(2);
        pager.setAdapter(pagerAdapter);
        pager.setCurrentItem(1);
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
     * An adapter for the ViewPager. It populates the different pages that can be swiped through.
     */
    private static class MainPagerAdapter extends FragmentPagerAdapter {

        public MainPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new NotificationsFragment();
                case 1:
                    return new RadarFragment();
                case 2:
                    return new FriendsFragment();
                default:
                    return new RadarFragment();
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

}
