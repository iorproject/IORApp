package engine;

import main.java.DB.DBHandler;
import main.java.DB.Entities.CompanyLogo;
import main.java.DB.Entities.Receipt;
import main.java.DB.Entities.User;

import java.util.ArrayList;
import java.util.List;

public class IorEngine {


    public static void registerUser(User user) throws Throwable {

        DBHandler.getInstance().registerUser(user);

    }

    public static User getUserInfo(String email) throws Throwable {

        return DBHandler.getInstance().getCredentialUser(email);

    }

    public static List<CompanyLogo> getUserCompanies() throws Throwable {

        return DBHandler.getInstance().getAllCompaniesLogo();
    }

    public static List<Receipt> getCompanyReceipts(String email, String companyName) throws Throwable {


            return DBHandler.getInstance().getCompanyReceiptsByUser(email, companyName);
    }

    public static List<User> getUserPartners(String email) throws Throwable {


        List<User> partnersDB = DBHandler.getInstance().getAllAccessPermissionFriendshipsByUser(email);
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
