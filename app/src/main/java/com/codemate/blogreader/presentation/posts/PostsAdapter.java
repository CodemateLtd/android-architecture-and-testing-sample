package com.codemate.blogreader.presentation.posts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codemate.blogreader.R;
import com.codemate.blogreader.domain.model.BlogPost;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ironman on 26/07/16.
 */
public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostViewHolder> {
    public static final int ITEM_TYPE_BLOG_POST = 1;

    private final PostsActivity.PostItemListener postItemListener;
    private List<BlogPost> blogPosts;

    public PostsAdapter(List<BlogPost> blogPosts, PostsActivity.PostItemListener postItemListener) {
        this.postItemListener = postItemListener;
        this.blogPosts = blogPosts;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View postView = inflater.inflate(R.layout.item_post, parent, false);

        return new PostViewHolder(postView, postItemListener);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        BlogPost blogPost = blogPosts.get(position);

        holder.title.setText(blogPost.getFormattedTitle());
        holder.body.setText(blogPost.getConcatenatedBody());
    }

    @Override
    public int getItemCount() {
        return blogPosts.size();
    }

    @Override
    public int getItemViewType(int position) {
        return ITEM_TYPE_BLOG_POST;
    }

    public void setBlogPosts(boolean animated, List<BlogPost> blogPosts) {
        this.blogPosts = new ArrayList<>(blogPosts);

        if (animated) {
            notifyItemRangeInserted(0, blogPosts.size());
        } else {
            notifyDataSetChanged();
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public void clear() {
        blogPosts.clear();
        notifyDataSetChanged();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;
        public TextView body;

        private PostsActivity.PostItemListener postItemListener;

        public PostViewHolder(View itemView, PostsActivity.PostItemListener postItemListener) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.postTitle);
            body = (TextView) itemView.findViewById(R.id.postBody);
            this.postItemListener = postItemListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            BlogPost blogPost = blogPosts.get(position);

            postItemListener.onPostClicked(blogPost);
        }
    }
}