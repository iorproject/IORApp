package main.java.DB.Entities;

import java.io.Serializable;
import java.util.Map;

public class UserReceipts implements Serializable {
    private Map<String,Map<String,Map<String,Receipt>>> companyList;

    public Map<String,Map<String,Map<String,Receipt>>> getUserReceipts() {
        return companyList;
    }
}
