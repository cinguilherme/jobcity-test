package matchbuilder;

import matchparser.Chance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class BowlingMatchBuilderTest {

    private MatchBuilder subject;

    @BeforeEach
    void setup() {
        subject = new BowlingMatchBuilder();
    }

    @Test
    void should_map_players_with_chances() {
        List<Chance> johnChances = validChances("John", 20);
        List<Chance> jeffChances = validChances("Jeff", 20);
        Stream<Chance> allChances = Stream.concat(johnChances.stream(), jeffChances.stream());
        Map<String, List<Chance>> playersChanceMap = subject.mapPlayersChance(allChances);
        assertThat(playersChanceMap).isNotEmpty();
        assertThat(playersChanceMap.get("John")).hasSize(20);
        assertThat(playersChanceMap.get("Jeff")).hasSize(20);
    }

    private List<Chance> validChances(String playerName, int numChances) {
        List<Chance> res = new ArrayList<>();
        for (int i = 0; i < numChances; i++) {
            res.add(Chance.builder().player(playerName).res("4").build());
        }
        return res;
    }

}