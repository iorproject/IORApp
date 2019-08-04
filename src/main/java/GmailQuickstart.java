package main.java;
import EmailProcessor.Processor;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleRefreshTokenRequest;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import gmailApiWrapper.*;
import main.java.DB.error.FirebaseException;
import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;

import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GmailQuickstart {

    private static final String USER = "shikoba21@gmail.com";
    private static Tika tikaParser = new Tika();  // for get the string from byte[]. external jar.
    private static Metadata metadata = new Metadata();  // for get the string from byte[]. external jar.

    public static void main(String[] args) throws Throwable {
        Processor emailProcessor = new Processor();
        emailProcessor.Run();

//        try {
//            IEmailApiWrapper wrapper = EmailApiWrapperFactory.createEmailApiWrapper(eEmailApi.GMAIL
//                    , USER,
//                    "ya29.GltUB3m-1ma9GCrjY8tuFzngd-PAEGgGXZq75fNzAo1uhAR-TDNofdbjKSCFXERZoBJhTZPk0-oSvLB3_DwPC7iy4iM3_3ah3IG-UWkjXRga7gicDi5W-t0jqELa",
//                    "1/DvWTHOyK03h8VvJi6IXtZ12JbyvG8ir0VHtj4DLX528");
//
//
//            Calendar now = Calendar.getInstance();
//            now.set(2019, 06, 29, 8, 00);
//            Date date = now.getTime();
//
//            int xuu = 5;
//
//            List<EmailMessage> messages = wrapper.getMessages(date);
//            for (EmailMessage message : messages) {
//
//                if (message.getAttachments().size() > 0) {
//
//                    for (EmailAttachment att : message.getAttachments()) {
//
//                            String str = att.getString();
//                            int x = 5;
//
//                    }
//                }
//            }
//        }
//
//        catch (Exception e) {
//
//            int x= 5;
//
//        }


//        try {
//            GmailApiWrapper gmailApiWrapper = new GmailApiWrapper();
//            List<IEmailMessage> messages = gmailApiWrapper.getMessages(USER);
//            for (IEmailMessage message : messages) {
//
//                if (message.getAttachments().size() > 0) {
//
//                    for (Attachment attachment : message.getAttachments()) {
//
//                        byte[] dataBytes = gmailApiWrapper.getAttachmentBytes(attachment,
//                                USER, message);
//
//
//                        String data = getStringFromBytes(dataBytes);
//                        int x = 5;
//                    }
//                }
//
//            }
//        }
//        catch (Exception e) {
//
//            String error = e.getMessage();
//            int d = 4;
//        }

    }


}