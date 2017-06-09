package com.innoapps.eventmanagement.common.myblogs.adapter;

import android.app.Activity;
import android.content.Intent;
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
import com.innoapps.eventmanagement.common.blogs.BlogDetailsActivity;
import com.innoapps.eventmanagement.common.blogs.model.BlogsModel;
import com.innoapps.eventmanagement.common.blogs.presenter.BlogPresenterImpl;
import com.innoapps.eventmanagement.common.blogs.presenter.BlogsPresenter;
import com.innoapps.eventmanagement.common.blogs.view.BlogsView;
import com.innoapps.eventmanagement.common.myblogs.MyBlogDetailsActivity;
import com.innoapps.eventmanagement.common.myblogs.model.MyBlogsModel;
import com.innoapps.eventmanagement.common.myblogs.view.MyBlogsView;
import com.innoapps.eventmanagement.common.utils.AppConstant;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by ankit on 3/20/2017.
 */

public class MyBlogsAdapter extends RecyclerView.Adapter<MyBlogsAdapter.Holder> {

    ArrayList<MyBlogsModel.BlogList> blogLists;

    Activity context;
    BlogsPresenter blogsPresenter;
    MyBlogsView blogsView;
    Activity activity;

    public MyBlogsAdapter(Activity context, ArrayList<MyBlogsModel.BlogList> blogLists, MyBlogsView blogsView) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.my_blogs_item, null);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {

        setFont(holder);
        holder.rl1.setTag(position);

        MyBlogsModel.BlogList blogList = blogLists.get(position);


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

        if (!blogList.getBlogTitle().isEmpty() && blogList.getBlogTitle() != null) {
            // holder.user_name.setText(blogList.getBlogTitle());

            String str = blogList.getBlogTitle();
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

                MyBlogsModel.BlogList blogList1 = blogLists.get(tag);
                String blog_id = blogList1.getBlogId();
                Intent intent = new Intent(context, MyBlogDetailsActivity.class);
                intent.putExtra("BlogId", blog_id);
                intent.putExtra("position", position);
                context.startActivity(intent);
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
