package com.innoapps.eventmanagement.common.login;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.innoapps.eventmanagement.R;
import com.innoapps.eventmanagement.common.forgotpassword.ForgotPasswordActivity;
import com.innoapps.eventmanagement.common.helper.Config;
import com.innoapps.eventmanagement.common.helper.SnackNotifyHelper;
import com.innoapps.eventmanagement.common.interfaces.SnakeOnClick;
import com.innoapps.eventmanagement.common.login.model.LoginUser;
import com.innoapps.eventmanagement.common.login.presenter.LoginIntracter;
import com.innoapps.eventmanagement.common.login.presenter.LoginPresenter;
import com.innoapps.eventmanagement.common.login.presenter.LoginPresenterImpl;
import com.innoapps.eventmanagement.common.login.view.LoginView;
import com.innoapps.eventmanagement.common.navigation.NavigationActivity;
import com.innoapps.eventmanagement.common.signup.RegistrationActivity;
import com.innoapps.eventmanagement.common.utils.CheckPermissions;
import com.innoapps.eventmanagement.common.utils.NotificationUtils;
import com.innoapps.eventmanagement.common.utils.SnackNotify;
import com.innoapps.eventmanagement.common.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginView {
    @InjectView(R.id.input_email)
    EditText input_email;
    @InjectView(R.id.input_password)
    EditText input_password;
    @InjectView(R.id.btn_signIn)
    Button btn_signIn;
    @InjectView(R.id.txt_signUp)
    TextView txt_signUp;
    @InjectView(R.id.txt_notAccount)
    TextView txt_notAccount;
    @InjectView(R.id.txt_sign_in)
    TextView txt_sign_in;
    @InjectView(R.id.txt_manage_account)
    TextView txt_manage_account;
    @InjectView(R.id.txt_one_step)
    TextView txt_one_step;
    @InjectView(R.id.forgot_pass)
    TextView forgot_pass;
    @InjectView(R.id.coordinateLayout)
    CoordinatorLayout coordinateLayout;
    SnakeOnClick snakeOnClick;

    private static final String TAG = NavigationActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    String userName, password;
    LoginPresenter loginPresenter;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.inject(this);
        broadcast();

        setFonts();
        callSnakeOnClick();
        loginPresenter = new LoginPresenterImpl(this, this);
        CheckPermissions.checkPermissions(this);

    }


    private void setFonts() {

        //Typeface face= Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Bold.otf");
        //txt_sign_in.setTypeface(face);

        txt_manage_account.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/AvenirNext-Regular.ttf"));
        txt_one_step.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/AvenirNext-Regular.ttf"));
        txt_sign_in.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/AvenirNext-DemiBold.ttf"));
        forgot_pass.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/AvenirNext-DemiBold.ttf"));
        txt_notAccount.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/AvenirNext-Medium.ttf"));
        btn_signIn.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/AvenirNext-DemiBold.ttf"));
        input_email.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/AvenirNext-Medium.ttf"));
        input_password.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/AvenirNext-Medium.ttf"));
        txt_signUp.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/AvenirNext-DemiBold.ttf"));


    }


    @OnClick(R.id.txt_signUp)
    public void registration() {
        startActivity(new Intent(this, RegistrationActivity.class));
        finish();
    }

    @OnClick(R.id.btn_signIn)
    public void loginUser() {
        mCallLogin();
        //startActivity(new Intent(this, NavigationActivity.class));
    }


    @OnClick(R.id.forgot_pass)
    public void forgotPassword() {
        startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
    }

    private void mCallLogin() {
        userName = input_email.getText().toString().trim();
        password = input_password.getText().toString().trim();

        loginPresenter.validateSimpleLogin(userName, password);


    }


    @Override
    public void onLoginSuccessful(LoginUser.UserDeatils userDeatils) {

        startActivity(new Intent(this, NavigationActivity.class));
        finish();

    }

    @Override
    public void onLoginUnSuccessful(String msg) {
        SnackNotify.showMessage(msg, coordinateLayout);


    }

    @Override
    public void onEmailError(String msg) {
        Utils.showError(input_email, msg, this);


    }

    @Override
    public void onPasswordError(String msg) {
        Utils.showError(input_password, msg, this);


    }

    @Override
    public void onLoginInternetError() {

        SnackNotifyHelper.checkConnection(this, snakeOnClick, coordinateLayout);


    }

    private void callSnakeOnClick() {
        snakeOnClick = new SnakeOnClick() {
            @Override
            public void onRetryClick() {
                loginUser();

            }
        };
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

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
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
    protected void onResume() {
        super.onResume();

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

}
