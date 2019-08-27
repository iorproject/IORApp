package main.java;

import gmailApiWrapper.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Shiko {


    public static void main(String[] args) {


        try {

            IEmailApiWrapper wrapper = EmailApiWrapperFactory.createEmailApiWrapper(eEmailApi.GMAIL
                    , "omerblechman@gmail.com",
                    "ya29.GltuB0c34wrxeqTyyG6hEp9tqiI_V6zUiDVU3tica2vbMQCA0kCIgWpjcxdaY_3zHHaLi_wJIgi1U7yWcCLhOo1S6YXBsBQyWmfPXTbQGzW1JOmhZmpKibz649GJ",
                    "1/et_q7KKYDU6pqeZNBVZaW8ZneTDlamHczdt7Y-gxOJQ");


            Calendar now = Calendar.getInstance();
            now.set(2019, 1, 27, 10, 22, 59);
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
