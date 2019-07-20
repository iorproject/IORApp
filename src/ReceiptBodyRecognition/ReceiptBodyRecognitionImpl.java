package ReceiptBodyRecognition;

import dbObjects.ApproveIndicator;
import main.java.DB.Entities.TotalIndicator;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReceiptBodyRecognitionImpl implements IReceiptBodyRecognition {
    private final String regax = "(USD|EUR|€|\\$)\\s?(\\d{1,3}(?:[.,]\\d{3})*(?:[.,]\\d{2}))|(\\d{1,3}(?:[.,]\\d{3})*(?:[.,]\\d{2})?)\\s?(USD|EUR|€|\\$)";
    private List<ApproveIndicator> approveIndicators;
    private List<TotalIndicator> totalIndicators;
    private List<ApproveIndicator> approvedIdentifiers = new LinkedList<>();
    private String totalIdentifier = "";
    private String totalPrice;

    public void setApproveIndicators(List<ApproveIndicator> approveIndicators) {
        this.approveIndicators = approveIndicators;
    }

    public void setTotalIndicators(List<TotalIndicator> totalIndicators) {
        this.totalIndicators = totalIndicators;
    }

    @Override
    public String getTotalPrice() {
        return totalPrice;
    }

    @Override
    public boolean recognize(String content) {
        return recognizeTotal(content) && recognizeApproved(content);
    }

    private boolean recognizeTotal(String content) {
//        totalIdentifier = totalIndicators.stream().filter(content::contains).findFirst().get();
//        totalIdentifier = String.valueOf(totalIndicators.stream().filter(t -> content.contains(t.getIndicators())).findFirst().get());
        int index = content.lastIndexOf(totalIdentifier);
        return findPrice(content.substring(index)) && totalIdentifier.equals("");
    }

    private boolean findPrice(String content) {
        final Pattern pattern = Pattern.compile(regax);
        Matcher matcher = pattern.matcher(content);
        boolean result = matcher.find();
        totalPrice = result? matcher.group() : "";
        return result;
    }

    private boolean recognizeApproved(String content) {
        for(ApproveIndicator indicator : approveIndicators){
/*
            List<String> indicatorList = indicator.getIndicators();
            if(indicatorList.size() == 1){
                if(content.contains(indicatorList.get(0))){
                    approvedIdentifiers.add(indicator);
                    int sum = 0;
                    sum = approvedIdentifiers.stream().mapToInt(ApproveIndicator::getScore).sum();
                    if(sum >= 30)
                        return true;
                }
            }
*/
        }

        return false;
    }
}
