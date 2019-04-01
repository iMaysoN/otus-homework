package ru.otus.services.message;

import ru.otus.services.MessageService;

public interface Addressee {
    Address getAddress();

    MessageService getMessageService();
}
