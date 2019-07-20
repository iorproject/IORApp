package ReceiptBodyRecognition;

import dbObjects.ApproveIndicator;
import main.java.DB.Entities.TotalIndicator;

import java.util.List;

public class ReceiptBodyRecognitionBuilder {

    public IReceiptBodyRecognition Build(List<ApproveIndicator> approveIndicators, List<TotalIndicator> totalIndicators){
        ReceiptBodyRecognitionImpl receiptRecognition = new ReceiptBodyRecognitionImpl();
        receiptRecognition.setApproveIndicators(approveIndicators);
        receiptRecognition.setTotalIndicators(totalIndicators);
        return receiptRecognition;
    }
}
