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
        createGameResponse answer = new createGameResponse(true, newMatch.getId());

        return ResponseEntity.ok(answer);
    }
}
