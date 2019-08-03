package engine;

import main.java.DB.DBHandler;
import main.java.DB.Entities.Receipt;
import main.java.DB.Entities.User;
import main.java.DB.error.FirebaseException;
import main.java.DB.error.JacksonUtilityException;
import main.java.DB.util.JacksonUtility;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IorEngine {


    public static void registerUser(User user) {


        try {
            DBHandler.getInstance().registerUser(user);
            DBHandler.getInstance().getUserReceipts(user.getEmail());
        }
        catch (FirebaseException | JacksonUtilityException | UnsupportedEncodingException e) {

        }
    }



}
