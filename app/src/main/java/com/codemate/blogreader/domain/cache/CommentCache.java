package com.codemate.blogreader.domain.cache;

import com.codemate.blogreader.domain.model.Comment;

import java.util.List;

/**
 * Created by ironman on 04/08/16.
 */
public interface CommentCache {
    List<Comment> getAll(int postId);
    void persistAll(int postId, List<Comment> comments);
    void clearAll();
}
