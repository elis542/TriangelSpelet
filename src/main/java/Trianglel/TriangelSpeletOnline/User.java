package Trianglel.TriangelSpeletOnline;

import GameLogic.ActiveGame;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public class User {
   private WebSocketSession session;
   private ActiveGame game;
   private String name = "NoName";

    public User(WebSocketSession session) {
        this.session = session;
        UserHandler.addUser(this);
    }

    public WebSocketSession getSession() {
        return session;
    }

    public void setGame(ActiveGame game) throws IOException {
        this.game = game;
        try {
            game.addPlayer(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ActiveGame getGame() {
        return game;
    }

    public void setName(String name) {this.name = name;}

    public String getName() {return name;}

    public void initiateRemoval() throws IOException {
        if (game != null) {
            game.removePlayer(this);
        } else {
            return;
        }
    }
}
