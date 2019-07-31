package ReceiptBodyRecognition;

public interface IReceiptBodyRecognition {
    //    List<ApproveIndicator> getApprovedIdentifiers();
//    String getTotalIdentifier();
    float getTotalPrice();
    String getCurrency();
    boolean recognize(String content);
//    void setApproveIndicators(List<ApproveIndicator> approveIndicators);
//    void setTotalIndicators(List<String> totalIndicators);
}
