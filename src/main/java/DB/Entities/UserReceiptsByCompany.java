package main.java.DB.Entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class UserReceiptsByCompany implements Serializable {
    private Map<String,Receipt> receipt;

    public Collection<Receipt> getCompanyReceipts() {
        return !receipt.isEmpty() ?
                receipt.values() :
                new ArrayList<>();
    }
}
