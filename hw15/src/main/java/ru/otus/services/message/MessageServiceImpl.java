package ru.otus.services.message;

import org.springframework.stereotype.Service;
import ru.otus.services.MessageService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class MessageServiceImpl implements MessageService {
    private final static Logger logger = Logger.getLogger(MessageServiceImpl.class.getName());

    private final List<Thread> workers;
    private final Map<Address, LinkedBlockingQueue<Message>> messagesMap;

    public MessageServiceImpl() {
        workers = new ArrayList<>();
        messagesMap = new HashMap<>();
    }

    @Override
    public void addAddressee(Addressee addressee) {
        messagesMap.put(addressee.getAddress(), new LinkedBlockingQueue<>());
        start(addressee);
    }

    @Override
    public void sendMessage(Message message) {
        messagesMap.get(message.getTo()).add(message);
    }

    @Override
    public void start(Addressee addressee) {
        Address address = addressee.getAddress();
        String name = "Message-service-workers-" + address.getId();
        Thread thread = new Thread(() -> {
            LinkedBlockingQueue<Message> queue = messagesMap.get(address);
            while (true) {
                try {
                    Message message = queue.take();
                    message.exec(addressee);
                } catch (InterruptedException e) {
                    logger.log(Level.INFO, "Thread interrupted. Finishing: " + name);
                    return;
                }
            }
        });
        thread.setName(name);
        thread.start();
        workers.add(thread);
    }

    @Override
    public void dispose() {
        workers.forEach(Thread::interrupt);
    }
}
