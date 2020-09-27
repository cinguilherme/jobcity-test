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
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        Stream<String> filesPath = Arrays.stream(args).map(String::valueOf);

        System.out.println("Bowling game interpreter:");

        MatchParser fileParser = new MatchTextParser();
        MatchBuilder bowlingMatchBuilder = new BowlingMatchBuilder();
        ScoreCalculator scoreCalculator = new BowlingScoreCalculator();

        filesPath.forEach(fPath -> {
            List<Chance> chanceStream = fileParser.parseInput(fPath);
            Boolean isValidData = bowlingMatchBuilder.validatePlayerMatchData(chanceStream);

            if (isValidData) {

                Map<String, List<Chance>> playersMap = bowlingMatchBuilder.mapPlayersChance(chanceStream);

                List<List<FrameScore>> allPlayersScores = playersMap.values().stream()
                        .map(bowlingMatchBuilder::getPlayersChancesAsFrameScore)
                        .map(scoreCalculator::calculateFramesScores).collect(Collectors.toList());

            }

        });

    }

}
