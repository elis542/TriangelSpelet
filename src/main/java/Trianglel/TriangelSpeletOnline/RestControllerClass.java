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

    @GetMapping("/GetServers")
    public ResponseEntity<ArrayList<ActiveGame>> getServer() {

    return ResponseEntity.status(HttpStatus.OK).body(new ArrayList<ActiveGame>()); //Ska fixas
    }

    private record createGameResponse(boolean successful, String gameId){};
    @GetMapping("/CreateGame")
    public ResponseEntity<createGameResponse> createGame() {
        ActiveGame newMatch = new ActiveGame();
        GameHandler.addGame(newMatch);
        System.out.println("Match created ID: " + newMatch.getId());

        createGameResponse answer = new createGameResponse(true, newMatch.getId());

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
