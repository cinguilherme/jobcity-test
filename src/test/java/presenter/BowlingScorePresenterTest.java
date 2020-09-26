package presenter;

import matchbuilder.FrameScore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BowlingScorePresenterTest {

    private MatchScorePresenter scorePresenter;

    @BeforeEach
    void setup() {
        scorePresenter = new BowlingScorePresenter();
    }

    @Test
    void shouldPresentCorrectly() {
        String playerName = "Jeff";
        List<FrameScore> frameScores = sampleSimpleAllStrikesValidCalculatedScores();
        PlayerPresenterScore presentScore = scorePresenter.presentPlayerScore(playerName, frameScores);

        String expectedFrameLine = "Frame\t\t1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\t\t9\t\t10\n";
        String expectePlayerLine = "Jeff\n";
        String expected_Pinfalls = "Pinfalls\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\tX\tX\n";
        String expected___Scores = "Score\t\t30\t\t60\t\t90\t\t120\t\t150\t\t180\t\t210\t\t240\t\t270\t\t300\n";

        assertThat(presentScore)
                .extracting(PlayerPresenterScore::getFrame, PlayerPresenterScore::getPinFalls,
                        PlayerPresenterScore::getScore, PlayerPresenterScore::getPlayerName)
                .containsExactlyInAnyOrder(expected_Pinfalls, expectedFrameLine, expectePlayerLine, expected___Scores);
    }

    private List<FrameScore> sampleMixedValidScores() {
        return List.of(
                FrameScore.builder().firstChance(8).secondChance(2).build(),
                FrameScore.builder().firstChance(7).secondChance(3).build(),
                FrameScore.builder().firstChance(3).secondChance(4).build(),
                FrameScore.builder().firstChance(10).build(),
                FrameScore.builder().firstChance(2).secondChance(8).build(),
                FrameScore.builder().firstChance(10).build(),
                FrameScore.builder().firstChance(10).build(),
                FrameScore.builder().firstChance(8).secondChance(0).build(),
                FrameScore.builder().firstChance(10).build(),
                FrameScore.builder().firstChance(8).secondChance(2).frameTenExclusive(9).build()
        );
    }

    private List<FrameScore> sampleSimpleAllStrikesValidCalculatedScores() {
        return List.of(
                FrameScore.builder().firstChance(10).frameFinalScore(30).build(),
                FrameScore.builder().firstChance(10).frameFinalScore(60).build(),
                FrameScore.builder().firstChance(10).frameFinalScore(90).build(),
                FrameScore.builder().firstChance(10).frameFinalScore(120).build(),
                FrameScore.builder().firstChance(10).frameFinalScore(150).build(),
                FrameScore.builder().firstChance(10).frameFinalScore(180).build(),
                FrameScore.builder().firstChance(10).frameFinalScore(210).build(),
                FrameScore.builder().firstChance(10).frameFinalScore(240).build(),
                FrameScore.builder().firstChance(10).frameFinalScore(270).build(),
                FrameScore.builder().isFinalFrame(true).firstChance(10).secondChance(10).frameTenExclusive(10).frameFinalScore(300).build()
        );
    }

    private List<FrameScore> sampleSimpleAllSpareValidScores() {
        return List.of(
                FrameScore.builder().firstChance(5).secondChance(5).build(),
                FrameScore.builder().firstChance(5).secondChance(5).build(),
                FrameScore.builder().firstChance(5).secondChance(5).build(),
                FrameScore.builder().firstChance(5).secondChance(5).build(),
                FrameScore.builder().firstChance(5).secondChance(5).build(),
                FrameScore.builder().firstChance(5).secondChance(5).build(),
                FrameScore.builder().firstChance(5).secondChance(5).build(),
                FrameScore.builder().firstChance(5).secondChance(5).build(),
                FrameScore.builder().firstChance(5).secondChance(5).build(),
                FrameScore.builder().firstChance(5).secondChance(5).frameTenExclusive(5).build()
        );
    }

    private List<FrameScore> sampleSimpleNoConvertionsValidScores() {
        return List.of(
                FrameScore.builder().firstChance(4).secondChance(5).build(),
                FrameScore.builder().firstChance(4).secondChance(5).build(),
                FrameScore.builder().firstChance(4).secondChance(5).build(),
                FrameScore.builder().firstChance(4).secondChance(5).build(),
                FrameScore.builder().firstChance(4).secondChance(5).build(),
                FrameScore.builder().firstChance(4).secondChance(5).build(),
                FrameScore.builder().firstChance(4).secondChance(5).build(),
                FrameScore.builder().firstChance(4).secondChance(5).build(),
                FrameScore.builder().firstChance(4).secondChance(5).build(),
                FrameScore.builder().firstChance(4).secondChance(5).build()
        );
    }

}