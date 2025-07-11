package GameLogic;
import com.aventrix.jnanoid.jnanoid.NanoIdUtils;

import java.security.SecureRandom;
import java.util.ArrayList;


public class ActiveGame {
    private String gameId;
    private static final SecureRandom random = new SecureRandom();
    private static final char[] idChars = "123456789aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVxXyYzZ".toCharArray();
    private static final int idSize = 10;
    private ArrayList<Player> players = new ArrayList<>();

    public ActiveGame() {
        createGameId();
    }

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
