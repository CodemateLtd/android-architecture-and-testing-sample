package com.codemate.blogreader.injection;

import android.app.Application;

import com.codemate.blogreader.FakeBlogApi;
import com.codemate.blogreader.MVPApplication;
import com.codemate.blogreader.data.cache.SharedPrefCommentCache;
import com.codemate.blogreader.data.cache.SharedPrefPostCache;
import com.codemate.blogreader.domain.interactors.GetPostCommentsInteractor;
import com.codemate.blogreader.domain.interactors.GetPostsInteractor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.schedulers.Schedulers;

/**
 * Created by ironman on 05/08/16.
 */
@Module
public class AppModule {
    MVPApplication application;

    public AppModule(MVPApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    protected Application provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    GetPostsInteractor provideGetPosts() {
        return new GetPostsInteractor(
                new FakeBlogApi(),
                new SharedPrefPostCache(application),
                Schedulers.immediate(),
                Schedulers.immediate()
        );
    }

    @Provides
    @Singleton
    GetPostCommentsInteractor provideGetPostComments() {
        return new GetPostCommentsInteractor(
                new FakeBlogApi(),
                new SharedPrefCommentCache(application),
                Schedulers.immediate(),
                Schedulers.immediate()
        );
    }
}