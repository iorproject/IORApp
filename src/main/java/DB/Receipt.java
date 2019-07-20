package main.java.DB;

import java.util.Date;

public class Receipt {
    private String companyName;
    private int totalPrice;
    private String content;
    private String attachmentUrl;
    private Date createdDate;

    public Receipt(String companyName, int totalPrice, String content, String attachmentUrl, Date createdDate) {
        this.companyName = companyName;
        this.totalPrice = totalPrice;
        this.content = content;
        this.attachmentUrl = attachmentUrl;
        this.createdDate = createdDate;
    }

    public String getCompanyName() {
        return companyName;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public String getContent() {
        return content;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public Date getCreatedDate() {
        return createdDate;
    }
}
