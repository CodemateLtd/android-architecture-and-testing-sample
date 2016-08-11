package com.codemate.blogreader.presentation.postdetails;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codemate.blogreader.R;
import com.codemate.blogreader.domain.model.BlogPost;
import com.codemate.blogreader.domain.model.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ironman on 03/08/16.
 */
public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder> {
    public static final int ITEM_TYPE_COMMENT = 2;

    private List<Comment> comments;

    public CommentsAdapter(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View commentView = inflater.inflate(R.layout.item_comment, parent, false);

        return new CommentViewHolder(commentView);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        Comment comment = comments.get(position);

        holder.name.setText(comment.name);
        holder.email.setText(comment.email);
        holder.body.setText(comment.body);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    @Override
    public int getItemViewType(int position) {
        return ITEM_TYPE_COMMENT;
    }

    public void setComments(boolean animated, List<Comment> comments) {
        this.comments = new ArrayList<>(comments);

        if (animated) {
            notifyItemRangeInserted(0, comments.size());
        } else {
            notifyDataSetChanged();
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public void clear() {
        comments.clear();
        notifyDataSetChanged();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        public ImageView profilePic;
        public TextView name;
        public TextView email;
        public TextView body;

        public CommentViewHolder(View itemView) {
            super(itemView);

            profilePic = (ImageView) itemView.findViewById(R.id.commentProfilePic);
            name = (TextView) itemView.findViewById(R.id.commentName);
            email = (TextView) itemView.findViewById(R.id.commentEmail);
            body = (TextView) itemView.findViewById(R.id.commentBody);
        }
    }
}
