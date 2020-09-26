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
            map.computeIfAbsent(chance.getPlayer(), k -> new ArrayList<>());
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
        boolean lastFirst = false;
        boolean lastSecond = false;
        boolean lastThird = false;

        List<FrameScore> allFrames = new ArrayList<>();
        FrameScore.FrameScoreBuilder lastFrame = FrameScore.builder();

        for (Chance chance : playerChances.collect(Collectors.toList())) {
            int value = getResultValue(chance);
            if (allFrames.size() == 9) { //last frame evaluation
                lastFrame.isFinalFrame(true);
                if (!lastFirst) {
                    lastFrame.firstChance(value);
                    lastFirst = true;
                } else if (!lastSecond) {
                    lastFrame.secondChance(value);
                    lastSecond = true;
                } else if (!lastThird) {
                    lastFrame.frameTenExclusive(value);
                    lastThird = true;
                }
            } else { // non last frame

                if (value == 10) {
                    allFrames.add(FrameScore.builder().firstChance(10).build());
                } else if (value < 10 && frameOpen) {
                    lastValue = value;
                    frameOpen = false;
                } else {
                    frameOpen = true;
                    allFrames.add(FrameScore.builder().firstChance(lastValue).secondChance(value).build());
                }
            }
        }
        allFrames.add(lastFrame.build());
        return allFrames;
    }

    private int getResultValue(Chance chance) {
        return chance.getRes().equalsIgnoreCase("F") ? 0 : Integer.parseInt(chance.getRes());
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
