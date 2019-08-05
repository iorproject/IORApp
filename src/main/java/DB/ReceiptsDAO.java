package main.java.DB;

import dbObjects.ApproveIndicator;
import main.java.DB.Entities.CompanyLogo;
import main.java.DB.Entities.Receipt;
import main.java.DB.Entities.TotalIndicator;
import main.java.DB.Entities.User;

import java.util.Date;
import java.util.List;

public interface ReceiptsDAO {
    ApproveIndicator getApprovalIndicators() throws Throwable;
    TotalIndicator getTotalIndicator() throws Throwable;
    List<Receipt> getUserReceipts(String email) throws Throwable;
    List<Receipt> getCompanyReceiptsByUser(String email, final String company) throws Throwable;
    void setLastSearchMailTime(String email, Date lastUpdatedSearchTime) throws Throwable;
    Date getLastSearchMailTime(String email) throws Throwable;
    void insertReceipt(Receipt receipt) throws Throwable;
    List<User> getAllUsers() throws Throwable;
    void registerUser(User user) throws Throwable;
    List<String> getCompaniesNames(String email) throws Throwable;
    List<User> getAllFriendshipsByUser(String email) throws Throwable;
    void sendFriendshipRequest(String receiverEmail, String requesterEmail) throws Throwable;
    void acceptFriendshipRequest(String receiverEmail, String requesterEmail) throws Throwable;
    void rejectFriendshipRequest(String receiverEmail, String requesterEmail) throws Throwable;
    void removeFriendShip(String requesterEmail, String toDeleteEmail) throws Throwable;
    List<User> getAllRequestsByUser(String email) throws Throwable;
    User getCredentialUser(String email) throws Throwable;
    List<CompanyLogo> getAllCompaniesLogo() throws Throwable;

}
