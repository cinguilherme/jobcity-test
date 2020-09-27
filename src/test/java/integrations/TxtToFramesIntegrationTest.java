package integrations;

import matchbuilder.BowlingMatchBuilder;
import matchbuilder.FrameScore;
import matchbuilder.MatchBuilder;
import matchparser.Chance;
import matchparser.MatchParser;
import matchparser.MatchTextParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import scorecalculator.BowlingScoreCalculator;
import scorecalculator.ScoreCalculator;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class TxtToFramesIntegrationTest {

    private final String firstMatchResource = "firstMatch.txt";
    private final String secondMatchResource = "secondMatch.txt";
    private final String emptyMatchResource = "empty.txt";

    private final ClassLoader classLoader = getClass().getClassLoader();

    private MatchParser matchTextParser;
    private MatchBuilder bowlingMatchBuilder;
    private ScoreCalculator scoreCalculator;

    @BeforeEach
    void setup() {
        matchTextParser = new MatchTextParser();
        bowlingMatchBuilder = new BowlingMatchBuilder();
        scoreCalculator = new BowlingScoreCalculator();
    }

    @Test
    @DisplayName("Should parse the txt file for the first game with mixed results and construct FrameScores according")
    void should_parse_txt_valid_input_one() {
        String resource = classLoader.getResource(firstMatchResource).getFile();

        List<Chance> chanceStream = matchTextParser.parseInput(resource);
        Map<String, List<Chance>> map = bowlingMatchBuilder.mapPlayersChance(chanceStream);

        List<Boolean> validDatas = map.values().stream().map(playerList -> bowlingMatchBuilder.validatePlayerMatchData(playerList)).collect(toList());
        assertThat(validDatas).allMatch(hasToBeTrue -> hasToBeTrue);

        List<List<FrameScore>> allPlayersScores = map.values().stream()
                .map(playerList -> bowlingMatchBuilder.getPlayersChancesAsFrameScore(playerList))
                .collect(toList());
        assertThat(allPlayersScores).allMatch(scores -> scores.size() == 10);

        List<List<FrameScore>> allPlayersCalculatedScores = allPlayersScores.stream()
                .map(playerScores -> scoreCalculator.calculateFramesScores(playerScores))
                .collect(toList());

        List<FrameScore> johnScores = allPlayersCalculatedScores.get(0);
        List<FrameScore> jeffScores = allPlayersCalculatedScores.get(0);

        assertThat(johnScores.get(9).getFrameFinalScore()).isEqualTo(151);
        assertThat(jeffScores.get(9).getFrameFinalScore()).isEqualTo(151);
    }

    @Test
    @DisplayName("Should parse the txt file for the second game with all strikes and construct FrameScores according")
    void should_parse_txt_valid_input_two() {
        String resource = classLoader.getResource(secondMatchResource).getFile();

        List<Chance> chanceStream = matchTextParser.parseInput(resource);
        Map<String, List<Chance>> map = bowlingMatchBuilder.mapPlayersChance(chanceStream);

        List<Boolean> validDatas = map.values().stream().map(playerList -> bowlingMatchBuilder.validatePlayerMatchData(playerList)).collect(toList());
        assertThat(validDatas).allMatch(hasToBeTrue -> hasToBeTrue);

        List<List<FrameScore>> allPlayersScores = map.values().stream()
                .map(playerList -> bowlingMatchBuilder.getPlayersChancesAsFrameScore(playerList))
                .collect(toList());
        List<FrameScore> carlScores = allPlayersScores.get(0);
        assertThat(carlScores).hasSize(10);
        assertThat(carlScores).allMatch(FrameScore::isStrike);

        List<FrameScore> frameScoresCalculated = scoreCalculator.calculateFramesScores(carlScores);
        assertThat(frameScoresCalculated.get(9).getFrameFinalScore()).isEqualTo(300);
    }

    @Test
    @DisplayName("Should parse the txt file for the empty game")
    void should_parse_txt_invalid_input_empty() {
        String resource = classLoader.getResource(emptyMatchResource).getFile();
        List<Chance> chanceStream = matchTextParser.parseInput(resource);
        Map<String, List<Chance>> map = bowlingMatchBuilder.mapPlayersChance(chanceStream);

        List<Boolean> validDatas = map.values().stream()
                .map(playerList -> bowlingMatchBuilder.validatePlayerMatchData(playerList))
                .collect(toList());
        assertThat(validDatas).isEmpty();
    }

}
