package matchbuilder;

import matchparser.Chance;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public interface MatchBuilder {
    public Map<String, List<Chance>> mapPlayersChance(Stream<Chance> chancesStream);

    public Boolean validatePlayerMatch(Stream<Chance> chancesStream);

    public List<PlayerScore> producePlayersScores(Stream<Chance> chanceStream);
}
