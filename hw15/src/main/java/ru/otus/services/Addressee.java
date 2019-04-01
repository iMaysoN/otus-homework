package ru.otus.services;

import ru.otus.services.message.Address;

public interface Addressee {
    Address getAddress();

    MessageService getMessageService();
}
