package com.sparak.domain;

import org.mongodb.morphia.annotations.Embedded;

/**
 * Created by vignesh on 1/5/2016.
 */

@Embedded
public class Post {
    private int id;
    private String topic;
    private String body;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
