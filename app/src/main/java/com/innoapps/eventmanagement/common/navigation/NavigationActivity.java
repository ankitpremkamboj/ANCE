package com.innoapps.eventmanagement.common.navigation;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.messaging.FirebaseMessaging;
import com.innoapps.eventmanagement.R;
import com.innoapps.eventmanagement.common.blogs.AddBlogActivity;
import com.innoapps.eventmanagement.common.blogs.BlogsFragment;
import com.innoapps.eventmanagement.common.events.EventsFragment;
import com.innoapps.eventmanagement.common.events.model.EventsModel;
import com.innoapps.eventmanagement.common.events.view.EventView;
import com.innoapps.eventmanagement.common.friends.FriendsActivity;
import com.innoapps.eventmanagement.common.helper.Config;
import com.innoapps.eventmanagement.common.login.LoginActivity;
import com.innoapps.eventmanagement.common.myblogs.MyBlogsFragment;
import com.innoapps.eventmanagement.common.myfriends.MyFriendsActivity;
import com.innoapps.eventmanagement.common.photofeed.PhotoFeedFragment;
import com.innoapps.eventmanagement.common.postevents.PostEventsActivity;
import com.innoapps.eventmanagement.common.userprofile.UserProfileActivity;
import com.innoapps.eventmanagement.common.userprofile.model.UserProfileDetailsModel;
import com.innoapps.eventmanagement.common.userprofile.model.UserProfileModel;
import com.innoapps.eventmanagement.common.userprofile.presenter.UserProfilePresenter;
import com.innoapps.eventmanagement.common.userprofile.presenter.UserProfilePresenterImpl;
import com.innoapps.eventmanagement.common.userprofile.view.UserProfileView;
import com.innoapps.eventmanagement.common.utils.AppConstant;
import com.innoapps.eventmanagement.common.utils.GlideCircleTransform;
import com.innoapps.eventmanagement.common.utils.NotificationUtils;
import com.innoapps.eventmanagement.common.utils.UserSession;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, EventView, UserProfileView {


    View headerView;
    @InjectView(R.id.nav_view)
    NavigationView nav_view;
    @InjectView(R.id.drawer_layout)
    DrawerLayout drawer_layout;
    @InjectView(R.id.imgMenu)
    ImageView imgMenu;

    //  @InjectView(R.id.user_image)
    ImageView user_image;

    // @InjectView(R.id.user_name)
    TextView user_name;
    //  @InjectView(R.id.user_mobile)
    TextView user_mobile;
    //  @InjectView(R.id.user_email)
    TextView user_email;
    UserSession userSession;

    @InjectView(R.id.addFriend)
    ImageView addFriend;

    @InjectView(R.id.bell_img)
    ImageView bell_img;

    @InjectView(R.id.addBlog)
    ImageView addBlog;

    @InjectView(R.id.event_title)
    TextView event_title;
    UserProfilePresenter userProfilePresenter;
    FloatingActionButton fab;

    private boolean exit = false;

    ArrayList<EventsModel.EventList> eventListsData;

    private static final String TAG = NavigationActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    // private TextView txtRegId, txtMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);
        bell_img.setVisibility(View.INVISIBLE);

        userSession = new UserSession(this);
        userProfilePresenter = new UserProfilePresenterImpl(this, this);
        broadcast();
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                startActivity(new Intent(NavigationActivity.this, PostEventsActivity.class));
            }
        });


        nav_view.setNavigationItemSelectedListener(this);
        // event_title.setText("Events");
        event_title.setText("ANCE 2017");
        manageHeaderLayout();
        //   userProfileDetails();
        inflateFargment();

        setFonts();
        userProfilePresenter.fetchUserProfileDetails(this, this);
        long maxMemory = Runtime.getRuntime().maxMemory();

    }


    private void setFonts() {

        // input_event_title.setTypeface(Typeface.createFromAsset(getAssets(), AppConstant.AVENIRNEXT_MEDIUM));
        // input_description.setTypeface(Typeface.createFromAsset(getAssets(), AppConstant.AVENIRNEXT_MEDIUM));
        // input_location.setTypeface(Typeface.createFromAsset(getAssets(), AppConstant.AVENIRNEXT_MEDIUM));
        //  btn_post_event.setTypeface(Typeface.createFromAsset(getAssets(), AppConstant.AVENIRNEXT_DEMIBOLD));

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (userSession.isProRefresh()) {
            userSession.profileRefresh(false);
            userProfilePresenter.fetchUserProfileDetails(this, this);
        }
        // userProfilePresenter.fetchUserProfileDetails(this, this);
        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());

        //  userProfilePresenter.fetchUserProfileDetails(this, this);
    }

    @OnClick(R.id.addFriend)
    public void addFriendList() {

        Intent intent = new Intent(this, FriendsActivity.class);
        startActivity(intent);
    }

    private void inflateFargment() {
        fab.setVisibility(View.VISIBLE);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        EventsFragment eventsFragment = new EventsFragment();
        fragmentTransaction.replace(R.id.frameLayout, eventsFragment, EventsFragment.class.getSimpleName());
        //  fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    public void manageHeaderLayout() {

        View headerView = nav_view.inflateHeaderView(R.layout.nav_header_main);


        user_image = (ImageView) headerView.findViewById(R.id.user_image);
        user_name = (TextView) headerView.findViewById(R.id.user_name);
        user_mobile = (TextView) headerView.findViewById(R.id.user_mobile);
        user_email = (TextView) headerView.findViewById(R.id.user_email);

        user_name.setTypeface(Typeface.createFromAsset(getAssets(), AppConstant.AVENIRNEXT_DEMIBOLD));
        user_mobile.setTypeface(Typeface.createFromAsset(getAssets(), AppConstant.AVENIRNEXT_DEMIBOLD));
        user_email.setTypeface(Typeface.createFromAsset(getAssets(), AppConstant.AVENIRNEXT_DEMIBOLD));


        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                    drawer_layout.closeDrawer(GravityCompat.START);
                } else {
                    drawer_layout.openDrawer(GravityCompat.START);
                }
            }
        });

    }


    @Override
    public void onBackPressed() {

        //  DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        EventsFragment fragment1 = (EventsFragment) getSupportFragmentManager().findFragmentByTag(EventsFragment.class.getSimpleName());
        if (fragment1 != null && fragment1.isVisible()) {
            event_title.setText("ANCE 2017");
            fab.setVisibility(View.VISIBLE);
            bell_img.setVisibility(View.INVISIBLE);
            addFriend.setVisibility(View.VISIBLE);
            addBlog.setVisibility(View.INVISIBLE);
        }

      /*  if (exit) {

        } else {
            Toast.makeText(this, "Press Back again to Exit.",Toast.LENGTH_SHORT).show();
            exit = true;
            android.os.Handler handler = new android.os.Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 2000);
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @OnClick(R.id.addBlog)
    public void addBlog() {
        Intent intent = new Intent(this, AddBlogActivity.class);
        startActivity(intent);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

       /* if (id == R.id.nav_about_us) {
            // Handle the camera action
        } else*/
        if (id == R.id.nav_nace) {

            event_title.setText("ANCE 2017");
            fab.setVisibility(View.GONE);

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            ANCE2017Fragment ance2017Fragment = new ANCE2017Fragment();
            fragmentTransaction.replace(R.id.frameLayout, ance2017Fragment, ANCE2017Fragment.class.getSimpleName());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();


        } else if (id == R.id.nav_my_account) {
            Intent intent = new Intent(NavigationActivity.this, UserProfileActivity.class);
            // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        } else if (id == R.id.nav_timeline) {
            //event_title.setText("Events");
            event_title.setText("ANCE 2017");
            fab.setVisibility(View.VISIBLE);
            bell_img.setVisibility(View.INVISIBLE);
            addFriend.setVisibility(View.VISIBLE);
            addBlog.setVisibility(View.INVISIBLE);
            inflateFargment();

        } else if (id == R.id.nav_blogging) {

            event_title.setText("Blogs");
            fab.setVisibility(View.GONE);
            bell_img.setVisibility(View.INVISIBLE);
            addFriend.setVisibility(View.INVISIBLE);
            addBlog.setVisibility(View.VISIBLE);

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            BlogsFragment blogsFragment = new BlogsFragment();
            fragmentTransaction.replace(R.id.frameLayout, blogsFragment, BlogsFragment.class.getSimpleName());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_photo_feed) {

            event_title.setText("Photo Feed");
            fab.setVisibility(View.GONE);
            bell_img.setVisibility(View.INVISIBLE);
            addFriend.setVisibility(View.INVISIBLE);
            addBlog.setVisibility(View.INVISIBLE);

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            PhotoFeedFragment photoFeedFragment = new PhotoFeedFragment();
            fragmentTransaction.replace(R.id.frameLayout, photoFeedFragment, BlogsFragment.class.getSimpleName());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }


        else if (id == R.id.nav_logout) {
            showYesNoDialog();

         /*   Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);*/

        } else if (id == R.id.nav_my_friend) {
            Intent intent = new Intent(NavigationActivity.this, MyFriendsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_my_blogs) {

            event_title.setText("My Blogs");
            fab.setVisibility(View.GONE);
            bell_img.setVisibility(View.INVISIBLE);
            addFriend.setVisibility(View.INVISIBLE);
            addBlog.setVisibility(View.VISIBLE);

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            MyBlogsFragment myBlogsFragment = new MyBlogsFragment();
            fragmentTransaction.replace(R.id.frameLayout, myBlogsFragment, MyBlogsFragment.class.getSimpleName());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
        //nav_sponsor

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void getEventSuccessfull(ArrayList<EventsModel.EventList> eventLists) {
        this.eventListsData = eventLists;

    }

    @Override
    public void getEventUnSuccessful(String message) {

    }

    @Override
    public void eventInternetError() {

    }


    public void showYesNoDialog() {
        try {

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:

                            UserSession userSession = new UserSession(NavigationActivity.this);
                            userSession.clearUserSession();
                            Intent intent = new Intent(NavigationActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                            break;

                        case DialogInterface.BUTTON_NEGATIVE:

                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to logout?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNameError(String name) {

    }

    @Override
    public void onMobileNumberError(String mobileNumber) {

    }

    @Override
    public void onCompanyNameError(String companyName) {

    }

    @Override
    public void onProfileImageError(String profileImage) {

    }

    @Override
    public void onUserProfileInternetError() {

    }

    @Override
    public void onUserProfileSuccessful(UserProfileModel profileModel) {
        //  userProfilePresenter.fetchUserProfileDetails(this, this);
    }

    @Override
    public void onGetUserProfileSuccessful(UserProfileDetailsModel userProfileDetailsModel) {

        try {
            String name = userProfileDetailsModel.getUserProfile().getName();
            String mobile = userProfileDetailsModel.getUserProfile().getMobile();
            String email = userProfileDetailsModel.getUserProfile().getEmail();

            if (!name.isEmpty() && name != null) {

                String cap = name.substring(0, 1).toUpperCase() + name.substring(1);
                user_name.setText(cap);
                // user_name.setText(userProfileDetailsModel.getUserProfile().getName());
            } else {
                user_name.setText("N/A");
            }
            if (!mobile.isEmpty() && mobile != null) {
                user_mobile.setText(userProfileDetailsModel.getUserProfile().getMobile());
            } else {
                user_mobile.setText("N/A");
            }
            if (!email.isEmpty() && email != null) {
                user_email.setText(userProfileDetailsModel.getUserProfile().getEmail());
            } else {
                user_email.setText("N/A");
            }

            Glide.with(this)
                    .load(userProfileDetailsModel.getUserProfile().getImage())
                    .transform(new GlideCircleTransform(this))
                    .placeholder(R.mipmap.ic_default_profile)
                    .error(R.mipmap.ic_default_profile)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(user_image);


        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUserProfileUnSuccessful(String msg) {
    }


    public void broadcast() {

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                    //  txtMessage.setText(message);
                }
            }
        };
        displayFirebaseRegId();
    }

    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);

       /* if (!TextUtils.isEmpty(regId))
            txtRegId.setText("Firebase Reg Id: " + regId);
        else
            txtRegId.setText("Firebase Reg Id is not received yet!");*/
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }
}
