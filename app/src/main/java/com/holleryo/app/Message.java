package com.holleryo.app;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by vimal on 2014-08-03.
 */


@ParseClassName("Message")
public class Message extends ParseObject {

    public String messageId;
    public String message;
    public String hollerId;
    public String userId;
    public String userName;
    public String postedOn;

    public Message() {

    }

    public Message(String messageId, String message, String hollerId, String userId, String userName, String postedOn) {
        this.messageId = messageId;
        this.message = message;
        this.hollerId = hollerId;
        this.userId = userId;
        this.postedOn = postedOn;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getHollerId() {
        return hollerId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
