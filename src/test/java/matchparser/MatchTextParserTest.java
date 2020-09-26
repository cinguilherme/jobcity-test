package matchparser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

class MatchTextParserTest {

    String firstMatchResource = "firstMatch.txt";

    private MatchTextParser subject;

    @BeforeEach
    void setup() {
        subject = new MatchTextParser();
    }

    @Test
    void shouldParse_txtFile_with_valid_format() {
        String filePath = firstMatchResource;

        List<Chance> chanceActual = subject.parseInput(filePath).collect(toList());

        int linesExpected = 35;
        assertThat(chanceActual.size()).isEqualTo(linesExpected);
        assertThat(chanceActual).noneMatch(Objects::isNull);
        assertThat(chanceActual)
                .extracting(Chance::getPlayer)
                .containsAll(List.of("Jeff", "John"));

    }

}