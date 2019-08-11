package main.java.DB.Entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserReceiptsByCompany implements Serializable {
    private Map<String,Receipt> receipt;

    public List<Receipt> getCompanyReceipts() {
        return !receipt.isEmpty()?
                receipt.values().stream()
                .collect(Collectors.toList())
                : new ArrayList<>();
    }
}
