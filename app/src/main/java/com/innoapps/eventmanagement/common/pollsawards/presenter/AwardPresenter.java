package com.innoapps.eventmanagement.common.pollsawards.presenter;

import android.app.Activity;

import com.innoapps.eventmanagement.common.pollsawards.view.AwardsView;

/**
 * Created by ankit on 3/23/2017.
 */

public interface AwardPresenter {

    void callGetAwardList(AwardsView blogsView, Activity activity);
}
