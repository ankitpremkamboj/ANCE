package com.innoapps.eventmanagement.common.events.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.innoapps.eventmanagement.R;
import com.innoapps.eventmanagement.common.events.EventUserDescriptionActivity;
import com.innoapps.eventmanagement.common.events.model.EventsModel;
import com.innoapps.eventmanagement.common.events.presenter.EventPresenter;
import com.innoapps.eventmanagement.common.events.view.OnLoadMoreListener;
import com.innoapps.eventmanagement.common.helper.GPSTracker;
import com.innoapps.eventmanagement.common.helper.TouchImageView;
import com.innoapps.eventmanagement.common.navigation.NavigationActivity;
import com.innoapps.eventmanagement.common.utils.AppConstant;
import com.innoapps.eventmanagement.common.utils.GlideCircleTransform;
import com.innoapps.eventmanagement.common.utils.SnackNotify;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by ankit on 3/29/2017.
 */

public class NewAdapter extends RecyclerView.Adapter {

    ArrayList<EventsModel.EventList> eventLists;
    Context context;
    GPSTracker gps;

    int likevalue;
    EventPresenter eventPresenter;

    private boolean isLoadingAdded = false;
    EventsModel.EventList eventList;
    public static final int VIEW_ITEM = 1;
    public static final int VIEW_PROG = 0;

    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    // private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount;
    LinearLayoutManager linearLayoutManager;


    public NewAdapter(Context context, ArrayList<EventsModel.EventList> eventLists, EventPresenter eventPresenter, RecyclerView recyclerView, LinearLayoutManager layoutMan) {
        this.context = context;
        this.eventLists = eventLists;
        this.eventPresenter = eventPresenter;
        this.linearLayoutManager = layoutMan;
        gps = new GPSTracker(context);

        //  if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

        linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();


        recyclerView
                .addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        totalItemCount = linearLayoutManager.getItemCount();
                        lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                        if (!loading
                                && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                            // End has been reached
                            // Do something
                            if (onLoadMoreListener != null) {
                                onLoadMoreListener.onLoadMore();
                            }
                            loading = true;
                        }
                    }
                });
        //}


    }


    public class Holder extends RecyclerView.ViewHolder {

        @InjectView(R.id.imageView)
        ImageView imageView;

        @InjectView(R.id.txt_name)
        TextView txt_name;

        @InjectView(R.id.txt_time)
        TextView txt_time;

        @InjectView(R.id.mail)
        ImageView mail;

        @InjectView(R.id.call)
        ImageView call;

        @InjectView(R.id.txt_title)
        TextView txt_title;

        @InjectView(R.id.like_image)
        ImageView like_image;

        @InjectView(R.id.txt_like)
        TextView txt_like;

        @InjectView(R.id.location_image)
        ImageView location_image;

        @InjectView(R.id.event_image)
        ImageView event_image;

        @InjectView(R.id.txt_location)
        TextView txt_location;
        @InjectView(R.id.txt_eventName)
        TextView txt_eventName;
        @InjectView(R.id.content_main)
        RelativeLayout content_main;


        public Holder(View itemView) {
            super(itemView);

            ButterKnife.inject(this, itemView);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // View view = LayoutInflater.from(context).inflate(R.layout.content_main, null);
       /* //RecyclerView.ViewHolder vh;
        Holder holder;
        View vh;

        if (viewType == VIEW_ITEM) {
            vh = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_main, parent, false);

            holder = new Holder(vh);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.progress_item, parent, false);

           // vh = new ProgressViewHolder(v);
        }*/


        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.content_main, parent, false);

            vh = new Holder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.progress_item, parent, false);

            vh = new ProgressViewHolder(v);
        }
        return vh;
        //  return new Holder(view);
    }


    public void setLoaded() {
        loading = false;
    }

    public void clearData() {
        eventLists = new ArrayList<>();
    }

    public void updateData(ArrayList<EventsModel.EventList> arrayListNewData) {

        if (arrayListNewData != null && arrayListNewData != null) {
            for (int i = 0; i < arrayListNewData.size(); i++) {
                eventLists.add(arrayListNewData.get(i));
            }

            notifyDataSetChanged();
        }
    }


    @Override
    public int getItemViewType(int position) {
        return eventLists.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        setFont(holder);
        // setTextColor(holder);

        if (holder instanceof Holder) {
            ((Holder) holder).call.setTag(position);
            ((Holder) holder).mail.setTag(position);
            ((Holder) holder).like_image.setTag(position);


            eventList = eventLists.get(position);

            if (!eventList.getUserName().equalsIgnoreCase("") && eventList.getUserName() != null) {

                String str = eventList.getUserName();
                String cap = str.substring(0, 1).toUpperCase() + str.substring(1);
                ((Holder) holder).txt_name.setText(cap);
            } else {
                ((Holder) holder).txt_name.setText("N/A");
            }

            if (!eventList.getAddedDate().isEmpty() && eventList.getAddedDate() != null) {
                ((Holder) holder).txt_time.setText(eventList.getAddedDate());
            } else {
                ((Holder) holder).txt_time.setText("N/A");
            }

            if (!eventList.getEventDescription().isEmpty() && eventList.getEventDescription() != null) {
                ((Holder) holder).txt_title.setText(eventList.getEventDescription());
            } else {
                ((Holder) holder).txt_title.setText("N/A");
            }
            if (eventList.getTotalLike() != null) {
                ((Holder) holder).txt_like.setText(String.valueOf(eventList.getTotalLike()));
            } else {
                ((Holder) holder).txt_like.setText("N/A");
            }

            // holder.txt_location.setText("5 KM far");

            if (!eventList.getEventName().isEmpty() && eventList.getEventName() != null) {

                String str = eventList.getEventName();
                String cap = str.substring(0, 1).toUpperCase() + str.substring(1);
                ((Holder) holder).txt_eventName.setText(cap);
            } else {
                ((Holder) holder).txt_eventName.setText("N/A");

            }

            if (eventList.getLikeVal() != null) {
                if (eventList.getLikeVal() == 0) {

                    ((Holder) holder).like_image.setImageResource(R.mipmap.un_like_shape);
                    likevalue = 1;
                }
            }
            if (eventList.getLikeVal() != null) {
                if (eventList.getLikeVal() == 1) {
                    ((Holder) holder).like_image.setImageResource(R.mipmap.like_shape);
                    likevalue = 0;
                }
            }
            Glide.with(context)
                    .load(eventList.getUserImage())
                    .transform(new GlideCircleTransform(context))
                    .placeholder(R.mipmap.ic_default_profile)
                    .error(R.mipmap.ic_default_profile)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(((Holder) holder).imageView);

            Glide.with(context)
                    .load(eventList.getImage())
                    .placeholder(R.mipmap.event_default)
                    .error(R.mipmap.event_default)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(((Holder) holder).event_image);


            ((Holder) holder).mail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        int tag = Integer.parseInt(v.getTag().toString());

                        EventsModel.EventList valueData = eventLists.get(tag);
                        if (!valueData.getUserEmail().isEmpty() && valueData.getUserEmail() != null) {
                            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                    "mailto", valueData.getUserEmail(), null));
                            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Regarding you Events");
                            emailIntent.putExtra(Intent.EXTRA_TEXT, " ");
                            context.startActivity(Intent.createChooser(emailIntent, "Send email..."));
                        } else {
                            SnackNotify.showMessage("Email ID does not exist.", ((Holder) holder).content_main);
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            });

            ((Holder) holder).call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        int tag = Integer.parseInt(v.getTag().toString());

                        EventsModel.EventList valueData = eventLists.get(tag);

                        if (!valueData.getUserPhone().isEmpty() && valueData.getUserPhone() != null) {

                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:" + valueData.getUserPhone()));
                            context.startActivity(intent);
                        } else {
                            SnackNotify.showMessage("Mobile number does not exist.", ((Holder) holder).content_main);
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            });


            ((Holder) holder).like_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        int tag = Integer.parseInt(v.getTag().toString());
                        EventsModel.EventList valueData = eventLists.get(tag);

                        // if (valueData.getLikeVal() != null) {
                        if (valueData.getLikeVal() == 0) {
                            likevalue = 1;
                            eventPresenter.callLike(valueData.getEventId(), String.valueOf(likevalue));
                            ((Holder) holder).like_image.setImageResource(R.mipmap.like_shape);
                            ((Holder) holder).txt_like.setText(String.valueOf(valueData.getTotalLike() + 1));
                            valueData.setTotalLike(valueData.getTotalLike() + 1);
                            valueData.setLikeVal(1);

                        } else if (valueData.getLikeVal() == 1) {
                            likevalue = 0;
                            eventPresenter.callLike(valueData.getEventId(), String.valueOf(likevalue));
                            ((Holder) holder).like_image.setImageResource(R.mipmap.un_like_shape);
                            ((Holder) holder).txt_like.setText(String.valueOf(valueData.getTotalLike() - 1));
                            valueData.setTotalLike(valueData.getTotalLike() - 1);
                            valueData.setLikeVal(0);
                        }


                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            });

            ((Holder) holder).event_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle bun = new Bundle();
                    NavigationActivity activity = (NavigationActivity) (context);
                    FragmentManager fm = activity.getSupportFragmentManager();
                    MyDialogFragment frag = new MyDialogFragment();
                    bun.putString("image", eventList.getImage());
                    frag.setArguments(bun);
                    frag.show(fm, "txn_tag");

                }
            });

            ((Holder) holder).imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {

                        String name = eventList.getUserName();
                        String event_title = eventList.getEventName();
                        String email = eventList.getUserEmail();
                        String mobile = eventList.getUserPhone();
                        String image = eventList.getUserImage();

                        Intent intent = new Intent(context, EventUserDescriptionActivity.class);
                        intent.putExtra("NAME", name);
                        intent.putExtra("TITLE", event_title);
                        intent.putExtra("EMAIL", email);
                        intent.putExtra("MOBILE", mobile);
                        intent.putExtra("IMAGE", image);

                        context.startActivity(intent);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }

                }
            });


            ((Holder) holder).txt_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    try {

                        String name = eventList.getUserName();
                        String event_title = eventList.getEventName();
                        String email = eventList.getUserEmail();
                        String mobile = eventList.getUserPhone();
                        String image = eventList.getUserImage();

                        Intent intent = new Intent(context, EventUserDescriptionActivity.class);
                        intent.putExtra("NAME", name);
                        intent.putExtra("TITLE", event_title);
                        intent.putExtra("EMAIL", email);
                        intent.putExtra("MOBILE", mobile);
                        intent.putExtra("IMAGE", image);
                        context.startActivity(intent);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            });


            if (gps.canGetLocation()) {

                double latitude = gps.getLatitude();
                double longitude = gps.getLongitude();


                if (eventList.getLatitude().equalsIgnoreCase("")) {
                    ((Holder) holder).txt_location.setText("N/A");
                } else {
                    double distance = distance(latitude, longitude, Double.valueOf(eventList.getLatitude()), Double.valueOf(eventList.getLongitude()));
                    ((Holder) holder).txt_location.setText(String.format("%.1f", distance) + " KM far");
                }


            } else {
                // can't get location
                // GPS or Network is not enabled
                // Ask user to enable GPS/network in settings
                gps.showSettingsAlert();
            }
        } else {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }

    }

    @Override
    public int getItemCount() {
        return eventLists.size();
    }


    private void setFont(RecyclerView.ViewHolder holder) {

       /* holder.txt_name.setTypeface(Typeface.createFromAsset(context.getAssets(), AppConstant.AVENIRNEXT_MEDIUM));
        holder.txt_time.setTypeface(Typeface.createFromAsset(context.getAssets(), AppConstant.AVENIRNEXT_MEDIUM));
        holder.txt_eventName.setTypeface(Typeface.createFromAsset(context.getAssets(), AppConstant.AVENIRNEXT_MEDIUM));
        holder.txt_title.setTypeface(Typeface.createFromAsset(context.getAssets(), AppConstant.AVENIRNEXT_MEDIUM));
        holder.txt_like.setTypeface(Typeface.createFromAsset(context.getAssets(), AppConstant.AVENIRNEXT_MEDIUM));
        holder.txt_location.setTypeface(Typeface.createFromAsset(context.getAssets(), AppConstant.AVENIRNEXT_MEDIUM));*/
    }

    public static class MyDialogFragment extends DialogFragment {
        String imageURl;

        Matrix matrix = new Matrix();
        Matrix savedMatrix = new Matrix();
        PointF startPoint = new PointF();
        PointF midPoint = new PointF();
        float oldDist = 1f;
        static final int NONE = 0;
        static final int DRAG = 1;
        static final int ZOOM = 2;
        int mode = NONE;


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


                Bundle bundle = this.getArguments();
                imageURl = bundle.getString("image");
                if (android.os.Build.VERSION.SDK_INT > 9) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                }
                //TouchImageview its for zoom image
                //ZoomImage imageView = (ZoomImage) root.findViewById(R.id.image);

                TouchImageView imageView = (TouchImageView) root.findViewById(R.id.image);
                //ImageView imageView = (ImageView) root.findViewById(R.id.image);

                ImageView back = (ImageView) root.findViewById(R.id.back);
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                });


                if (imageURl != null & !imageURl.equalsIgnoreCase("")) {
                    Glide.with(getActivity())
                            .load(imageURl)
                            .placeholder(R.mipmap.event_default)
                            .error(R.mipmap.event_default)
                            .skipMemoryCache(true)
                            .centerCrop()
                            .fitCenter()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(imageView);
                }


                //   imageView.setOnTouchListener(new TouchImageView(getActivity()));

            } catch (Exception e) {
                e.printStackTrace();
            }

            return root;
        }

    }


    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }


    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
        }
    }
}
