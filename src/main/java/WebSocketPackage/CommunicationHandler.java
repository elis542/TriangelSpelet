package WebSocketPackage;

import Trianglel.TriangelSpeletOnline.User;
import Trianglel.TriangelSpeletOnline.UserHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class CommunicationHandler extends TextWebSocketHandler {

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        User user = new User(session);
        System.out.println("User connected");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        User temp = UserHandler.getUserBySession(session);
        if (temp != null) {
            UserHandler.removeUser(temp);
            System.out.println("User disconnected");
        }
    }
}
