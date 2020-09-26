package scorecalculator;

import lombok.Builder;
import lombok.Getter;
import matchbuilder.FrameScore;

import java.util.List;

@Builder
@Getter
public class Score {

    private String playerName;
    private List<FrameScore> playerFrames;
    
}
