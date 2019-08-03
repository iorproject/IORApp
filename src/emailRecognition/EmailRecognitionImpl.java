package emailRecognition;

import ReceiptBodyRecognition.IReceiptBodyRecognition;
import gmailApiWrapper.Attachment;
import gmailApiWrapper.EmailAttachment;
import gmailApiWrapper.FileFormat;
import gmailApiWrapper.EmailMessage;
import main.java.DB.Entities.Receipt;
import main.java.DB.Entities.eContentType;
import main.java.DB.error.FirebaseException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import main.java.DB.*;

public class EmailRecognitionImpl implements IEmailRecognition{
    private final String[] excludeMails = new String[]{"gmail, hotmail, outlook"};
    private IReceiptBodyRecognition bodyRecognition;
    private String hostName;
    private ReceiptsDAO dbHandler;

    public EmailRecognitionImpl(IReceiptBodyRecognition bodyRecognition) {
        this.bodyRecognition = bodyRecognition;
        dbHandler = DBHandler.getInstance();
    }

    public void Recognize(EmailMessage emailMessage){
        init();
        if(!validateFromField(emailMessage.getFrom()))
            return;
        if(recognizeAttachments(emailMessage))
            return;
        recognizeByBody(emailMessage);

    }

    private void init() {
        hostName = "";
    }

    private boolean validateFromField(String from) {
        String domain = from.substring(from.indexOf("@") + 1);
        hostName = domain.substring(0, domain.indexOf(".") - 1).toLowerCase();
        return Arrays.stream(excludeMails).noneMatch(hostName::equals);
    }

    private boolean validateAttachments(EmailMessage emailMessage){
        return false;
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
            saveReceipt(emailMessage, eContentType.PDF, attachment.getBytes());
            return true;
        }
        return false;
    }

    private void recognizeByBody(EmailMessage emailMessage) {
        String content = emailMessage.getContent();
        if (bodyRecognition.recognize(content)) {
            saveReceipt(emailMessage, eContentType.STRING, content.getBytes(StandardCharsets.UTF_8));
        }
    }

    private void saveReceipt(EmailMessage emailMessage, eContentType eType, byte[] bytes) {
        Receipt receipt = new Receipt(hostName, emailMessage.getFrom(), eType, bytes, emailMessage.getDate(),bodyRecognition.getCurrency(), bodyRecognition.getTotalPrice());
        try {
            dbHandler.insertReceipt(receipt);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


}
