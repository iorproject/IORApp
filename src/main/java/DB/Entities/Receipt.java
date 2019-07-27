package main.java.DB.Entities;

import java.io.Serializable;

public class Receipt implements Serializable {

    private String email;
    private String companyName;
    private String type;
    private byte[] body;
    private String creationDate;
    private int totalScoreIndicator;
    private int totalPrice;

    public Receipt(String companyName, String email, String type, byte[] body, String creationDate, int totalScoreIndicator,int totalPrice) {
        this.email = email;
        this.type = type;
        this.body = body;
        this.creationDate = creationDate;
        this.totalScoreIndicator = totalScoreIndicator;
        this.companyName = companyName;
        this.totalPrice = totalPrice;
    }

    public String getEmail() {
        return email;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getType() {
        return type;
    }

    public byte[] getBody(){
        return body;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public int getTotalScoreIndicator() {
        return totalScoreIndicator;
    }

    public int getTotalPrice(){
        return totalPrice;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}
