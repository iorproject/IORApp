package emailRecognition;

import gmailApiWrapper.EmailMessage;
import main.java.DB.Entities.eContentType;
import main.java.DB.error.FirebaseException;

public interface IEmailRecognition {
    void Recognize(EmailMessage emailMessage) throws FirebaseException;
}
