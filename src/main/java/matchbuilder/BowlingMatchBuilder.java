package matchbuilder;

import matchparser.Chance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
    public Boolean validatePlayerMatchData(Stream<Chance> chancesStream) {

        return chancesStream.allMatch(this::validateSimpleValuesLimits);
    }

    @Override
    public List<FrameScore> getPlayersChancesAsFrameScore(Stream<Chance> playerChances) {
        boolean frameOpen = true;
        int lastValue = 0;
        List<FrameScore> allFrames = new ArrayList<>();

        for (Chance chance : playerChances.collect(Collectors.toList())) {
            int value = chance.getRes().equalsIgnoreCase("F") ? 0 : Integer.parseInt(chance.getRes());
            if (value == 10) { //strike
                allFrames.add(FrameScore.builder().firstChance(10).build());
            } else if (value < 10 && frameOpen) { //maybe spare
                lastValue = value;
                frameOpen = false;
            } else {
                frameOpen = true;
                allFrames.add(FrameScore.builder().firstChance(lastValue).secondChance(value).build());
            }
        }
        return allFrames;
    }

    private boolean validateSimpleValuesLimits(Chance chance) {
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
