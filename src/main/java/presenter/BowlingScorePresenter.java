package presenter;

import matchbuilder.FrameScore;

import java.util.List;

public class BowlingScorePresenter implements MatchScorePresenter {

    @Override
    public PlayerPresenterScore presentPlayerScore(String playerName, List<FrameScore> calculatedScore) {

        return PlayerPresenterScore.builder()
                .playerName(playerName + "\n")
                .frame("Frame\t\t1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\t\t9\t\t10\n")
                .score(buildScorePresentedString(calculatedScore))
                .pinFalls(buildPinFalls(calculatedScore))
                .build();
    }

    private String buildPinFalls(List<FrameScore> calculatedScore) {
        return calculatedScore.stream()
                .map(this::pinValue)
                .reduce("Pinfalls", (acc, cur) -> acc + cur) + "\n";
    }

    private String pinValue(FrameScore frameScore) {
        if (!frameScore.isFinalFrame()) {
            if (frameScore.isStrike()) {
                return "\t\tX";
            } else if (frameScore.isSpare()) {
                return "\t" + frameScore.getFirstChance() + "\t" + "/";
            } else {
                return "\t" + frameScore.getFirstChance() + "\t" + frameScore.getSecondChance();
            }
        } else {
            return "\t\tX\tX\tX";
        }
    }

    private String buildScorePresentedString(List<FrameScore> calculatedScore) {
        return calculatedScore.stream()
                .map(FrameScore::getFrameFinalScore)
                .map(String::valueOf)
                .reduce("Score", (acc, cur) -> acc + "\t\t" + cur) + "\n";
    }

    private String getCharRepresentation(FrameScore frameScore) {
        if (frameScore.isStrike()) return "\tX";
        else if (frameScore.isSpare()) return getRegularValueString(frameScore.getFirstChance()) + "\t/";
        else {
            return getRegularValueString(frameScore.getFirstChance()) +
                    "\t" +
                    getRegularValueString(frameScore.getSecondChance());
        }
    }

    private String getLastFrameRepresentation(FrameScore frameScore) {
        return getRegularValueString(
                frameScore.getFirstChance()) + "\t" +
                getRegularValueString(frameScore.getSecondChance()) + "\t" +
                getRegularValueString(frameScore.getFrameTenExclusive());
    }

    private String getRegularValueString(int value) {
        if (value == 0) return "-";
        return String.valueOf(value);
    }
}
