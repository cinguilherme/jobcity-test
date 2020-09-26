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

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class TxtToFrames {

    private final String firstMatchResource = "firstMatch.txt";
    private final String secondMatchResource = "secondMatch.txt";
    private final String emptyMatchResource = "empty.txt";

    private MatchParser matchTextParser;
    private MatchBuilder bowlingMatchBuilder;

    @BeforeEach
    void setup() {
        matchTextParser = new MatchTextParser();
        bowlingMatchBuilder = new BowlingMatchBuilder();
    }

    @Test
    @DisplayName("Should parse the txt file for the first game with mixed results and construct FrameScores according")
    void should_parse_txt_valid_input_one() {
        Stream<Chance> chanceStream = matchTextParser.parseInput(firstMatchResource);
        Map<String, List<Chance>> map = bowlingMatchBuilder.mapPlayersChance(chanceStream);

        List<Boolean> validDatas = map.values().stream().map(playerList -> bowlingMatchBuilder.validatePlayerMatchData(playerList.stream())).collect(toList());
        assertThat(validDatas).allMatch(hasToBeTrue -> hasToBeTrue);

        List<List<FrameScore>> allPlayersScores = map.values().stream()
                .map(playerList -> bowlingMatchBuilder.getPlayersChancesAsFrameScore(playerList.stream()))
                .collect(toList());
        assertThat(allPlayersScores).allMatch(scores -> scores.size() == 10);
    }

    @Test
    @DisplayName("Should parse the txt file for the second game with all strikes and construct FrameScores according")
    void should_parse_txt_valid_input_two() {
        Stream<Chance> chanceStream = matchTextParser.parseInput(secondMatchResource);
        Map<String, List<Chance>> map = bowlingMatchBuilder.mapPlayersChance(chanceStream);

        List<Boolean> validDatas = map.values().stream().map(playerList -> bowlingMatchBuilder.validatePlayerMatchData(playerList.stream())).collect(toList());
        assertThat(validDatas).allMatch(hasToBeTrue -> hasToBeTrue);

        List<List<FrameScore>> allPlayersScores = map.values().stream()
                .map(playerList -> bowlingMatchBuilder.getPlayersChancesAsFrameScore(playerList.stream()))
                .collect(toList());
        List<FrameScore> carlScores = allPlayersScores.get(0);
        assertThat(carlScores).hasSize(10);
        assertThat(carlScores).allMatch(FrameScore::isStrike);
    }

    @Test
    @DisplayName("Should parse the txt file for the empty game")
    void should_parse_txt_invalid_input_empty() {
        Stream<Chance> chanceStream = matchTextParser.parseInput(emptyMatchResource);
        Map<String, List<Chance>> map = bowlingMatchBuilder.mapPlayersChance(chanceStream);

        List<Boolean> validDatas = map.values().stream().map(playerList -> bowlingMatchBuilder.validatePlayerMatchData(playerList.stream())).collect(toList());
        assertThat(validDatas).isEmpty();
    }

}
