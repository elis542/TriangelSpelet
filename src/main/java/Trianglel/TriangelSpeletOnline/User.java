package Trianglel.TriangelSpeletOnline;

import GameLogic.ActiveGame;
import org.springframework.web.socket.WebSocketSession;

public class User {
   private WebSocketSession session;
   private ActiveGame game;

    public User(WebSocketSession session) {
        this.session = session;
        UserHandler.addUser(this);
    }

    public WebSocketSession getSession() {
        return session;
    }

    public void setGame(ActiveGame game) {
        this.game = game;
    }
}
