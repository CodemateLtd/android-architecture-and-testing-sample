package com.codemate.blogreader.presentation.posts;

import com.codemate.blogreader.FakeBlogApi;
import com.codemate.blogreader.data.network.BlogService;
import com.codemate.blogreader.domain.cache.PostCache;
import com.codemate.blogreader.domain.interactors.GetPostsInteractor;
import com.codemate.blogreader.domain.model.BlogPost;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by ironman on 26/07/16.
 */
public class PostsPresenterTest {
    @Mock
    private BlogService.BlogApi blogApi;

    @Mock
    private PostsView postsView;

    @Mock
    private PostCache postCache;

    private PostsPresenter postsPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        GetPostsInteractor getPosts = new GetPostsInteractor(
                blogApi,
                postCache,
                Schedulers.immediate(),
                Schedulers.immediate()
        );

        postsPresenter = new PostsPresenter(postsView, getPosts);
    }

    @Test
    public void loadPosts_AndDisplayInView() {
        when(blogApi.getPosts())
                .thenReturn(Observable.just(FakeBlogApi.POSTS));
        postsPresenter.loadPosts(false);

        // Verify that each of these methods get called once, in the correct order.
        InOrder inOrder = inOrder(postsView, postCache);
        inOrder.verify(postsView, times(1)).setProgressIndicator(true);
        inOrder.verify(postCache, times(1)).getAll();
        inOrder.verify(postsView, times(1)).showPosts(FakeBlogApi.POSTS);
        inOrder.verify(postsView, times(1)).setProgressIndicator(false);
    }

    @Test
    public void onError_ErrorDisplayedInUI() {
        final Throwable exception = new Exception();
        when(blogApi.getPosts())
                .thenReturn(Observable.create(new Observable.OnSubscribe<List<BlogPost>>() {
                    @Override
                    public void call(Subscriber<? super List<BlogPost>> subscriber) {
                        subscriber.onError(exception);
                    }
                }));

        postsPresenter.loadPosts(false);
        verify(postsView).showError(exception);
    }

    @Test
    public void clickOnPost_ShowsPostDetails() {
        BlogPost post = new BlogPost(69, 3, "Test post", "Test post Body.");

        postsPresenter.openPostDetails(post);
        verify(postsView).showPostDetails(post);
    }
}
