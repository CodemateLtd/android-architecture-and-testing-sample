package com.codemate.blogreader.presentation.postdetails;

import com.codemate.blogreader.domain.model.Comment;

import java.util.List;

/**
 * Created by ironman on 05/08/16.
 */

public interface PostDetailView {
    void setProgressIndicator(boolean active);

    void showComments(List<Comment> comments);

    void onError(Throwable e);

    void hideError();
}