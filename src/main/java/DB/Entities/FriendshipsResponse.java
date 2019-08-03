package main.java.DB.Entities;

import java.util.HashMap;
import java.util.Map;

public class FriendshipsResponse {
    private Map<String,Map<String,User>> friendships;

    public Map<String,User> getFriendships(String email) {
        if(friendships.containsKey(email)){
            return friendships.get(email);
        }
        return new HashMap<>();
    }
}
