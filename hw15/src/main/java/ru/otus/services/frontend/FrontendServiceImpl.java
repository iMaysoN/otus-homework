package ru.otus.services.frontend;

import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import ru.otus.entity.User;
import ru.otus.services.FrontendService;
import ru.otus.services.MessageService;
import ru.otus.services.message.Address;
import ru.otus.services.message.Message;
import ru.otus.services.message.MessageServiceContext;
import ru.otus.services.message.types.MsgDeleteUser;
import ru.otus.services.message.types.MsgGetUsers;
import ru.otus.services.message.types.MsgSaveUser;
import ru.otus.websocket.WebSocketMessage;

import java.util.List;

@Service
public class FrontendServiceImpl implements FrontendService {
    private final Address address;
    private final MessageServiceContext context;
    private final SimpMessageSendingOperations sendingOperations;

    public FrontendServiceImpl(MessageServiceContext context, SimpMessageSendingOperations sendingOperations) {
        this.context = context;
        this.address = context.getFrontAddress();
        this.sendingOperations = sendingOperations;
        context.getMessageSystem().addAddressee(this);
    }

    @Override
    public void saveUser(User user) {
        Message message = new MsgSaveUser(getAddress(), context.getDbAddress(), user);
        context.getMessageSystem().sendMessage(message);
    }

    @Override
    public void deleteUser(User user) {
        Message message = new MsgDeleteUser(getAddress(), context.getDbAddress(), user);
        context.getMessageSystem().sendMessage(message);
    }

    @Override
    public void getUsers() {
        Message message = new MsgGetUsers(getAddress(), context.getDbAddress());
        context.getMessageSystem().sendMessage(message);
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public MessageService getMessageService() {
        return context.getMessageSystem();
    }

    @Override
    public void sendUsers(List<User> users) {
        sendingOperations.convertAndSend("/topic/response", new WebSocketMessage("send", null, users));
    }
}
