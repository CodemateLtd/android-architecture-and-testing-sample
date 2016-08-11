package com.codemate.blogreader.data.cache;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.codemate.blogreader.FakeBlogApi;
import com.codemate.blogreader.domain.model.Comment;
import com.codemate.blogreader.domain.cache.CommentCache;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by ironman on 04/08/16.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class SharedPrefCommentCacheTest {
    private CommentCache commentCache;

    @Before
    public void setUp() {
        commentCache = new SharedPrefCommentCache(InstrumentationRegistry.getContext());
    }

    @Test
    public void cacheWorks() {
        int postId = FakeBlogApi.COMMENTS.get(0).postId;

        commentCache.persistAll(postId, FakeBlogApi.COMMENTS);
        List<Comment> comments = commentCache.getAll(postId);

        assertNotNull(comments);
        assertSame(3, comments.size());

        ensureCommentSame(FakeBlogApi.COMMENTS.get(0), comments.get(0));
        ensureCommentSame(FakeBlogApi.COMMENTS.get(1), comments.get(1));
        ensureCommentSame(FakeBlogApi.COMMENTS.get(2), comments.get(2));
    }

    private void ensureCommentSame(Comment actual, Comment cached) {
        assertEquals(actual.postId, cached.postId);
        assertEquals(actual.id, cached.id);
        assertEquals(actual.name, cached.name);
        assertEquals(actual.email, cached.email);
        assertEquals(actual.body, cached.body);
    }
}
