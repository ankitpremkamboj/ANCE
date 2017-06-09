package com.innoapps.eventmanagement.common.speakers;

import android.app.Dialog;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.innoapps.eventmanagement.R;
import com.innoapps.eventmanagement.common.events.adapter.EventAdapter;
import com.innoapps.eventmanagement.common.friends.adapter.FriendAdapter;
import com.innoapps.eventmanagement.common.friends.model.FriendsModel;
import com.innoapps.eventmanagement.common.friends.presenter.FriendsPresenter;
import com.innoapps.eventmanagement.common.friends.presenter.FriendsPresenterImpl;
import com.innoapps.eventmanagement.common.friends.view.FriendsView;
import com.innoapps.eventmanagement.common.helper.TouchImageView;
import com.innoapps.eventmanagement.common.speakers.adapter.SpeakerAdapter;
import com.innoapps.eventmanagement.common.speakers.model.SpeakerModel;
import com.innoapps.eventmanagement.common.speakers.presenter.SpeakerPresenter;
import com.innoapps.eventmanagement.common.speakers.presenter.SpeakerPresenterImpl;
import com.innoapps.eventmanagement.common.speakers.view.SpeakerView;
import com.innoapps.eventmanagement.common.utils.DividerItemDecoration;
import com.innoapps.eventmanagement.common.utils.SnackNotify;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class SpeakersActivity extends AppCompatActivity implements SpeakerView {

    @InjectView(R.id.coordinateLayout)
    CoordinatorLayout coordinateLayout;
    @InjectView(R.id.recyclerViewFriend)
    RecyclerView recyclerViewFriend;
    @InjectView(R.id.img_back)
    ImageView img_back;
    @InjectView(R.id.txt_header_title)
    TextView txt_header_title;
    @InjectView(R.id.speaker_img)
    ImageView speaker_img;
    /* @InjectView(R.id.input_layout_search)
     TextInputLayout input_layout_search;
     @InjectView(R.id.input_search)
     EditText input_search;*/
    @InjectView(R.id.img_search)
    ImageView img_search;
    SpeakerPresenter friendsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaker);
        ButterKnife.inject(this);
        img_search.setVisibility(View.GONE);
        friendsPresenter = new SpeakerPresenterImpl();
        // friendsPresenter.callGetSpeakerList(this, SpeakersActivity.this);
        txt_header_title.setText("Speakers");


    }

    @OnClick(R.id.speaker_img)
    public void speaker() {
        FragmentManager fm = this.getSupportFragmentManager();
        MyDialogFragment frag = new MyDialogFragment();
        // bun.putString("image", eventList.getImage());
        // frag.setArguments(bun);
        frag.show(fm, "txn_tag");
    }

    @Override
    public void getSpeakerSuccessful(ArrayList<SpeakerModel.SpeakerList> userFriendLists) {
        Log.e("Friend List Data", "" + userFriendLists.size());

        SpeakerAdapter friendAdapter = new SpeakerAdapter(SpeakersActivity.this, userFriendLists);
        recyclerViewFriend.addItemDecoration(new DividerItemDecoration(SpeakersActivity.this, DividerItemDecoration.VERTICAL_LIST));
        recyclerViewFriend.setLayoutManager(new LinearLayoutManager(SpeakersActivity.this));
        recyclerViewFriend.setItemAnimator(new DefaultItemAnimator());
        recyclerViewFriend.setAdapter(friendAdapter);

    }

    @OnClick(R.id.img_back)
    public void back() {
        finish();
    }

    @Override
    public void getSpeakerUnSuccessful(String message) {
        SnackNotify.showMessage(message, coordinateLayout);

    }


    @Override
    public void SpeakerInternetError() {

    }


    public static class MyDialogFragment extends DialogFragment {
        String imageURl;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setStyle(DialogFragment.STYLE_NORMAL, R.style.MY_DIALOG);
        }

        @Override
        public void onStart() {
            super.onStart();
            Dialog d = getDialog();
            if (d != null) {
                int width = ViewGroup.LayoutParams.MATCH_PARENT;
                int height = ViewGroup.LayoutParams.MATCH_PARENT;
                d.getWindow().setLayout(width, height);
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.image_view_dialog, container, false);
            try {


              /*  Bundle bundle = this.getArguments();
                imageURl = bundle.getString("image");*/
                if (android.os.Build.VERSION.SDK_INT > 9) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                }
                //TouchImageview its for zoom image
                TouchImageView imageView = (TouchImageView) root.findViewById(R.id.image);
                ImageView back = (ImageView) root.findViewById(R.id.back);
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                });

                //if (imageURl != null & !imageURl.equalsIgnoreCase("")) {
                    Glide.with(getActivity())
                            .load("")
                            .placeholder(R.drawable.speakers_international_all_2)
                            .error(R.drawable.speakers_international_all_2)
                            .skipMemoryCache(true)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(imageView);
              //  }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return root;
        }

    }
}
