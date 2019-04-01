package ru.otus.domain;


public class UserSimple {

    private long id;
    private String name;

    public UserSimple(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public UserSimple() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
