package com.codemate.blogreader.data.cache;

import android.content.Context;

import com.codemate.blogreader.data.cache.converter.CommentJsonConverter;
import com.codemate.blogreader.domain.cache.CommentCache;
import com.codemate.blogreader.domain.model.Comment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by ironman on 04/08/16.
 */
public class SharedPrefCommentCache extends SharedPrefCache implements CommentCache {
    private static final long VALIDITY_PERIOD = TimeUnit.MINUTES.toMillis(15);
    private final CommentJsonConverter converter;

    public SharedPrefCommentCache(Context context) {
        super(context, "comment_cache");

        converter = new CommentJsonConverter();
    }

    @Override
    public List<Comment> getAll(int postId) {
        setCacheKeySuffix("_post_id_" + postId);

        if (isDataStillValid(VALIDITY_PERIOD)) {
            String data = getData();

            if (data != null) {
                return converter.convertJsonToComments(getData());
            }
        }

        return new ArrayList<>(0);
    }

    @Override
    public void persistAll(int postId, List<Comment> comments) {
        String json = converter.convertCommentsToJson(comments);
        setCacheKeySuffix("_post_id_" + postId);
        persistData(json);
    }

    @Override
    public void clearAll() {
        clearCache();
    }
}
