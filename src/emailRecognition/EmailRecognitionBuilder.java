package emailRecognition;

import ReceiptBodyRecognition.IReceiptBodyRecognition;
import ReceiptBodyRecognition.ReceiptBodyRecognitionBuilder;
import dbObjects.ApproveIndicator;
import main.java.DB.Entities.TotalIndicator;

import java.util.List;

public class EmailRecognitionBuilder {
    public static IEmailRecognition Build(ApproveIndicator approveIndicators, TotalIndicator totalIndicators){
        IReceiptBodyRecognition receiptBodyRecognition = ReceiptBodyRecognitionBuilder.Build(approveIndicators, totalIndicators);
        return new EmailRecognitionImpl(receiptBodyRecognition);
    }
}
