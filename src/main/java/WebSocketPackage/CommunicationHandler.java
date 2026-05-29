package WebSocketPackage;

import GameLogic.ActiveGame;
import Trianglel.TriangelSpeletOnline.GameHandler;
import Trianglel.TriangelSpeletOnline.User;
import Trianglel.TriangelSpeletOnline.UserHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class CommunicationHandler extends TextWebSocketHandler {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static void sendTextMessage(WebSocketSession session, String type, Object data) throws IOException {
        ObjectNode message = mapper.createObjectNode();
        message.put("type", type);

        if (data == null) {
            message.putNull("data");
        } else {
            JsonNode node = mapper.valueToTree(data);
            message.set("data", node);
        }

        message.put("timestamp", System.currentTimeMillis());
        session.sendMessage(new TextMessage(message.toString()));
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        User user = new User(session);
        System.out.println("User connected");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        User commandUser = UserHandler.getUserBySession(session);
        if (commandUser == null) {
            //session.sendMessage(new TextMessage(message.getPayload()));
            return;
        }
        ActiveGame game = null;
        if (commandUser.getGame() != null) {
            game = commandUser.getGame();
        }

        JsonNode typeNode = mapper.readTree(message.getPayload());
        String type = typeNode.get("type").asText();

        switch (type) {
            case "chatMessage":
                JsonNode dataRoot = typeNode.get("data");
                String msg = "";

                if (dataRoot != null && dataRoot.has("message")) {
                    msg = dataRoot.get("message").asText(); //Todo: This needs to be cleaned to prevent XSS attacks, same with the name selection
                }

                System.out.println("ChatMessage in game: " + msg);

                commandUser.getGame().sendChat(commandUser.getName(), msg);
                break;

            case "connect":
                String gameId = typeNode.get("data").asText();
                game = GameHandler.getGame(gameId);

                record responseConnect(boolean state, ArrayList<String> players, boolean isStarted) {}

                if (game == null) {
                    sendTextMessage(session, "connection", new responseConnect(false, null, false));
                    return;
                } else {
                    commandUser.setGame(game);
                    sendTextMessage(session, "connection", new responseConnect(true, game.getPlayerNames(), game.getIsStarted()));
                }
                break;

            case "setName":
                String name= typeNode.get("data").asText();

                if (name == null) {
                    break;
                }

                commandUser.setName(name);
                break;

            case "startGame": //data -> is just a simple "true" boolean;
                ActiveGame gameToStart = commandUser.getGame();
                if (game == null) {return;}
                game.startGame();
                break;

            case  "doMove": //Player places a triangle TODO: this is not done
                if (game == null) {return;}
                JsonNode node = typeNode.get("data");

                try {
                    int from = Integer.parseInt(node.get("from").asText());
                    int toX = Integer.parseInt(node.get("toX").asText());
                    int toY = Integer.parseInt(node.get("toY").asText());
                    int rotation = Integer.parseInt(node.get("rotation").asText());
                } catch (NumberFormatException e) {
                    return;
                }


        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws IOException {
        User temp = UserHandler.getUserBySession(session);
        if (temp != null) {
            try {
                UserHandler.removeUser(temp);
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("User disconnected");
        }
    }
}
