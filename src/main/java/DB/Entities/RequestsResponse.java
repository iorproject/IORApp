package main.java.DB.Entities;

import java.util.HashMap;
import java.util.Map;

public class RequestsResponse {
    private Map<String,Map<String,User>> requests;

    public Map<String,User> getRequests(String email) {
        if(requests.containsKey(email)){
            return requests.get(email);
        }
        return new HashMap<>();
    }
}
