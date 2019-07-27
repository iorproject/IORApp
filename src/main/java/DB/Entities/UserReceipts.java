package main.java.DB.Entities;

import java.util.Map;

public class UserReceipts {
    private Map<String,Map<String,Receipt>> companyList;

    public Map<String,Map<String,Receipt>> getUserReceipts() {
        return companyList;
    }
}
