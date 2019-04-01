package ru.otus.services;

import ru.otus.services.message.Addressee;
import ru.otus.services.message.Message;

public interface MessageService {
    void addAddressee(Addressee addressee);

    void sendMessage(Message message);

    void start(Addressee addressee);

    void dispose();
}
