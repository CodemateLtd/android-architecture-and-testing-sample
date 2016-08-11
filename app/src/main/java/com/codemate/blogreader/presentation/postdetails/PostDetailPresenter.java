package com.codemate.blogreader.presentation.postdetails;

import com.codemate.blogreader.domain.interactors.GetPostCommentsInteractor;
import com.codemate.blogreader.domain.model.Comment;

import java.util.List;

import rx.Subscriber;

/**
 * Created by ironman on 28/07/16.
 */
public class PostDetailPresenter {
    private final PostDetailView postDetailView;
    private final GetPostCommentsInteractor getPostCommentsInteractor;

    public PostDetailPresenter(PostDetailView postDetailView, GetPostCommentsInteractor getPostCommentsInteractor) {
        this.postDetailView = postDetailView;
        this.getPostCommentsInteractor = getPostCommentsInteractor;
    }

    public void loadComments(int postId, boolean forceRefresh) {
        postDetailView.hideError();
        postDetailView.setProgressIndicator(true);

        getPostCommentsInteractor.setPostId(postId);
        getPostCommentsInteractor.setForceRefresh(forceRefresh);
        getPostCommentsInteractor.execute(new Subscriber<List<Comment>>() {
            @Override
            public void onCompleted() {
                postDetailView.setProgressIndicator(false);
            }

            @Override
            public void onError(Throwable e) {
                postDetailView.onError(e);
            }

            @Override
            public void onNext(List<Comment> comments) {
                postDetailView.showComments(comments);
            }
        });
    }

    public void destroy() {
        getPostCommentsInteractor.unsubscribe();
    }
}
