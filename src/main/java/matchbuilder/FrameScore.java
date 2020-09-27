package matchbuilder;

import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
public class FrameScore {

    private String playerName;
    private int firstChance;
    private int secondChance;
    private int frameTenExclusive;
    private int frameFinalScore;
    private boolean isFinalFrame;

    public boolean isStrike() {
        return firstChance == 10;
    }

    public boolean isSpare() {
        return firstChance != 10 && firstChance + secondChance == 10;
    }
}
