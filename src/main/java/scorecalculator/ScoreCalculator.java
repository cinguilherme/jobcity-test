package scorecalculator;

import matchbuilder.FrameScore;

import java.util.List;

public interface ScoreCalculator {
    List<FrameScore> calculateFramesScores(List<FrameScore> frameScores);
}
