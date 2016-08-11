package com.codemate.blogreader.domain.model;

/**
 * Created by ironman on 26/07/16.
 */
public class BlogPost {
    private static final int MAX_BODY_LENGTH = 100;

    public int userId;
    public int id;
    public String title;
    public String body;

    public BlogPost(int userId, int id, String title, String body) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public String getFormattedTitle() {
        return title.substring(0, 1).toUpperCase() + title.substring(1, title.length());
    }

    public String getConcatenatedBody() {
        boolean tooLong = body != null && body.length() > MAX_BODY_LENGTH;

        if (!tooLong) {
            return body;
        }

        return body.substring(0, MAX_BODY_LENGTH) + "...";
    }
}
