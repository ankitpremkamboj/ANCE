package com.innoapps.eventmanagement.common.events;

import android.graphics.Typeface;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.innoapps.eventmanagement.R;
import com.innoapps.eventmanagement.common.utils.GlideCircleTransform;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class EventUserDescriptionActivity extends AppCompatActivity {

    @InjectView(R.id.input_name)
    EditText input_name;
    @InjectView(R.id.input_email)
    EditText input_email;
    @InjectView(R.id.input_mobile)
    EditText input_mobile;
    @InjectView(R.id.input_company)
    EditText input_company;

    @InjectView(R.id.back)
    ImageView back;

    @InjectView(R.id.txt_title)
    TextView txt_title;

    @InjectView(R.id.ivUserImage)
    ImageView ivUserImage;
    String name, email, mobile, image, title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_user_description);
        ButterKnife.inject(this);
        setFonts();

        getIntentData();
    }

    private void getIntentData() {
        try {
            if (getIntent() != null) {
                name = getIntent().getStringExtra("NAME");
                email = getIntent().getStringExtra("EMAIL");
                mobile = getIntent().getStringExtra("MOBILE");
                title = getIntent().getStringExtra("TITLE");
                image = getIntent().getStringExtra("IMAGE");


                if (!name.isEmpty() && name != null) {
                    input_name.setText(name);
                } else {
                    input_name.setText("N/A");
                }
                if (!email.isEmpty() && email != null) {
                    input_email.setText(email);
                } else {
                    input_email.setText("N/A");
                }

                if (!mobile.isEmpty() && mobile != null) {
                    input_mobile.setText(mobile);
                } else {
                    input_mobile.setText("N/A");
                }

                if (!title.isEmpty() && title != null) {
                    txt_title.setText(title);
                } else {
                    txt_title.setText("N/A");
                }


                Picasso.with(this)
                        .load(image)
                        .placeholder(R.mipmap.ic_default_profile) // optional
                        .error(R.mipmap.ic_default_profile)
                        .into(ivUserImage);
               /* Glide.with(this)
                        .load(image)
                        .transform(new GlideCircleTransform(this))
                        .placeholder(R.mipmap.ic_default_profile)
                        .centerCrop()
                        .error(R.mipmap.ic_default_profile)
                        // .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(ivUserImage);*/

            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.back)
    public void back() {
        finish();
    }

    private void setFonts() {

        txt_title.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/" + "AvenirNext-DemiBold" + ".ttf"));
        input_name.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/" + "AvenirNext-Medium" + ".ttf"));
        input_email.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/" + "AvenirNext-Medium" + ".ttf"));
        input_mobile.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/" + "AvenirNext-Medium" + ".ttf"));
        input_company.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/" + "AvenirNext-Medium" + ".ttf"));


    }


}
