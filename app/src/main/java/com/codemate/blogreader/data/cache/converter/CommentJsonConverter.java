package com.codemate.blogreader.data.cache.converter;

import com.codemate.blogreader.domain.model.Comment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class CommentJsonConverter {
    private final Gson gson;

    public CommentJsonConverter() {
        gson = new Gson();
    }
    
    public List<Comment> convertJsonToComments(String data) {
        Type listType = new TypeToken<List<Comment>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    public String convertCommentsToJson(List<Comment> items) {
        return gson.toJson(items);
    }
}