package ReceiptBodyRecognition;

import dbObjects.ApproveIndicator;
import main.java.DB.Entities.OrderNumberApproveIndicator;
import main.java.DB.Entities.TotalIndicator;

import java.util.*;

public class ReceiptBodyRecognitionImpl implements IReceiptBodyRecognition {
    private final int PASS_SCORE = 30;
    private final ApproveIndicator approveIndicators;
    private final TotalIndicator totalIndicators;
    private final OrderNumberApproveIndicator orderNumberApproveIndicator;
    private float totalPrice;
    private String currency;
    private String orderNumber;

    public ReceiptBodyRecognitionImpl(
            ApproveIndicator approveIndicators,
            TotalIndicator totalIndicators,
            OrderNumberApproveIndicator orderNumberApproveIndicator
    ){
        this.approveIndicators = approveIndicators;
        this.totalIndicators = totalIndicators;
        this.orderNumberApproveIndicator = orderNumberApproveIndicator;
        this.orderNumber = null;
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
        String contentToLower = content.toLowerCase();
        contentToLower = contentToLower.replaceAll("\\u00a0"," ");
        boolean recognized = recognizeTotal(contentToLower) && recognizeApproved(contentToLower);
        if (recognized) {
            orderNumber = findReceiptNumber(contentToLower);
        }
        return recognized;
    }

    @Override
    public String getOrderNumber() {
        return orderNumber;
    }

    private boolean recognizeTotal(String content) {
        List<String> allStrings = new LinkedList<>();
        totalIndicators.
                getIndicator().
                entrySet().
                stream().
                sorted(Comparator.comparing(Map.Entry::getKey)).
                forEach(t -> allStrings.addAll(t.getValue()));
        Collections.reverse(allStrings);
        try{
            String totalIdentifier = String.valueOf(allStrings.
                    stream().
                    filter(str -> content.contains(str)).
                    findFirst().get());
            int index = content.lastIndexOf(totalIdentifier);
            return !totalIdentifier.equals("") && findPrice(content.substring(index));
        } catch (Exception e){
            return false;
        }
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
            if(content.contains(entry.getKey().toLowerCase())) {
                score += entry.getValue();
                if(score >= PASS_SCORE)
                    return true;
            }
        }
        return false;
    }

    private String findReceiptNumber(String content) {
        List<String> allStrings = new LinkedList<>();
        orderNumberApproveIndicator.
                getIndicators().
                entrySet().
                stream().
                sorted(Comparator.comparing(Map.Entry::getValue)).
                forEach(t -> allStrings.add(t.getKey()));
        Collections.reverse(allStrings);
        try{
            String receiptNumberIdentifier = String.valueOf(allStrings.
                    stream().
                    filter(str -> content.contains(str)).
                    findFirst().get());
            return findOneReceiptNumberMatch(content, receiptNumberIdentifier);

//            int index = content.lastIndexOf(receiptNumberIdentifier);
//            if(!receiptNumberIdentifier.equals("")) {
//                return PriceFinder.getReceiptNumber(content.substring(index));
//            } else {
//                return "";
//            }
        } catch (Exception e){
            return "";
        }
    }

    private String findOneReceiptNumberMatch(String content, String receiptNumberIdentifier) {
        int index = 0;
        index = content.indexOf(receiptNumberIdentifier);
        while (content.length() > 1 && index != -1){
            content = content.substring(index);
            content = content.substring(receiptNumberIdentifier.length());
            String match = PriceFinder.getReceiptNumber(content);
            if(match != null)
                return match;
            content = content.substring(receiptNumberIdentifier.length());
            index = content.indexOf(receiptNumberIdentifier);
        }

        return "";
    }
}