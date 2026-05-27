package GameLogic;
import Trianglel.TriangelSpeletOnline.ScheduledActions;
import Trianglel.TriangelSpeletOnline.User;
import WebSocketPackage.CommunicationHandler;
import com.aventrix.jnanoid.jnanoid.NanoIdUtils;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;


public class ActiveGame {
    private String gameId;

    private static final SecureRandom random = new SecureRandom();
    private static final char[] idChars = "123456789aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVxXyYzZ".toCharArray();
    private static final int idSize = 10;

    private ArrayList<User> players = new ArrayList<>();
    private boolean active = true;
    private boolean isStarted = false;
    private boolean joiningAllowed = true;

    private LinkedList<TriangelClass> triangels = new LinkedList<>();
    private TriangelClass[][] PlayingField = new TriangelClass[132][77];


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

    public void joiningOff() {
        joiningAllowed = false;
    }

    public boolean getJoining() {
        return joiningAllowed;
    }

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

    public void startGame() throws IOException, InterruptedException {
        isStarted = true;

        sendPublicMessage("startGame", true);
        players.clear();
        Thread.sleep(2000); //Allows players to join before handing out triangels
        joiningAllowed = false;
        createTriangels();
        handoutTriangels();
    }

    //GAME LOGIC BELOW

    public void handoutTriangels() throws IOException {
        for (User user : players) {
            for (int x = 0; x < 5; x++) {
                user.addTriangel(triangels.get(0));
                triangels.remove(0);
            }
            CommunicationHandler.sendTextMessage(user.getSession(),"YourTriangels", user.getTriangels());
        }
    }

    private void createTriangels() {
        triangels.add(new TriangelClass(new int[] {0, 0, 4}));
        triangels.add(new TriangelClass(new int[] {0, 4, 4}));
        triangels.add(new TriangelClass(new int[] {2, 0, 0}));
        triangels.add(new TriangelClass(new int[] {3, 2, 3}));
        triangels.add(new TriangelClass(new int[] {1, 0, 2}));
        triangels.add(new TriangelClass(new int[] {3, 2, 5}));
        triangels.add(new TriangelClass(new int[] {3, 3, 0}));
        triangels.add(new TriangelClass(new int[] {5, 5, 5}));
        triangels.add(new TriangelClass(new int[] {1, 1, 4}));
        triangels.add(new TriangelClass(new int[] {5, 0, 0}));
        triangels.add(new TriangelClass(new int[] {2, 2, 4}));
        triangels.add(new TriangelClass(new int[] {2, 5, 2}));
        triangels.add(new TriangelClass(new int[] {1, 1, 3}));
        triangels.add(new TriangelClass(new int[] {4, 2, 1}));
        triangels.add(new TriangelClass(new int[] {5, 4, 5}));
        triangels.add(new TriangelClass(new int[] {1, 1, 1}));
        triangels.add(new TriangelClass(new int[] {5, 2, 3}));
        triangels.add(new TriangelClass(new int[] {2, 4, 4}));
        triangels.add(new TriangelClass(new int[] {5, 5, 1}));
        triangels.add(new TriangelClass(new int[] {3, 3, 2}));
        triangels.add(new TriangelClass(new int[] {4, 4, 4}));
        triangels.add(new TriangelClass(new int[] {4, 3, 3}));
        triangels.add(new TriangelClass(new int[] {4, 2, 0}));
        triangels.add(new TriangelClass(new int[] {0, 2, 2}));
        triangels.add(new TriangelClass(new int[] {0, 5, 0}));
        triangels.add(new TriangelClass(new int[] {2, 1, 3}));
        triangels.add(new TriangelClass(new int[] {0, 0, 0}));
        triangels.add(new TriangelClass(new int[] {5, 2, 1}));
        triangels.add(new TriangelClass(new int[] {5, 3, 3}));
        triangels.add(new TriangelClass(new int[] {5, 4, 4}));
        triangels.add(new TriangelClass(new int[] {2, 3, 4}));
        triangels.add(new TriangelClass(new int[] {3, 3, 3}));
        triangels.add(new TriangelClass(new int[] {5, 3, 0}));
        triangels.add(new TriangelClass(new int[] {3, 5, 3}));
        triangels.add(new TriangelClass(new int[] {3, 4, 4}));
        triangels.add(new TriangelClass(new int[] {4, 3, 0}));
        triangels.add(new TriangelClass(new int[] {2, 2, 2}));
        triangels.add(new TriangelClass(new int[] {0, 1, 5}));
        triangels.add(new TriangelClass(new int[] {3, 4, 5}));
        triangels.add(new TriangelClass(new int[] {1, 3, 0}));
        triangels.add(new TriangelClass(new int[] {5, 4, 0}));
        triangels.add(new TriangelClass(new int[] {1, 1, 2}));
        triangels.add(new TriangelClass(new int[] {2, 1, 5}));
        triangels.add(new TriangelClass(new int[] {0, 3, 0}));
        triangels.add(new TriangelClass(new int[] {2, 4, 5}));
        triangels.add(new TriangelClass(new int[] {0, 5, 2}));
        triangels.add(new TriangelClass(new int[] {5, 3, 1}));
        triangels.add(new TriangelClass(new int[] {3, 1, 4}));
        triangels.add(new TriangelClass(new int[] {5, 0, 1}));
        triangels.add(new TriangelClass(new int[] {5, 1, 1}));
        triangels.add(new TriangelClass(new int[] {0, 0, 2}));
        triangels.add(new TriangelClass(new int[] {0, 1, 0}));
        triangels.add(new TriangelClass(new int[] {2, 2, 3}));
        triangels.add(new TriangelClass(new int[] {4, 0, 1}));
        triangels.add(new TriangelClass(new int[] {2, 3, 0}));
        triangels.add(new TriangelClass(new int[] {3, 1, 3}));
        triangels.add(new TriangelClass(new int[] {4, 1, 5}));

        Collections.shuffle(triangels);
    }
}
