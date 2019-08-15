package ReceiptBodyRecognition;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PriceFinder {
//TODO:or: adding ils
    public static String findPriceExpression(String content){
        final String foreignRegex = "(ils|usd|eur|€|\\$)\\s?(\\d{1,3}(?:[.,]\\d{3})*(?:[.,]\\d{2}))|(\\d{1,3}(?:[.,]\\d{3})*(?:[.,]\\d{2})?)\\s?(ils|usd|eur|€|\\$)";
        final String foreignExpression = find(content, foreignRegex);
        final String isrRegex = "(שקל|ש'ח|ש\"ח|₪)(\\d{1,3}(?:[.,]\\d{3})*(?:[.,]\\d{2})?)\\s?|(\\d{1,3}(?:[.,]\\d{3})*(?:[.,]\\d{2})?)\\s?(שקל|ש'ח|ש\"ח|₪)";
        if (foreignExpression == null){
            String isrExpression = find(content, isrRegex);
            if(isrExpression == null)
                return find(content, "(\\d{1,3}(?:[.,]\\d{3})*(?:[.,]\\d{2})?)\\s?|(\\d{1,3}(?:[.,]\\d{3})*(?:[.,]\\d{2})?)\\s?");
            else
                return isrExpression;
        }
        return foreignExpression;
    }

    public static float getPriceNumber(String priceExpression){
        final String priceNumberRegex = "\\s?(\\d{1,3}(?:[.,]\\d{3})*(?:[.,]\\d{2}))|(\\d{1,3}(?:[.,]\\d{3})*(?:[.,]\\d{2})?)\\s?";
        String strNumber = find(priceExpression, priceNumberRegex);
        strNumber = strNumber.replace(",", "");
        strNumber = strNumber.trim();
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

    public static String getReceiptNumber(String content){
        final String receiptNumberRegex = "^[#]{0,1}(?=.*\\d)[a-zA-Z\\d-.]+$";
        content = content.trim();
        while(content.charAt(0) != '#' && !Character.isDigit(content.charAt(0)) &&
                !Character.isLetter(content.charAt(0))){
            content = content.substring(1);
        }
        content = content.trim();
        String firstWord = content.split("[ \\r\\t\\n\\,\\?\\;\\.\\:\\!]")[0].toLowerCase();
        if(firstWord.equals("#"))
            firstWord = firstWord + content.split("[ \\r\\t\\n\\,\\?\\;\\.\\:\\!]")[1].toLowerCase();

        return find(firstWord, receiptNumberRegex);
    }

    private static String find(String content, String regex){
        final Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        return matcher.find() ? matcher.group() : null;
    }
}
