package com.innoapps.eventmanagement.common.photofeed.presenter;

import android.app.Activity;

import com.innoapps.eventmanagement.common.myfriendevent.view.MyFriendEventView;
import com.innoapps.eventmanagement.common.photofeed.view.PhotoFeedView;

/**
 * Created by ankit on 4/4/2017.
 */

public interface PhotoFeedPresenter {

    void callGetPhotoFeedList(PhotoFeedView photoFeedView, Activity activity);

    void callUploadPhotoFeedList(PhotoFeedView photoFeedView, Activity activity, String imagePath);


}

