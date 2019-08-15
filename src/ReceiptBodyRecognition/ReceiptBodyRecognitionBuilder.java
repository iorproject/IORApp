package ReceiptBodyRecognition;

import dbObjects.ApproveIndicator;
import main.java.DB.Entities.OrderNumberApproveIndicator;
import main.java.DB.Entities.TotalIndicator;

import java.util.List;

public class ReceiptBodyRecognitionBuilder {

    public static IReceiptBodyRecognition Build(
            ApproveIndicator approveIndicators,
            TotalIndicator totalIndicators,
            OrderNumberApproveIndicator orderNumberApproveIndicator){
        return new ReceiptBodyRecognitionImpl(approveIndicators, totalIndicators, orderNumberApproveIndicator);
    }
}
