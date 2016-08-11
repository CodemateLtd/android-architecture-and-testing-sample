package com.codemate.blogreader.presentation.posts;

import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.codemate.blogreader.R;
import com.codemate.blogreader.RecyclerViewMatcher;
import com.codemate.blogreader.domain.model.BlogPost;
import com.codemate.blogreader.FakeBlogApi;
import com.codemate.blogreader.presentation.postdetails.PostDetailActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by ironman on 26/07/16.
 */
@RunWith(AndroidJUnit4.class)
public class PostsScreenTest {
    @Rule
    public ActivityTestRule<PostsActivity> postsActivityActivityTestRule =
            new ActivityTestRule<>(PostsActivity.class);

    @Test
    public void testPosts_DisplayedInUi() {
        BlogPost fakePostOne = FakeBlogApi.POSTS.get(0);

        onView(RecyclerViewMatcher.withRecyclerView(R.id.postList).atPosition(0))
                .check(matches(hasDescendant(withText(fakePostOne.getFormattedTitle()))))
                .check(matches(hasDescendant(withText(fakePostOne.getConcatenatedBody()))));
    }

    @Test
    public void testClickingPost_OpensDetails() {
        Intents.init();

        onView(withId(R.id.postList))
                .perform(actionOnItemAtPosition(0, click()));

        intended(toPackage("com.codemate.blogreader.mock"));
        intended(hasExtraWithKey(PostDetailActivity.EXTRA_POST_ID));
        intended(hasExtraWithKey(PostDetailActivity.EXTRA_POST_TITLE));
        intended(hasExtraWithKey(PostDetailActivity.EXTRA_POST_BODY));

        Intents.release();
    }
}
