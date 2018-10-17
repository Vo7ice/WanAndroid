package com.java.io.wanandroid.net;

import com.java.io.wanandroid.bean.Article;
import com.java.io.wanandroid.bean.Data;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author huguojin
 */
public interface ApiService {

    /**
     * http://www.wanandroid.com/article/list/0/json
     * 方法：GET
     * 参数：页码，拼接在连接中，从0开始。
     */
    @GET("/article/list/{page}/json")
    Observable<Data<Article>> getHomeArticles(@Path("page") int page);
}
