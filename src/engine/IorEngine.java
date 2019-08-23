package engine;

import main.java.DB.DBHandler;
import main.java.DB.Entities.CompanyLogo;
import main.java.DB.Entities.Receipt;
import main.java.DB.Entities.User;

import java.util.ArrayList;
import java.util.List;

public class IorEngine {


    public static void registerUser(User user) {
        try {
            DBHandler.getInstance().registerUser(user);
        }
        catch (Throwable e) {
        }
    }

    public static User getUserInfo(String email) {

        User user = null;
        try {
            user = DBHandler.getInstance().getCredentialUser(email);
        }
        catch (Throwable e) {

        }

        return user;
    }

    public static List<CompanyLogo> getUserCompanies() {

        List<CompanyLogo> companies = null;
        List<String> requestsEmails = new ArrayList<>();

        try {
            companies = DBHandler.getInstance().getAllCompaniesLogo();

        }
        catch (Throwable t) {

        }

        return companies;
    }

    public static List<Receipt> getCompanyReceipts(String email, String companyName) {

        List<Receipt> receipts = null;
        try {
            receipts = DBHandler.getInstance().getCompanyReceiptsByUser(email, companyName);

        }
        catch (Throwable t) {


    }

        return receipts;
    }

    public static List<User> getUserPartners(String email) throws Throwable {

        List<User> partnersDB = null;


        partnersDB = DBHandler.getInstance().getAllAccessPermissionFriendshipsByUser(email);
        for (User user : partnersDB) {

            user.setAccessToken(null);
            user.setRefreshToken(null);
        }

        return partnersDB;
    }

    public static void sendUserShareRequest(String receiverEmail, String requesterEmail) throws Throwable {
            DBHandler.getInstance().sendFriendshipRequest(receiverEmail,requesterEmail);
    }

    public static void acceptUserShareRequest(String receiverEmail, String requesterEmail) throws Throwable {
            DBHandler.getInstance().acceptFriendshipRequest(receiverEmail,requesterEmail);
    }

    public static void removeUserFriendship(String requesterEmail, String toDeleteEmail) throws Throwable {
        DBHandler.getInstance().removeFriendShip(requesterEmail,toDeleteEmail);
    }

    public static void rejectUserShareRequest(String receiverEmail, String requesterEmail) throws Throwable {
        DBHandler.getInstance().rejectFriendshipRequest(receiverEmail,requesterEmail);
    }

    public static List<String> getUserShareRequests(String email) {

        List<User> requestsUsers;
        List<String> requestsEmails = new ArrayList<>();

        try {
            requestsUsers = DBHandler.getInstance().getAllRequestsByUser(email);
            if (requestsUsers != null) {
                for (User user : requestsUsers) {
                    requestsEmails.add(user.getEmail());
                }
            }
        }
        catch (Throwable t) {

        }

        return requestsEmails;
    }

    public static List<Receipt> getAllUserRecepits(String email) throws Throwable  {

        List<Receipt> receipts = DBHandler.getInstance().getUserReceipts(email);
        return receipts;
    }


    public static int getAmountPartners(String email) throws Throwable {

        return DBHandler.getInstance().getAmountOfAccessFriendships(email);
    }

    public static int getAmountFollowingMyReceipts(String email) throws Throwable {

        return DBHandler.getInstance().getAmountOfViewingFriendships(email);
    }

    public static void setUserProfileImage(String email, String profileImage) throws Throwable
    {
        DBHandler.getInstance().saveUserDisplayPicture(email, profileImage);
    }

    public static List<User> getFollowers(String email)throws Throwable
    {
        return DBHandler.getInstance().getAllViewingPermissionFriendshipsByUser(email);
    }

    public static List<User> getMemberShipRequestUsers(String email) throws Throwable
    {
        return DBHandler.getInstance().getAllRequestsByUser(email);

    }

    public static void acceptFriendship(String userEmail, String friendEmail) throws Throwable
    {
        DBHandler.getInstance().acceptFriendshipRequest(userEmail,friendEmail);
    }

}
