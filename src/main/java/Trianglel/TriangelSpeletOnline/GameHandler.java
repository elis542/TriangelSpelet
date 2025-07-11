package Trianglel.TriangelSpeletOnline;

import GameLogic.ActiveGame;

import java.util.ArrayList;

public class GameHandler {
    private static ArrayList<ActiveGame> gameList = new ArrayList<>();

    private GameHandler() {}

    public static void addGame(ActiveGame game) {
        gameList.add(game);
    }

    public static ActiveGame getGame(String gameId) {
        for (ActiveGame game : gameList) {
            if (game.getId().equals(gameId)) {
                return game;
            }
        }
        return null;
    }
}
