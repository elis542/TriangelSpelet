package GameLogic;
import Trianglel.TriangelSpeletOnline.User;
import WebSocketPackage.CommunicationHandler;
import com.aventrix.jnanoid.jnanoid.NanoIdUtils;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;


public class ActiveGame {
    private String gameId;
    private static final SecureRandom random = new SecureRandom();
    private static final char[] idChars = "123456789aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVxXyYzZ".toCharArray();
    private static final int idSize = 10;
    private ArrayList<User> players = new ArrayList<>();
    private boolean active = true;
    private boolean isStarted = false;

    private void sendPublicMessage(String type, Object data) throws IOException {
        for (User user : players) {
            CommunicationHandler.sendTextMessage(user.getSession(), type, data);
        }
    }

    record chatMessageData(String name, String msg){}
    public void sendChat(String name, String msg) throws IOException {

        sendPublicMessage("chatMessage", new chatMessageData(name, msg));
    }

    public ArrayList<String> getPlayerNames() {
        ArrayList<String> playerNames  = new ArrayList<>();
        for (User player : players) {
            playerNames.add(player.getName());
        }
        return playerNames;
    }

    record updatePlayersData(String action, String name){}
    public void addPlayer(User player) throws IOException {
        sendPublicMessage("playerUpdate", new updatePlayersData("add", player.getName()));
        players.add(player);
    }

    public void removePlayer(User player) throws IOException {
        players.remove(player);
        sendPublicMessage("playerUpdate", new updatePlayersData("remove", player.getName()));

    }

    public ActiveGame() {
        createGameId();
    }

    public boolean getActive() {return active;}

    public boolean getIsStarted() {return isStarted;}

    public void setInactive() {active = false;}

    private void createGameId() {
        gameId = NanoIdUtils.randomNanoId(random, idChars, idSize);
    }

    public String getId() {
        return gameId;
    }

    public int getNumberOfPlayers() {
        return players.size();
    }
}
