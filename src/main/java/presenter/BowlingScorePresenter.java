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

    @Override
    public void presentInvalidData(String fPath) {
        System.out.println("Invalid Data detected for file: " + fPath);
    }

    @Override
    public void initialPresentation(String filePath) {
        System.out.println("#############################################");
        System.out.println("Bowling Game Interpreter:");
        String workdir = System.getProperty("user.dir");
        System.out.println("Lookup Files Directory: " + workdir);
        System.out.println("Filepath: " + filePath);
        System.out.println("#############################################");
    }

    @Override
    public void fileNotProvided() {
        System.out.println("Input file not provided");
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
                return TAB + frameScore.getFirstScorePresentable() + TAB + "/";
            } else {
                return TAB + frameScore.getFirstScorePresentable() + TAB + frameScore.getSecondScorePresentable();
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
            return TAB + frameScore.getFirstScorePresentable() +
                    TAB + frameScore.getSecondScorePresentable();
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
