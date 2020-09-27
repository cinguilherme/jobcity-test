package matchparser;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class MatchTextParser implements MatchParser {

    public static final String UTF_8 = "UTF-8";

    @Override
    public List<Chance> parseInput(String filePath) {
        return getFileAsString(filePath)
                .map(this::toStreamOfChance)
                .orElse(Stream.empty())
                .collect(toList());
    }

    private Stream<Chance> toStreamOfChance(List<String> strings) {
        return strings.stream().map(this::toChance);
    }

    private Chance toChance(String line) {
        String[] values = line.split(" ");
        return values.length != 2 ?
                Chance.builder().error(true).errorStr(line).build()
                : Chance.builder().player(values[0]).res(values[1]).build();

    }

    private Optional<List<String>> getFileAsString(String filePath) {
        ClassLoader classLoader = getClass().getClassLoader();
        try {
            File file = new File(Objects.requireNonNull(classLoader.getResource(filePath)).getFile());
            List<String> data = FileUtils.readLines(file, UTF_8);
            return Optional.of(data);
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
