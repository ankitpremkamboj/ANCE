package com.innoapps.eventmanagement.common.events.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.UnitTransformation;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.innoapps.eventmanagement.R;
import com.innoapps.eventmanagement.common.events.EventUserDescriptionActivity;
import com.innoapps.eventmanagement.common.events.model.EventsModel;
import com.innoapps.eventmanagement.common.events.presenter.EventPresenter;
import com.innoapps.eventmanagement.common.helper.GPSTracker;
import com.innoapps.eventmanagement.common.helper.TouchImageView;
import com.innoapps.eventmanagement.common.navigation.NavigationActivity;
import com.innoapps.eventmanagement.common.utils.AppConstant;
import com.innoapps.eventmanagement.common.utils.GlideCircleTransform;
import com.innoapps.eventmanagement.common.utils.SnackNotify;
import com.innoapps.eventmanagement.common.voiewmoretext.ViewMoreText;
import com.squareup.picasso.Picasso;

import java.net.UnknownHostException;
import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by ankit on 3/15/2017.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.Holder> {

    ArrayList<EventsModel.EventList> eventLists;
    Context context;
    GPSTracker gps;

    int likevalue;
    EventPresenter eventPresenter;


    public EventAdapter(Context context,  ArrayList<EventsModel.EventList>eventLists, EventPresenter eventPresenter) {
        this.context = context;
        this.eventLists = eventLists;
        this.eventPresenter = eventPresenter;
        gps = new GPSTracker(context);
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
        @InjectView(R.id.progress)
        ProgressBar progress;

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
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.content_main, null);

        return new Holder(view);
    }


    @Override
    public void onBindViewHolder(final Holder holder, int position) {
        setFont(holder);
        // setTextColor(holder);

        holder.call.setTag(position);
        holder.mail.setTag(position);
        holder.like_image.setTag(position);


        final EventsModel.EventList eventList = eventLists.get(position);

        if (!eventList.getUserName().equalsIgnoreCase("") && eventList.getUserName() != null) {

            String str = eventList.getUserName();
            String cap = str.substring(0, 1).toUpperCase() + str.substring(1);
            holder.txt_name.setText(cap);
        } else {
            holder.txt_name.setText("N/A");
        }

        if (!eventList.getAddedDate().isEmpty() && eventList.getAddedDate() != null) {
            holder.txt_time.setText(eventList.getAddedDate());
        } else {
            holder.txt_time.setText("N/A");
        }

        if (!eventList.getEventDescription().isEmpty() && eventList.getEventDescription() != null) {
            holder.txt_title.setText(eventList.getEventDescription());
            holder.txt_title.setLines(2);
            //  ViewMoreText.makeTextViewResizable(holder.txt_title, 2, "View More", true);

        } else {
            holder.txt_title.setText("N/A");
        }
        if (eventList.getTotalLike() != null) {
            holder.txt_like.setText(String.valueOf(eventList.getTotalLike()));
        } else {
            holder.txt_like.setText("N/A");
        }

        // holder.txt_location.setText("5 KM far");

        if (!eventList.getEventName().isEmpty() && eventList.getEventName() != null) {

            String str = eventList.getEventName();
            String cap = str.substring(0, 1).toUpperCase() + str.substring(1);
            holder.txt_eventName.setText(cap);
        } else {
            holder.txt_eventName.setText("N/A");

        }

        if (eventList.getLikeVal() != null) {
            if (eventList.getLikeVal() == 0) {

                holder.like_image.setImageResource(R.mipmap.un_like_shape);
                likevalue = 1;
            }
        }
        if (eventList.getLikeVal() != null) {
            if (eventList.getLikeVal() == 1) {
                holder.like_image.setImageResource(R.mipmap.like_shape);
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
                .into(holder.imageView);


      /*  Glide.with(context)
                .load(eventList.getImage())
               // .placeholder(R.mipmap.event_default)
                .crossFade()
                .error(R.mipmap.event_default)
                .skipMemoryCache(true)
                .centerCrop()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.event_image);*/


        Glide.with(context)
                .load(eventList.getImage())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        // if (e instanceof UnknownHostException)
                        holder.progress.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        holder.progress.setVisibility(View.GONE);

                        return false;
                    }
                })
                .crossFade()
                .error(R.mipmap.event_default)
                .skipMemoryCache(true)
                .centerCrop()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.event_image);

        // Picasso.with(context).load(eventList.getImage()).placeholder(R.mipmap.event_default).centerCrop().into(holder.event_image);


    /*    final DrawableRequestBuilder<String> req = Glide
                .with(context)
                .fromString()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE) // disable network delay for demo
                .skipMemoryCache(true) // make sure transform runs for demo
                .crossFade(2000) // default, just stretch time for noticability
                ;

        req.clone()
                // .placeholder(R.mipmap.event_default)
                .thumbnail(req.clone()
                        .transform(new Delay(500)) // wait a little
                        .load(eventList.getImage())
                )
                .transform(new Delay(1000)) // wait before going from thumbnail to image
                //.dontAnimate() // solves the problem
                .load(eventList.getImage())
                .into(holder.event_image);
*/

        holder.mail.setOnClickListener(new View.OnClickListener() {
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
                        SnackNotify.showMessage("Email ID does not exist.", holder.content_main);
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });

        holder.call.setOnClickListener(new View.OnClickListener() {
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
                        SnackNotify.showMessage("Mobile number does not exist.", holder.content_main);
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });


        holder.like_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    int tag = Integer.parseInt(v.getTag().toString());
                    EventsModel.EventList valueData = eventLists.get(tag);

                    // if (valueData.getLikeVal() != null) {
                    if (valueData.getLikeVal() == 0) {
                        likevalue = 1;
                        eventPresenter.callLike(valueData.getEventId(), String.valueOf(likevalue));
                        holder.like_image.setImageResource(R.mipmap.like_shape);
                        holder.txt_like.setText(String.valueOf(valueData.getTotalLike() + 1));
                        valueData.setTotalLike(valueData.getTotalLike() + 1);
                        valueData.setLikeVal(1);

                    } else if (valueData.getLikeVal() == 1) {
                        likevalue = 0;
                        eventPresenter.callLike(valueData.getEventId(), String.valueOf(likevalue));
                        holder.like_image.setImageResource(R.mipmap.un_like_shape);
                        holder.txt_like.setText(String.valueOf(valueData.getTotalLike() - 1));
                        valueData.setTotalLike(valueData.getTotalLike() - 1);
                        valueData.setLikeVal(0);
                    }


                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });

        holder.event_image.setOnClickListener(new View.OnClickListener() {
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

        holder.imageView.setOnClickListener(new View.OnClickListener() {
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


        holder.txt_name.setOnClickListener(new View.OnClickListener() {
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
                holder.txt_location.setText("N/A");
            } else {
                double distance = distance(latitude, longitude, Double.valueOf(eventList.getLatitude()), Double.valueOf(eventList.getLongitude()));
                holder.txt_location.setText(String.format("%.1f", distance) + " KM far");
            }


        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }

    }

    @Override
    public int getItemCount() {
        return eventLists.size();
    }

    public void updateData(ArrayList<EventsModel.EventList> arrayListNewData) {

        if (arrayListNewData != null && arrayListNewData != null) {
            for (int i = 0; i < arrayListNewData.size(); i++) {
                eventLists.add(arrayListNewData.get(i));
            }

            notifyDataSetChanged();
        }
    }

    public void clearData() {
        eventLists = new ArrayList<>();
    }

    private void setFont(Holder holder) {

        holder.txt_name.setTypeface(Typeface.createFromAsset(context.getAssets(), AppConstant.AVENIRNEXT_MEDIUM));
        holder.txt_time.setTypeface(Typeface.createFromAsset(context.getAssets(), AppConstant.AVENIRNEXT_MEDIUM));
        holder.txt_eventName.setTypeface(Typeface.createFromAsset(context.getAssets(), AppConstant.AVENIRNEXT_MEDIUM));
        holder.txt_title.setTypeface(Typeface.createFromAsset(context.getAssets(), AppConstant.AVENIRNEXT_MEDIUM));
        holder.txt_like.setTypeface(Typeface.createFromAsset(context.getAssets(), AppConstant.AVENIRNEXT_MEDIUM));
        holder.txt_location.setTypeface(Typeface.createFromAsset(context.getAssets(), AppConstant.AVENIRNEXT_MEDIUM));
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


                Bundle bundle = this.getArguments();
                imageURl = bundle.getString("image");
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

    class Delay extends UnitTransformation {
        private final int sleepTime;

        public Delay(int sleepTime) {
            this.sleepTime = sleepTime;
        }

        @Override
        public Resource transform(Resource resource, int outWidth, int outHeight) {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException ex) {
            }
            return super.transform(resource, outWidth, outHeight);
        }
    }
}
