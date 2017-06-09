package com.innoapps.eventmanagement.common.refelentry.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.innoapps.eventmanagement.R;
import com.innoapps.eventmanagement.common.agenda.adapter.AgendaAdapter;
import com.innoapps.eventmanagement.common.agenda.model.AgendaModel;
import com.innoapps.eventmanagement.common.refelentry.model.RefelEntryModel;
import com.innoapps.eventmanagement.common.utils.AppConstant;
import com.innoapps.eventmanagement.common.utils.GlideCircleTransform;

import java.util.ArrayList;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by ankit on 3/17/2017.
 */

public class RefelEntryAdapter extends RecyclerView.Adapter<RefelEntryAdapter.Holder> {

    ArrayList<RefelEntryModel.WinnerList> winnerLists;
    Context context;

    public RefelEntryAdapter(Context context, ArrayList<RefelEntryModel.WinnerList> winnerLists) {
        this.context = context;
        this.winnerLists = winnerLists;
    }

    public class Holder extends RecyclerView.ViewHolder {


        @InjectView(R.id.winner_name)
        TextView winner_name;

        @InjectView(R.id.winner_title)
        TextView winner_title;

        @InjectView(R.id.txt_first_char)
        TextView txt_first_char;

        @InjectView(R.id.txt_rated)
        TextView txt_rated;

        @InjectView(R.id.user_images)
        ImageView user_images;


        public Holder(View itemView) {
            super(itemView);

            ButterKnife.inject(this, itemView);
        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.refel_entry_items, null);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        setFont(holder);

        RefelEntryModel.WinnerList winnerListData = winnerLists.get(position);


        if (!winnerListData.getUserName().isEmpty() && winnerListData.getUserName() != null) {
            try {
                String lastName = winnerListData.getUserName().substring(winnerListData.getUserName().indexOf(" ") + 1);
                String lastaps = lastName.substring(0, 1).toUpperCase() + lastName.substring(1);

                String lastLetter = String.valueOf(lastaps.charAt(0));

                String firstcap = winnerListData.getUserName().substring(0, 1).toUpperCase() + winnerListData.getUserName().substring(1);

                String firstLetter = String.valueOf(firstcap.charAt(0));
                holder.txt_first_char.setText(firstLetter + lastLetter);
                holder.txt_first_char.setTextColor(getRandomColor());
            } catch (StringIndexOutOfBoundsException e) {
                e.printStackTrace();
                String firstcap = winnerListData.getUserName().substring(0, 1).toUpperCase() + winnerListData.getUserName().substring(1);

                String firstLetter = String.valueOf(firstcap.charAt(0));
                holder.txt_first_char.setText(firstLetter + firstLetter);
                holder.txt_first_char.setTextColor(getRandomColor());
            }

        } else {
            holder.txt_first_char.setText("NA");
            holder.txt_first_char.setTextColor(getRandomColor());
        }


        if (!winnerListData.getUserName().isEmpty() && winnerListData.getUserName() != null) {
            // holder.winner_name.setText(winnerListData.getUserName());
            String str = winnerListData.getUserName();
            String cap = str.substring(0, 1).toUpperCase() + str.substring(1);
            holder.winner_name.setText(cap);
        } else {
            holder.winner_name.setText("N/A");
        }

        if (!winnerListData.getPrizeName().isEmpty() && winnerListData.getPrizeName() != null) {
            holder.winner_title.setText(winnerListData.getPrizeName());
        } else {
            holder.winner_title.setText("N/A");
        }


        if (!winnerListData.getResult().isEmpty() && winnerListData.getResult() != null) {

            holder.txt_rated.setText(winnerListData.getResult());

            holder.txt_rated.setTextColor(getRandomColor());
        }


      /*  if (!winnerListData.getUserImage().isEmpty() && winnerListData.getUserImage() != null) {
            Glide.with(context)
                    .load(winnerListData.getUserImage())
                    .transform(new GlideCircleTransform(context))
                    .placeholder(R.mipmap.ic_default_profile)
                    .error(R.mipmap.ic_default_profile)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(holder.user_image);

        }*/
    }

    @Override
    public int getItemCount() {

        return winnerLists.size();
    }


    private void setFont(Holder holder) {
        holder.winner_name.setTypeface(Typeface.createFromAsset(context.getAssets(), AppConstant.AVENIRNEXT_MEDIUM));
        holder.winner_title.setTypeface(Typeface.createFromAsset(context.getAssets(), AppConstant.AVENIRNEXT_DEMIBOLD));
        holder.txt_rated.setTypeface(Typeface.createFromAsset(context.getAssets(), AppConstant.AVENIRNEXT_MEDIUM));
    }

    public int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }
}