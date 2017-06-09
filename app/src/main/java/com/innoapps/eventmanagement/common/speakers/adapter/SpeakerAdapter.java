package com.innoapps.eventmanagement.common.speakers.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.innoapps.eventmanagement.R;
import com.innoapps.eventmanagement.common.friends.model.FriendsModel;
import com.innoapps.eventmanagement.common.speakers.model.SpeakerModel;
import com.innoapps.eventmanagement.common.utils.AppConstant;
import com.innoapps.eventmanagement.common.utils.GlideCircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Ankit on 3/6/2017.
 */

public class SpeakerAdapter extends RecyclerView.Adapter<SpeakerAdapter.Holder> {

    ArrayList<SpeakerModel.SpeakerList> userFriendLists;
    Context context;

    public SpeakerAdapter(Context context, ArrayList<SpeakerModel.SpeakerList> userFriendLists) {
        this.context = context;
        this.userFriendLists = userFriendLists;
    }


    public class Holder extends RecyclerView.ViewHolder {

        @InjectView(R.id.user_image)
        ImageView user_image;

        @InjectView(R.id.addremove)
        TextView addremove;

        @InjectView(R.id.email)
        TextView email;

        @InjectView(R.id.tagline)
        TextView tagline;

        @InjectView(R.id.user_name)
        TextView user_name;


        public Holder(View itemView) {
            super(itemView);

            ButterKnife.inject(this, itemView);
        }
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.speaker_items, null);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        setFont(holder);
        // setTextColor(holder);

        //  holder.card_view.setTag(position);
        //  holder.frmDetails.setTag(position);


        SpeakerModel.SpeakerList userFriendList = userFriendLists.get(position);

       /* if (!userFriendList.getSpeakerEmail().isEmpty() && userFriendList.getSpeakerEmail() != null) {
            holder.email.setText(userFriendList.getSpeakerEmail());
        } else {
            holder.email.setText("N/A");
        }*/

        if (!userFriendList.getSpeakerTagline().isEmpty() && userFriendList.getSpeakerTagline() != null) {
            holder.tagline.setText(userFriendList.getSpeakerTagline());
        } else {
            holder.tagline.setText("N/A");
        }
        if (!userFriendList.getSpeakerName().isEmpty() && userFriendList.getSpeakerName() != null) {
            holder.user_name.setText(userFriendList.getSpeakerName());
        } else {
            holder.user_name.setText("N/A");
        }


        if (!userFriendList.getSpeakerPhoto().isEmpty() && userFriendList.getSpeakerPhoto() != null) {
            Glide.with(context)
                    .load(userFriendList.getSpeakerPhoto())
                    .transform(new GlideCircleTransform(context))
                    .placeholder(R.mipmap.ic_default_profile)
                    .error(R.mipmap.ic_default_profile)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(holder.user_image);

        }


    }

    private void setFont(Holder holder) {
        holder.addremove.setTypeface(Typeface.createFromAsset(context.getAssets(), AppConstant.AVENIRNEXT_DEMIBOLD));
        holder.email.setTypeface(Typeface.createFromAsset(context.getAssets(), AppConstant.AVENIRNEXT_DEMIBOLD));
        holder.tagline.setTypeface(Typeface.createFromAsset(context.getAssets(), AppConstant.AVENIRNEXT_DEMIBOLD));
        holder.user_name.setTypeface(Typeface.createFromAsset(context.getAssets(), AppConstant.AVENIRNEXT_MEDIUM));
    }

    @Override
    public int getItemCount() {
        return userFriendLists.size();
    }

}
