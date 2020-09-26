package matchbuilder;

import matchparser.Chance;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public interface MatchBuilder {
    Map<String, List<Chance>> mapPlayersChance(Stream<Chance> chancesStream);

    Boolean validatePlayerMatchData(Stream<Chance> chancesStream);

    List<FrameScore> getPlayersChancesAsFrameScore(Stream<Chance> playerChances);

    List<PlayerScore> producePlayersScores(Stream<Chance> chanceStream);
}
