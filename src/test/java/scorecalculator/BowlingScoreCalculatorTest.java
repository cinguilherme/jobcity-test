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
    void shouldCalculateEachFrameScore() {
        List<FrameScore> frameScores = sampleSimpleAllStrikesValidScores();
        List<FrameScore> actualScores = scoreCalculator.calculateFramesScores(frameScores);
        assertThat(actualScores)
                .extracting(FrameScore::getFrameFinalScore)
                .containsExactlyInAnyOrder(30, 60, 90, 120, 150, 180, 210, 240, 270, 300);
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
                FrameScore.builder().firstChance(10).build(),
                FrameScore.builder().firstChance(10).build(),
                FrameScore.builder().firstChance(10).build(),
                FrameScore.builder().firstChance(10).build(),
                FrameScore.builder().firstChance(10).build(),
                FrameScore.builder().firstChance(10).build(),
                FrameScore.builder().firstChance(10).build(),
                FrameScore.builder().firstChance(10).build(),
                FrameScore.builder().firstChance(10).build(),
                FrameScore.builder().firstChance(10).build(),
                FrameScore.builder().firstChance(10).build()
        );
    }

    private List<FrameScore> sampleSimpleNoConvertionsValidScores() {
        return List.of(
                FrameScore.builder().firstChance(4).build(),
                FrameScore.builder().firstChance(4).build(),
                FrameScore.builder().firstChance(4).build(),
                FrameScore.builder().firstChance(4).build(),
                FrameScore.builder().firstChance(4).build(),
                FrameScore.builder().firstChance(4).build(),
                FrameScore.builder().firstChance(4).build(),
                FrameScore.builder().firstChance(4).build(),
                FrameScore.builder().firstChance(4).build(),
                FrameScore.builder().firstChance(4).build(),
                FrameScore.builder().firstChance(4).build(),
                FrameScore.builder().firstChance(4).build(),
                FrameScore.builder().firstChance(4).build(),
                FrameScore.builder().firstChance(4).build(),
                FrameScore.builder().firstChance(4).build(),
                FrameScore.builder().firstChance(4).build(),
                FrameScore.builder().firstChance(4).build(),
                FrameScore.builder().firstChance(4).build(),
                FrameScore.builder().firstChance(4).build(),
                FrameScore.builder().firstChance(4).build(),
                FrameScore.builder().firstChance(4).build()
        );
    }
}