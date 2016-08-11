package com.codemate.blogreader.presentation.postdetails;

import com.codemate.blogreader.FakeBlogApi;
import com.codemate.blogreader.data.network.BlogService;
import com.codemate.blogreader.domain.cache.CommentCache;
import com.codemate.blogreader.domain.interactors.GetPostCommentsInteractor;
import com.codemate.blogreader.domain.model.BlogPost;
import com.codemate.blogreader.domain.model.Comment;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by ironman on 03/08/16.
 */
public class PostDetailPresenterTest {
    @Mock
    private BlogService.BlogApi blogApi;

    @Mock
    private PostDetailView postDetailView;

    @Mock
    private CommentCache commentCache;

    private PostDetailPresenter postDetailPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        GetPostCommentsInteractor getPostComments = new GetPostCommentsInteractor(
                blogApi,
                commentCache,
                Schedulers.immediate(),
                Schedulers.immediate()
        );

        postDetailPresenter = new PostDetailPresenter(postDetailView, getPostComments);
    }

    @Test
    public void loadComments_andDisplayInView() {
        when(blogApi.getComments(1))
                .thenReturn(Observable.just(FakeBlogApi.COMMENTS));
        postDetailPresenter.loadComments(1, false);

        // Verify that each of these methods get called at once, in the correct order.
        InOrder inOrder = inOrder(postDetailView, commentCache);
        inOrder.verify(postDetailView, times(1)).setProgressIndicator(true);
        inOrder.verify(commentCache, times(1)).getAll(1);
        inOrder.verify(postDetailView, times(1)).showComments(FakeBlogApi.COMMENTS);
        inOrder.verify(postDetailView, times(1)).setProgressIndicator(false);
    }

    @Test
    public void onError_ErrorDisplayedInUI() {
        final Throwable exception = new Exception();
        when(blogApi.getComments(1))
                .thenReturn(Observable.create(new Observable.OnSubscribe<List<Comment>>() {
                    @Override
                    public void call(Subscriber<? super List<Comment>> subscriber) {
                        subscriber.onError(exception);
                    }
                }));

        postDetailPresenter.loadComments(1, false);
        verify(postDetailView).onError(exception);
    }
}
