package matchbuilder;

import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
public class FrameScore {

    private String playerName;

    private int firstChance;
    private boolean isFirstFault;

    private int secondChance;
    private boolean isSecondFault;

    private int frameTenExclusive;
    private boolean isTenExclusiveFault;

    private int frameFinalScore;
    private boolean isFinalFrame;

    public boolean isStrike() {
        return firstChance == 10;
    }

    public boolean isSpare() {
        return firstChance != 10 && firstChance + secondChance == 10;
    }

    public String getFirstScorePresentable() {
        if (this.firstChance == 10) return "X";
        if (this.firstChance == 0 && this.isFirstFault) return "F";
        return "" + this.firstChance;
    }

    public String getSecondScorePresentable() {
        if (this.firstChance + this.secondChance == 10) return "/";
        if (this.secondChance == 0 && this.isSecondFault) return "F";
        return "" + this.secondChance;
    }
}
