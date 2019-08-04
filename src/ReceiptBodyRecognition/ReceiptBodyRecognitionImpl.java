package ReceiptBodyRecognition;

import dbObjects.ApproveIndicator;
import main.java.DB.Entities.TotalIndicator;

import java.util.*;

public class ReceiptBodyRecognitionImpl implements IReceiptBodyRecognition {
    private final int PASS_SCORE = 30;
    private final ApproveIndicator approveIndicators;
    private final TotalIndicator totalIndicators;
    private float totalPrice;
    private String currency;

    public ReceiptBodyRecognitionImpl(ApproveIndicator approveIndicators, TotalIndicator totalIndicators){
        this.approveIndicators = approveIndicators;
        this.totalIndicators = totalIndicators;
    }

    @Override
    public float getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String getCurrency() {
        return currency;
    }

    @Override
    public boolean recognize(String content) {
        return recognizeTotal(content) && recognizeApproved(content);
    }

    private boolean recognizeTotal(String content) {
//        totalIdentifier = totalIndicators.stream().filter(content::contains).findFirst().get();
        List<String> allStrings = new LinkedList<>();
        totalIndicators.
                getIndicator().
                entrySet().
                stream().
                sorted(Comparator.comparing(Map.Entry::getKey)).
                forEach(t -> allStrings.addAll(t.getValue()));
        Optional<String> totalIdentifier = Optional.ofNullable(String.valueOf(allStrings.
                stream().
                sorted(Comparator.reverseOrder()).
                filter(content::contains).
                findFirst()));
        int index = content.lastIndexOf(totalIdentifier.toString());
        return !totalIdentifier.equals("") && findPrice(content.substring(index));
    }

    private boolean findPrice(String content) {
        String priceExpression = PriceFinder.findPriceExpression(content);
        if (priceExpression == null)
            return false;
        totalPrice = PriceFinder.getPriceNumber(priceExpression);
        currency = PriceFinder.getCurrency(priceExpression);
        return !currency.equals("") && totalPrice != -1;
    }

    private boolean recognizeApproved(String content) {
        int score = 0;
        Map<String,Integer> indicatorMap = approveIndicators.getIndicators();
        for(Map.Entry<String,Integer> entry : indicatorMap.entrySet()){
            if(content.contains(entry.getKey())) {
                score += entry.getValue();
                if(score >= PASS_SCORE)
                    return true;
            }
        }
        return false;
    }
}
