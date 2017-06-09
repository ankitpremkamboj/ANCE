package com.innoapps.eventmanagement.common.agenda.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.innoapps.eventmanagement.R;
import com.innoapps.eventmanagement.common.agenda.model.AgendaModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ankit on 3/17/2017.
 */

public class SpinAdapter extends ArrayAdapter<AgendaModel.AgendaList> {

    Context context;
    ArrayList<AgendaModel.AgendaList> agendaLists;

    public SpinAdapter(Context context, int textViewResourceId, ArrayList<AgendaModel.AgendaList> agendaLists) {
        super(context, textViewResourceId, agendaLists);
        this.context = context;
        this.agendaLists = agendaLists;
    }


    public int getCount() {
        return agendaLists.size();
    }

    public long getItemId(int position) {
        return position;
    }

    public AgendaModel.AgendaList getItem(int position) {
        return agendaLists.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        TextView label = new TextView(context);
        label.setTextColor(Color.WHITE);
        label.setText(dateFormate(agendaLists.get(position).getDate()));
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setPadding(80, 20, 80, 40);
        String datevalue = agendaLists.get(position).getDate();
        label.setText(dateFormate(datevalue));
        return label;
    }

    public String dateFormate(String spdate) {
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = null;
        try {
            date = form.parse(spdate);
        } catch (ParseException e) {

            e.printStackTrace();
        }
        SimpleDateFormat postFormater = new SimpleDateFormat("dd, MMM  yyyy");
        String newDateStr = postFormater.format(date);
        return newDateStr;
    }
}
