package com.innoapps.eventmanagement.common.navigation;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.innoapps.eventmanagement.R;
import com.innoapps.eventmanagement.common.aboutus.AboutUsActivity;
import com.innoapps.eventmanagement.common.agenda.AgendaActivity;
import com.innoapps.eventmanagement.common.agenda.adapter.AgendaAdapter;
import com.innoapps.eventmanagement.common.pollsawards.PollsAwardsActivity;
import com.innoapps.eventmanagement.common.refelentry.RefelEntryActivity;
import com.innoapps.eventmanagement.common.speakers.SpeakersActivity;
import com.innoapps.eventmanagement.common.sponsors.SponsorsActivity;
import com.innoapps.eventmanagement.common.utils.AppConstant;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by ankit on 3/16/2017.
 */

public class ANCE2017Fragment extends Fragment {

    @InjectView(R.id.about_cardView)
    CardView about_cardView;

    @InjectView(R.id.agenda_cardView)
    CardView agenda_cardView;

    @InjectView(R.id.speakers_cardView)
    CardView speakers_cardView;

    @InjectView(R.id.polls_cardView)
    CardView polls_cardView;

    @InjectView(R.id.spouncher_cardView)
    CardView spouncher_cardView;

    @InjectView(R.id.rafle_cardView)
    CardView rafle_cardView;

    @InjectView(R.id.txt_about)
    TextView txt_about;

    @InjectView(R.id.agenda_about)
    TextView agenda_about;

    @InjectView(R.id.speakers_about)
    TextView speakers_about;

    @InjectView(R.id.polls_about)
    TextView polls_about;

    @InjectView(R.id.txt_spouncher)
    TextView txt_spouncher;

    @InjectView(R.id.txt_rafle)
    TextView txt_rafle;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ance_fragment, container, false);

        ButterKnife.inject(this, view);
        setFont();
        return view;

    }


    @OnClick(R.id.speakers_cardView)
    public void speakers() {

        Intent intent = new Intent(getActivity(), SpeakersActivity.class);
        // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    @OnClick(R.id.about_cardView)
    public void aboutUs() {

        Intent intent = new Intent(getActivity(), AboutUsActivity.class);
        startActivity(intent);

    }

    @OnClick(R.id.agenda_cardView)
    public void agenda() {
        Intent intent = new Intent(getActivity(), AgendaActivity.class);
        // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    @OnClick(R.id.polls_cardView)
    public void awards() {
      Intent intent = new Intent(getActivity(), PollsAwardsActivity.class);
        startActivity(intent);

    }

    @OnClick(R.id.spouncher_cardView)
    public void spouncher() {
        Intent intent = new Intent(getActivity(), SponsorsActivity.class);
        startActivity(intent);

    }

    @OnClick(R.id.rafle_cardView)
    public void rafle() {

        Intent intent = new Intent(getActivity(), RefelEntryActivity.class);
        startActivity(intent);

    }


    private void setFont() {
        txt_about.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), AppConstant.AVENIRNEXT_MEDIUM));
        agenda_about.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), AppConstant.AVENIRNEXT_MEDIUM));
        speakers_about.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), AppConstant.AVENIRNEXT_MEDIUM));
        polls_about.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), AppConstant.AVENIRNEXT_MEDIUM));
        txt_spouncher.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), AppConstant.AVENIRNEXT_MEDIUM));
        txt_rafle.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), AppConstant.AVENIRNEXT_MEDIUM));

    }


}
