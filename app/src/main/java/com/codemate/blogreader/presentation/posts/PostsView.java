package com.codemate.blogreader.presentation.posts;

import com.codemate.blogreader.domain.model.BlogPost;

import java.util.List;

/**
 * Created by ironman on 26/07/16.
 */
public interface PostsView {
    void setProgressIndicator(boolean active);

    void showPosts(List<BlogPost> blogPosts);

    void showPostDetails(BlogPost post);

    void showError(Throwable throwable);

    void hideError();
}