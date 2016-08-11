package com.codemate.blogreader.data.cache.converter;

import com.codemate.blogreader.FakeBlogApi;
import com.codemate.blogreader.domain.model.BlogPost;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by ironman on 09/08/16.
 */
public class PostJsonConverterTest {
    @Test
    public void testConverterWorks() {
        PostJsonConverter converter = new PostJsonConverter();

        List<BlogPost> original = FakeBlogApi.POSTS;
        String json = converter.convertPostsToJson(original);

        assertNotNull(json);

        List<BlogPost> converted = converter.convertJsonToPosts(json);

        assertNotNull(converted);
        assertEquals(original.size(), converted.size());
    }
}
