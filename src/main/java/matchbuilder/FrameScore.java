package matchbuilder;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FrameScore {
    private int firstChance;
    private int secondChance;

    public boolean isStrike() {
        return firstChance == 10;
    }

    public boolean isSpare() {
        return firstChance != 10 && firstChance + secondChance == 10;
    }
}
