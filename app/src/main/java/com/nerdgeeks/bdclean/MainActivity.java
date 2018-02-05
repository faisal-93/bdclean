package com.nerdgeeks.bdclean;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.nerdgeeks.bdclean.Fragment.EventFragment;
import com.nerdgeeks.bdclean.Fragment.NoticeFragment;
import com.nerdgeeks.bdclean.Fragment.ProfileFragment;
import com.nerdgeeks.bdclean.Fragment.TeamFragment;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Drawer result;
    private TextView titleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.mToolbar);
        titleText = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        loadFragment(new ProfileFragment());

        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withHeader(R.layout.actionbar_header_layout)
                .withActionBarDrawerToggle(false)
                .withHeaderPadding(true)
                .withFullscreen(true)
                .withTranslucentNavigationBar(true)
                .withDisplayBelowStatusBar(false)
                .addDrawerItems(
                        new PrimaryDrawerItem()
                                .withName(R.string.drawer_item_1)
                                .withTextColor(Color.GRAY)
                                .withSelectedTextColor(Color.DKGRAY)
                                .withIdentifier(1),
                        new PrimaryDrawerItem()
                                .withName(R.string.drawer_item_2)
                                .withTextColor(Color.GRAY)
                                .withSelectedTextColor(Color.DKGRAY)
                                .withIdentifier(2),
                        new PrimaryDrawerItem()
                                .withName(R.string.drawer_item_3)
                                .withTextColor(Color.GRAY)
                                .withSelectedTextColor(Color.DKGRAY)
                                .withIdentifier(3),
                        new PrimaryDrawerItem()
                                .withName(R.string.drawer_item_4)
                                .withTextColor(Color.GRAY)
                                .withSelectedTextColor(Color.DKGRAY)
                                .withIdentifier(4),
                        new PrimaryDrawerItem()
                                .withName(R.string.drawer_item_5)
                                .withTextColor(Color.GRAY)
                                .withSelectedTextColor(Color.DKGRAY)
                                .withIdentifier(5)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem.getIdentifier()==5){
                            //action logout
                            onActionLogout();
                        }
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .withDrawerGravity(Gravity.END)
                .build();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    //Logout method
    private void onActionLogout() {
        
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
            intentActivity(this, LoginActivity.class);
        }
    }

    private void intentActivity(MainActivity activity, Class activityClass) {
        startActivity(new Intent(activity, activityClass));
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        outState = result.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment mFragment;
            switch (item.getItemId()) {

                case R.id.profile:
                    titleText.setText(R.string.title_profile);
                    mFragment = new ProfileFragment();
                    loadFragment(mFragment);
                    return true;

                case R.id.events:
                    titleText.setText(R.string.title_events);
                    mFragment = new EventFragment();
                    loadFragment(mFragment);
                    return true;

                case R.id.notice:
                    titleText.setText(R.string.title_notice);
                    mFragment = new NoticeFragment();
                    loadFragment(mFragment);
                    return true;

                case R.id.team:
                    titleText.setText(R.string.title_team);
                    mFragment = new TeamFragment();
                    loadFragment(mFragment);
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment mFragment){
        FragmentTransaction mTransaction = getSupportFragmentManager().beginTransaction();
        mTransaction.replace(R.id.frame_container, mFragment);
        mTransaction.addToBackStack(null);
        mTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.drawerToggle:
                if (result.isDrawerOpen()){
                    result.closeDrawer();
                } else {
                    result.openDrawer();
                }
                return true;
            default:
                return false;
        }
    }
}
