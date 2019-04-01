package ru.otus.services.message.types;

import ru.otus.services.FrontendService;
import ru.otus.services.message.Address;
import ru.otus.services.message.Addressee;
import ru.otus.services.message.Message;

public abstract class MsgToFrontend extends Message {
    public MsgToFrontend(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof FrontendService) {
            exec((FrontendService) addressee);
        }
    }

    public abstract void exec(FrontendService frontendService);
}
