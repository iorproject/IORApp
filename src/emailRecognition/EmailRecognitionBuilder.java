package emailRecognition;

import ReceiptBodyRecognition.IReceiptBodyRecognition;
import ReceiptBodyRecognition.ReceiptBodyRecognitionBuilder;
import dbObjects.ApproveIndicator;
import main.java.DB.Entities.OrderNumberApproveIndicator;
import main.java.DB.Entities.TotalIndicator;
import main.java.DB.error.FirebaseException;

import java.util.List;

public class EmailRecognitionBuilder {
    public static IEmailRecognition Build(
            ApproveIndicator approveIndicators,
            TotalIndicator totalIndicators,
            OrderNumberApproveIndicator orderNumberApproveIndicator) throws FirebaseException {
        IReceiptBodyRecognition receiptBodyRecognition = ReceiptBodyRecognitionBuilder.Build(
                approveIndicators,
                totalIndicators,
                orderNumberApproveIndicator);
        return new EmailRecognitionImpl(receiptBodyRecognition);
    }
}