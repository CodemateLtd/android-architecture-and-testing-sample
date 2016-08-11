package com.codemate.blogreader.domain.cache;

import com.codemate.blogreader.domain.model.BlogPost;

import java.util.List;

/**
 * Created by ironman on 04/08/16.
 */
public interface PostCache {
    List<BlogPost> getAll();
    void persistAll(List<BlogPost> items);
    void clearAll();
}
