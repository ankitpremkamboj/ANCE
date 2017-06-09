package com.innoapps.eventmanagement.common.friends.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.innoapps.eventmanagement.R;
import com.innoapps.eventmanagement.common.events.model.EventsModel;
import com.innoapps.eventmanagement.common.friends.model.FriendsModel;
import com.innoapps.eventmanagement.common.friends.model.RequestSuccessModel;
import com.innoapps.eventmanagement.common.friends.presenter.FriendsPresenter;
import com.innoapps.eventmanagement.common.friends.view.FriendsView;
import com.innoapps.eventmanagement.common.helper.Progress;
import com.innoapps.eventmanagement.common.requestresponse.ApiAdapter;
import com.innoapps.eventmanagement.common.utils.AppConstant;
import com.innoapps.eventmanagement.common.utils.GlideCircleTransform;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Ankit on 3/6/2017.
 */

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.Holder> implements FriendsView {

    ArrayList<FriendsModel.UserList> userFriendLists;
    Context context;
    FriendsPresenter friendsPresenter;
    String active_value;
    Holder holder;

    public FriendAdapter(Context context, ArrayList<FriendsModel.UserList> userFriendLists, FriendsPresenter friendsPresenter) {
        this.context = context;
        this.userFriendLists = userFriendLists;
        this.friendsPresenter = friendsPresenter;
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

        @InjectView(R.id.layout_addremove)
        LinearLayout layout_addremove;


        public Holder(View itemView) {
            super(itemView);

            ButterKnife.inject(this, itemView);
        }
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.friends_items, null);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        this.holder = holder;
        setFont(holder);
        // setTextColor(holder);

        holder.layout_addremove.setTag(position);
        //  holder.frmDetails.setTag(position);


        FriendsModel.UserList userFriendList = userFriendLists.get(position);


        if (!userFriendList.getUserPhone().equalsIgnoreCase("")) {
            holder.tagline.setText(userFriendList.getUserPhone());
        } else {
            holder.tagline.setText("N/A");
        }


        if (!userFriendList.getEmail().equalsIgnoreCase("")) {
            holder.email.setText(userFriendList.getEmail());
        } else {
            holder.email.setText("N/A");
        }

        holder.user_name.setText(userFriendList.getName());

        if (userFriendList.getStatus().equalsIgnoreCase("0")) {
            holder.addremove.setText("PENDING");
            holder.addremove.setTextColor(ContextCompat.getColor(context, R.color.color_pending));
            active_value = "0";
        } else if (userFriendList.getStatus().equalsIgnoreCase("1")) {
            holder.addremove.setText("REMOVE");
            holder.addremove.setTextColor(ContextCompat.getColor(context, R.color.remove_color));
            active_value = "1";
        } else if (userFriendList.getStatus().equalsIgnoreCase("")) {
            holder.addremove.setText("ADD");
            holder.addremove.setTextColor(ContextCompat.getColor(context, R.color.add_color));
            active_value = "0";
        }
       /* if (holder.addremove.getText().toString().equalsIgnoreCase("REMOVE")) {
            active_value = "2";
        }*/

/*
        if (userFriendList.getStatus().equalsIgnoreCase("0")) {
            holder.addremove.setText("REMOVE");
            holder.addremove.setTextColor(ContextCompat.getColor(context, R.color.remove_color));

        } else {
            holder.addremove.setText("ADD");
            holder.addremove.setTextColor(ContextCompat.getColor(context, R.color.add_color));
        }*/


        // initially setting company logo  (please change the url because i am just giving dummy url to upload image)
        if (!userFriendList.getUserPic().isEmpty()) {

            Glide.with(context)
                    .load(userFriendList.getUserPic())
                    .transform(new GlideCircleTransform(context))
                    .placeholder(R.mipmap.ic_default_profile)
                    .error(R.mipmap.ic_default_profile)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.user_image);
        }

        holder.layout_addremove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = Integer.parseInt(view.getTag().toString());
                FriendsModel.UserList userList = userFriendLists.get(tag);

                if (holder.addremove.getText().toString().equalsIgnoreCase("REMOVE")) {
                    active_value = "2";
                    friendsPresenter.callSendFriendRequest(userList.getUserId(), active_value, holder);
                    userFriendLists.remove(tag);
                    notifyDataSetChanged();
                } else {
                    friendsPresenter.callSendFriendRequest(userList.getUserId(), active_value, holder);
                    holder.addremove.setText("PENDING");
                    holder.addremove.setTextColor(ContextCompat.getColor(context, R.color.color_pending));

                }
            }
        });
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


    @Override
    public void getFriendsSuccessfull(ArrayList<FriendsModel.UserList> userFriendLists) {

    }

    @Override
    public void getFriendsUnSuccessful(String message) {

    }

    @Override
    public void FriendsInternetError() {

    }

    @Override
    public void getFriendsRequestSuccessful(String message, Holder holder) {

        // holder.addremove.setText("PENDING");
        // holder.addremove.setTextColor(ContextCompat.getColor(context, R.color.color_Gray));
    }

}
