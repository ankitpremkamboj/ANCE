package com.innoapps.eventmanagement.common.myfriends.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.innoapps.eventmanagement.R;
import com.innoapps.eventmanagement.common.friends.adapter.FriendAdapter;
import com.innoapps.eventmanagement.common.friends.model.FriendsModel;
import com.innoapps.eventmanagement.common.friends.presenter.FriendsPresenter;
import com.innoapps.eventmanagement.common.friends.view.FriendsView;
import com.innoapps.eventmanagement.common.myfriendevent.MyFriendEventActivity;
import com.innoapps.eventmanagement.common.myfriends.model.MyFriendsModel;
import com.innoapps.eventmanagement.common.myfriends.presenter.MyFriendsPresenter;
import com.innoapps.eventmanagement.common.myfriends.view.MyFriendsView;
import com.innoapps.eventmanagement.common.utils.AppConstant;
import com.innoapps.eventmanagement.common.utils.GlideCircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * Created by Ankit on 3/6/2017.
 */

public class MyFriendAdapter extends RecyclerView.Adapter<MyFriendAdapter.Holder> implements MyFriendsView {

    ArrayList<MyFriendsModel.UserFriendList> userFriendLists;
    Context context;
    MyFriendsPresenter friendsPresenter;
    String active_value;
    Holder holder;

    public MyFriendAdapter(Context context, ArrayList<MyFriendsModel.UserFriendList> userFriendLists, MyFriendsPresenter friendsPresenter) {
        this.context = context;
        this.userFriendLists = userFriendLists;
        this.friendsPresenter = friendsPresenter;
    }


    public class Holder extends RecyclerView.ViewHolder {

        @InjectView(R.id.user_image)
        ImageView user_image;

        /* @InjectView(R.id.addremove)
         TextView addremove;
 */
        @InjectView(R.id.email)
        TextView email;

        @InjectView(R.id.tagline)
        TextView tagline;

        @InjectView(R.id.user_name)
        TextView user_name;

        @InjectView(R.id.layout_addremove)
        LinearLayout layout_addremove;

        @InjectView(R.id.txt_friend)
        TextView txt_friend;

        @InjectView(R.id.txt_add_friend)
        TextView txt_add_friend;

        @InjectView(R.id.rl1)
        RelativeLayout rl1;


        public Holder(View itemView) {
            super(itemView);

            ButterKnife.inject(this, itemView);
        }
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_friends_items, null);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {
        this.holder = holder;
        setFont(holder);
        // setTextColor(holder);
        holder.rl1.setTag(position);

        holder.layout_addremove.setTag(position);
        holder.txt_add_friend.setTag(position);
        holder.txt_friend.setTag(position);


        MyFriendsModel.UserFriendList userFriendList = userFriendLists.get(position);


        if (!userFriendList.getUserFriendPhone().equalsIgnoreCase("")) {
            holder.tagline.setText(userFriendList.getUserFriendPhone());
        } else {
            holder.tagline.setText("N/A");
        }


        if (!userFriendList.getUserFriendEmail().equalsIgnoreCase("")) {
            holder.email.setText(userFriendList.getUserFriendEmail());
        } else {
            holder.email.setText("N/A");
        }

        holder.user_name.setText(userFriendList.getUserFriendName());

        if (userFriendList.getStatus().equalsIgnoreCase("0")) {
            holder.txt_add_friend.setText("ADD");
            holder.txt_add_friend.setTextColor(ContextCompat.getColor(context, R.color.add_color));
            holder.txt_add_friend.setVisibility(View.VISIBLE);
            holder.txt_friend.setText("REMOVE");
            holder.txt_friend.setTextColor(ContextCompat.getColor(context, R.color.remove_color));
            active_value = "1";
        } else if (userFriendList.getStatus().equalsIgnoreCase("1")) {
            holder.txt_friend.setText("FRIEND");
            holder.txt_friend.setTextColor(ContextCompat.getColor(context, R.color.add_color));
            holder.txt_add_friend.setVisibility(View.GONE);
            active_value = "1";
        } /*else if (userFriendList.getStatus().equalsIgnoreCase("")) {
            holder.txt_friend.setText("ADD");
            holder.txt_friend.setTextColor(ContextCompat.getColor(context, R.color.add_color));

            if (holder.txt_friend.getText().toString().equalsIgnoreCase("")) {
                active_value = "2";
            }
        }*/


        // initially setting company logo  (please change the url because i am just giving dummy url to upload image)
        if (!userFriendList.getUserFriendImage().isEmpty()) {
          /*  Picasso.with(context)
                    .load(userFriendList.getUserFriendImage())
                    .placeholder(R.mipmap.ic_default_profile) // optional
                    .error(R.mipmap.ic_default_profile)         // optional
                    .into(holder.user_image);*/

            Glide.with(context)
                    .load(userFriendList.getUserFriendImage())
                    .transform(new GlideCircleTransform(context))
                    .placeholder(R.mipmap.ic_default_profile)
                    .error(R.mipmap.ic_default_profile)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.user_image);

        }

        holder.txt_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = Integer.parseInt(view.getTag().toString());
                MyFriendsModel.UserFriendList userList = userFriendLists.get(tag);

                if (holder.txt_friend.getText().toString().equalsIgnoreCase("REMOVE")) {
                    active_value = "2";
                    friendsPresenter.callSendFriendRequest(userList.getUserFriendId(), active_value);
                    userFriendLists.remove(tag);
                    notifyDataSetChanged();
                }

                // holder.addremove.setText("PENDING");
                // holder.addremove.setTextColor(ContextCompat.getColor(context, R.color.color_pending));
            }
        });

        holder.txt_add_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = Integer.parseInt(view.getTag().toString());
                MyFriendsModel.UserFriendList userList = userFriendLists.get(tag);
                friendsPresenter.callSendFriendRequest(userList.getUserFriendId(), active_value);
                holder.txt_friend.setText("FRIEND");
                holder.txt_friend.setTextColor(ContextCompat.getColor(context, R.color.add_color));
                holder.txt_add_friend.setVisibility(View.GONE);
            }
        });

        holder.rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = Integer.parseInt(view.getTag().toString());
                MyFriendsModel.UserFriendList userList = userFriendLists.get(tag);
                Intent intent = new Intent(context, MyFriendEventActivity.class);
                intent.putExtra("FriendID", userList.getUserFriendId());

                context.startActivity(intent);
            }
        });
    }

    private void setFont(Holder holder) {


        //   holder.addremove.setTypeface(Typeface.createFromAsset(context.getAssets(), AppConstant.AVENIRNEXT_DEMIBOLD));
        holder.email.setTypeface(Typeface.createFromAsset(context.getAssets(), AppConstant.AVENIRNEXT_DEMIBOLD));
        holder.tagline.setTypeface(Typeface.createFromAsset(context.getAssets(), AppConstant.AVENIRNEXT_DEMIBOLD));
        holder.user_name.setTypeface(Typeface.createFromAsset(context.getAssets(), AppConstant.AVENIRNEXT_MEDIUM));

        holder.txt_add_friend.setTypeface(Typeface.createFromAsset(context.getAssets(), AppConstant.AVENIRNEXT_DEMIBOLD));
        holder.txt_friend.setTypeface(Typeface.createFromAsset(context.getAssets(), AppConstant.AVENIRNEXT_DEMIBOLD));

    }

    @Override
    public int getItemCount() {
        return userFriendLists.size();
    }


    @Override
    public void getFriendsSuccessfull(ArrayList<MyFriendsModel.UserFriendList> userFriendLists) {

    }

    @Override
    public void getFriendsUnSuccessful(String message) {

    }

    @Override
    public void FriendsInternetError() {

    }


    @Override
    public void getFriendsRequestSuccessful(String message) {
        holder.txt_friend.setText("FRIEND");
        holder.txt_friend.setTextColor(ContextCompat.getColor(context, R.color.add_color));
        holder.txt_add_friend.setVisibility(View.GONE);
        //  notifyDataSetChanged();
    }

}
