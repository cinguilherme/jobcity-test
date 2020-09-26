package matchbuilder;

import matchparser.Chance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class BowlingMatchBuilderTest {

    public static final String JOHN = "John";
    public static final String JEFF = "Jeff";

    private MatchBuilder subject;

    @BeforeEach
    void setup() {
        subject = new BowlingMatchBuilder();
    }

    @Test
    void should_map_players_with_chances() {
        List<Chance> johnChances = validChances(JOHN, 20);
        List<Chance> jeffChances = validChances(JEFF, 10);
        Stream<Chance> allChances = Stream.concat(johnChances.stream(), jeffChances.stream());
        Map<String, List<Chance>> playersChanceMap = subject.mapPlayersChance(allChances);
        assertThat(playersChanceMap).isNotEmpty();
        assertThat(playersChanceMap.get(JOHN)).hasSize(20);
        assertThat(playersChanceMap.get(JEFF)).hasSize(10);
    }

    @Test
    void should_validate_player_match() {
        List<Chance> johnChances = validChances(JOHN, 20);
        Boolean actual = subject.validatePlayerMatchData(johnChances.stream());
        assertThat(actual).isTrue();
    }

    @Test
    void should_validate_player_match_with_faults() {
        List<Chance> johnChances = validChancesNoSparesWithFaults(JOHN, 20, 4);
        Boolean actual = subject.validatePlayerMatchData(johnChances.stream());
        assertThat(actual).isTrue();
    }

    @Test
    void should_validate_player_match_with_all_faults() {
        List<Chance> johnChances = validChancesNoSparesWithFaults(JOHN, 20, 20);
        Boolean actual = subject.validatePlayerMatchData(johnChances.stream());
        assertThat(actual).isTrue();
    }

    @Test
    void shouldConvertPlayersChancesIntoFrameScores_All_Faults() {
        List<Chance> johnChances = validChancesNoSparesWithFaults(JOHN, 20, 20);
        List<FrameScore> playerFrameScores = subject.getPlayersChancesAsFrameScore(johnChances.stream());

        assertThat(playerFrameScores).hasSize(10);
        assertThat(playerFrameScores).noneMatch(FrameScore::isSpare);
        assertThat(playerFrameScores).noneMatch(FrameScore::isStrike);
        assertThat(playerFrameScores).allMatch(f -> f.getFirstChance() == 0);
        assertThat(playerFrameScores).allMatch(f -> f.getSecondChance() == 0);

        assertThat(playerFrameScores.get(9)).extracting(FrameScore::getFrameTenExclusive).isEqualTo(0);
    }

    @Test
    void shouldConvertPlayersChancesIntoFrameScores_Some_Faults() {
        List<Chance> johnChances = validChancesNoSparesWithFaults(JOHN, 20, 4);
        List<FrameScore> playerFrameScores = subject.getPlayersChancesAsFrameScore(johnChances.stream());

        assertThat(playerFrameScores).hasSize(10);
        assertThat(playerFrameScores).noneMatch(FrameScore::isSpare);
        assertThat(playerFrameScores).noneMatch(FrameScore::isStrike);
        assertThat(playerFrameScores).extracting(FrameScore::getFirstChance, FrameScore::getSecondChance)
                .contains(tuple(4, 4), tuple(0, 0));
        assertThat(playerFrameScores).noneMatch(f -> f.getFirstChance() > 4 && f.getSecondChance() > 4);
        assertThat(playerFrameScores).noneMatch(f -> f.getFirstChance() < 0 && f.getSecondChance() < 0);
    }

    @Test
    void shouldConvertPlayersChancesIntoFrameScores_All_Strikes() {
        List<Chance> johnChances = validChancesAllStrikes(JOHN, 11);
        List<FrameScore> playerFrameScores = subject.getPlayersChancesAsFrameScore(johnChances.stream());
        assertThat(playerFrameScores).hasSize(10);
        assertThat(playerFrameScores).noneMatch(FrameScore::isSpare);
        assertThat(playerFrameScores).allMatch(FrameScore::isStrike);
    }

    @Test
    void shouldConvertPlayersChancesIntoFrameScores_All_Spare() {
        List<Chance> johnChances = validChancesAllSpare(JOHN, 20);
        List<FrameScore> playerFrameScores = subject.getPlayersChancesAsFrameScore(johnChances.stream());
        assertThat(playerFrameScores).hasSize(10);
        assertThat(playerFrameScores).allMatch(FrameScore::isSpare);
        assertThat(playerFrameScores).noneMatch(FrameScore::isStrike);
    }

    @Test
    void shouldHaveLastFrameValid_with_spare_no_strike() {
        List<Chance> johnChances = validChancesAllSpare(JOHN, 21);
        List<FrameScore> playerFrameScores = subject.getPlayersChancesAsFrameScore(johnChances.stream());
        assertThat(playerFrameScores).hasSize(10);

        FrameScore frameScore = playerFrameScores.get(9);
        assertThat(frameScore.getFrameTenExclusive()).isEqualTo(5);
    }

    @Test
    void shouldHaveLastFrameValid_no_spare_no_strike() {
        List<Chance> johnChances = validChancesNoSparesWithFaultsInitWithFaults(JOHN, 20, 4);
        List<FrameScore> playerFrameScores = subject.getPlayersChancesAsFrameScore(johnChances.stream());
        assertThat(playerFrameScores).hasSize(10);

        FrameScore frameScore = playerFrameScores.get(9);
        assertThat(frameScore.getFrameTenExclusive()).isEqualTo(0);
    }

    @Test
    void shouldHaveLastFrameValid_with_spare() {

    }

    @Test
    void shouldHaveLastFrameValid_with_strike() {

    }

    private List<Chance> validChances(String playerName, int numChances) {
        List<Chance> res = new ArrayList<>();
        for (int i = 0; i < numChances; i++) {
            res.add(Chance.builder().player(playerName).res("4").build());
        }
        return res;
    }

    private List<Chance> validChancesAllStrikes(String playerName, int numChances) {
        List<Chance> res = new ArrayList<>();
        for (int i = 0; i < numChances; i++) {
            res.add(Chance.builder().player(playerName).res("10").build());
        }
        return res;
    }

    private List<Chance> validChancesAllSpare(String playerName, int numChances) {
        List<Chance> res = new ArrayList<>();
        for (int i = 0; i < numChances; i++) {
            res.add(Chance.builder().player(playerName).res("5").build());
        }
        return res;
    }
    
    private List<Chance> someInvalidChances(String playerName, int numChances, int numInvalids) {
        List<Chance> res = new ArrayList<>();
        for (int i = 0; i < numChances - numInvalids; i++) {
            res.add(Chance.builder().player(playerName).res("4").build());
        }
        for (int i = 0; i < numInvalids; i++) {
            res.add(Chance.builder().player(playerName).res("14").build());
        }
        return res;
    }

    private List<Chance> validChancesNoSparesWithFaults(String playerName, int numChances, int numFaults) {
        List<Chance> res = new ArrayList<>();
        for (int i = 0; i < numChances - numFaults; i++) {
            res.add(Chance.builder().player(playerName).res("4").build());
        }
        for (int i = 0; i < numFaults; i++) {
            res.add(Chance.builder().player(playerName).res("F").build());
        }
        return res;
    }

    private List<Chance> validChancesNoSparesWithFaultsInitWithFaults(String playerName, int chances, int faults) {
        List<Chance> res = new ArrayList<>();

        for (int i = 0; i < faults; i++) {
            res.add(Chance.builder().player(playerName).res("F").build());
        }
        for (int i = 0; i < chances - faults; i++) {
            res.add(Chance.builder().player(playerName).res("4").build());
        }

        return res;
    }
}