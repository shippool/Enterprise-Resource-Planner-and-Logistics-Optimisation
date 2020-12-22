package com.app.sample.insta;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.app.sample.insta.adapter.PageFragmentAdapter;

import com.app.sample.insta.data.GlobalVariable;
import com.app.sample.insta.data.Tools;
import com.app.sample.insta.fragment.PageFriendFragment;
import com.app.sample.insta.fragment.PageHomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;

public class ActivityMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton fab;
    private View parent_view;
  //  private FirebaseAuth firebaseAuth;

    private DrawerLayout drawer;
    private NavigationView naviagtionview;

    private PageFragmentAdapter adapter;

    private PageHomeFragment f_home;
    private PageFriendFragment f_ticket;

    private static int[] imageResId = {
            R.drawable.tab_home,
            R.drawable.tab_profile,
            R.drawable.tab_gallery,
            R.drawable.tab_friend,
            R.drawable.tab_profile
    };

    private static final int TAKE_PICTURE = 1;
    private Uri imageUri;

    private GlobalVariable globalVariable;

    private ModelApi modelApi;
    static boolean active = false;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        active = true;

        if (!Utils.allPermissionsGranted(this))
        {
            Utils.requestRuntimePermissions(this);
        }

        String temp = (String) getIntent().getSerializableExtra("Userobj");


        parent_view = findViewById(android.R.id.content);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        naviagtionview = findViewById(R.id.navi_view);
        naviagtionview.setNavigationItemSelectedListener(this);

        View headerView = naviagtionview.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.username_drawer);
        navUsername.setText(temp);

        drawer = findViewById(R.id.drawer);

        toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open,R.string.close);


        drawer.addDrawerListener(toggle);

        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        // for system bar in lollipop
        Tools.systemBarLolipop(this);


    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        active = false;
    }

//    private void updateUI(FirebaseUser currentUser)
//    {
//
//        if (currentUser == null)
//        {
//
//        } else
//        {
//            return;
//        }
//    }

    private void setupViewPager(ViewPager viewPager)
    {
        adapter = new PageFragmentAdapter(getSupportFragmentManager());

        if (f_home == null) {
            f_home = new PageHomeFragment();
        }
//        if (f_ticket == null) {
//            f_ticket = new PageFriendFragment();
//        }

        adapter.addFragment(f_home, null);
     //   adapter.addFragment(f_ticket, null);
        viewPager.setAdapter(adapter);
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(imageResId[0]);
        tabLayout.getTabAt(1).setIcon(imageResId[1]);
    }

    private void setupTabClick() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                viewPager.setCurrentItem(position);
                //actionbar.setTitle(adapter.getTitle(position));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private long exitTime = 0;

    public void doExitApp()
    {

        if(drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if ((System.currentTimeMillis() - exitTime) > 2000)
        {
            Toast.makeText(this, R.string.press_again_exit_app, Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finishAffinity(); // Close all activites
            System.exit(0);  // Releasing resources
        }
    }

    @Override
    public void onBackPressed()
    {

        doExitApp();
    }

    public void onMainMenuClick(View v)
    {
        Intent i = null;

        switch (v.getId())
        {
            case R.id.daily_route:
                i = new Intent(this, ActivityOrderDetails.class);
            break;
            case R.id.all_orders:
                i = new Intent(this, ActivityOrderDetails.class);
                break;
        }
        startActivity(i);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        if(item.getItemId() == R.id.orders)
        {
                drawer.closeDrawers();
        }
        else if(item.getItemId() == R.id.updateclientdelivery)
        {
            Toast.makeText(this,"Update Client Delivery Pressed",Toast.LENGTH_SHORT).show();
        }
        else if(item.getItemId() == R.id.profile)
        {
            Toast.makeText(this,"Profile Pressed",Toast.LENGTH_SHORT).show();
        }
        else if(item.getItemId() == R.id.help)
        {
            Toast.makeText(this,"Help Pressed",Toast.LENGTH_SHORT).show();
        }

        return true;
    }


}
