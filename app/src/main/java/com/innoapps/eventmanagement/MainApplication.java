package com.innoapps.eventmanagement;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;


//import com.innoapps.eventmanagement.common.utils.ACRAReportSender;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

/**
 * Created by Braintech on 7/14/2016.
 */
@ReportsCrashes(formKey = "", // will not be used
        mailTo = "ankit.kambojsre@gmailcom", mode = ReportingInteractionMode.TOAST, resToastText = R.string.app_name)

public class MainApplication extends Application {
    private static MainApplication mainApplication;

    public static MainApplication getInstance() {
        if (null == mainApplication) {
            return mainApplication;
        }
        return mainApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();


        initImageLoader(this);

        mainApplication = (MainApplication) getApplicationContext();
        ACRA.init(MainApplication.this);
    }
    public  void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
