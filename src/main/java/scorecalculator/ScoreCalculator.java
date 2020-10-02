package scorecalculator;

import matchbuilder.FrameScore;

import java.util.List;

public interface ScoreCalculator {
    /**
     * Based on the FrameScores rebuilds the List with scores calculated.
     *
     * @param frameScores
     * @return
     */
    List<FrameScore> calculateFramesScores(List<FrameScore> frameScores);
}
