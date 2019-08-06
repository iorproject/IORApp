package engine;

import main.java.DB.DBHandler;
import main.java.DB.Entities.Receipt;
import main.java.DB.Entities.User;
import main.java.DB.error.FirebaseException;
import main.java.DB.error.JacksonUtilityException;
import main.java.DB.util.JacksonUtility;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public static List<String> getUserPartners(String email) {

        List<User> partners = null;
        List<String> res = null;

        try {
            partners = DBHandler.getInstance().getAllFriendshipsByUser(email);
            if (partners != null) {

                res = new ArrayList<>();
                for(User user : partners) {

                    res.add(user.getEmail());
                }
            }
        }
        catch (Throwable e) {


        }

        return res;
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
}
