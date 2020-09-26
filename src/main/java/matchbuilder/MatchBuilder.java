package matchbuilder;

import matchparser.Chance;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface MatchBuilder {
    public Boolean validateMatch(Stream<Chance> chancesStream);
    public List<PlayerScore> producePlayersScores(Stream<Chance> chanceStream);
}
