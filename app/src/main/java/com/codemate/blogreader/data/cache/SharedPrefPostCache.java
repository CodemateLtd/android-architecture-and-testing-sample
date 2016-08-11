package com.codemate.blogreader.data.cache;

import android.content.Context;

import com.codemate.blogreader.data.cache.converter.PostJsonConverter;
import com.codemate.blogreader.domain.cache.PostCache;
import com.codemate.blogreader.domain.model.BlogPost;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by ironman on 04/08/16.
 */
public class SharedPrefPostCache extends SharedPrefCache implements PostCache {
    private static final long VALIDITY_PERIOD = TimeUnit.HOURS.toMillis(1);
    private final PostJsonConverter converter;

    public SharedPrefPostCache(Context context) {
        super(context, "post_cache");

        converter = new PostJsonConverter();
    }

    @Override
    public List<BlogPost> getAll() {
        if (isDataStillValid(VALIDITY_PERIOD)) {
            String data = getData();

            if (data != null) {
                return converter.convertJsonToPosts(data);
            }
        }

        return new ArrayList<>(0);
    }

    @Override
    public void persistAll(List<BlogPost> items) {
        persistData(converter.convertPostsToJson(items));
    }

    @Override
    public void clearAll() {
        clearCache();
    }
}
