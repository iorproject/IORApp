package ReceiptBodyRecognition;

import dbObjects.ApproveIndicator;
import main.java.DB.Entities.OrderNumberApproveIndicator;
import main.java.DB.Entities.TotalIndicator;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReceiptBodyRecognitionImpl implements IReceiptBodyRecognition {
    private final int PASS_SCORE = 30;
    private final ApproveIndicator approveIndicators;
    private final TotalIndicator totalIndicators;
    private final OrderNumberApproveIndicator orderNumberApproveIndicator;
    private float totalPrice;
    private String currency;
    private String orderNumber;
    private static final Logger LOGGER = Logger.getLogger("MyLog");

    ReceiptBodyRecognitionImpl(
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
                    filter(content::contains).
                    findFirst().get());
            int index = content.lastIndexOf(totalIdentifier);
            LOGGER.log(Level.WARNING, "\t $$$$$$   Identifier = " + totalIdentifier);
            return !totalIdentifier.equals("") && findPrice(content, index);
        } catch (Exception e){
            LOGGER.log(Level.WARNING, "\t recognizeTotal failed");
            return false;
        }
    }

    private boolean findPrice(String content, int index) {
        try {
            String priceExpression = PriceFinder.findPriceExpression(content, index);
            if (priceExpression == null){
                LOGGER.log(Level.WARNING, "\t findPrice failed");
                return false;
            }
            totalPrice = PriceFinder.getPriceNumber(priceExpression);
            currency = PriceFinder.getCurrency(priceExpression);
            return !currency.equals("") && totalPrice != -1;
        } catch (Exception ignored){
            LOGGER.log(Level.WARNING, "\t findPrice Error");
            return false;
        }
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
        LOGGER.log(Level.WARNING, "\t recognizeApproved failed");
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
                    filter(content::contains).
                    findFirst().get());
            LOGGER.log(Level.WARNING, "\t $$$$$$   receiptNumberIdentifier = " + receiptNumberIdentifier);
            return findOneReceiptNumberMatch(content, receiptNumberIdentifier);
        } catch (Exception e){
            LOGGER.log(Level.WARNING, "\t findReceiptNumber failed");
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
            if(match != null){
                LOGGER.log(Level.WARNING, "\t %%%%number = " +  match);
                return match;
            }
            content = content.substring(receiptNumberIdentifier.length());
            index = content.indexOf(receiptNumberIdentifier);
        }

        LOGGER.log(Level.WARNING, "\t findOneReceiptNumberMatch failed");
        return "";
    }
}