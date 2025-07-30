package Trianglel.TriangelSpeletOnline;

import GameLogic.ActiveGame;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class RestControllerClass {

    @GetMapping("/status")
    public ResponseEntity<Boolean> getStatus() {

        return ResponseEntity.ok(true);
    }

    public record GetServersResponse(String gameId, int numberOfPlayers) {}
    @GetMapping("/GetServers")
    public ResponseEntity<ArrayList<GetServersResponse>> getServer() {
        ArrayList<GetServersResponse> returnList = new ArrayList<>();
        ArrayList<ActiveGame> gameList = GameHandler.getGameListNotStarted();

        for (ActiveGame game : gameList) {
            returnList.add(new GetServersResponse(game.getId(), game.getNumberOfPlayers()));
        }


    return ResponseEntity.status(HttpStatus.OK).body(returnList);
    }

    public record CreateGameResponse(boolean successful, String gameId){}
    @GetMapping("/CreateGame")
    public ResponseEntity<CreateGameResponse> createGame() {
        ActiveGame newMatch = new ActiveGame();
        GameHandler.addGame(newMatch);
        System.out.println("Match created ID: " + newMatch.getId());

        CreateGameResponse answer = new CreateGameResponse(true, newMatch.getId());

        return ResponseEntity.ok(answer);
    }


    @PostMapping("/joinGame")
    public ResponseEntity<Boolean> joinGame(@RequestBody String gameId) {
        ActiveGame temp = GameHandler.getGame(gameId);
        if (temp == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }
        return ResponseEntity.ok(true);
    }
}
