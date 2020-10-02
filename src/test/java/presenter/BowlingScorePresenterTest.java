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
    void shouldPresentCorrectly_perfect_score() {
        List<FrameScore> frameScores = sampleSimpleAllStrikesValidCalculatedScores("Jeff");
        PlayerPresenterScore presentScore = scorePresenter.presentPlayerScore(frameScores);

        String expectedFrameLine = "Frame\t\t1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\t\t9\t\t10";
        String expectePlayerLine = "Jeff";
        String expected_Pinfalls = "Pinfalls\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\tX\tX\tX";
        String expected___Scores = "Score\t\t30\t\t60\t\t90\t\t120\t\t150\t\t180\t\t210\t\t240\t\t270\t\t300";

        assertThat(presentScore)
                .extracting(PlayerPresenterScore::getFrame, PlayerPresenterScore::getPinFalls,
                        PlayerPresenterScore::getScore, PlayerPresenterScore::getPlayerName)
                .containsExactlyInAnyOrder(expected_Pinfalls, expectedFrameLine, expectePlayerLine, expected___Scores);
    }

    @Test
    void shouldPresentCorrecly_all_spare_results() {
        List<FrameScore> frameScores = sampleSimpleAllSpareValidScores("Jeff");
        PlayerPresenterScore presentScore = scorePresenter.presentPlayerScore(frameScores);

        String expectedFrameLine = "Frame\t\t1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\t\t9\t\t10";
        String expectePlayerLine = "Jeff";
        String expected_Pinfalls = "Pinfalls\t5\t/\t5\t/\t5\t/\t5\t/\t5\t/\t5\t/\t5\t/\t5\t/\t5\t/\t5\t/\t5";
        String expected___Scores = "Score\t\t15\t\t30\t\t45\t\t60\t\t75\t\t90\t\t105\t\t120\t\t135\t\t150";

        assertThat(presentScore)
                .extracting(PlayerPresenterScore::getFrame, PlayerPresenterScore::getPinFalls,
                        PlayerPresenterScore::getScore, PlayerPresenterScore::getPlayerName)
                .containsExactlyInAnyOrder(expected_Pinfalls, expectedFrameLine, expectePlayerLine, expected___Scores);
    }

    @Test
    void shouldPresentCorrecly_all_no_convertions_results() {
        String player = "Max";
        List<FrameScore> frameScores = sampleSimpleNoConvertionsValidScores();
        PlayerPresenterScore presentScore = scorePresenter.presentPlayerScore(frameScores);

        String expectedFrameLine = "Frame\t\t1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\t\t9\t\t10";
        String expectePlayerLine = "Max";
        String expected_Pinfalls = "Pinfalls\t4\t5\t4\t5\t4\t5\t4\t5\t4\t5\t4\t5\t4\t5\t4\t5\t4\t5\t4\t5";
        String expected___Scores = "Score\t\t9\t\t18\t\t27\t\t36\t\t45\t\t54\t\t63\t\t72\t\t81\t\t90";

        assertThat(presentScore)
                .extracting(PlayerPresenterScore::getFrame, PlayerPresenterScore::getPinFalls,
                        PlayerPresenterScore::getScore, PlayerPresenterScore::getPlayerName)
                .containsExactlyInAnyOrder(expected_Pinfalls, expectedFrameLine, expectePlayerLine, expected___Scores);
    }

    @Test
    void shouldPresentCorrecly_all_mix_results() {
        String player = "Mix";
        List<FrameScore> frameScores = sampleMixedValidScores();
        PlayerPresenterScore presentScore = scorePresenter.presentPlayerScore(frameScores);

        String expectedFrameLine = "Frame\t\t1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\t\t9\t\t10";
        String expectePlayerLine = "Mix";
        String expected_Pinfalls = "Pinfalls\t8\t/\t7\t/\t3\t4\t\tX\t2\t/\t\tX\t\tX\t8\t0\t\tX\t8\t/\t9";
        String expected___Scores = "Score\t\t17\t\t30\t\t37\t\t57\t\t77\t\t105\t\t123\t\t131\t\t151\t\t170";

        assertThat(presentScore)
                .extracting(PlayerPresenterScore::getFrame, PlayerPresenterScore::getPinFalls,
                        PlayerPresenterScore::getScore, PlayerPresenterScore::getPlayerName)
                .containsExactlyInAnyOrder(expected_Pinfalls, expectedFrameLine, expectePlayerLine, expected___Scores);
    }

    @Test
    void shouldPresentCorrecly_all_fault_results() {
        String player = "Faults";
        List<FrameScore> frameScores = sampleSimpleAllFaultValidCalculatedScores(player);
        PlayerPresenterScore presentScore = scorePresenter.presentPlayerScore(frameScores);

        String expectedFrameLine = "Frame\t\t1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\t\t9\t\t10";
        String expectePlayerLine = "Faults";
        String expected_Pinfalls = "Pinfalls\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF";
        String expected___Scores = "Score\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0";

        assertThat(presentScore)
                .extracting(PlayerPresenterScore::getFrame, PlayerPresenterScore::getPinFalls,
                        PlayerPresenterScore::getScore, PlayerPresenterScore::getPlayerName)
                .containsExactlyInAnyOrder(expected_Pinfalls, expectedFrameLine, expectePlayerLine, expected___Scores);
    }

    private List<FrameScore> sampleMixedValidScores() {
        return List.of(
                FrameScore.builder().playerName("Mix").frameFinalScore(17)
                        .firstChance(8).secondChance(2).build(),
                FrameScore.builder().playerName("Mix").frameFinalScore(30)
                        .firstChance(7).secondChance(3).build(),
                FrameScore.builder().playerName("Mix").frameFinalScore(37)
                        .firstChance(3).secondChance(4).build(),
                FrameScore.builder().playerName("Mix").frameFinalScore(57)
                        .firstChance(10).build(),
                FrameScore.builder().playerName("Mix").frameFinalScore(77)
                        .firstChance(2).secondChance(8).build(),
                FrameScore.builder().playerName("Mix").frameFinalScore(105)
                        .firstChance(10).build(),
                FrameScore.builder().playerName("Mix").frameFinalScore(123)
                        .firstChance(10).build(),
                FrameScore.builder().playerName("Mix").frameFinalScore(131)
                        .firstChance(8).secondChance(0).build(),
                FrameScore.builder().playerName("Mix").frameFinalScore(151)
                        .firstChance(10).build(),
                FrameScore.builder().playerName("Mix").frameFinalScore(170).isFinalFrame(true)
                        .firstChance(8).secondChance(2).frameTenExclusive(9).build()
        );
    }

    private List<FrameScore> sampleSimpleAllStrikesValidCalculatedScores(String playerName) {
        return List.of(
                FrameScore.builder().playerName(playerName).firstChance(10).frameFinalScore(30).build(),
                FrameScore.builder().playerName(playerName).firstChance(10).frameFinalScore(60).build(),
                FrameScore.builder().playerName(playerName).firstChance(10).frameFinalScore(90).build(),
                FrameScore.builder().playerName(playerName).firstChance(10).frameFinalScore(120).build(),
                FrameScore.builder().playerName(playerName).firstChance(10).frameFinalScore(150).build(),
                FrameScore.builder().playerName(playerName).firstChance(10).frameFinalScore(180).build(),
                FrameScore.builder().playerName(playerName).firstChance(10).frameFinalScore(210).build(),
                FrameScore.builder().playerName(playerName).firstChance(10).frameFinalScore(240).build(),
                FrameScore.builder().playerName(playerName).firstChance(10).frameFinalScore(270).build(),
                FrameScore.builder().playerName(playerName).isFinalFrame(true).firstChance(10).secondChance(10)
                        .frameTenExclusive(10).frameFinalScore(300).build()
        );
    }

    private List<FrameScore> sampleSimpleAllFaultValidCalculatedScores(String playerName) {
        return List.of(
                FrameScore.builder().playerName(playerName).firstChance(0).isFirstFault(true).isSecondFault(true).secondChance(0).frameFinalScore(0).build(),
                FrameScore.builder().playerName(playerName).firstChance(0).isFirstFault(true).isSecondFault(true).secondChance(0).frameFinalScore(0).build(),
                FrameScore.builder().playerName(playerName).firstChance(0).isFirstFault(true).isSecondFault(true).secondChance(0).frameFinalScore(0).build(),
                FrameScore.builder().playerName(playerName).firstChance(0).isFirstFault(true).isSecondFault(true).secondChance(0).frameFinalScore(0).build(),
                FrameScore.builder().playerName(playerName).firstChance(0).isFirstFault(true).isSecondFault(true).secondChance(0).frameFinalScore(0).build(),
                FrameScore.builder().playerName(playerName).firstChance(0).isFirstFault(true).isSecondFault(true).secondChance(0).frameFinalScore(0).build(),
                FrameScore.builder().playerName(playerName).firstChance(0).isFirstFault(true).isSecondFault(true).secondChance(0).frameFinalScore(0).build(),
                FrameScore.builder().playerName(playerName).firstChance(0).isFirstFault(true).isSecondFault(true).secondChance(0).frameFinalScore(0).build(),
                FrameScore.builder().playerName(playerName).firstChance(0).isFirstFault(true).isSecondFault(true).secondChance(0).frameFinalScore(0).build(),
                FrameScore.builder().playerName(playerName).isFinalFrame(true).firstChance(0).secondChance(0).isFirstFault(true).isSecondFault(true).frameFinalScore(0).build()
        );
    }

    private List<FrameScore> sampleSimpleAllSpareValidScores(String playerName) {
        return List.of(
                FrameScore.builder().playerName(playerName).firstChance(5).secondChance(5).frameFinalScore(15).build(),
                FrameScore.builder().playerName(playerName).firstChance(5).secondChance(5).frameFinalScore(30).build(),
                FrameScore.builder().playerName(playerName).firstChance(5).secondChance(5).frameFinalScore(45).build(),
                FrameScore.builder().playerName(playerName).firstChance(5).secondChance(5).frameFinalScore(60).build(),
                FrameScore.builder().playerName(playerName).firstChance(5).secondChance(5).frameFinalScore(75).build(),
                FrameScore.builder().playerName(playerName).firstChance(5).secondChance(5).frameFinalScore(90).build(),
                FrameScore.builder().playerName(playerName).firstChance(5).secondChance(5).frameFinalScore(105).build(),
                FrameScore.builder().playerName(playerName).firstChance(5).secondChance(5).frameFinalScore(120).build(),
                FrameScore.builder().playerName(playerName).firstChance(5).secondChance(5).frameFinalScore(135).build(),
                FrameScore.builder().playerName(playerName).firstChance(5).secondChance(5)
                        .isFinalFrame(true).frameTenExclusive(5).frameFinalScore(150).build()
        );
    }

    private List<FrameScore> sampleSimpleNoConvertionsValidScores() {
        return List.of(
                FrameScore.builder().firstChance(4).secondChance(5).playerName("Max").frameFinalScore(9).build(),
                FrameScore.builder().firstChance(4).secondChance(5).playerName("Max").frameFinalScore(18).build(),
                FrameScore.builder().firstChance(4).secondChance(5).playerName("Max").frameFinalScore(27).build(),
                FrameScore.builder().firstChance(4).secondChance(5).playerName("Max").frameFinalScore(36).build(),
                FrameScore.builder().firstChance(4).secondChance(5).playerName("Max").frameFinalScore(45).build(),
                FrameScore.builder().firstChance(4).secondChance(5).playerName("Max").frameFinalScore(54).build(),
                FrameScore.builder().firstChance(4).secondChance(5).playerName("Max").frameFinalScore(63).build(),
                FrameScore.builder().firstChance(4).secondChance(5).playerName("Max").frameFinalScore(72).build(),
                FrameScore.builder().firstChance(4).secondChance(5).playerName("Max").frameFinalScore(81).build(),
                FrameScore.builder().firstChance(4).secondChance(5).playerName("Max")
                        .frameFinalScore(90).isFinalFrame(true).build()
        );
    }

}