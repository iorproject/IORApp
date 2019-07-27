package main.java.DB.Entities;

import java.util.Map;

public class CredentialsResponse {
    private Map<String,User> credentials;

    public Map<String,User> getCredentials() {
        return credentials;
    }
}
