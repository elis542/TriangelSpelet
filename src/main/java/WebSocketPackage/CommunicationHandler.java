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
        User temp = UserHandler.getUserBySession(session);
        if (temp == null) {
            //session.sendMessage(new TextMessage(message.getPayload()));
            return;
        }

        JsonNode typeNode = mapper.readTree(message.getPayload());
        String type = typeNode.get("type").asText();

        switch (type) {
            case "chatMessage":
                //System.out.println("ChatMessage in game" + temp.);
                return;

            case "connect":
                String gameId = typeNode.get("data").asText();

                ActiveGame game = GameHandler.getGame(gameId);

                record responseConnect(boolean state, ArrayList<String> players) {}

                if (game == null) {
                    sendTextMessage(session, "connection", new responseConnect(false, null));
                    System.out.println("Player tried connecting to nonexistent game");
                    return;
                } else {
                    temp.setGame(game);

                    sendTextMessage(session, "connection", new responseConnect(true, game.getPlayerNames()));
                    System.out.println("Player connected to game!");
                }
                return;

            case "setName":
                String name = typeNode.get("data").asText();
                System.out.println(name);
                temp.setName(name);
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
