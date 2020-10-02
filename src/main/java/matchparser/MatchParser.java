package matchparser;

import java.util.List;

public interface MatchParser {

    /**
     * Interprets a file and build a list of Chance objects which represents each valid line of the file.
     * invalid lines will also be returned as a invalid Chance
     *
     * @param filePath
     * @return
     */
    public List<Chance> parseInput(String filePath);

}
