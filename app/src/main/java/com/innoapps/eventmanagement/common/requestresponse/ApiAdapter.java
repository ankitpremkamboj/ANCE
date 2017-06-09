package com.innoapps.eventmanagement.common.requestresponse;

import android.content.Context;


import com.innoapps.eventmanagement.BuildConfig;
import com.innoapps.eventmanagement.common.helper.NetworkHelper;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.logging.HttpLoggingInterceptor;


/**
 * Created by Braintech on 6/10/2016.
 */
public class ApiAdapter {

    private static APIService sInstance;


    public static APIService getInstance(Context context) throws NoInternetException {

        if (NetworkHelper.isNetworkAvaialble(context)) {
            return getApiService();
        } else {
            throw new NoInternetException("No Internet connection available");
        }
    }


    public static class NoInternetException extends Exception {
        public NoInternetException(String message) {
            super(message);
        }
    }

    public static APIService getApiService() {
        if (sInstance == null) {
            synchronized (ApiAdapter.class) {
                if (sInstance == null) {

                    sInstance = new Retrofit.Builder()
                            .baseUrl(Const.BASE_URL)
                            .client(getOkHttpClient()).addConverterFactory(GsonConverterFactory.create()).build()
                            .create(APIService.class);
                }
            }
            }
        return sInstance;
    }

    private static OkHttpClient getOkHttpClient() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(httpLoggingInterceptor);
        }


        return builder.readTimeout(20000, TimeUnit.SECONDS)
                .connectTimeout(20000, TimeUnit.SECONDS)
                .build();
    }

}
