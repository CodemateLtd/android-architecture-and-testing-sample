package com.codemate.blogreader.injection;

import com.codemate.blogreader.MVPApplication;
import com.codemate.blogreader.presentation.postdetails.PostDetailActivity;
import com.codemate.blogreader.presentation.posts.PostsActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ironman on 05/08/16.
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(PostsActivity postsFragment);
    void inject(PostDetailActivity postDetailFragment);

    final class Initializer {
        private Initializer(){
        }

        public static AppComponent init(MVPApplication application) {
            return DaggerAppComponent.builder()
                    .appModule(new AppModule(application))
                    .build();
        }
    }
}