package com.innoapps.eventmanagement.common.sponsors.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.innoapps.eventmanagement.R;
import com.innoapps.eventmanagement.common.speakers.adapter.SpeakerAdapter;
import com.innoapps.eventmanagement.common.speakers.model.SpeakerModel;
import com.innoapps.eventmanagement.common.sponsors.model.SponsorsModel;
import com.innoapps.eventmanagement.common.utils.AppConstant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by ankit on 3/16/2017.
 */

public class SponsorsAdapter extends RecyclerView.Adapter<SponsorsAdapter.Holder> {

    ArrayList<SponsorsModel.SponserList> sponserLists;
    Context context;

    public SponsorsAdapter(Context context, ArrayList<SponsorsModel.SponserList> sponserLists) {
        this.context = context;
        this.sponserLists = sponserLists;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sponsors_items, null);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        setFont(holder);

        SponsorsModel.SponserList sponserList = sponserLists.get(position);


        if (!sponserList.getSponserTagline().isEmpty() && sponserList.getSponserTagline() != null) {
            holder.sponsor_tagline.setText(sponserList.getSponserTagline());
        } else {
            holder.sponsor_tagline.setText("N/A");
        }

        if (!sponserList.getSponserName().isEmpty() && sponserList.getSponserName() != null) {
            String str = sponserList.getSponserName();
            String cap = str.substring(0, 1).toUpperCase() + str.substring(1);
            holder.sponsor_name.setText(cap);
        } else {
            holder.sponsor_name.setText("N/A");
        }

        if (!sponserList.getSponserPhoto().isEmpty() && sponserList.getSponserPhoto() != null) {
            Picasso.with(context)
                    .load(sponserList.getSponserPhoto())
                    .placeholder(R.mipmap.event_default) // optional
                    .error(R.mipmap.event_default)         // optional
                    .into(holder.sponsor_image);
        }


    }

    @Override
    public int getItemCount() {
        return sponserLists.size();
    }


    public class Holder extends RecyclerView.ViewHolder {

        @InjectView(R.id.sponsor_image)
        ImageView sponsor_image;

        @InjectView(R.id.sponsor_name)
        TextView sponsor_name;

        @InjectView(R.id.sponsor_tagline)
        TextView sponsor_tagline;


        public Holder(View itemView) {
            super(itemView);

            ButterKnife.inject(this, itemView);
        }
    }

    private void setFont(Holder holder) {
        holder.sponsor_tagline.setTypeface(Typeface.createFromAsset(context.getAssets(), AppConstant.AVENIRNEXT_DEMIBOLD));
        holder.sponsor_name.setTypeface(Typeface.createFromAsset(context.getAssets(), AppConstant.AVENIRNEXT_MEDIUM));


    }

}
