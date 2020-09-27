package main;

import matchbuilder.BowlingMatchBuilder;
import matchbuilder.FrameScore;
import matchbuilder.MatchBuilder;
import matchparser.Chance;
import matchparser.MatchParser;
import matchparser.MatchTextParser;
import scorecalculator.BowlingScoreCalculator;
import scorecalculator.ScoreCalculator;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class Main {

    public static void main(String[] args) {
        List<String> filesPath = Arrays.stream(args).map(String::valueOf).collect(toList());

        System.out.println("#############################################");
        System.out.println("Bowling Game Interpreter:");

        String workdir = System.getProperty("user.dir");
        System.out.println("Working Directory = " + workdir);
        filesPath.stream().forEach(fileName -> {
            System.out.println("Filename: " + fileName);
            System.out.println("Windows: Lookup Filename: " + workdir + "\\" + fileName);
        });
        System.out.println("#############################################");

        MatchParser fileParser = new MatchTextParser();
        MatchBuilder bowlingMatchBuilder = new BowlingMatchBuilder();
        ScoreCalculator scoreCalculator = new BowlingScoreCalculator();

        filesPath.forEach(fPath -> {
            List<Chance> chanceStream = fileParser.parseInput(workdir + "\\" + fPath);
            Boolean isValidData = bowlingMatchBuilder.validatePlayerMatchData(chanceStream);

            if (isValidData) {

                Map<String, List<Chance>> playersMap = bowlingMatchBuilder.mapPlayersChance(chanceStream);

                List<List<FrameScore>> allPlayersScores = playersMap.values().stream()
                        .map(bowlingMatchBuilder::getPlayersChancesAsFrameScore)
                        .map(scoreCalculator::calculateFramesScores).collect(toList());

            }

        });

    }

}
