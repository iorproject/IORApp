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

public class DBHandler implements  ReceiptsDAO{
    private static DBHandler dbHandler;
    private static Object lockObject = new Object();

    private DBHandler() throws FirebaseException {
    }

    public static DBHandler getInstance() throws FirebaseException {
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
    public ApproveIndicator getApprovalIndicators() throws UnsupportedEncodingException, FirebaseException {
        return FirebaseDao.getInstance().getApprovalIndicators();
    }

    @Override
    public TotalIndicator getTotalIndicator() throws UnsupportedEncodingException, FirebaseException {
        return FirebaseDao.getInstance().getTotalIndicator();
    }

    @Override
    public List<Receipt> getUserReceipts(String email) throws UnsupportedEncodingException, FirebaseException {
        return FirebaseDao.getInstance().getUserReceipts(email);
    }

    @Override
    public List<Receipt> getCompanyReceiptsByUser(String email, String company) throws UnsupportedEncodingException, FirebaseException {
        return FirebaseDao.getInstance().getCompanyReceiptsByUser(email,company);
    }

    @Override
    public void setLastSearchMailTime(String email, Date lastUpdatedSearchTime) throws UnsupportedEncodingException, FirebaseException {
        FirebaseDao.getInstance().setLastSearchMailTime(email,lastUpdatedSearchTime);
    }

    @Override
    public Date getLastSearchMailTime(String email) throws UnsupportedEncodingException, FirebaseException {
        return FirebaseDao.getInstance().getLastSearchMailTime(email);
    }

    @Override
    public void insertReceipt(Receipt receipt) throws UnsupportedEncodingException, FirebaseException {
        FirebaseDao.getInstance().insertReceipt(receipt);
    }

    @Override
    public List<User> getAllUsers() throws UnsupportedEncodingException, FirebaseException {
        return FirebaseDao.getInstance().getAllUsers();
    }

    @Override
    public void registerUser(User user) throws UnsupportedEncodingException, FirebaseException, JacksonUtilityException {
        FirebaseDao.getInstance().registerUser(user);
    }
}
