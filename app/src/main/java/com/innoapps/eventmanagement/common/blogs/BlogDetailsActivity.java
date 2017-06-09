package com.innoapps.eventmanagement.common.blogs;

import android.graphics.Typeface;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.innoapps.eventmanagement.R;
import com.innoapps.eventmanagement.common.blogs.adapter.BlogsAdapter;
import com.innoapps.eventmanagement.common.blogs.model.BlogsModel;
import com.innoapps.eventmanagement.common.blogs.model.DetailsBlogsModel;
import com.innoapps.eventmanagement.common.blogs.presenter.BlogPresenterImpl;
import com.innoapps.eventmanagement.common.blogs.presenter.BlogsPresenter;
import com.innoapps.eventmanagement.common.blogs.view.BlogsView;
import com.innoapps.eventmanagement.common.utils.AppConstant;
import com.innoapps.eventmanagement.common.utils.SnackNotify;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class BlogDetailsActivity extends AppCompatActivity implements BlogsView {
    @InjectView(R.id.blog_image)
    ImageView blog_image;

    @InjectView(R.id.img_back)
    ImageView img_back;

    @InjectView(R.id.txt_header_title)
    TextView txt_header_title;

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
    BlogsPresenter blogsPresenter;
    @InjectView(R.id.coordinateLayout)
    CoordinatorLayout coordinateLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_details);
        ButterKnife.inject(this);
        blogsPresenter = new BlogPresenterImpl();
        setFont();
        if (getIntent().getStringExtra("BlogId") != null) {
            String blogId = getIntent().getStringExtra("BlogId");
            blogsPresenter.callGetBlogDetails(this, this, blogId);
        }
    }

    @Override
    public void getBlogSuccessfull(ArrayList<BlogsModel.BlogList> blogLists) {

    }

    @OnClick(R.id.img_back)
    public void back() {
        finish();
    }

    @Override
    public void getBlogDetailsSuccessfull(ArrayList<DetailsBlogsModel.BlogList> blogLists) {
        String blogImage = blogLists.get(0).getBlogPhoto();
        String blogTitle = blogLists.get(0).getBlogTitle();
        String blogDescription = blogLists.get(0).getBlogDescription();
        String addedDate = blogLists.get(0).getAddedDate();
        String userName = blogLists.get(0).getUserName();

        if (!blogTitle.isEmpty() && blogTitle != null) {
            String cap = blogTitle.substring(0, 1).toUpperCase() + blogTitle.substring(1);
            blog_name.setText(cap);
            blog_date.setText(addedDate);
        } else {
            blog_date.setText("N/A");

        }
        if (!userName.isEmpty() && userName != null) {
            String capital = userName.substring(0, 1).toUpperCase() + userName.substring(1);
            user_name.setText(capital);
        } else {
            user_name.setText("N/A");
        }

        if (!blogDescription.isEmpty() && blogDescription != null) {
            blog_content.setText(blogDescription);
        } else {
            blog_content.setText("N/A");
        }

        Glide.with(this)
                .load(blogImage)
                // .transform(new GlideCircleTransform(context))
                .placeholder(R.mipmap.event_default)
                .error(R.mipmap.event_default)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(blog_image);


    }

    @Override
    public void getBlogUnSuccessful(String message) {
        SnackNotify.showMessage(message, coordinateLayout);
    }

    @Override
    public void postBlogSuccessful(String message) {

    }

    @Override
    public void blogInternetError() {

    }


    private void setFont() {
        blog_name.setTypeface(Typeface.createFromAsset(getAssets(), AppConstant.AVENIRNEXT_DEMIBOLD));
        user_name.setTypeface(Typeface.createFromAsset(getAssets(), AppConstant.AVENIRNEXT_MEDIUM));
        by.setTypeface(Typeface.createFromAsset(getAssets(), AppConstant.AVENIRNEXT_MEDIUM));
        dot.setTypeface(Typeface.createFromAsset(getAssets(), AppConstant.AVENIRNEXT_MEDIUM));
        blog_date.setTypeface(Typeface.createFromAsset(getAssets(), AppConstant.AVENIRNEXT_MEDIUM));
        blog_name.setTypeface(Typeface.createFromAsset(getAssets(), AppConstant.AVENIRNEXT_MEDIUM));

    }

}
