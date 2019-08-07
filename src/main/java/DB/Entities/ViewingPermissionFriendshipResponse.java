package main.java.DB.Entities;

import java.util.HashMap;
import java.util.Map;

public class ViewingPermissionFriendshipResponse {
    private Map<String,User> viewingPermission;

    public Map<String,User> getViewingPermissionFriendships(String email) {
            return viewingPermission;
    }
}
