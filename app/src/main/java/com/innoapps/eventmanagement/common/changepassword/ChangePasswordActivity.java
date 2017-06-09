package com.innoapps.eventmanagement.common.changepassword;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.innoapps.eventmanagement.R;
import com.innoapps.eventmanagement.common.forgotpassword.ForgetPwdModel;
import com.innoapps.eventmanagement.common.friends.model.RequestSuccessModel;
import com.innoapps.eventmanagement.common.helper.Progress;
import com.innoapps.eventmanagement.common.requestresponse.ApiAdapter;
import com.innoapps.eventmanagement.common.utils.AppConstant;
import com.innoapps.eventmanagement.common.utils.SnackNotify;
import com.innoapps.eventmanagement.common.utils.UserSession;
import com.innoapps.eventmanagement.common.utils.Utils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {
    @InjectView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;


    @InjectView(R.id.input_new_password)
    EditText input_new_password;

    @InjectView(R.id.input_confirm_password)
    EditText input_confirm_password;

    @InjectView(R.id.btn_save)
    Button btn_save;

    @InjectView(R.id.img_back)
    ImageView img_back;

    @InjectView(R.id.txt_header_title)
    TextView txt_header_title;
    UserSession userSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.inject(this);
        setFont();
        userSession = new UserSession(this);
        txt_header_title.setText("New Password");

    }

    @OnClick(R.id.img_back)
    public void back() {
        finish();
    }

    @OnClick(R.id.btn_save)
    public void save() {

        String newPassword = input_new_password.getText().toString().trim();
        String confirmPassword = input_confirm_password.getText().toString().trim();

        if (validateCredential(newPassword, confirmPassword)) {
            try {
                ApiAdapter.getInstance(this);
                callChangePassword(confirmPassword);
            } catch (ApiAdapter.NoInternetException e) {
                e.printStackTrace();

            }
        }
    }

    private void callChangePassword(String newPassword) {
        Progress.start(this);

        Call<RequestSuccessModel> callLogin;


        callLogin = ApiAdapter.getApiService().changePassword("change_password", newPassword, userSession.getUserID());

        callLogin.enqueue(new Callback<RequestSuccessModel>() {
            @Override
            public void onResponse(Call<RequestSuccessModel> call, Response<RequestSuccessModel> response) {

                Progress.stop();
                try {
                    //getting whole data from response
                    RequestSuccessModel forgetPwdModel = response.body();

                    String message = forgetPwdModel.getMsg();

                    if (forgetPwdModel.getStatus() == 1 && forgetPwdModel.getStatusCode() == 1) {

                        showYesNoDialog(message);

                    } else {
                        SnackNotify.showMessage(message, coordinatorLayout);
                    }
                } catch (NullPointerException e) {
                    SnackNotify.showMessage(getString(R.string.server_error), coordinatorLayout);
                }
            }

            @Override
            public void onFailure(Call<RequestSuccessModel> call, Throwable t) {
                Progress.stop();
                SnackNotify.showMessage(getString(R.string.server_error), coordinatorLayout);

            }

        });
    }


    public boolean validateCredential(String newpasseord, String confirmpassword) {

        if (TextUtils.isEmpty(newpasseord)) {
            Utils.showError(input_new_password, getString(R.string.empty_new_password), this);
            return false;
        } else if (TextUtils.isEmpty(confirmpassword)) {
            Utils.showError(input_confirm_password, getString(R.string.empty_confirm_password), this);
            return false;
        } else if (!newpasseord.equals(confirmpassword)) {
            //   Utils.showError(input_confirm_password, getString(R.string.empty_confirm_password), this);
            SnackNotify.showMessage(getString(R.string.password_not_match), coordinatorLayout);
            return false;
        } else {
            return true;
        }
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
            builder.setMessage(message).setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setFont() {
        input_confirm_password.setTypeface(Typeface.createFromAsset(getAssets(), AppConstant.AVENIRNEXT_MEDIUM));
        input_new_password.setTypeface(Typeface.createFromAsset(getAssets(), AppConstant.AVENIRNEXT_MEDIUM));
        btn_save.setTypeface(Typeface.createFromAsset(getAssets(), AppConstant.AVENIRNEXT_DEMIBOLD));
        txt_header_title.setTypeface(Typeface.createFromAsset(getAssets(), AppConstant.AVENIRNEXT_DEMIBOLD));
    }
}
