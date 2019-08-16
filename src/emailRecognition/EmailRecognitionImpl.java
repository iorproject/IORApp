package emailRecognition;

import ReceiptBodyRecognition.IReceiptBodyRecognition;
import gmailApiWrapper.EmailAttachment;
import gmailApiWrapper.FileFormat;
import gmailApiWrapper.EmailMessage;
import main.java.DB.Entities.Receipt;
import main.java.DB.Entities.eContentType;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import main.java.DB.*;

public class EmailRecognitionImpl implements IEmailRecognition{
//    private final String[] excludeMails = new String[]{"gmail, hotmail, outlook"};
    private final String[] excludeMails = new String[]{"hotmail, outlook"};//TODO: here for tests
    private IReceiptBodyRecognition bodyRecognition;
    private String hostName;
    private ReceiptsDAO dbHandler;

    EmailRecognitionImpl(IReceiptBodyRecognition bodyRecognition) {
        this.bodyRecognition = bodyRecognition;
        dbHandler = DBHandler.getInstance();
    }

    public void Recognize(EmailMessage emailMessage){
        init();
        if(unValidateFromField(emailMessage.getFrom()))
            return;
        if(recognizeAttachments(emailMessage))
            return;
        recognizeByBody(emailMessage);

    }

    private void init() {
        hostName = "";
    }

    private boolean unValidateFromField(String from) {
        String domain = from.substring(from.indexOf("@") + 1);
        hostName = domain.substring(0, domain.indexOf(".")).toLowerCase();
        return Arrays.stream(excludeMails).anyMatch(hostName::equals);
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
        if(bodyRecognition.recognize(attachment.getString())){
            saveReceipt(emailMessage, eContentType.PDF, attachment.getBytes(), attachment.getName());
            return true;
        }
        return false;
    }

    private void recognizeByBody(EmailMessage emailMessage) {
        String content = emailMessage.getContent();
        if (bodyRecognition.recognize(content)) {
            saveReceipt(emailMessage, eContentType.STRING, content.getBytes(StandardCharsets.UTF_8), null);
        }
    }

    private void saveReceipt(EmailMessage emailMessage, eContentType eType, byte[] bytes, String fileName) {
        Receipt receipt = new Receipt(hostName, emailMessage.getFrom(), eType, emailMessage.getDate(), bytes,
                bodyRecognition.getCurrency(), bodyRecognition.getTotalPrice(), fileName, bodyRecognition.getOrderNumber());
        try {
            //TODO: send my email to insert Receipt
            dbHandler.insertReceipt("ior46800@gmail.com",receipt);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }


}