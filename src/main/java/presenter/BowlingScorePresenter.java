package presenter;

import matchbuilder.FrameScore;

import java.util.List;

public class BowlingScorePresenter implements MatchScorePresenter {

    public static final String DOUBLE_TAB = "\t\t";
    public static final String TAB = "\t";

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
                return DOUBLE_TAB + "X";
            } else if (frameScore.isSpare()) {
                return TAB + frameScore.getFirstChance() + TAB + "/";
            } else {
                return TAB + frameScore.getFirstChance() + TAB + frameScore.getSecondChance();
            }
        } else {
            return getLastFrameString(frameScore);
        }
    }

    private String getLastFrameString(FrameScore frameScore) {
        if (frameScore.isStrike()) {
            return DOUBLE_TAB + "X" +
                    TAB + "X" +
                    TAB + "X";
        } else if (frameScore.isSpare()) {
            return TAB + frameScore.getFirstChance() +
                    TAB + "/" +
                    TAB + (frameScore.getFrameTenExclusive() == 10 ? "X" : frameScore.getFrameTenExclusive());
        } else {
            return TAB + frameScore.getFirstChance() +
                    TAB + frameScore.getSecondChance();
        }
    }

    private String buildScorePresentedString(List<FrameScore> calculatedScore) {
        return calculatedScore.stream()
                .map(FrameScore::getFrameFinalScore)
                .map(String::valueOf)
                .reduce("Score", (acc, cur) -> acc + DOUBLE_TAB + cur) + "\n";
    }

    private String getCharRepresentation(FrameScore frameScore) {
        if (frameScore.isStrike()) return "\tX";
        else if (frameScore.isSpare()) return getRegularValueString(frameScore.getFirstChance()) + "\t/";
        else {
            return getRegularValueString(frameScore.getFirstChance()) +
                    TAB +
                    getRegularValueString(frameScore.getSecondChance());
        }
    }

    private String getLastFrameRepresentation(FrameScore frameScore) {
        return getRegularValueString(
                frameScore.getFirstChance()) + TAB +
                getRegularValueString(frameScore.getSecondChance()) + TAB +
                getRegularValueString(frameScore.getFrameTenExclusive());
    }

    private String getRegularValueString(int value) {
        if (value == 0) return "-";
        return String.valueOf(value);
    }
}
