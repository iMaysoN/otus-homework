package ru.otus.services.message.types;

import ru.otus.services.DbService;
import ru.otus.services.message.Address;

public class MsgGetUsers extends MsgToDB {

    public MsgGetUsers(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(DbService dbService) {
        dbService.getMessageService()
                .sendMessage(new MsgGetUsersAnswer(getTo(), getFrom(), dbService.getUsers()));
    }
}
