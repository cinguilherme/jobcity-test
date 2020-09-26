package matchbuilder;

import matchparser.Chance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class BowlingMatchBuilder implements MatchBuilder {

    @Override
    public Map<String, List<Chance>> mapPlayersChance(Stream<Chance> chancesStream) {

        Map<String, List<Chance>> map = new HashMap<>();
        chancesStream.forEach(chance -> {
            if (map.get(chance.getPlayer()) == null) {
                map.put(chance.getPlayer(), new ArrayList<>());
            }
            map.get(chance.getPlayer()).add(chance);
        });
        return map;
    }

    @Override
    public Boolean validateMatch(Stream<Chance> chancesStream) {


        return null;
    }

    @Override
    public List<PlayerScore> producePlayersScores(Stream<Chance> chanceStream) {
        return null;
    }
}
