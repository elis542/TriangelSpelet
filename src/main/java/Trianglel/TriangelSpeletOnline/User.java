package Trianglel.TriangelSpeletOnline;

import org.springframework.web.socket.WebSocketSession;

public class User {
   private WebSocketSession session;

    public User(WebSocketSession session) {
        this.session = session;
        UserHandler.addUser(this);
    }

    public WebSocketSession getSession() {
        return session;
    }
}
