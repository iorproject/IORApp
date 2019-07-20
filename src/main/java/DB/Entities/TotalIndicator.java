package main.java.DB.Entities;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TotalIndicator implements Serializable {
    private Map<Integer,List<String>> totalList;

    public Map<Integer,List<String>> getIndicator() {
        return totalList;
    }


}
