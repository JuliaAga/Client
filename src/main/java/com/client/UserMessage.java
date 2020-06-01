package com.client;

/*Класс хранит используемые из сообщения пользователся поля*/
public class UserMessage {
    private String name;
    private String secondname;
    private String message;

    public String getName() {
        return name;
    }

    public String getSecondname() {
        return secondname;
    }

    public String getMessage() {
        return message;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSecondname(String secondname) {
        this.secondname = secondname;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
