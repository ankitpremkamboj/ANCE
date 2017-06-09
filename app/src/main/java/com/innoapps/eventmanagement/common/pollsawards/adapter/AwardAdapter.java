package com.innoapps.eventmanagement.common.pollsawards.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.innoapps.eventmanagement.R;
import com.innoapps.eventmanagement.common.blogs.model.BlogsModel;
import com.innoapps.eventmanagement.common.blogs.presenter.BlogPresenterImpl;
import com.innoapps.eventmanagement.common.blogs.view.BlogsView;
import com.innoapps.eventmanagement.common.pollsawards.model.AwardModel;
import com.innoapps.eventmanagement.common.utils.AppConstant;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by ankit on 3/23/2017.
 */

public class AwardAdapter extends RecyclerView.Adapter<AwardAdapter.Holder> {

    Context context;
    ArrayList<AwardModel.AwardList> awardLists;


    public AwardAdapter(Activity context, ArrayList<AwardModel.AwardList> awardLists) {
        this.context = context;
        this.awardLists = awardLists;

    }

    public class Holder extends RecyclerView.ViewHolder {


        @InjectView(R.id.certificate_img)
        ImageView certificate_img;

        @InjectView(R.id.award_name)
        TextView award_name;

        @InjectView(R.id.user_name)
        TextView user_name;

        @InjectView(R.id.date_time)
        TextView date_time;

        @InjectView(R.id.description)
        TextView description;


        public Holder(View itemView) {
            super(itemView);

            ButterKnife.inject(this, itemView);
        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.award_item, null);

        return new Holder(view);
    }


    @Override
    public void onBindViewHolder(Holder holder, int position) {

        setFont(holder);


        AwardModel.AwardList awardList = awardLists.get(position);
        String datetime = awardList.getAddedDate();
        String description = awardList.getAwardDesc();
        String awardName = awardList.getAwardName();

        if (!datetime.isEmpty() && datetime != null) {
            holder.date_time.setText(datetime);
        } else {
            holder.date_time.setText("N/A");

        }

        if (!description.isEmpty() && description != null) {
            holder.description.setText(description);

        } else {
            holder.description.setText("N/A");

        }
        if (!awardName.isEmpty() && awardName != null) {
            holder.award_name.setText(awardName);
        } else {
            holder.award_name.setText("N/A");
        }

    }

    @Override
    public int getItemCount() {

        return awardLists.size();
    }


    private void setFont(Holder holder) {
        holder.award_name.setTypeface(Typeface.createFromAsset(context.getAssets(), AppConstant.AVENIRNEXT_DEMIBOLD));
        holder.user_name.setTypeface(Typeface.createFromAsset(context.getAssets(), AppConstant.AVENIRNEXT_MEDIUM));
        holder.date_time.setTypeface(Typeface.createFromAsset(context.getAssets(), AppConstant.AVENIRNEXT_MEDIUM));
        holder.description.setTypeface(Typeface.createFromAsset(context.getAssets(), AppConstant.AVENIRNEXT_MEDIUM));

    }

}
