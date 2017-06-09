package com.innoapps.eventmanagement.common.sponsors;

import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.innoapps.eventmanagement.R;
import com.innoapps.eventmanagement.common.speakers.SpeakersActivity;
import com.innoapps.eventmanagement.common.speakers.adapter.SpeakerAdapter;
import com.innoapps.eventmanagement.common.sponsors.adapter.SponsorsAdapter;
import com.innoapps.eventmanagement.common.sponsors.model.SponsorsModel;
import com.innoapps.eventmanagement.common.sponsors.presenter.SponsorsPresenter;
import com.innoapps.eventmanagement.common.sponsors.presenter.SponsorsPresenterImpl;
import com.innoapps.eventmanagement.common.sponsors.view.SponsorsView;
import com.innoapps.eventmanagement.common.utils.DividerItemDecoration;
import com.innoapps.eventmanagement.common.utils.SnackNotify;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class SponsorsActivity extends AppCompatActivity implements SponsorsView {

    @InjectView(R.id.coordinateLayout)
    CoordinatorLayout coordinateLayout;
    @InjectView(R.id.recyclerViewFriend)
    RecyclerView recyclerViewFriend;
    @InjectView(R.id.img_back)
    ImageView img_back;
    @InjectView(R.id.txt_header_title)
    TextView txt_header_title;

    @InjectView(R.id.img_search)
    ImageView img_search;

    SponsorsPresenter sponsorsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsors);
        ButterKnife.inject(this);
        img_search.setVisibility(View.VISIBLE);
        txt_header_title.setText("Exhibitors/Sponsors");
        sponsorsPresenter = new SponsorsPresenterImpl();
        sponsorsPresenter.callSponsorsList(this, this);

    }

    @Override
    public void getSponsorsSuccessful(ArrayList<SponsorsModel.SponserList> sponserLists) {

        SponsorsAdapter friendAdapter = new SponsorsAdapter(SponsorsActivity.this, sponserLists);
        recyclerViewFriend.addItemDecoration(new DividerItemDecoration(SponsorsActivity.this, DividerItemDecoration.VERTICAL_LIST));
        recyclerViewFriend.setLayoutManager(new LinearLayoutManager(SponsorsActivity.this));
        recyclerViewFriend.setItemAnimator(new DefaultItemAnimator());
        recyclerViewFriend.setAdapter(friendAdapter);
    }

    @Override
    public void getSponsorsUnSuccessful(String message) {
        SnackNotify.showMessage(message, coordinateLayout);
    }

    @Override
    public void SponsorsInternetError() {

    }

    @OnClick(R.id.img_back)
    public void back() {
        finish();
    }
}
