package com.innoapps.eventmanagement.common.refelentry;

import android.graphics.Typeface;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.innoapps.eventmanagement.R;
import com.innoapps.eventmanagement.common.agenda.AgendaActivity;
import com.innoapps.eventmanagement.common.agenda.adapter.AgendaAdapter;
import com.innoapps.eventmanagement.common.refelentry.adapter.RefelEntryAdapter;
import com.innoapps.eventmanagement.common.refelentry.model.RefelEntryModel;
import com.innoapps.eventmanagement.common.refelentry.presenter.RefelEntryPresenter;
import com.innoapps.eventmanagement.common.refelentry.presenter.RefelEntryPresenterImpl;
import com.innoapps.eventmanagement.common.refelentry.view.RefelEntryView;
import com.innoapps.eventmanagement.common.signup.view.RegisterView;
import com.innoapps.eventmanagement.common.utils.AppConstant;
import com.innoapps.eventmanagement.common.utils.DividerItemDecoration;
import com.innoapps.eventmanagement.common.utils.SnackNotify;
import com.innoapps.eventmanagement.common.utils.UserSession;
import com.innoapps.eventmanagement.common.utils.Utils;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class RefelEntryActivity extends AppCompatActivity implements RefelEntryView {

    @InjectView(R.id.img_back)
    ImageView img_back;
    @InjectView(R.id.txt_header_title)
    TextView txt_header_title;
    @InjectView(R.id.txt_lucy_draw)
    TextView txt_lucy_draw;
    @InjectView(R.id.edt_tickt_number)
    EditText edt_tickt_number;
    @InjectView(R.id.btn_send)
    Button btn_send;
    @InjectView(R.id.txt_list)
    TextView txt_list;
    @InjectView(R.id.recyclerViewFriend)
    RecyclerView recyclerViewFriend;
    @InjectView(R.id.coordinateLayout)
    CoordinatorLayout coordinateLayout;
    RefelEntryPresenter refelEntryPresenter;
    String refelentryID;
    UserSession userSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refel_entry);
        userSession = new UserSession(this);
        ButterKnife.inject(this);
        refelEntryPresenter = new RefelEntryPresenterImpl();
        refelEntryPresenter.callRefelEntryList(this, this);
        txt_header_title.setText("ANCE Raffle Entry");
        setFont();
    }

    //set text fonts
    private void setFont() {

        txt_header_title.setTypeface(Typeface.createFromAsset(getAssets(), AppConstant.AVENIRNEXT_DEMIBOLD));
        txt_lucy_draw.setTypeface(Typeface.createFromAsset(getAssets(), AppConstant.AVENIRNEXT_DEMIBOLD));
        edt_tickt_number.setTypeface(Typeface.createFromAsset(getAssets(), AppConstant.AVENIRNEXT_MEDIUM));
        btn_send.setTypeface(Typeface.createFromAsset(getAssets(), AppConstant.AVENIRNEXT_MEDIUM));
        txt_list.setTypeface(Typeface.createFromAsset(getAssets(), AppConstant.AVENIRNEXT_DEMIBOLD));

    }

    @OnClick(R.id.btn_send)
    public void sendTicket() {
        String ticketValue = edt_tickt_number.getText().toString().trim();
        if (!TextUtils.isEmpty(ticketValue)) {
            Utils.hideKeyboardIfOpen(this);
            refelEntryPresenter.callRefelEntryTicketNo(this, this, ticketValue, userSession.getUserID());
        } else {
            Utils.showError(edt_tickt_number, "Please enter ticket number.", this);
        }
    }

    @OnClick(R.id.img_back)
    public void back() {
        finish();
    }

    @Override
    public void getRefelEntrySuccessful(ArrayList<RefelEntryModel.WinnerList> refralLists) {


        RefelEntryAdapter refelEntryAdapter = new RefelEntryAdapter(RefelEntryActivity.this, refralLists);
        recyclerViewFriend.addItemDecoration(new DividerItemDecoration(RefelEntryActivity.this, DividerItemDecoration.VERTICAL_LIST));
        recyclerViewFriend.setLayoutManager(new LinearLayoutManager(RefelEntryActivity.this));
        recyclerViewFriend.setItemAnimator(new DefaultItemAnimator());
        refelEntryAdapter.notifyDataSetChanged();
        recyclerViewFriend.setAdapter(refelEntryAdapter);
    }

    @Override
    public void getRefelEntryUnSuccessful(String message) {
        SnackNotify.showMessage(message, coordinateLayout);

    }

    @Override
    public void RefelEntryInternetError() {

    }

    @Override
    public void sendRefelTicketNoSuccessful(String message) {
        SnackNotify.showMessage(message, coordinateLayout);
        edt_tickt_number.setText("");

    }
}
