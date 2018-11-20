package com.codemate.blogreader.presentation.postdetails;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.codemate.blogreader.FakeBlogApi;
import com.codemate.blogreader.domain.model.BlogPost;
import com.codemate.blogreader.domain.model.Comment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.codemate.blogreader.RecyclerViewMatcher.withRecyclerView;

/**
 * Created by ironman on 03/08/16.
 */
@RunWith(AndroidJUnit4.class)
public class PostDetailsScreenTest {
    private static final BlogPost POST =
            new BlogPost(1, 69, "Blog post title.", "This is a blog post content.");

    @Rule
    public ActivityTestRule<PostDetailActivity> postDetailTestRule =
            new ActivityTestRule<>(PostDetailActivity.class, true, false);

    @Before
    public void launchDetailActivityBeforeEachTest() {
        Intent intent = new Intent();
        intent.putExtra(PostDetailActivity.EXTRA_POST_ID, POST.id);
        intent.putExtra(PostDetailActivity.EXTRA_POST_TITLE, POST.getFormattedTitle());
        intent.putExtra(PostDetailActivity.EXTRA_POST_BODY, POST.body);

        postDetailTestRule.launchActivity(intent);
    }

    @Test
    public void postDetails_DisplayedInUi() {
        onView(withId(R.id.postTitle)).check(matches(withText(POST.title)));
        onView(withId(R.id.postBody)).check(matches(withText(POST.body)));
    }

    @Test
    public void comment_DisplayedInUi() {
        Comment fakeCommentOne = FakeBlogApi.COMMENTS.get(0);

        onView(withRecyclerView(R.id.commentList).atPosition(0))
                .check(matches(hasDescendant(withText(fakeCommentOne.name))))
                .check(matches(hasDescendant(withText(fakeCommentOne.email))))
                .check(matches(hasDescendant(withText(fakeCommentOne.body))));
    }
}
