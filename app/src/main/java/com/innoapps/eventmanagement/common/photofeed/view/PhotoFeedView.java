package com.innoapps.eventmanagement.common.photofeed.view;

import com.innoapps.eventmanagement.common.myfriends.model.MyFriendsModel;
import com.innoapps.eventmanagement.common.photofeed.model.PhotoFeedModel;

import java.util.ArrayList;

/**
 * Created by ankit on 4/4/2017.
 */

public interface PhotoFeedView {

    void getPhotoFeedSuccessfull(ArrayList<PhotoFeedModel.GalleryList> galleryLists);

    void getPhotoFeedUnSuccessful(String message);
    void getPhotoFeedUploadSuccessful(String message);

    void PhotoFeedInternetError();
}
