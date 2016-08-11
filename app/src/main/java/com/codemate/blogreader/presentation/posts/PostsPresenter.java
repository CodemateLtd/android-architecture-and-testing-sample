package com.codemate.blogreader.presentation.posts;

import com.codemate.blogreader.domain.interactors.GetPostsInteractor;
import com.codemate.blogreader.domain.model.BlogPost;

import java.util.List;

import rx.Subscriber;

/**
 * Created by ironman on 26/07/16.
 */
public class PostsPresenter {
    private final PostsView postsView;
    private final GetPostsInteractor getPostsInteractor;

    public PostsPresenter(PostsView postsView, GetPostsInteractor getPostsInteractor) {
        this.postsView = postsView;
        this.getPostsInteractor = getPostsInteractor;
    }

    public void loadPosts(boolean forceRefresh) {
        postsView.hideError();
        postsView.setProgressIndicator(true);

        getPostsInteractor.setForceRefresh(forceRefresh);
        getPostsInteractor.execute(new Subscriber<List<BlogPost>>() {
            @Override
            public void onCompleted() {
                postsView.setProgressIndicator(false);
            }

            @Override
            public void onError(Throwable e) {
                postsView.showError(e);
            }

            @Override
            public void onNext(List<BlogPost> blogPosts) {
                postsView.showPosts(blogPosts);
            }
        });
    }

    public void openPostDetails(BlogPost blogPost) {
        postsView.showPostDetails(blogPost);
    }

    public void destroy() {
        getPostsInteractor.unsubscribe();
    }
}
