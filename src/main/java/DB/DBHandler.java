package main.java.DB;

import dbObjects.ApproveIndicator;
import main.java.DB.Entities.Receipt;
import main.java.DB.Entities.TotalIndicator;
import main.java.DB.error.FirebaseException;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

public class DBHandler implements  ReceiptsDAO{
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
}
