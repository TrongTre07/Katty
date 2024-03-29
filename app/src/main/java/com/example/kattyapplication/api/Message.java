package com.example.kattyapplication.API;

public class Message {
    private int id;
    private String content;

    public Message(String content) {
        this.content = content;
    }

    public Message(int id, String content) {
        id = id;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Message{" +
                "Id=" + id +
                ", content='" + content + '\'' +
                '}';
    }
}
