package com.codemate.blogreader.domain.model;

/**
 * Created by ironman on 26/07/16.
 */
public class Comment {
    public int postId;
    public int id;
    public String name;
    public String email;
    public String body;

    public Comment(int postId, int id, String name, String email, String body) {
        this.postId = postId;
        this.id = id;
        this.name = name;
        this.email = email;
        this.body = body;
    }
}
