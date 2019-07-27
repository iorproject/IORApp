package main.java.DB;

import dbObjects.ApproveIndicator;
import main.java.DB.Entities.Receipt;
import main.java.DB.Entities.TotalIndicator;
import main.java.DB.Entities.User;
import main.java.DB.error.FirebaseException;
import main.java.DB.error.JacksonUtilityException;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

public interface ReceiptsDAO {
    ApproveIndicator getApprovalIndicators() throws UnsupportedEncodingException, FirebaseException;
    TotalIndicator getTotalIndicator() throws UnsupportedEncodingException, FirebaseException;
    List<Receipt> getUserReceipts(String email) throws UnsupportedEncodingException, FirebaseException;
    List<Receipt> getCompanyReceiptsByUser(String email, final String company) throws UnsupportedEncodingException, FirebaseException;
    void setLastSearchMailTime(String email, Date lastUpdatedSearchTime) throws UnsupportedEncodingException, FirebaseException;
    Date getLastSearchMailTime(String email) throws UnsupportedEncodingException, FirebaseException;
    void insertReceipt(Receipt receipt) throws UnsupportedEncodingException, FirebaseException;
    List<User> getAllUsers() throws UnsupportedEncodingException, FirebaseException;
    void registerUser(User user) throws UnsupportedEncodingException, FirebaseException, JacksonUtilityException;
}
