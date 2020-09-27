package matchparser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

class MatchTextParserTest {

    private final String firstMatchResource = "firstMatch.txt";
    private final String secondMatchResource = "secondMatch.txt";
    private final String emptyResource = "empty.txt";
    private final String badFormatResource = "badlyFormatedFile.txt";

    private final ClassLoader classLoader = getClass().getClassLoader();

    private MatchParser subject;

    @BeforeEach
    void setup() {
        subject = new MatchTextParser();
    }

    @Test
    void shouldParse_txtFile_with_valid_format() {
        String resource = classLoader.getResource(firstMatchResource).getFile();

        List<Chance> chanceActual = subject.parseInput(resource);

        int linesExpected = 35;
        assertThat(chanceActual.size()).isEqualTo(linesExpected);
        assertThat(chanceActual).noneMatch(Objects::isNull);
        assertThat(chanceActual)
                .extracting(Chance::getPlayer)
                .containsAll(List.of("Jeff", "John"));
    }

    @Test
    void shouldParse_txtFile_with_valid_format_secondMatch() {
        String resource = classLoader.getResource(secondMatchResource).getFile();

        List<Chance> chanceActual = subject.parseInput(resource);

        int linesExpected = 12;
        assertThat(chanceActual.size()).isEqualTo(linesExpected);
        assertThat(chanceActual).noneMatch(Objects::isNull);
        assertThat(chanceActual)
                .extracting(Chance::getPlayer)
                .containsAll(List.of("Carl"));
    }

    @Test
    void shouldParse_txtFile_with_no_data_and_return_empty() {
        String resource = classLoader.getResource(emptyResource).getFile();

        List<Chance> chanceActual = subject.parseInput(resource);

        int linesExpected = 0;
        assertThat(chanceActual.size()).isEqualTo(linesExpected);
    }

    @Test
    void shouldParse_txtFile_with_bad_formatted_text_and_return_empty() {
        String resource = classLoader.getResource(badFormatResource).getFile();
        
        List<Chance> chanceActual = subject.parseInput(resource);

        int linesExpected = 29;
        assertThat(chanceActual.size()).isEqualTo(linesExpected);
        assertThat(chanceActual).anyMatch(Chance::isError);
        assertThat(chanceActual.stream().filter(Chance::isError).count()).isEqualTo(6);
        assertThat(chanceActual.stream().filter(Chance::isError).map(Chance::getErrorStr))
                .contains("Jeff 10 John 7");
    }


}