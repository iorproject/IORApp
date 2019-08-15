package main.java;

import gmailApiWrapper.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Shiko {


    public static void main(String[] args) {


        try {

            IEmailApiWrapper wrapper = EmailApiWrapperFactory.createEmailApiWrapper(eEmailApi.GMAIL
                    , "ior46800@gmail.com",
                    "ya29.GltaBwP_l8w7KZnu1-HC4HghrcA4o55b96f4uDEZF3NXSRafpccbLridsaFKAvnsH6TebWXKEF5rtvZNGmi3LAeWT3DC5S7VU9gJouKVJK5uiAro3O4BvTm3KbDr",
                    "1/ocew_v4KhjTltPhzK3Ofm1xdWf3ZeMaYJAeTk1LjlMU");


            Calendar now = Calendar.getInstance();
            now.set(2019, 06, 29, 8, 00);
            Date date = now.getTime();

            int xuu = 5;

            List<EmailMessage> messages = wrapper.getMessages(date);
            for (EmailMessage message : messages) {

                if (message.getAttachments().size() > 0) {

                    for (EmailAttachment att : message.getAttachments()) {

                        String str = att.getString();
                        int x = 5;

                    }
                }
            }
        }
        catch (Exception e ) {

            int trtrt = 6;
        }



    }
}
