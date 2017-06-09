package com.innoapps.eventmanagement.common.aboutus;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.innoapps.eventmanagement.R;
import com.innoapps.eventmanagement.common.utils.AppConstant;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class AboutUsActivity extends AppCompatActivity {
    @InjectView(R.id.img_back)
    ImageView img_back;
    @InjectView(R.id.txt_header_title)
    TextView txt_header_title;
    @InjectView(R.id.txt_about_ance)
    TextView txt_about_ance;
    @InjectView(R.id.txt_about_ance_description)
    TextView txt_about_ance_description;
    @InjectView(R.id.txt_objective)
    TextView txt_objective;
    @InjectView(R.id.txt_objective_description)
    TextView txt_objective_description;
    @InjectView(R.id.txt_attend)
    TextView txt_attend;
    @InjectView(R.id.txt_attend_description)
    TextView txt_attend_description;
    @InjectView(R.id.txt_details_ance)
    TextView txt_details_ance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ButterKnife.inject(this);
        txt_header_title.setText("About Us");
        setFont();
    }

    @OnClick(R.id.img_back)
    public void back() {
        finish();
    }


    @OnClick(R.id.txt_details_ance)
    public void detailANCE() {
        String url = "http://www.asiannetworkers.com/about.html";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    private void setFont() {
        txt_about_ance.setTypeface(Typeface.createFromAsset(getAssets(), AppConstant.AVENIRNEXT_MEDIUM));
        txt_about_ance_description.setTypeface(Typeface.createFromAsset(getAssets(), AppConstant.AVENIRNEXT_MEDIUM));
        txt_objective.setTypeface(Typeface.createFromAsset(getAssets(), AppConstant.AVENIRNEXT_MEDIUM));
        txt_objective_description.setTypeface(Typeface.createFromAsset(getAssets(), AppConstant.AVENIRNEXT_MEDIUM));
        txt_attend.setTypeface(Typeface.createFromAsset(getAssets(), AppConstant.AVENIRNEXT_MEDIUM));
        txt_attend_description.setTypeface(Typeface.createFromAsset(getAssets(), AppConstant.AVENIRNEXT_MEDIUM));
        txt_header_title.setTypeface(Typeface.createFromAsset(getAssets(), AppConstant.AVENIRNEXT_MEDIUM));
        txt_details_ance.setTypeface(Typeface.createFromAsset(getAssets(), AppConstant.AVENIRNEXT_MEDIUM));
    }
}
