package main.java.DB.Entities;

import java.io.Serializable;
import java.util.Map;

public class UserReceiptByCompany implements Serializable {
    private Map<String,Receipt> receipt;

    public Receipt getCompanyReceipt(String id) {
        return !receipt.isEmpty()?
                receipt.get(id):
                null;
    }
}
