package com.holleryo.app;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vimal on 2014-08-03.
 */
public class MessageList {

    public static List<Message> getMessages() {
        List<Message> messages = new ArrayList<Message>();

        messages.add(new Message("1", "Some message showing interest in a holler", "22", "11", "User 1", "29-10-14"));
        messages.add(new Message("2", "My message showing interest in a holler", "22", "11", "User 1", "29-10-14"));
        messages.add(new Message("3", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam consequat commodo odio, id lacinia lacus laoreet vitae. Nam faucibus sapien in dignissim sagittis", "22", "11", "", "29-10-14"));
        messages.add(new Message("4", "Quisque congue neque orci, eu vestibulum orci pretium nec. ", "22", "11", "User 2", "29-10-14"));
        messages.add(new Message("5", "Vestibulum diam leo, dictum sed lectus in, pulvinar euismod arcu. ", "22", "11", "User 3", "29-10-14"));
        messages.add(new Message("5", "Interdum et malesuada fames ac ante ipsum primis in faucibus", "22", "11", "User 3", "29-10-14"));
        messages.add(new Message("5", "Fusce imperdiet leo eget arcu porttitor rhoncus. Integer sodales justo sed massa malesuada dictum. ", "22", "11", "User 3", "29-10-14"));
        messages.add(new Message("5", "Nunc at purus accumsan risus aliquam consectetur. Duis dignissim tellus convallis sapien", "22", "11", "User 3", "29-10-14"));


        return messages;
    }
}
