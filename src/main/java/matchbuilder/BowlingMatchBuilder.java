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
    public Boolean validatePlayerMatch(Stream<Chance> chancesStream) {

        return chancesStream.allMatch(this::validateSimpleValues);

    }

    private boolean validateSimpleValues(Chance chance) {
        String res = chance.getRes();

        return res.equalsIgnoreCase(BowlingRules.Fault) ||
                (Integer.parseInt(res) >= BowlingRules.MinimalValue &&
                        Integer.parseInt(res) <= BowlingRules.MaxValue);
    }

    @Override
    public List<PlayerScore> producePlayersScores(Stream<Chance> chanceStream) {
        return null;
    }
}
