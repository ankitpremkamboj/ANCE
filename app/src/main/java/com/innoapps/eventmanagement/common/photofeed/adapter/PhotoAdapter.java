package com.innoapps.eventmanagement.common.photofeed.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.innoapps.eventmanagement.R;
import com.innoapps.eventmanagement.common.photofeed.model.PhotoFeedModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import java.util.ArrayList;

/**
 * Created by ankit on 4/4/2017.
 */

public class PhotoAdapter extends BaseAdapter {
    ArrayList<PhotoFeedModel.GalleryList> galleryLists;
    Context context;
    DisplayImageOptions options;

    public  PhotoAdapter(Context context, ArrayList<PhotoFeedModel.GalleryList> galleryLists) {
        this.galleryLists = galleryLists;
        this.context = context;

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.event_default)
                .showImageForEmptyUri(R.mipmap.event_default)
                .showImageOnFail(R.mipmap.event_default)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();
    }


    @Override
    public int getCount() {
        return galleryLists.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View contentView, ViewGroup parent) {
        LayoutInflater inflator;
        ViewHolder viewHolder;
        if (contentView == null) {
            inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            contentView = inflator.inflate(R.layout.photofeed_item_layout, null);
            viewHolder = new ViewHolder(contentView);
            contentView.setTag(viewHolder);
        } else {

            viewHolder = (ViewHolder) contentView.getTag();
        }
        populateData(viewHolder, position);

        return contentView;


    }

    public void populateData(ViewHolder viewHolder, final int pos) {
        try {
            final String url = galleryLists.get(pos).getGalleryImage();// listData.get(pos)._photo_link;
            if (url != null & !url.isEmpty()) {
            ImageLoader imageLoader = ImageLoader.getInstance(); // Get singleton instance
                imageLoader.displayImage(url, viewHolder.iv_NewsImage, options);
                imageLoader.loadImage(url, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        // Do whatever you want with Bitmap
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class ViewHolder {
        private ImageView iv_NewsImage;

        ViewHolder(View view) {

            iv_NewsImage = (ImageView) view.findViewById(R.id.image);

        }
    }
}
