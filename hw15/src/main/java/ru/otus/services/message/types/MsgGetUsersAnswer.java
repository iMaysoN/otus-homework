package ru.otus.services.message.types;

import ru.otus.entity.User;
import ru.otus.services.FrontendService;
import ru.otus.services.message.Address;

import java.util.List;

public class MsgGetUsersAnswer extends MsgToFrontend {
    private final List<User> users;

    MsgGetUsersAnswer(Address from, Address to, List<User> users) {
        super(from, to);
        this.users = users;
    }

    @Override
    public void exec(FrontendService frontendService) {
        frontendService.sendUsers(users);
    }
}
