package com.codemate.blogreader.domain.interactors;

import android.util.Log;

import com.codemate.blogreader.data.network.BlogService;
import com.codemate.blogreader.domain.cache.PostCache;
import com.codemate.blogreader.domain.model.BlogPost;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Scheduler;
import rx.functions.Func1;

/**
 * Created by ironman on 26/07/16.
 */
public class GetPostsInteractor extends BaseInteractor<List<BlogPost>> {
    private final BlogService.BlogApi blogApi;
    private final PostCache blogPostCache;
    private boolean forceRefresh;

    @Inject
    public GetPostsInteractor(BlogService.BlogApi blogApi, PostCache blogPostCache,
                              Scheduler worker, Scheduler observer) {
        super(worker, observer);
        this.blogApi = blogApi;
        this.blogPostCache = blogPostCache;
    }

    @Override
    protected Observable<List<BlogPost>> buildObservable() {
        if (forceRefresh) {
            blogPostCache.clearAll();
        }

        final List<BlogPost> cachedPosts = blogPostCache.getAll();

        if (cachedPosts.size() > 0) {
            Log.d("GetPostsInteractor", "using cached posts");
            return Observable.just(cachedPosts);
        }

        return blogApi.getPosts()
                .filter(new Func1<List<BlogPost>, Boolean>() {
                    @Override
                    public Boolean call(List<BlogPost> blogPosts) {
                        blogPostCache.persistAll(blogPosts);
                        return true;
                    }
                });
    }

    @Override
    public void setForceRefresh(boolean forceRefresh) {
        this.forceRefresh = forceRefresh;
    }
}