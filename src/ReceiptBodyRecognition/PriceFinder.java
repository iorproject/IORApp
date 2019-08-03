package ReceiptBodyRecognition;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PriceFinder {

    public static String findPriceExpression(String content){
        final String foreignRegex = "(USD|EUR|€|\\$)\\s?(\\d{1,3}(?:[.,]\\d{3})*(?:[.,]\\d{2}))|(\\d{1,3}(?:[.,]\\d{3})*(?:[.,]\\d{2})?)\\s?(USD|EUR|€|\\$)";
        final String foreignExpression = find(content, foreignRegex);
        final String isrRegex = "(שקל|ש\'ח|ש\"ח|₪)\\s?(\\d{1,3}(?:[.,]\\d{3})*(?:[.,]\\d{2}))|(\\d{1,3}(?:[.,]\\d{3})*(?:[.,]\\d{2})?)\\s?(שקל|ש\'ח|ש\"ח|₪)";
        if (foreignExpression == null)
            return find(content, isrRegex);
        return foreignExpression;
    }

    public static float getPriceNumber(String priceExpression){
        final String priceNumberRegex = "\\s?(\\d{1,3}(?:[.,]\\d{3})*(?:[.,]\\d{2}))|(\\d{1,3}(?:[.,]\\d{3})*(?:[.,]\\d{2})?)\\s?";
        String strNumber = find(priceExpression, priceNumberRegex);
        try {
            return Float.parseFloat(strNumber);
        } catch (Exception e) {
            return -1;
        }
    }

    public static String getCurrency(String priceExpression){
        if(priceExpression.contains("EUR") || priceExpression.contains("€"))
            return "€";
        else if (priceExpression.contains("USD") || priceExpression.contains("$"))
            return "$";
        return "₪";
    }

    private static String find(String content, String regex){
        final Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        return matcher.find() ? matcher.group() : null;
    }
}
