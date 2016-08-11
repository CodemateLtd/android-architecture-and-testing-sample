package com.codemate.blogreader.presentation.postdetails;

import android.content.Context;
import android.content.Intent;

import com.codemate.blogreader.util.MockIntentFactory;
import com.codemate.blogreader.domain.model.BlogPost;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by ironman on 05/08/16.
 */
public class PostDetailActivityTest {
    @Mock
    public Intent intent;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        MockIntentFactory.mockFactory(intent);
    }

    @Test
    public void intentCreatedProperly() {
        BlogPost post = new BlogPost(69, 1, "Test Post", "Test post body.");
        Intent intent = PostDetailActivity.create(mock(Context.class), post);

        verify(intent).putExtra(PostDetailActivity.EXTRA_POST_USER_ID, post.userId);
        verify(intent).putExtra(PostDetailActivity.EXTRA_POST_ID, post.id);
        verify(intent).putExtra(PostDetailActivity.EXTRA_POST_TITLE, post.title);
        verify(intent).putExtra(PostDetailActivity.EXTRA_POST_BODY, post.body);
    }

    @After
    public void tearDown() {
        MockIntentFactory.reset();
    }
}
