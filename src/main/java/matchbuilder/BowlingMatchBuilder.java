package matchbuilder;

import matchparser.Chance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BowlingMatchBuilder implements MatchBuilder {

    @Override
    public Map<String, List<Chance>> mapPlayersChance(List<Chance> chancesStream) {

        Map<String, List<Chance>> map = new HashMap<>();
        chancesStream.forEach(chance -> {
            map.computeIfAbsent(chance.getPlayer(), k -> new ArrayList<>());
            map.get(chance.getPlayer()).add(chance);
        });
        return map;
    }

    @Override
    public Boolean validateDoesNotHaveErrors(List<Chance> chances) {
        return chances.stream().noneMatch(Chance::isError);
    }

    @Override
    public Boolean validatePlayerMatchData(List<Chance> chancesStream) {
        return chancesStream.stream().allMatch(this::validateSimpleValuesLimits);
    }

    @Override
    public List<FrameScore> getPlayersChancesAsFrameScore(List<Chance> playerChances) {
        boolean frameOpen = true;
        int lastValue = 0;
        boolean lastValFault = false;
        boolean lastFirst = false;
        boolean lastSecond = false;
        boolean lastThird = false;

        List<FrameScore> allFrames = new ArrayList<>();
        FrameScore.FrameScoreBuilder lastFrame = FrameScore.builder();

        for (Chance chance : playerChances) {
            lastFrame.playerName(chance.getPlayer());
            int value = getResultValue(chance);
            boolean valueFault = getFaultValue(chance);
            if (allFrames.size() == 9) { //last frame evaluation
                lastFrame.isFinalFrame(true);
                if (!lastFirst) {
                    lastFrame.firstChance(value);
                    lastFrame.isFirstFault(valueFault);
                    lastFirst = true;
                } else if (!lastSecond) {
                    lastFrame.secondChance(value);
                    lastFrame.isSecondFault(valueFault);
                    lastSecond = true;
                } else if (!lastThird) {
                    lastFrame.frameTenExclusive(value);
                    lastFrame.isTenExclusiveFault(valueFault);
                    lastThird = true;
                }
            } else { // non last frame

                FrameScore.FrameScoreBuilder builder = FrameScore.builder().playerName(chance.getPlayer());
                if (value == 10) {
                    allFrames.add(builder.firstChance(10).build());
                } else if (value < 10 && frameOpen) {
                    lastValue = value;
                    lastValFault = valueFault;
                    frameOpen = false;
                } else {
                    frameOpen = true;
                    allFrames.add(builder
                            .firstChance(lastValue).isFirstFault(lastValFault)
                            .secondChance(value).isSecondFault(valueFault)
                            .build());
                }
            }
        }
        allFrames.add(lastFrame.build());
        return allFrames;
    }

    private int getResultValue(Chance chance) {
        return chance.getRes().equalsIgnoreCase("F") ? 0 : Integer.parseInt(chance.getRes());
    }

    private boolean getFaultValue(Chance chance) {
        return chance.getRes().equalsIgnoreCase("F");
    }

    private boolean validateSimpleValuesLimits(Chance chance) {
        String res = chance.getRes();

        return res.equalsIgnoreCase(BowlingRules.Fault) ||
                (Integer.parseInt(res) >= BowlingRules.MinimalValue &&
                        Integer.parseInt(res) <= BowlingRules.MaxValue);
    }
}
