package com.codemate.blogreader.presentation.posts;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.codemate.blogreader.MVPApplication;
import com.codemate.blogreader.R;
import com.codemate.blogreader.domain.interactors.GetPostsInteractor;
import com.codemate.blogreader.domain.model.BlogPost;
import com.codemate.blogreader.presentation.anim.ListItemAnimator;
import com.codemate.blogreader.presentation.postdetails.PostDetailActivity;
import com.codemate.blogreader.presentation.view.ErrorViewLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class PostsActivity extends AppCompatActivity implements PostsView {
    @Inject
    GetPostsInteractor getPostsInteractor;

    private boolean shouldAnimate = true;
    private PostsPresenter presenter;
    private PostsAdapter postAdapter;

    private RecyclerView postList;
    private ErrorViewLayout errorLayout;
    private SwipeRefreshLayout refreshLayout;
    
    PostItemListener postItemListener = new PostItemListener() {
        @Override
        public void onPostClicked(BlogPost clickedBlogPost) {
            presenter.openPostDetails(clickedBlogPost);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

        if (savedInstanceState != null) {
            shouldAnimate = false;
        }
        
        MVPApplication.component().inject(this);
        setUpActionBar();

        presenter = new PostsPresenter(this, getPostsInteractor);
        postAdapter = new PostsAdapter(new ArrayList<BlogPost>(0), postItemListener);
        
        setUpViews();
    }

    private void setUpActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setUpViews() {
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        errorLayout = (ErrorViewLayout) findViewById(R.id.errorLayout);
        postList = (RecyclerView) findViewById(R.id.postList);

        refreshLayout.setColorSchemeColors(
                ContextCompat.getColor(this, R.color.colorPrimary),
                ContextCompat.getColor(this, R.color.colorAccent),
                ContextCompat.getColor(this, R.color.colorPrimaryDark)
        );

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                shouldAnimate = true;

                postAdapter.clear();
                presenter.loadPosts(true);
            }
        });

        postList.setAdapter(postAdapter);
        postList.setLayoutManager(new LinearLayoutManager(this));
        postList.setItemAnimator(new ListItemAnimator(PostsAdapter.ITEM_TYPE_BLOG_POST));

        errorLayout.setOnActionPressedListener(new ErrorViewLayout.OnActionPressedListener() {
            @Override
            public void onActionPressed() {
                presenter.loadPosts(true);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        if (postAdapter.isEmpty()) {
            presenter.loadPosts(false);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destroy();
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
    public void showPosts(List<BlogPost> blogPosts) {
        postAdapter.setBlogPosts(shouldAnimate, blogPosts);
    }

    @Override
    public void showPostDetails(BlogPost post) {
        startActivity(PostDetailActivity.create(this, post));
    }

    @Override
    public void showError(Throwable e) {
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

    public interface PostItemListener {
        void onPostClicked(BlogPost clickedBlogPost);
    }
}
