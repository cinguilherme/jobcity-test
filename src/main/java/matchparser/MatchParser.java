package matchparser;

import java.util.stream.Stream;

public interface MatchParser {

    public Stream<Chance> parseInput(String filePath);

}
