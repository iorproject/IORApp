package main.java;
import gmailApiWrapper.*;
import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;

import java.io.*;
import java.util.List;

public class GmailQuickstart {

    private static final String USER = "ior46800@gmail.com";
    private static Tika tikaParser = new Tika();  // for get the string from byte[]. external jar.
    private static Metadata metadata = new Metadata();  // for get the string from byte[]. external jar.

    public static void main(String[] args) {


        try {
            IEmailApiWrapper wrapper = EmailApiWrapperFactory.createEmailApiWrapper(eEmailApi.GMAIL
                    , USER, "ya29.GltTB49TCKMmKuuZ--9oyap3IG3G0J_GmE9_suzgSx9OBnKDuwieMe054hDIgMP39yiSg12IyIoBYTpD-drCO4tkFd1vedfEWPkBr4OeVosrT_tHlPuXGMJzYtpy",
                    "1/hzVLripZb7bUDRHJ-9O2qCoCsIsYv-QuWHm_zGH93PwyxMnR6NpqRbisQ-0bVx68");


            List<EmailMessage> messages = wrapper.getMessages();
            for (EmailMessage message : messages) {

                if (message.getAttachments().size() > 0) {

                    for (EmailAttachment att : message.getAttachments()) {

                            String str = att.getString();
                            int x = 5;

                    }
                }
            }
        }
        catch (Exception e) {

            int x= 5;


        }


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







