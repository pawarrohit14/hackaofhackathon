package info.androidhive.navigationdrawer.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import backend.gppmon.handler.RTMProviderImpl;
import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.fragment.BackoutListFragment;
import info.androidhive.navigationdrawer.fragment.BroadcastFragment;
import info.androidhive.navigationdrawer.fragment.ErrorLogFragment;
import info.androidhive.navigationdrawer.fragment.EventStatusFragment;
import info.androidhive.navigationdrawer.fragment.InterfaceFragment;
import info.androidhive.navigationdrawer.fragment.QueueListFragment;
import info.androidhive.navigationdrawer.fragment.TaskListFragment;
import info.androidhive.navigationdrawer.json.QueueRec;
import info.androidhive.navigationdrawer.service.BroadcastService;
import info.androidhive.navigationdrawer.utils.CircleTransform;

public class MainActivity extends BaseActivity {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;
    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;


    private BroadcastFragment broadcastFragment;
    private InterfaceFragment interfaceFragment;
    private QueueListFragment queueListFragment;
    private ErrorLogFragment errorLogFragment;
    private BackoutListFragment backoutListFragment;


    // urls to load navigation header background image
    // and profile image
    private static final String urlNavHeaderBg = "http://cssslider.com/sliders/demo-10/data1/images/3.jpg";
    private static final String urlProfileImg = "https://b.zmtcdn.com/data/user_profile_pictures/669/2c13c53f7a6da8c1573f9b95c8ace669.jpg?fit=around%7C400%3A400&crop=400%3A400%3B%2A%2C%2A";

    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_INTERFACE = "interface";
    private static final String TAG_BROADCAST = "broadcast";
    private static final String TAG_QUEUE_LIST = "queue_list";
    private static final String TAG_BACKOUT_LIST = "backout_list";
    private static final String TAG_TASK_LIST = "task_list";
    private static final String TAG_EVENT_STATUS = "event_status";
    private static final String TAG_ERROR_LOG = "error_log";
    private static final String TAG_LOGOUT = "logout";
    public static final String MY_PREFS_NAME = "eagle_eye_prefs";
    public static String CURRENT_TAG = TAG_BROADCAST;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        txtWebsite = (TextView) navHeader.findViewById(R.id.website);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);

        // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);
        // load nav menu header data
        loadNavHeader();
        // initializing navigation menu
        setUpNavigationView();


        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_BROADCAST;
            loadHomeFragment();
        }

        if (broadcastFragment == null) {
            broadcastFragment = new BroadcastFragment();
        }
        if (interfaceFragment == null) {
            interfaceFragment = new InterfaceFragment();
        }
        if (queueListFragment == null) {
            queueListFragment = new QueueListFragment();
        }
        if (errorLogFragment == null) {
            errorLogFragment = new ErrorLogFragment();
        }
        if (backoutListFragment == null) {
            backoutListFragment = new BackoutListFragment();
        }

        //   setBroadcastMessagehandler(broadcastFragment);

    }

    private void loadNavHeader() {
        // name, website
        txtName.setText("Rohit Pawar");
        txtWebsite.setText("www.finastra.com");

        // loading header background image
        Glide.with(this).load(urlNavHeaderBg)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgNavHeaderBg);

        // Loading profile image
        Glide.with(this).load(urlProfileImg)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);

        navigationView.getMenu().getItem(0).setActionView(R.layout.menu_dot);
    }


    private void loadHomeFragment() {

        selectNavMenu();
        setToolbarTitle();
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();
            return;
        }

        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                if (fragment == null) {
                    logout();

                } else
                {  FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private void logout() {

        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("USER", "");
        editor.putString("PASS", "");
        editor.putBoolean("LOGGED_IN", false);
        editor.apply();

        this.stopService(new Intent(this, BroadcastService.class));
        try {
            RTMProviderImpl.getInstance().logout();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();


    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {

            case 0:
                // broadcast fragment
                // BroadcastFragment broadcastFragment = new BroadcastFragment();
                return broadcastFragment;

            case 1:

                // interface fragment
                //  InterfaceFragment interfaceFragment = new InterfaceFragment();
                return interfaceFragment;
            case 2:
                // queue fragment
                // QueueListFragment queueListFragment = new QueueListFragment();
                return queueListFragment;

            case 3:
                // backout
                // BackoutListFragment backoutListFragment = new BackoutListFragment();
                return backoutListFragment;

            case 4:
                // settings fragment
                // ErrorLogFragment errorLogFragment = new ErrorLogFragment();
                return errorLogFragment;

            case 5:
                // settings fragment
                TaskListFragment taskListFragment = new TaskListFragment();
                return taskListFragment;

            case 6:
                // settings fragment
                EventStatusFragment eventStatusFragment = new EventStatusFragment();
                return eventStatusFragment;

            case 7:
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                return null;
            default:
                return broadcastFragment;
        }

    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;

                    case R.id.nav_broadcast:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_BROADCAST;
                        break;

                    case R.id.nav_interface:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_INTERFACE;
                        break;

                    case R.id.nav_queue_list:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_QUEUE_LIST;
                        break;

                    case R.id.nav_backout_list:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_BACKOUT_LIST;
                        break;


                    case R.id.nav_error_log:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_ERROR_LOG;
                        break;

                    case R.id.nav_task_list:
                        navItemIndex = 5;
                        CURRENT_TAG = TAG_TASK_LIST;
                        break;
                    case R.id.nav_event_status:
                        navItemIndex = 6;
                        CURRENT_TAG = TAG_EVENT_STATUS;
                        break;

                    case R.id.nav_logout:
                        navItemIndex = 7;
                        CURRENT_TAG = TAG_LOGOUT;
                        break;

                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        } else {
            //Exit application on backpress
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            a.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(a);
        }
        if (shouldLoadHomeFragOnBackPress) {

            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_BROADCAST;
                loadHomeFragment();
                return;
            }
        }

        super.onBackPressed();


    }


    @Override
    protected void onResume() {
        super.onResume();

    }

}
