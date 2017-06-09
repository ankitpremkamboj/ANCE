package com.innoapps.eventmanagement.common.agenda.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.innoapps.eventmanagement.R;
import com.innoapps.eventmanagement.common.agenda.model.AgendaModel;
import com.innoapps.eventmanagement.common.utils.AppConstant;

import java.util.ArrayList;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by ankit on 3/16/2017.
 */

public class AgendaAdapter extends RecyclerView.Adapter<AgendaAdapter.Holder> {

    ArrayList<AgendaModel.AgendaList> agendaLists;
    ArrayList<AgendaModel.AgendaList.Datum> datumArrayList;
    Context context;

    public AgendaAdapter(Context context, ArrayList<AgendaModel.AgendaList> agendaLists, ArrayList<AgendaModel.AgendaList.Datum> datumArrayList) {
        this.context = context;
        this.agendaLists = agendaLists;
        this.datumArrayList = datumArrayList;
    }

    public class Holder extends RecyclerView.ViewHolder {


        @InjectView(R.id.agenda_name)
        TextView agenda_name;

        @InjectView(R.id.agenda_date_time)
        TextView agenda_date_time;

        @InjectView(R.id.txt_first_char)
        TextView txt_first_char;


        public Holder(View itemView) {
            super(itemView);

            ButterKnife.inject(this, itemView);
        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.agenda_items, null);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        setFont(holder);


        AgendaModel.AgendaList.Datum datum = datumArrayList.get(position);


        if (!datum.getAgendaTitle().isEmpty() && datum.getAgendaTitle() != null) {

            String str = datum.getAgendaTitle();
            String cap = str.substring(0, 1).toUpperCase() + str.substring(1);

            String firstLetter = String.valueOf(cap.charAt(0));
            holder.txt_first_char.setText(firstLetter);
            holder.txt_first_char.setTextColor(getRandomColor());
        } else {
            holder.txt_first_char.setText("N");
            holder.txt_first_char.setTextColor(getRandomColor());
        }

        if (!datum.getAgendaTitle().isEmpty() && datum.getAgendaTitle() != null) {
          //  holder.agenda_name.setText(datum.getAgendaTitle());
            String str = datum.getAgendaTitle();
            String cap = str.substring(0, 1).toUpperCase() + str.substring(1);
            holder.agenda_name.setText(cap);
        } else {
            holder.agenda_name.setText("N/A");
        }

        if (!datum.getToTime().isEmpty() && datum.getToTime() != null) {
            holder.agenda_date_time.setText(datum.getFromTime() + " to " + datum.getToTime());
        } else {
            holder.agenda_date_time.setText("N/A");
        }

    }

    @Override
    public int getItemCount() {

        return datumArrayList.size();
    }


    private void setFont(Holder holder) {
        holder.agenda_date_time.setTypeface(Typeface.createFromAsset(context.getAssets(), AppConstant.AVENIRNEXT_DEMIBOLD));
        holder.agenda_name.setTypeface(Typeface.createFromAsset(context.getAssets(), AppConstant.AVENIRNEXT_MEDIUM));
        holder.txt_first_char.setTypeface(Typeface.createFromAsset(context.getAssets(), AppConstant.AVENIRNEXT_MEDIUM));
    }

    public int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }
}