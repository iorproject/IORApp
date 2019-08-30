package emailRecognition;

import ReceiptBodyRecognition.IReceiptBodyRecognition;
import gmailApiWrapper.EmailAttachment;
import gmailApiWrapper.FileFormat;
import gmailApiWrapper.EmailMessage;
import main.java.DB.Entities.Receipt;
import main.java.DB.Entities.eContentType;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.java.DB.*;

public class EmailRecognitionImpl implements IEmailRecognition{
    private final String[] realExcludeMails = new String[]{"gmail", "hotmail", "outlook"};
    private final String[] excludeMails = new String[]{"hotmail", "outlook"};//TODO: here for tests
    private IReceiptBodyRecognition bodyRecognition;
    private String hostName;
    private ReceiptsDAO dbHandler;
    private static final Logger LOGGER = Logger.getLogger("MyLog");

    EmailRecognitionImpl(IReceiptBodyRecognition bodyRecognition) {
        this.bodyRecognition = bodyRecognition;
        dbHandler = DBHandler.getInstance();
    }

    public void Recognize(EmailMessage emailMessage){
        init();
        if(unValidateFromField(emailMessage.getContent(), emailMessage.getFrom()))
            return;
        if(recognizeAttachments(emailMessage))
            return;
        recognizeByBody(emailMessage);

    }

    private void init() {
        hostName = "";
    }

    private boolean unValidateFromField(String content, String from) {
        String originalHostName = extractHostName(from);
        if(Arrays.stream(realExcludeMails).anyMatch(originalHostName::equals)){
            String res = extractCompanyName(content);
            if(res == null){
                hostName = originalHostName;
            } else {
                hostName = extractHostName(res);
            }
        } else {
            hostName = originalHostName;
        }

//        LOGGER.log(Level.INFO, "@@@@Company Name:" + hostName);
        hostName = hostName.substring(0,1).toUpperCase() + hostName.substring(1);
        return Arrays.stream(excludeMails).anyMatch(hostName::equals);
    }

    private String extractCompanyName(String content) {
        final String forwardRegex = "(from|מאת)";
        ArrayList<String> forwardResults = findList(content, forwardRegex);
        if(forwardResults != null)
        {
            for(String forwardResult : forwardResults){
                int forwardIndex = content.indexOf(forwardResult);
                String result = extractOneMail(content, forwardIndex);
                try {
                    if(result != null){
                        hostName = extractHostName(result);
                        if(Arrays.stream(realExcludeMails).anyMatch(hostName::equals)){
                            content = content.substring(forwardIndex + 30);
                        } else{
                            return result;
                        }
                    }
                    else{
                        content = content.substring(forwardIndex + 30);
                    }
                } catch (Exception ignored){}
            }
        }

        return null;
    }

    private String extractOneMail(String content, int forwardIndex) {
        final String mailRegex = "([a-z0-9_.-]+)@([a-z0-9_.-]+[a-z])";
        ArrayList<String> all = findList(content,mailRegex);
        int closestIndex = content.length();
        String closestMail = null;
        for(String mail : all){
            int gap = Math.abs( content.indexOf(mail) - forwardIndex);
            if(gap < closestIndex){
                closestIndex = gap;
                closestMail = mail;
            }
        }

        return closestIndex < 100 ? closestMail : null;
    }

    private ArrayList<String> findList(String content, String foreignRegex) {
        ArrayList<String> currency = new ArrayList<String>();
        Matcher m = Pattern.compile(foreignRegex)
                .matcher(content);
        while (m.find()) {
            currency.add(m.group());
        }

        return currency;
    }

    private String extractHostName(String content){
        String domain = content.substring(content.indexOf("@") + 1);
        String hostName = domain.substring(0, domain.lastIndexOf(".")).toLowerCase();
        if(hostName.lastIndexOf(".") != -1){
            String lastPart = hostName.substring(hostName.lastIndexOf(".") + 1);
            if(lastPart.equals("co")){
                return extractHostName("@" + hostName);
            } else {
                hostName = lastPart;
            }
        }

        return hostName;
    }

    private boolean recognizeAttachments(EmailMessage emailMessage) {
        for(EmailAttachment attachment : emailMessage.getAttachments()){
            if(attachment.getType().equals(FileFormat.PDF)){
                if(recognizeAttachmentContent(emailMessage, attachment)){
                    return true;
                }
            }
        }

        return false;
    }

    private boolean recognizeAttachmentContent(EmailMessage emailMessage, EmailAttachment attachment) {
//        LOGGER.log(Level.INFO, "$$$$$$$$Attachment$$$$$$$");
        if(bodyRecognition.recognize(attachment.getString())){
            saveReceipt(emailMessage, eContentType.PDF, attachment.getBytes(), attachment.getName());
            return true;
        }
        return false;
    }

    private void recognizeByBody(EmailMessage emailMessage) {
        //LOGGER.log(Level.INFO, "^^^^^^^^^^^Body^^^^^^^^^^^");
        String content = emailMessage.getContent();
        if (bodyRecognition.recognize(content)) {
            saveReceipt(emailMessage, eContentType.STRING, content.getBytes(StandardCharsets.UTF_8), null);
        }
    }

    private void saveReceipt(EmailMessage emailMessage, eContentType eType, byte[] bytes, String fileName) {
        Receipt receipt = new Receipt(hostName, emailMessage.getFrom(), eType, emailMessage.getDate(),bytes,
                bodyRecognition.getCurrency(), bodyRecognition.getTotalPrice(), fileName, bodyRecognition.getOrderNumber());
        try {
            dbHandler.insertReceipt(emailMessage.getTo(),receipt);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        LOGGER.log(Level.INFO, "########################SAVED####################" + bodyRecognition.getTotalPrice() + " " + bodyRecognition.getCurrency());
    }
}