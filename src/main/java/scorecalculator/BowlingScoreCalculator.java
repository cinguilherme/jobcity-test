package scorecalculator;

import matchbuilder.FrameScore;

import java.util.ArrayList;
import java.util.List;

public class BowlingScoreCalculator implements ScoreCalculator {
    @Override
    public List<FrameScore> calculateFramesScores(List<FrameScore> frameScores) {

        List<FrameScore> calculatedFrames = new ArrayList<>();
        int lastSum = 0;
        for (int frameIndex = 0; frameIndex < frameScores.size(); frameIndex++) {
            FrameScore current = frameScores.get(frameIndex);
            FrameScore newFrame;
            if (current.isStrike()) {
                newFrame = getFrameScoreStrike(frameScores, lastSum, frameIndex, current);
            } else if (current.isSpare()) {
                newFrame = getFrameScoreSpare(frameScores, lastSum, frameIndex, current);
            } else {
                newFrame = calculateFrameWith(lastSum, current);
            }

            lastSum = newFrame.getFrameFinalScore();
            calculatedFrames.add(newFrame);
        }
        return calculatedFrames;
    }

    private FrameScore getFrameScoreSpare(List<FrameScore> frameScores, int lastSum, int frameIndex, FrameScore current) {
        int nextBall;
        if (frameIndex == 9) { //last frame
            nextBall = current.getFrameTenExclusive();
        } else {
            nextBall = frameScores.get(frameIndex + 1).getFirstChance();
        }

        return calculateSpareFrameWith(lastSum, current, nextBall);
    }

    private FrameScore getFrameScoreStrike(List<FrameScore> frameScores, int lastSum, int frameIndex, FrameScore current) {
        int nextBall;
        int nextTwoBall;
        if (frameIndex == 9) { //last frame
            int res = current.getFirstChance() + current.getSecondChance() + current.getFrameTenExclusive();
            return current.toBuilder().frameFinalScore(res + lastSum).build();
        } else if (frameIndex == 8) { // edge cases
            FrameScore nextFrame = frameScores.get(frameIndex + 1);
            nextBall = nextFrame.getFirstChance();
            nextTwoBall = nextFrame.getSecondChance();
        } else {
            nextBall = frameScores.get(frameIndex + 1).getFirstChance();
            nextTwoBall = frameScores.get(frameIndex + 1).isStrike() ?
                    frameScores.get(frameIndex + 2).getFirstChance() :
                    frameScores.get(frameIndex + 1).getSecondChance();
        }

        return calculateStrikeFrameWith(lastSum, current, nextBall, nextTwoBall);
    }

    private FrameScore calculateStrikeFrameWith(int lastSum, FrameScore current, int nextBall, int nextTwoBall) {
        return current
                .toBuilder()
                .frameFinalScore(lastSum + current.getFirstChance() + current.getSecondChance() + nextBall + nextTwoBall)
                .build();
    }

    private FrameScore calculateSpareFrameWith(int lastSum, FrameScore current, int nextBall) {
        return current
                .toBuilder()
                .frameFinalScore(lastSum + current.getFirstChance() + current.getSecondChance() + nextBall)
                .build();

    }

    private FrameScore calculateFrameWith(int lastSum, FrameScore current) {
        return current
                .toBuilder()
                .frameFinalScore(lastSum + current.getFirstChance() + current.getSecondChance())
                .build();
    }
}
