package scorecalculator;

import matchbuilder.FrameScore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BowlingScoreCalculatorTest {

    private ScoreCalculator scoreCalculator;

    @BeforeEach
    void setup() {
        scoreCalculator = new BowlingScoreCalculator();
    }

    @Test
    void shouldCalculateEachFrameScoreAllStrike() {
        List<FrameScore> frameScores = sampleSimpleAllStrikesValidScores();
        List<FrameScore> actualScores = scoreCalculator.calculateFramesScores(frameScores);
        assertThat(actualScores)
                .extracting(FrameScore::getFrameFinalScore)
                .containsExactlyInAnyOrder(30, 60, 90, 120, 150, 180, 210, 240, 270, 300);
    }

    @Test
    void shouldCalculateEachFrameScoreAllSpare_all_5() {
        List<FrameScore> frameScores = sampleSimpleAllSpareValidScores();
        List<FrameScore> actualScores = scoreCalculator.calculateFramesScores(frameScores);
        assertThat(actualScores)
                .extracting(FrameScore::getFrameFinalScore)
                .containsExactlyInAnyOrder(15, 30, 45, 60, 75, 90, 105, 120, 135, 150);
    }

    @Test
    void shouldCalculateEachFrameScore_no_spare_no_strikes_all_9() {
        List<FrameScore> frameScores = sampleSimpleNoConvertionsValidScores();
        List<FrameScore> actualScores = scoreCalculator.calculateFramesScores(frameScores);
        assertThat(actualScores)
                .extracting(FrameScore::getFrameFinalScore)
                .containsExactlyInAnyOrder(9, 18, 27, 36, 45, 54, 63, 72, 81, 90);
    }

    @Test
    void shouldCaclculateMixedSetOfThrows() {
        List<FrameScore> frameScores = sampleMixedValidScores();
        List<FrameScore> actualScores = scoreCalculator.calculateFramesScores(frameScores);
        assertThat(actualScores)
                .extracting(FrameScore::getFrameFinalScore)
                .containsExactlyInAnyOrder(17, 30, 37, 57, 77, 105, 123, 131, 151, 170);
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

    private List<FrameScore> sampleSimpleAllStrikesValidScores() {
        return List.of(
                FrameScore.builder().firstChance(10).build(),
                FrameScore.builder().firstChance(10).build(),
                FrameScore.builder().firstChance(10).build(),
                FrameScore.builder().firstChance(10).build(),
                FrameScore.builder().firstChance(10).build(),
                FrameScore.builder().firstChance(10).build(),
                FrameScore.builder().firstChance(10).build(),
                FrameScore.builder().firstChance(10).build(),
                FrameScore.builder().firstChance(10).build(),
                FrameScore.builder().firstChance(10).secondChance(10).frameTenExclusive(10).build()
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