package ru.otus.services.message.types;

import ru.otus.entity.User;
import ru.otus.services.DbService;
import ru.otus.services.message.Address;

public class MsgSaveUser extends MsgToDB {
    private final User user;

    public MsgSaveUser(Address from, Address to, User user) {
        super(from, to);
        this.user = user;
    }

    @Override
    public void exec(DbService dbService) {
        dbService.save(user);
        dbService.getMessageService().sendMessage(new MsgGetUsersAnswer(getTo(), getFrom(), dbService.getUsers()));
    }
}
