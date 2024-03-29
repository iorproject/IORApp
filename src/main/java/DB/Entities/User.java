package main.java.DB.Entities;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private String email;
    private String name;
    private String accessToken;
    private String refreshToken;
    private Date registerDate;
    private String profileImage;

    public User(String email, String name, String accessToken, String refreshToken, Date registerDate) {
        this.email = email;
        this.name = name;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.registerDate = registerDate;
    }

    public User(String email, String name, Date registerDate, String profileImage) {
        this.email = email;
        this.name = name;
        this.registerDate = registerDate;
        this.profileImage = profileImage;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getEmail() {
        return email;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public String getName() { return name; }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }


}
