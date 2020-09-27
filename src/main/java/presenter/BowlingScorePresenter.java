package presenter;

import matchbuilder.FrameScore;

import java.util.List;

public class BowlingScorePresenter implements MatchScorePresenter {

    public static final String DOUBLE_TAB = "\t\t";
    public static final String TAB = "\t";

    @Override
    public PlayerPresenterScore presentPlayerScore(List<FrameScore> calculatedScore) {
        String playerNameExtracted = calculatedScore.stream()
                .filter(frame -> frame.getPlayerName() != null && !frame.getPlayerName().isBlank())
                .map(FrameScore::getPlayerName)
                .findFirst()
                .orElseGet(() -> "Player name not supplied");

        return PlayerPresenterScore.builder()
                .playerName(playerNameExtracted)
                .frame("Frame\t\t1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\t\t9\t\t10")
                .score(buildScorePresentedString(calculatedScore))
                .pinFalls(buildPinFalls(calculatedScore))
                .build();
    }

    private String buildPinFalls(List<FrameScore> calculatedScore) {
        return calculatedScore.stream()
                .map(this::pinValue)
                .reduce("Pinfalls", (acc, cur) -> acc + cur);
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
            return TAB + "X" +
                    TAB + (frameScore.getSecondChance() == 10 ? "X" : frameScore.getSecondChance()) +
                    TAB + (frameScore.getFrameTenExclusive() == 10 ? "X" : frameScore.getFrameTenExclusive());
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
                .reduce("Score", (acc, cur) -> acc + DOUBLE_TAB + cur);
    }

    private String getRegularValueString(int value) {
        if (value == 0) return "-";
        return String.valueOf(value);
    }
}
