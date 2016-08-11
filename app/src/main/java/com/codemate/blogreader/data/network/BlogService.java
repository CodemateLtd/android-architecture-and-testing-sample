package com.codemate.blogreader.data.network;

import com.codemate.blogreader.domain.model.BlogPost;
import com.codemate.blogreader.domain.model.Comment;

import java.util.List;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by ironman on 26/07/16.
 */
public class BlogService {
    private static final String FORUM_SERVER_URL = "http://jsonplaceholder.typicode.com";
    private BlogApi blogApi;

    public BlogService() {
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Accept", "application/json");
            }
        };

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(FORUM_SERVER_URL)
                .setRequestInterceptor(requestInterceptor)
                .build();

        blogApi = restAdapter.create(BlogApi.class);
    }

    public BlogApi getApi() {
        return blogApi;
    }

    public interface BlogApi {
        @GET("/posts")
        Observable<List<BlogPost>> getPosts();

        @GET("/comments")
        Observable<List<Comment>> getComments(@Query("postId") int postId);
    }
}