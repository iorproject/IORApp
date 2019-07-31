package ReceiptBodyRecognition;

import dbObjects.ApproveIndicator;
import main.java.DB.Entities.TotalIndicator;

import java.util.List;

public class ReceiptBodyRecognitionBuilder {

    public static IReceiptBodyRecognition Build(ApproveIndicator approveIndicators, TotalIndicator totalIndicators){
        return new ReceiptBodyRecognitionImpl(approveIndicators, totalIndicators);
    }
}
