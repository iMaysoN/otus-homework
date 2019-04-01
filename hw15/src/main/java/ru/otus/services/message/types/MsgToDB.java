package ru.otus.services.message.types;

import ru.otus.services.Addressee;
import ru.otus.services.DbService;
import ru.otus.services.message.Address;
import ru.otus.services.message.Message;

public abstract class MsgToDB extends Message {

    MsgToDB(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof DbService) {
            exec((DbService) addressee);
        }
    }

    public abstract void exec(DbService dbService);
}
