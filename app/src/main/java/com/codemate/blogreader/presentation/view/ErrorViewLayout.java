package com.codemate.blogreader.presentation.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.FrameLayout;

import com.codemate.blogreader.R;

/**
 * Created by ironman on 09/08/16.
 */
public class ErrorViewLayout extends FrameLayout implements View.OnClickListener {
    private View errorView;
    private View actualView;

    private OnActionPressedListener onActionPressedListener;
    private boolean errorVisible;

    public interface OnActionPressedListener {
        void onActionPressed();
    }

    public ErrorViewLayout(Context context) {
        super(context);
        init(context);
    }

    public ErrorViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        errorView = View.inflate(context, R.layout.view_error, null);
        addView(errorView, 0);

        errorView.findViewById(R.id.errorActionButton).setOnClickListener(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        actualView = getChildAt(1);
    }

    @Override
    public void onClick(View v) {
        if (onActionPressedListener != null) {
            onActionPressedListener.onActionPressed();
        }
    }

    public void setOnActionPressedListener(OnActionPressedListener listener) {
        this.onActionPressedListener = listener;
    }

    public void showError() {
        errorView.setVisibility(VISIBLE);
            errorView.animate()
                    .setInterpolator(new BounceInterpolator())
                    .setStartDelay(500)
                    .setDuration(500)
                    .scaleX(1)
                    .scaleY(1)
                    .alpha(1)
                    .start();

        actualView.setVisibility(GONE);
    }

    public void hideError() {
        errorView.setAlpha(0);
        errorView.setScaleX(0.5f);
        errorView.setScaleY(0.5f);
        errorView.setVisibility(GONE);
        actualView.setVisibility(VISIBLE);
    }
}
