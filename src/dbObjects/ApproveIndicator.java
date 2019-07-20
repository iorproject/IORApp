package dbObjects;

import java.io.Serializable;
import java.util.Map;

public class ApproveIndicator implements Serializable{
    private Map<String,Integer> approvalList;
    private int score;

    public Map<String,Integer> getIndicators() {
        return approvalList;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
