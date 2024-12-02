package com.holleryo.app;

import android.app.ActionBar;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.parse.ParseUser;


public class MainActivity extends FragmentActivity implements TabListener {

    ViewPager viewPager;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.Tab mainTab = actionBar.newTab();
        mainTab.setText("Activities").setTabListener(this);
        actionBar.addTab(mainTab);

        ActionBar.Tab hollerTab = actionBar.newTab();
        hollerTab.setText("Hollers").setTabListener(this);
        actionBar.addTab(hollerTab);

        ActionBar.Tab messageTab = actionBar.newTab();
        messageTab.setText("My Hollers").setTabListener(this);
        actionBar.addTab(messageTab);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            invokeLogOut();
            return true;
        }
        if (id == R.id.action_preference) {
            invokePreference();
            return true;
        }
        if (id == R.id.action_new_holler) {
            invokeNewHoller();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void invokeLogOut() {
        ParseUser.logOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        MainActivity.this.finish();
    }

    private void invokeNewHoller() {
        Intent newHollerIntent = new Intent(this, NewHollerActivity.class);
        startActivity(newHollerIntent);
    }

    private void invokePreference() {
        Intent preferenceIntent = new Intent(this, MainPreferenceActivity.class);
        startActivity(preferenceIntent);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        viewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;

            if (position == 0) {
                fragment = new MainFragment();
            }
            if (position == 1) {
                fragment = new HollerFragment();

            }
            if (position == 2) {
                fragment = new MyHollerFragment();
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
