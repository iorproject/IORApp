package emailRecognition;

import ReceiptBodyRecognition.IReceiptBodyRecognition;
import ReceiptBodyRecognition.ReceiptBodyRecognitionBuilder;
import dbObjects.ApproveIndicator;
import main.java.DB.Entities.TotalIndicator;
import main.java.DB.error.FirebaseException;

import java.util.List;

public class EmailRecognitionBuilder {
    public static IEmailRecognition Build(ApproveIndicator approveIndicators, TotalIndicator totalIndicators) throws FirebaseException {
        IReceiptBodyRecognition receiptBodyRecognition = ReceiptBodyRecognitionBuilder.Build(approveIndicators, totalIndicators);
        return new EmailRecognitionImpl(receiptBodyRecognition);
    }
}
