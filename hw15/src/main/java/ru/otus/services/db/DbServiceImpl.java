package ru.otus.services.db;

import org.springframework.stereotype.Service;
import ru.otus.entity.User;
import ru.otus.services.DbService;
import ru.otus.services.MessageService;
import ru.otus.services.message.Address;
import ru.otus.services.message.MessageServiceContext;

import java.util.List;

@Service
public class DbServiceImpl implements DbService {
    private final UserRepository userRepository;
    private final Address address;
    private final MessageServiceContext context;

    public DbServiceImpl(UserRepository userRepository, MessageServiceContext context) {
        this.userRepository = userRepository;
        this.context = context;
        this.address = context.getDbAddress();
        context.getMessageSystem().addAddressee(this);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.findById(id).ifPresent(userRepository::delete);
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public MessageService getMessageService() {
        return context.getMessageSystem();
    }
}
