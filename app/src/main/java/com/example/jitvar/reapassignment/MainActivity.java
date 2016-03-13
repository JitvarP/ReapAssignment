package com.example.jitvar.reapassignment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.jitvar.reapassignment.Database.UCDatabase;

import net.danlew.android.joda.JodaTimeAndroid;
public class MainActivity extends AppCompatActivity {
    private static final int ITEMS = 1;
    private static final String TAG = MainActivity.class.getSimpleName();
    private MyFragmentPagerAdapter mAdapter;
    private ViewPager mPager;
    private static UCDatabase database;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        JodaTimeAndroid.init(this);

        setupDatabase();
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        public MyFragmentPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return ProductListFragment.init(position);

                default:
                    return ProductListFragment.init(position);
            }
        }
    }

    private UCDatabase setupDatabase() {
        if (database == null) {
            database = new UCDatabase(this);
        }
        return database;
    }

    public static UCDatabase getDatabase() {
        if (database == null) {
            throw new RuntimeException("Database not initialized");
        }
        return database;
    }



}
