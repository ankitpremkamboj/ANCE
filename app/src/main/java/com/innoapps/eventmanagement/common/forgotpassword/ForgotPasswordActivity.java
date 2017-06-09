package com.innoapps.eventmanagement.common.forgotpassword;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.innoapps.eventmanagement.R;
import com.innoapps.eventmanagement.common.agenda.adapter.AgendaAdapter;
import com.innoapps.eventmanagement.common.helper.Progress;
import com.innoapps.eventmanagement.common.login.LoginActivity;
import com.innoapps.eventmanagement.common.login.model.LoginUser;
import com.innoapps.eventmanagement.common.login.presenter.LoginIntracter;
import com.innoapps.eventmanagement.common.login.presenter.LoginPresenterImpl;
import com.innoapps.eventmanagement.common.navigation.NavigationActivity;
import com.innoapps.eventmanagement.common.requestresponse.ApiAdapter;
import com.innoapps.eventmanagement.common.utils.AppConstant;
import com.innoapps.eventmanagement.common.utils.CheckPermissions;
import com.innoapps.eventmanagement.common.utils.SnackNotify;
import com.innoapps.eventmanagement.common.utils.UserSession;
import com.innoapps.eventmanagement.common.utils.Utils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ankit on 3/15/2017.
 */

public class ForgotPasswordActivity extends AppCompatActivity {

    @InjectView(R.id.input_email)
    EditText input_email;


    @InjectView(R.id.btn_cancel)
    Button btn_cancel;
    @InjectView(R.id.img_back)
    ImageView img_back;
    @InjectView(R.id.txt_email_content)
    TextView txt_email_content;


    @InjectView(R.id.btn_submit)
    Button btn_submit;

    @InjectView(R.id.txt_header_title)
    TextView txt_header_title;

    @InjectView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;


    UserSession userSession;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        ButterKnife.inject(this);
        setFont();
        userSession = new UserSession(this);
        txt_header_title.setText("Forgot Password");
    }

    @OnClick(R.id.btn_cancel)
    public void cancel() {
        finish();
    }

    @OnClick(R.id.img_back)
    public void back() {
        finish();
    }

    @OnClick(R.id.btn_submit)
    public void submit() {

        String email_value = input_email.getText().toString().trim();


        if (validateCredential(email_value)) {

            callForgotPassword(email_value);


        }
    }

    private void callForgotPassword(String email) {

        try {
            ApiAdapter.getInstance(this);
            callForgotPasswordMethod(email);
        } catch (ApiAdapter.NoInternetException e) {
            e.printStackTrace();

        }


    }

    private void callForgotPasswordMethod(String email) {
        Progress.start(this);
        Call<ForgetPwdModel> callLogin;


        callLogin = ApiAdapter.getApiService().forgotPassword("forgot_password", email);

        callLogin.enqueue(new Callback<ForgetPwdModel>() {
            @Override
            public void onResponse(Call<ForgetPwdModel> call, Response<ForgetPwdModel> response) {

                Progress.stop();
                try {
                    //getting whole data from response
                    ForgetPwdModel forgetPwdModel = response.body();

                    String message = forgetPwdModel.getMsg();

                    if (forgetPwdModel.getStatus() == 1 && forgetPwdModel.getStatusCode() == 1) {
                        //SnackNotify.showMessage(message, coordinatorLayout);
                        //  finish();
                        showYesNoDialog(message);

                    } else {
                        SnackNotify.showMessage(message, coordinatorLayout);
                    }
                } catch (NullPointerException e) {
                    SnackNotify.showMessage(getString(R.string.server_error), coordinatorLayout);
                }
            }

            @Override
            public void onFailure(Call<ForgetPwdModel> call, Throwable t) {
                Progress.stop();
                SnackNotify.showMessage(getString(R.string.server_error), coordinatorLayout);

            }

        });


    }


    public boolean validateCredential(String email) {

        if (TextUtils.isEmpty(email)) {
            Utils.showError(input_email, getString(R.string.empty_email), this);
            return false;
        } else {
            return true;
        }
    }


    private void setFont() {
        txt_email_content.setTypeface(Typeface.createFromAsset(getAssets(), AppConstant.AVENIRNEXT_REGULAR));
        input_email.setTypeface(Typeface.createFromAsset(getAssets(), AppConstant.AVENIRNEXT_MEDIUM));
        btn_cancel.setTypeface(Typeface.createFromAsset(getAssets(), AppConstant.AVENIRNEXT_DEMIBOLD));
        btn_submit.setTypeface(Typeface.createFromAsset(getAssets(), AppConstant.AVENIRNEXT_DEMIBOLD));
        txt_header_title.setTypeface(Typeface.createFromAsset(getAssets(), AppConstant.AVENIRNEXT_DEMIBOLD));
    }


    public void showYesNoDialog(String message) {
        try {

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:

                            finish();

                            break;

                        case DialogInterface.BUTTON_NEGATIVE:

                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Your password has been sent to the email address you have provided.").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
