package main.java.DB.Entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserReceiptByCompany implements Serializable {
    private Map<String,AttachmentReceipt> receipt;

    public AttachmentReceipt getCompanyReceipt(String id) {
        return !receipt.isEmpty()?
                receipt.get(id):
                null;
    }
}
