package ru.otus.services.message;

import org.springframework.stereotype.Service;

@Service
public class MessageServiceContext {
    private final MessageServiceImpl messageServiceImpl;

    private Address frontAddress = new Address("Frontend");
    private Address dbAddress = new Address("DB");

    public MessageServiceContext(MessageServiceImpl messageSystem) {
        this.messageServiceImpl = messageSystem;
    }

    public MessageServiceImpl getMessageSystem() {
        return messageServiceImpl;
    }

    public Address getFrontAddress() {
        return frontAddress;
    }

    public void setFrontAddress(Address frontAddress) {
        this.frontAddress = frontAddress;
    }

    public Address getDbAddress() {
        return dbAddress;
    }

    public void setDbAddress(Address dbAddress) {
        this.dbAddress = dbAddress;
    }
}
