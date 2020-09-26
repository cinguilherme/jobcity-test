package scorecalculator;

import matchbuilder.FrameScore;

import java.util.List;

public interface ScoreCalculator {

    Integer totalScoreCalculator(List<FrameScore> frameScores);

    List<FrameScore> calculateFramesScores(List<FrameScore> frameScores);
}
