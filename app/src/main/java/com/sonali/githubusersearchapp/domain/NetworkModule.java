package com.sonali.githubusersearchapp.domain;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.sonali.githubusersearchapp.AppConstants;
import com.sonali.githubusersearchapp.BuildConfig;
import com.sonali.githubusersearchapp.MyApplication;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class NetworkModule {

    private static NetworkModule mInstance;
    private Retrofit retrofit;

    public static NetworkModule getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkModule();
        }
        return mInstance;
    }

    private NetworkModule() {
    }


    private OkHttpClient provideOkHttpClient() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request request = chain.request().newBuilder()
                                .build();

                        if (isNetworkConnected()) {
                            return chain.proceed(request);
                        } else {
                            throw new NoInternetConnectionException();
                        }
                    }
                });

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        }
        return builder.build();
    }


    public Retrofit provideBaseRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(AppConstants.API_BASE_URL)
                    .client(provideOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }


    public boolean isNetworkConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) MyApplication.getApplicationInstance().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

}
