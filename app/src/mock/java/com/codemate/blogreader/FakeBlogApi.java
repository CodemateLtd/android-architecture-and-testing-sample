package com.codemate.blogreader;

import com.codemate.blogreader.data.network.BlogService;
import com.codemate.blogreader.domain.model.BlogPost;
import com.codemate.blogreader.domain.model.Comment;

import java.util.Arrays;
import java.util.List;

import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by ironman on 03/08/16.
 */
public class FakeBlogApi implements BlogService.BlogApi {
    public static final List<BlogPost> POSTS = Arrays.asList(
            new BlogPost(1, 69, "Example BlogPost One", "Example One BlogPost Body"),
            new BlogPost(2, 96, "Example BlogPost Two", "Example Two BlogPost Body"),
            new BlogPost(3, 96, "Example BlogPost Three", "Example Three BlogPost Body")
    );

    public static final List<Comment> COMMENTS = Arrays.asList(
            new Comment(1, 1, "Jorma", "jor@ma", "This is a nice little comment."),
            new Comment(1, 2, "Jarmo", "jar@mo", "I like turtles."),
            new Comment(1, 3, "Tarmo", "tar@mo", "Ken Lee, tulibudibudouchoo.")
    );

    @Override
    public Observable<List<BlogPost>> getPosts() {
        return Observable.just(POSTS);
    }

    @Override
    public Observable<List<Comment>> getComments(@Query("postId") int postId) {
        return Observable.just(COMMENTS);
    }
}
