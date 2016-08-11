package com.codemate.blogreader.domain.interactors;

import android.util.Log;

import com.codemate.blogreader.data.network.BlogService;
import com.codemate.blogreader.domain.cache.CommentCache;
import com.codemate.blogreader.domain.model.Comment;

import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.functions.Func1;

/**
 * Created by ironman on 28/07/16.
 */
public class GetPostCommentsInteractor extends BaseInteractor<List<Comment>> {
    private final BlogService.BlogApi blogApi;
    private final CommentCache commentCache;

    private int postId;
    private boolean forceRefresh;

    public GetPostCommentsInteractor(BlogService.BlogApi blogApi, CommentCache commentCache,
                                     Scheduler worker, Scheduler observer) {
        super(worker, observer);
        this.blogApi = blogApi;
        this.commentCache = commentCache;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    @Override
    protected Observable<List<Comment>> buildObservable() {
        if (forceRefresh) {
            commentCache.clearAll();
        }

        List<Comment> cachedComments = commentCache.getAll(postId);

        if (cachedComments.size() > 0) {
            Log.d("GetComments", "using cached comments");
            return Observable.just(cachedComments);
        }

        return blogApi.getComments(postId)
                .filter(new Func1<List<Comment>, Boolean>() {
                    @Override
                    public Boolean call(List<Comment> comments) {
                        commentCache.persistAll(postId, comments);
                        return true;
                    }
                });
    }

    @Override
    public void setForceRefresh(boolean forceRefresh) {
        this.forceRefresh = forceRefresh;
    }
}