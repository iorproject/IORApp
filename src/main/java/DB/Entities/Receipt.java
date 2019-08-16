package main.java.DB.Entities;

import java.io.Serializable;
import java.util.Date;

public class Receipt implements Serializable {

    private long id;
    private String email;
    private String companyName;
    private eContentType type;
    private Date creationDate;
    private byte[] body;
    private int totalScoreIndicator;
    private String currency;
    private float totalPrice;
    private String fileName;
    private String receiptNumber;
    private String attachmentURL;

    public Receipt(String companyName, String email, eContentType type,
                   Date creationDate, byte[] body ,String currency, float totalPrice, String fileName, String receiptNumber) {
        this.email = email;
        this.type = type;
        this.creationDate = creationDate;
        this.body = body;
        this.companyName = companyName;
        this.currency = currency;
        this.totalPrice = totalPrice;
        this.fileName = fileName;
        this.receiptNumber = receiptNumber;
    }

    public Receipt(String companyName, String email, eContentType type, Date creationDate, String currency, float totalPrice) {
        this.email = email;
        this.type = type;
        this.creationDate = creationDate;
        this.companyName = companyName;
        this.currency = currency;
        this.totalPrice = totalPrice;
    }

    public String getEmail() {
        return email;
    }

    public String getCompanyName() {
        return companyName;
    }

    public eContentType getType() {
        return type;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public int getTotalScoreIndicator() {
        return totalScoreIndicator;
    }

    public String getCurrency() { return currency; }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void eContentType(eContentType type) {
        this.type = type;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getFileName() {
        return fileName;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setId(long id){
        this.id = id;
    }

    public long getId(){
        return id;
    }

    public byte[] resetBody() {
        return body = null;
    }

    public byte[] getBody() {
        return body;
    }

    public String getAttachmentURL() {
        return attachmentURL;
    }

    public void setAttachmentURL(String attachmentURL) {
        this.attachmentURL = attachmentURL;
    }
}
