package com.codemate.blogreader.presentation.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.animation.DecelerateInterpolator;

import com.codemate.blogreader.util.ScreenUtils;

/**
 * Created by ironman on 09/08/16.
 */
public class ListItemAnimator extends DefaultItemAnimator {
    private final int itemTypeToAnimate;

    public ListItemAnimator(int itemTypeToAnimate) {
        this.itemTypeToAnimate = itemTypeToAnimate;
    }

    @Override
    public boolean animateAdd(RecyclerView.ViewHolder viewHolder) {
        if (viewHolder.getItemViewType() == itemTypeToAnimate) {
            runEnterAnimation(viewHolder);
            return false;
        }

        super.dispatchAddFinished(viewHolder);
        return false;
    }

    private void runEnterAnimation(final RecyclerView.ViewHolder viewHolder) {
        int screenHeight = ScreenUtils.getScreenHeight(viewHolder.itemView.getContext());

        viewHolder.itemView.setTranslationY(screenHeight);
        viewHolder.itemView.setScaleX(0.9f);
        viewHolder.itemView.setAlpha(0.5f);
        viewHolder.itemView.animate()
                .setInterpolator(new DecelerateInterpolator(3f))
                .setDuration(1000)
                .setStartDelay(250 + (viewHolder.getLayoutPosition() * 75))
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ListItemAnimator.super.dispatchAddFinished(viewHolder);
                    }
                })
                .translationY(0)
                .scaleX(1)
                .alpha(1)
                .start();
    }
}