package com.innoapps.eventmanagement.common.pollsawards.fragment;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innoapps.eventmanagement.R;
import com.innoapps.eventmanagement.common.pollsawards.adapter.AwardAdapter;
import com.innoapps.eventmanagement.common.pollsawards.model.AwardModel;
import com.innoapps.eventmanagement.common.pollsawards.presenter.AwardPresenter;
import com.innoapps.eventmanagement.common.pollsawards.presenter.AwardsPresenterImpl;
import com.innoapps.eventmanagement.common.pollsawards.view.AwardsView;
import com.innoapps.eventmanagement.common.utils.DividerItemDecoration;
import com.innoapps.eventmanagement.common.utils.SnackNotify;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by ankit on 3/23/2017.
 */

public class PollsFragment extends Fragment implements AwardsView {
    @InjectView(R.id.coordinateLayout)
    CoordinatorLayout coordinateLayout;
    @InjectView(R.id.recyclerViewAward)
    RecyclerView recyclerViewAward;
    AwardPresenter awardPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.award_fragment, container, false);

        ButterKnife.inject(this, view);
        awardPresenter = new AwardsPresenterImpl();
        awardPresenter.callGetAwardList(this, getActivity());
        // setFont();
        return view;
    }


    @Override
    public void getAwardsSuccessfull(ArrayList<AwardModel.AwardList> awardLists) {

        AwardAdapter awardAdapter = new AwardAdapter(getActivity(), awardLists);
        recyclerViewAward.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recyclerViewAward.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewAward.setItemAnimator(new DefaultItemAnimator());
        recyclerViewAward.setHasFixedSize(true);
        recyclerViewAward.setItemViewCacheSize(20);
        recyclerViewAward.setDrawingCacheEnabled(true);
        recyclerViewAward.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerViewAward.setAdapter(awardAdapter);

    }

    @Override
    public void getAwardsUnSuccessful(String message) {
        SnackNotify.showMessage(message, coordinateLayout);
    }

    @Override
    public void blogInternetError() {

    }
}