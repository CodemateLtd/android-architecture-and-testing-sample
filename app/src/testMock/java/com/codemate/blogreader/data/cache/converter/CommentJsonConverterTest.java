package com.codemate.blogreader.data.cache.converter;

import com.codemate.blogreader.FakeBlogApi;
import com.codemate.blogreader.domain.model.Comment;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by ironman on 09/08/16.
 */
public class CommentJsonConverterTest {
    @Test
    public void testConverterWorks() {
        CommentJsonConverter converter = new CommentJsonConverter();

        List<Comment> original = FakeBlogApi.COMMENTS;
        String json = converter.convertCommentsToJson(original);

        assertNotNull(json);

        List<Comment> converted = converter.convertJsonToComments(json);

        assertNotNull(converted);
        assertEquals(original.size(), converted.size());
    }
}
