package Trianglel.TriangelSpeletOnline;

import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserHandler {
    private static ArrayList<User> userList = new ArrayList<>();

    private UserHandler() {}

    public static synchronized void addUser(User user) {
        userList.add(user);
    }

    public static synchronized void removeUser(User user) throws IOException {
        user.initiateRemoval();
        userList.remove(user);
    }

    public static synchronized List<User> getUserList() {
        return userList;
    }

    public static User getUserBySession(WebSocketSession session) {
        for (User user : userList) {
            if (user.getSession() == session) {
                return user;
            }
        }
        return null;
    }
}
