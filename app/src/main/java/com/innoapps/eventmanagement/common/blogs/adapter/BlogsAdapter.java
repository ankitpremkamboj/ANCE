package com.innoapps.eventmanagement.common.blogs.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.innoapps.eventmanagement.R;
import com.innoapps.eventmanagement.common.agenda.adapter.AgendaAdapter;
import com.innoapps.eventmanagement.common.agenda.model.AgendaModel;
import com.innoapps.eventmanagement.common.blogs.BlogDetailsActivity;
import com.innoapps.eventmanagement.common.blogs.model.BlogsModel;
import com.innoapps.eventmanagement.common.blogs.presenter.BlogPresenterImpl;
import com.innoapps.eventmanagement.common.blogs.presenter.BlogsPresenter;
import com.innoapps.eventmanagement.common.blogs.view.BlogsView;
import com.innoapps.eventmanagement.common.events.model.EventsModel;
import com.innoapps.eventmanagement.common.utils.AppConstant;
import com.innoapps.eventmanagement.common.utils.GlideCircleTransform;

import java.util.ArrayList;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by ankit on 3/20/2017.
 */

public class BlogsAdapter extends RecyclerView.Adapter<BlogsAdapter.Holder> {

    ArrayList<BlogsModel.BlogList> blogLists;

    Activity context;
    BlogsPresenter blogsPresenter;
    BlogsView blogsView;
    Activity activity;

    public BlogsAdapter(Activity context, ArrayList<BlogsModel.BlogList> blogLists, BlogsView blogsView) {
        this.context = context;
        this.blogLists = blogLists;
        this.blogsView = blogsView;
        // this.activity = context;
        blogsPresenter = new BlogPresenterImpl();

    }

    public class Holder extends RecyclerView.ViewHolder {


        @InjectView(R.id.blog_image)
        ImageView blog_image;

        @InjectView(R.id.blog_name)
        TextView blog_name;

        @InjectView(R.id.blog_date)
        TextView blog_date;

        @InjectView(R.id.dot)
        TextView dot;

        @InjectView(R.id.by)
        TextView by;

        @InjectView(R.id.user_name)
        TextView user_name;

        @InjectView(R.id.blog_content)
        TextView blog_content;
        @InjectView(R.id.rl1)
        RelativeLayout rl1;


        public Holder(View itemView) {
            super(itemView);

            ButterKnife.inject(this, itemView);
        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.blogs_item, null);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        setFont(holder);
        holder.rl1.setTag(position);

        BlogsModel.BlogList blogList = blogLists.get(position);


        if (!blogList.getBlogTitle().isEmpty() && blogList.getBlogTitle() != null) {
            String str = blogList.getBlogTitle();
            String cap = str.substring(0, 1).toUpperCase() + str.substring(1);
            holder.blog_name.setText(cap);
        } else {
            holder.blog_name.setText("N/A");
        }


        if (!blogList.getAddedDate().isEmpty() && blogList.getAddedDate() != null) {
            holder.blog_date.setText(blogList.getAddedDate());
        } else {
            holder.blog_date.setText("N/A");
        }

        if (!blogList.getUserName().isEmpty() && blogList.getUserName() != null) {
            holder.user_name.setText(blogList.getUserName());

            String str = blogList.getUserName();
            String cap = str.substring(0, 1).toUpperCase() + str.substring(1);
            holder.user_name.setText(cap);
        } else {
            holder.user_name.setText("N/A");
        }
        if (!blogList.getBlogDescription().isEmpty() && blogList.getBlogDescription() != null) {
            String str = blogList.getBlogDescription();
            String cap = str.substring(0, 1).toUpperCase() + str.substring(1);
            holder.blog_content.setText(cap);
        } else {
            holder.blog_content.setText("N/A");
        }

        Glide.with(context)
                .load(blogList.getBlogPhoto())
                // .transform(new GlideCircleTransform(context))
                .placeholder(R.mipmap.event_default)
                .error(R.mipmap.event_default)
                .centerCrop()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.blog_image);

        holder.rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int tag = Integer.parseInt(v.getTag().toString());

                BlogsModel.BlogList blogList1 = blogLists.get(tag);
                String blog_id = blogList1.getBlogId();
                Intent intent = new Intent(context, BlogDetailsActivity.class);
                intent.putExtra("BlogId", blog_id);
                context.startActivity(intent);
                // blogsPresenter.callGetBlogList(blogsView, context, blog_id);
            }
        });
    }

    @Override
    public int getItemCount() {

        return blogLists.size();
    }


    private void setFont(Holder holder) {
        holder.blog_name.setTypeface(Typeface.createFromAsset(context.getAssets(), AppConstant.AVENIRNEXT_DEMIBOLD));
        holder.user_name.setTypeface(Typeface.createFromAsset(context.getAssets(), AppConstant.AVENIRNEXT_MEDIUM));
        holder.by.setTypeface(Typeface.createFromAsset(context.getAssets(), AppConstant.AVENIRNEXT_MEDIUM));
        holder.dot.setTypeface(Typeface.createFromAsset(context.getAssets(), AppConstant.AVENIRNEXT_MEDIUM));
        holder.blog_date.setTypeface(Typeface.createFromAsset(context.getAssets(), AppConstant.AVENIRNEXT_MEDIUM));
        holder.blog_name.setTypeface(Typeface.createFromAsset(context.getAssets(), AppConstant.AVENIRNEXT_MEDIUM));

    }


}
