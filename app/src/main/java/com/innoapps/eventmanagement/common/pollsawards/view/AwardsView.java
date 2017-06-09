package com.innoapps.eventmanagement.common.pollsawards.view;

import com.innoapps.eventmanagement.common.blogs.model.BlogsModel;
import com.innoapps.eventmanagement.common.blogs.model.DetailsBlogsModel;
import com.innoapps.eventmanagement.common.pollsawards.model.AwardModel;

import java.util.ArrayList;

/**
 * Created by ankit on 3/23/2017.
 */

public interface AwardsView {

    void getAwardsSuccessfull(ArrayList<AwardModel.AwardList> awardLists);

    void getAwardsUnSuccessful(String message);

    void blogInternetError();
}
