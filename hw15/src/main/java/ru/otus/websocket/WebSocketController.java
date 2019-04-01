package ru.otus.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.otus.services.FrontendService;

@Controller
public class WebSocketController {
    private final FrontendService frontendService;

    public WebSocketController(FrontendService frontendService) {
        this.frontendService = frontendService;
    }

    @MessageMapping({"/connect","/message"})
    @SendTo("/topic/response")
    public void getUsers() {
        frontendService.getUsers();
    }

    @MessageMapping("/save")
    @SendTo("/topic/response")
    public void saveUser(WebSocketMessage message) {
        if (message.getUser() != null) {
            frontendService.saveUser(message.getUser());
        }
    }

    @MessageMapping("/delete")
    @SendTo("/topic/response")
    public void deleteUser(WebSocketMessage message) {
        if (message.getUser() != null) {
            frontendService.deleteUser(message.getUser());
        }
    }
}
