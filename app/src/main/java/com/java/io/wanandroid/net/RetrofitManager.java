package com.java.io.wanandroid.net;

import android.content.Context;

import com.blankj.utilcode.util.NetworkUtils;
import com.java.io.wanandroid.Constant;
import com.java.io.wanandroid.base.App;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author huguojin
 */
public class RetrofitManager {

    private static final long CONNECT_TIMEOUT = 60;
    private static final long READ_TIMEOUT = 10;
    private static final long WRITE_TIMEOUT = 10;

    private static OkHttpClient mOkHttpClient;

    /**
     * 设缓存有效期为1天
     */
    private static final long CACHE_STALE_SEC = 60 * 60 * 24;

    /**
     * 查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
     */
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;

    private static final Interceptor M_REWRITE_CACHE_CONTROL_INTERCEPTOR = chain -> {
        Request request = chain.request();
        if (!NetworkUtils.isConnected()) {
            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }
        Response originalResponse = chain.proceed(request);
        if (NetworkUtils.isConnected()) {
            // read @Headers if network available
            String cacheControl = request.cacheControl().toString();
            return originalResponse.newBuilder()
                    .header("Cache-Control", cacheControl)
                    .removeHeader("Pragma")
                    .build();
        } else {
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-scale=" + CACHE_CONTROL_CACHE)
                    .removeHeader("Pragma")
                    .build();
        }
    };

    /**
     * 日志拦截
     */
    private static final Interceptor M_LOGGING_INTERCEPTOR = chain -> {
        Request request = chain.request();
        return chain.proceed(request);
    };

    private static OkHttpClient getOkHttpClient(Context context) {
        if (mOkHttpClient == null) {
            synchronized (RetrofitManager.class) {
                Cache cache = new Cache(new File(context.getCacheDir(), "HttpCache"), 1024 * 1024 * 100);
                if (mOkHttpClient == null) {
                    mOkHttpClient = new OkHttpClient.Builder().cache(cache)
                            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                            .addInterceptor(M_REWRITE_CACHE_CONTROL_INTERCEPTOR)
                            .addInterceptor(M_LOGGING_INTERCEPTOR)
                            // .cookieJar()
                            .build();
                }

            }
        }
        return mOkHttpClient;
    }

    /**
     * create
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T create(Class<T> clazz) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constant.BASE_URL)
                .client(getOkHttpClient(App.getAppContext()))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(clazz);
    }

}
