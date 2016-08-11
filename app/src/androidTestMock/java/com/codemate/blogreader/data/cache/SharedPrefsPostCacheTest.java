package com.codemate.blogreader.data.cache;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.codemate.blogreader.FakeBlogApi;
import com.codemate.blogreader.domain.model.BlogPost;
import com.codemate.blogreader.domain.cache.PostCache;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertSame;

/**
 * Created by ironman on 04/08/16.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class SharedPrefsPostCacheTest {
    private PostCache postsCache;

    @Before
    public void setUp() {
        postsCache = new SharedPrefPostCache(InstrumentationRegistry.getContext());
    }

    @Test
    public void cacheWorks() {
        postsCache.persistAll(FakeBlogApi.POSTS);

        List<BlogPost> posts = postsCache.getAll();

        assertNotNull(posts);
        assertSame(3, posts.size());

        ensurePostSame(FakeBlogApi.POSTS.get(0), posts.get(0));
        ensurePostSame(FakeBlogApi.POSTS.get(1), posts.get(1));
        ensurePostSame(FakeBlogApi.POSTS.get(2), posts.get(2));
    }

    private void ensurePostSame(BlogPost actual, BlogPost cached) {
        assertEquals(actual.userId, cached.userId);
        assertEquals(actual.id, cached.id);
        assertEquals(actual.title, cached.title);
        assertEquals(actual.body, cached.body);
    }
}
