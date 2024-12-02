package com.holleryo.app;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by vimal on 2014-08-03.
 */


@ParseClassName("Comment")
public class Comment extends ParseObject {

    public String commentId;
    public String comment;
    public String hollerId;
    public String userId;
    public String userName;
    public String postedOn;

    public Comment() {

    }



    public String getCommentId() {
        return commentId;
    }

    public String getComment() {
        return getString("comment");
    }


    public String getHollerId() {
        return hollerId;
    }

    public String getUserName() {
        return getString("username");
    }

    public void setHollerId(String hollerId) {
        this.hollerId = hollerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostedOn() {
        return postedOn;
    }

    public void setPostedOn(String postedOn) {
        this.postedOn = postedOn;
    }

    public ParseUser getUser() {
        return getParseUser("user");
    }
}
