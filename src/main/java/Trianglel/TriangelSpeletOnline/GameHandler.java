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

    public static ArrayList<ActiveGame> getGameListNotStarted() {
        ArrayList<ActiveGame> returnList = new ArrayList<>();

        for (ActiveGame game : gameList) {
            if (!game.getIsStarted()) {
                returnList.add(game);
            }
        }
        return returnList;
    }

    public static void gameChecker() {
        for (ActiveGame game : gameList) {
            if (game.getNumberOfPlayers() < 1) {
                System.out.println(game.getId() + " is inactive");
                game.setInactive();
            }
        }
        gameList.removeIf(game2 -> !game2.getActive());
    }
}
