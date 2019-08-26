package ReceiptBodyRecognition;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PriceFinder {
    private static final Logger LOGGER = Logger.getLogger("MyLog");

    public static String findPriceExpression(String content, int index){
        ArrayList<String> all = findAll(content.substring(index - 15));
        if (all == null){
            return null;
        }

        ArrayList<String> farFromIndex = new ArrayList<>();
        for(String str : all){
            if(content.lastIndexOf(str) >= index && content.lastIndexOf(str) - index <= 150)
                return str;
            else if (content.lastIndexOf(str) >= index && content.lastIndexOf(str) - index > 150){
                farFromIndex.add(str);
            }
        }

        if(farFromIndex.size() == 0)
            return all.get(all.size() - 1);

        if(farFromIndex.size() == all.size())
            return all.get(0);

        for (String str : farFromIndex)
            all.remove(str);

        return all.get(all.size() - 1);
    }

    private static ArrayList<String> findAll(String content) {
        final String regex = "(ils|usd|eur|€|\\$|שקל|ש'ח|ש\"ח|₪)[ \\s*<>?#!@]*(\\d{1,3}(?:[.,]\\d{3})*(?:[.,]\\d{2})?)\\s?|(\\d{1,3}(?:[.,]\\d{3})*(?:[.,]\\d{2})?)[ \\s*<>?#!@]*(ils|usd|eur|€|\\$|שקל|ש'ח|ש\"ח|₪)";
        return findList(content,regex);
    }

    public static float getPriceNumber(String priceExpression){
        final String priceNumberRegex = "(\\d{1,3}(?:[.,]\\d{3})*(?:[.,]\\d{2})?)\\s?";
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

    private static ArrayList<String> findList(String content, String regex) {
        ArrayList<String> list = new ArrayList<String>();
        Matcher m = Pattern.compile(regex)
                .matcher(content);
        while (m.find()) {
            String match = m.group();
            if(find(match, "^(?=.*\\d)(?=.*[1-9]).{1,10}$") != null)
                list.add(match);
        }

        return list.size() > 0 ? list : null;
    }

    private static String find(String content, String regex){
        final Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        return matcher.find() ? matcher.group() : null;
    }
}
