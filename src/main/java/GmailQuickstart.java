package main.java;
import gmailApiWrapper.*;
import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;

import java.io.*;
import java.util.List;

public class GmailQuickstart {

    private static final String USER = "omerblechman@gmail.com";
    private static Tika tikaParser = new Tika();  // for get the string from byte[]. external jar.
    private static Metadata metadata = new Metadata();  // for get the string from byte[]. external jar.

    public static void main(String[] args) {


        try {
            IEmailApiWrapper wrapper = EmailApiWrapperFactory.createEmailApiWrapper(eEmailApi.GMAIL
                    , USER, "Dsdasdas", "dasdsad");


            List<EmailMessage> messages = wrapper.getMessages();
            for (EmailMessage message : messages) {

                if (message.getAttachments().size() > 0) {

                    for (EmailAttachment att : message.getAttachments()) {



                    }
                }

            }

        }
        catch (Exception e) {
            
        }




        tikaParser.setMaxStringLength(-1);



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

    private static String getStringFromBytes(byte[] data) {

        String content = null;
        InputStream inputStream = new ByteArrayInputStream(data);
        try {
            content = tikaParser.parseToString(inputStream, metadata);
            content = content.replaceAll("\r", "")
                    .replaceAll("\t", "")
                    .replaceAll("\n +", "\n")
                    .replaceAll("\n+", "\n");
        }
        catch (Exception e) {

        }

        return content;

    }

}







