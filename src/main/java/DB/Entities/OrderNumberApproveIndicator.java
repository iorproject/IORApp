package main.java.DB.Entities;

import java.io.Serializable;
import java.util.Map;

public class OrderNumberApproveIndicator implements Serializable{
    private Map<String,Integer> orderNumberApprovalList;
    private int score;

    public Map<String,Integer> getIndicators() {
        return orderNumberApprovalList;
    }
}
