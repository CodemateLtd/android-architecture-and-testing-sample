package com.codemate.blogreader.presentation.postdetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.codemate.blogreader.MVPApplication;
import com.codemate.blogreader.R;
import com.codemate.blogreader.domain.interactors.GetPostCommentsInteractor;
import com.codemate.blogreader.domain.model.BlogPost;
import com.codemate.blogreader.domain.model.Comment;
import com.codemate.blogreader.presentation.anim.ListItemAnimator;
import com.codemate.blogreader.presentation.view.ErrorViewLayout;
import com.codemate.blogreader.util.IntentFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by ironman on 26/07/16.
 */
public class PostDetailActivity extends AppCompatActivity implements PostDetailView {
    public static final String EXTRA_POST_USER_ID = "USER_ID";
    public static final String EXTRA_POST_ID = "POST_ID";
    public static final String EXTRA_POST_TITLE = "POST_TITLE";
    public static final String EXTRA_POST_BODY = "POST_BODY";

    @Inject
    GetPostCommentsInteractor getPostCommentsInteractor;

    private boolean shouldAnimate = true;
    private PostDetailPresenter presenter;
    private CommentsAdapter commentsAdapter;
    private int postId;

    private SwipeRefreshLayout refreshLayout;
    private ErrorViewLayout errorLayout;
    private RecyclerView commentList;

    public static Intent create(Context context, BlogPost blogPost) {
        Intent intent = IntentFactory.createIntent(context, PostDetailActivity.class);
        intent.putExtra(PostDetailActivity.EXTRA_POST_USER_ID, blogPost.userId);
        intent.putExtra(PostDetailActivity.EXTRA_POST_ID, blogPost.id);
        intent.putExtra(PostDetailActivity.EXTRA_POST_TITLE, blogPost.getFormattedTitle());
        intent.putExtra(PostDetailActivity.EXTRA_POST_BODY, blogPost.body);

        return intent;
    }

    private BlogPost getPostFromIntent() {
        Intent intent = getIntent();

        return new BlogPost(
                intent.getIntExtra(EXTRA_POST_USER_ID, 0),
                intent.getIntExtra(EXTRA_POST_ID, 0),
                intent.getStringExtra(EXTRA_POST_TITLE),
                intent.getStringExtra(EXTRA_POST_BODY)
        );
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        if (savedInstanceState != null) {
            shouldAnimate = false;
        }

        MVPApplication.component().inject(this);
        setUpActionBar();

        postId = getIntent().getIntExtra(EXTRA_POST_ID, 0);
        presenter = new PostDetailPresenter(this, getPostCommentsInteractor);
        commentsAdapter = new CommentsAdapter(new ArrayList<Comment>(0));

        setUpViews();
    }

    private void setUpActionBar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    private void setUpViews() {
        BlogPost post = getPostFromIntent();
        ((TextView) findViewById(R.id.postTitle)).setText(post.getFormattedTitle());
        ((TextView) findViewById(R.id.postBody)).setText(post.body);

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        errorLayout = (ErrorViewLayout) findViewById(R.id.errorLayout);
        commentList = (RecyclerView) findViewById(R.id.commentList);

        refreshLayout.setColorSchemeColors(
                ContextCompat.getColor(this, R.color.colorPrimary),
                ContextCompat.getColor(this, R.color.colorAccent),
                ContextCompat.getColor(this, R.color.colorPrimaryDark)
        );

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                shouldAnimate = true;

                commentsAdapter.clear();
                presenter.loadComments(postId, true);
            }
        });

        commentList.setAdapter(commentsAdapter);
        commentList.setLayoutManager(new LinearLayoutManager(this));
        commentList.setItemAnimator(new ListItemAnimator(CommentsAdapter.ITEM_TYPE_COMMENT));

        errorLayout.setOnActionPressedListener(new ErrorViewLayout.OnActionPressedListener() {
            @Override
            public void onActionPressed() {
                presenter.loadComments(postId, true);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        if (commentsAdapter.isEmpty()) {
            presenter.loadComments(postId, false);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setProgressIndicator(final boolean active) {
        if (refreshLayout == null) {
            return;
        }

        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(active);
            }
        });
    }

    @Override
    public void showComments(List<Comment> comments) {
        commentsAdapter.setComments(shouldAnimate, comments);
    }

    @Override
    public void onError(Throwable e) {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
            }
        });

        errorLayout.showError();
    }

    @Override
    public void hideError() {
        errorLayout.hideError();
    }
}
