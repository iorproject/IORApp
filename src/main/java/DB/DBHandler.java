package main.java.DB;

import dbObjects.ApproveIndicator;
import main.java.DB.Entities.*;
import main.java.DB.error.JacksonUtilityException;

import java.util.Date;
import java.util.List;

public class DBHandler implements ReceiptsDAO{
    private static DBHandler dbHandler;
    private static Object lockObject = new Object();

    private DBHandler() {
    }

    public static DBHandler getInstance() {
        if(dbHandler == null){
            synchronized (lockObject){
                if(dbHandler == null){
                    dbHandler = new DBHandler();
                }
            }
        }
        return dbHandler;
    }

    @Override
    public ApproveIndicator getApprovalIndicators() throws Throwable {
        return FirebaseDao.getInstance().getApprovalIndicators();
    }

    @Override
    public OrderNumberApproveIndicator getOrderNumberApproval() throws Throwable {
        return FirebaseDao.getInstance().getOrderNumberApproval();
    }

    @Override
    public TotalIndicator getTotalIndicator() throws Throwable {
        return FirebaseDao.getInstance().getTotalIndicator();
    }

    @Override
    public List<Receipt> getUserReceipts(String email) throws Throwable {
        return FirebaseDao.getInstance().getUserReceipts(email);
    }

    @Override
    public List<Receipt> getCompanyReceiptsByUser(String email, String company) throws Throwable {
        return FirebaseDao.getInstance().getCompanyReceiptsByUser(email,company);
    }

    @Override
    public Receipt getCompanyReceiptByUser(String email, String company, long id) throws Throwable {
        return FirebaseDao.getInstance().getCompanyReceiptByUser(email,company,id);
    }

    @Override
    public void setLastSearchMailTime(String email, Date lastUpdatedSearchTime) throws Throwable {
        FirebaseDao.getInstance().setLastSearchMailTime(email,lastUpdatedSearchTime);
    }

    @Override
    public Date getLastSearchMailTime(String email) throws Throwable {
        return FirebaseDao.getInstance().getLastSearchMailTime(email);
    }

    @Override
    public void insertReceipt(String email, Receipt receipt) throws Throwable {
        FirebaseDao.getInstance().insertReceipt(email,receipt);
    }

    @Override
    public List<User> getAllUsers() throws Throwable {
        return FirebaseDao.getInstance().getAllUsers();
    }

    @Override
    public void registerUser(User user, Date startDateToSearchReceipts) throws Throwable, JacksonUtilityException {
        FirebaseDao.getInstance().registerUser(user, startDateToSearchReceipts);
    }

    @Override
    public List<String> getCompaniesNames(String email) throws Throwable {
        return FirebaseDao.getInstance().getCompaniesNames(email);
    }

    @Override
    public List<User> getAllAccessPermissionFriendshipsByUser(String email) throws Throwable {
        return FirebaseDao.getInstance().getAllAccessPermissionFriendshipsByUser(email);
    }

    @Override
    public List<User> getAllViewingPermissionFriendshipsByUser(String email) throws Throwable {
        return FirebaseDao.getInstance().getAllViewingPermissionFriendshipsByUser(email);
    }

    @Override
    public void sendFriendshipRequest(String receiverEmail,String requesterEmail) throws Throwable {
        FirebaseDao.getInstance().sendFriendshipRequest(receiverEmail,requesterEmail);
    }

    @Override
    public void acceptFriendshipRequest(String receiverEmail, String requesterEmail) throws Throwable {
        FirebaseDao.getInstance().acceptFriendshipRequest(receiverEmail,requesterEmail);
    }

    @Override
    public void rejectFriendshipRequest(String deleterEmail, String deletedEmail) throws Throwable {
        FirebaseDao.getInstance().rejectFriendshipRequest(deleterEmail,deletedEmail);
    }

    @Override
    public void removeFriendShip(String requesterEmail, String toDeleteEmail) throws Throwable {
        FirebaseDao.getInstance().removeFriendShip(requesterEmail,toDeleteEmail);
    }

    @Override
    public List<User> getAllRequestsByUser(String email) throws Throwable {
        return FirebaseDao.getInstance().getAllRequestsByUser(email);
    }

    @Override
    public User getCredentialUser(String email) throws Throwable {
        return FirebaseDao.getInstance().getCredentialUser(email);
    }

    @Override
    public List<CompanyLogo> getAllCompaniesLogo() throws Throwable{
        return FirebaseDao.getInstance().getAllCompaniesLogo();
    }

    @Override
    public int getAmountOfUserReceipts(String email) throws Throwable {
        return FirebaseDao.getInstance().getAmountOfUserReceipts(email);
    }

    @Override
    public int getAmountOfCompanyReceiptsByUser(String email, String company) throws Throwable {
        return FirebaseDao.getInstance().getAmountOfCompanyReceiptsByUser(email,company);
    }

    @Override
    public int getAmountOfAccessFriendships(String email) throws Throwable {
        return FirebaseDao.getInstance().getAmountOfAccessFriendships(email);
    }

    @Override
    public int getAmountOfViewingFriendships(String email) throws Throwable {
        return FirebaseDao.getInstance().getAmountOfViewingFriendships(email);
    }

    @Override
    public void saveUserDisplayPicture(String email, String encodeBitmap) throws Throwable {
        FirebaseDao.getInstance().saveUserDisplayPicture(email,encodeBitmap);
    }
}
