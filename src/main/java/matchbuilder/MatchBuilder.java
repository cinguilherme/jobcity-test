package matchbuilder;

import matchparser.Chance;

import java.util.List;
import java.util.Map;

public interface MatchBuilder {
    Map<String, List<Chance>> mapPlayersChance(List<Chance> chancesStream);

    Boolean validatePlayerMatchData(List<Chance> chancesStream);

    List<FrameScore> getPlayersChancesAsFrameScore(List<Chance> playerChances);

}
