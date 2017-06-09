package com.innoapps.eventmanagement.common.agenda;

import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.innoapps.eventmanagement.R;
import com.innoapps.eventmanagement.common.agenda.adapter.AgendaAdapter;
import com.innoapps.eventmanagement.common.agenda.adapter.SpinAdapter;
import com.innoapps.eventmanagement.common.agenda.model.AgendaModel;
import com.innoapps.eventmanagement.common.agenda.presenter.AgendaPresenter;
import com.innoapps.eventmanagement.common.agenda.presenter.AgendaPresenterImpl;
import com.innoapps.eventmanagement.common.agenda.view.AgendaView;
import com.innoapps.eventmanagement.common.sponsors.SponsorsActivity;
import com.innoapps.eventmanagement.common.sponsors.adapter.SponsorsAdapter;
import com.innoapps.eventmanagement.common.utils.DividerItemDecoration;
import com.innoapps.eventmanagement.common.utils.SnackNotify;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemSelected;

public class AgendaActivity extends AppCompatActivity implements AgendaView {

    @InjectView(R.id.coordinateLayout)
    CoordinatorLayout coordinateLayout;

    @InjectView(R.id.recyclerViewFriend)
    RecyclerView recyclerViewFriend;

    @InjectView(R.id.img_back)
    ImageView img_back;

    @InjectView(R.id.spinner)
    Spinner spinner;

    private SpinAdapter adapter;


    AgendaPresenter agendaPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
        ButterKnife.inject(this);
        agendaPresenter = new AgendaPresenterImpl();
        agendaPresenter.callAgendaList(this, this);


    }


    @Override
    public void getAgendaSuccessful(final ArrayList<AgendaModel.AgendaList> agendaLists) {
        ArrayList<AgendaModel.AgendaList.Datum> datumArrayList = agendaLists.get(0).getData();

        AgendaAdapter agendaAdapter = new AgendaAdapter(AgendaActivity.this, agendaLists, datumArrayList);
        recyclerViewFriend.addItemDecoration(new DividerItemDecoration(AgendaActivity.this, DividerItemDecoration.VERTICAL_LIST));
        recyclerViewFriend.setLayoutManager(new LinearLayoutManager(AgendaActivity.this));
        recyclerViewFriend.setItemAnimator(new DefaultItemAnimator());
        agendaAdapter.notifyDataSetChanged();
        recyclerViewFriend.setAdapter(agendaAdapter);


        adapter = new SpinAdapter(AgendaActivity.this, R.layout.simple_spinner_item, agendaLists);
        agendaAdapter.notifyDataSetChanged();
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {

                ArrayList<AgendaModel.AgendaList.Datum> datumArrayList = agendaLists.get(position).getData();

                AgendaAdapter agendaAdapter = new AgendaAdapter(AgendaActivity.this, agendaLists, datumArrayList);
                recyclerViewFriend.addItemDecoration(new DividerItemDecoration(AgendaActivity.this, DividerItemDecoration.VERTICAL_LIST));
                recyclerViewFriend.setLayoutManager(new LinearLayoutManager(AgendaActivity.this));
                recyclerViewFriend.setItemAnimator(new DefaultItemAnimator());
                agendaAdapter.notifyDataSetChanged();
                recyclerViewFriend.setAdapter(agendaAdapter);

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }
        });

    }

    @Override
    public void getAgendaUnSuccessful(String message) {
        SnackNotify.showMessage(message, coordinateLayout);

    }

    @OnItemSelected(R.id.spinner)
    public void spinnerSelect() {

    }

    @OnClick(R.id.img_back)
    public void back() {
        finish();
    }

    @Override
    public void AgendaInternetError() {

    }
}
