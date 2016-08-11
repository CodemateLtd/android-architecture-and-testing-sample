package com.codemate.blogreader.data.cache.converter;

import com.codemate.blogreader.domain.model.BlogPost;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class PostJsonConverter {
    private final Gson gson;

    public PostJsonConverter() {
        gson = new Gson();
    }
    
    public List<BlogPost> convertJsonToPosts(String data) {
        Type listType = new TypeToken<List<BlogPost>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    public String convertPostsToJson(List<BlogPost> items) {
        return gson.toJson(items);
    }
}