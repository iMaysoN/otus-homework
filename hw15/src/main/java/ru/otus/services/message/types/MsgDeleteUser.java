package ru.otus.services.message.types;

import ru.otus.entity.User;
import ru.otus.services.DbService;
import ru.otus.services.message.Address;

public class MsgDeleteUser extends MsgToDB {
    private final User user;

    public MsgDeleteUser(Address from, Address to, User user) {
        super(from, to);
        this.user = user;
    }

    @Override
    public void exec(DbService dbService) {
        dbService.deleteUser(user.getId());
        dbService.getMessageService()
                .sendMessage(new MsgGetUsersAnswer(getTo(), getFrom(), dbService.getUsers()));
    }
}
