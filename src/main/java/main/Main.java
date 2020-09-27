package main;

import matchbuilder.BowlingMatchBuilder;
import matchbuilder.FrameScore;
import matchbuilder.MatchBuilder;
import matchparser.Chance;
import matchparser.MatchParser;
import matchparser.MatchTextParser;
import presenter.BowlingScorePresenter;
import presenter.MatchScorePresenter;
import scorecalculator.BowlingScoreCalculator;
import scorecalculator.ScoreCalculator;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        System.out.println("Lookup Files Directory: " + workdir);

        filesPath.forEach(fileName -> System.out.println("Filename: " + fileName));

        System.out.println("#############################################");

        MatchParser fileParser = new MatchTextParser();
        MatchBuilder bowlingMatchBuilder = new BowlingMatchBuilder();
        ScoreCalculator scoreCalculator = new BowlingScoreCalculator();
        MatchScorePresenter presenter = new BowlingScorePresenter();

        filesPath.forEach(fPath -> {
            Path pathx = Paths.get(workdir.toString() + "/matchesFiles/" + fPath);
            try {
                String filePath = pathx.toRealPath().toString();
                System.out.println("File path: " + filePath);
                List<Chance> chanceStream = fileParser.parseInput(filePath);

                if (bowlingMatchBuilder.validateDoesNotHaveErrors(chanceStream) &&
                        bowlingMatchBuilder.validatePlayerMatchData(chanceStream)) {

                    Map<String, List<Chance>> playersMap = bowlingMatchBuilder.mapPlayersChance(chanceStream);

                    List<List<FrameScore>> allPlayersScores = playersMap.values().stream()
                            .map(bowlingMatchBuilder::getPlayersChancesAsFrameScore)
                            .map(scoreCalculator::calculateFramesScores).collect(toList());

                    allPlayersScores.forEach(list -> {
                        System.out.println("------------------------------------------------------------------------------------------");
                        presenter.presentPlayerScore(list).presentConsole();
                    });
                    System.out.println("##############################################################################################\n");

                } else {
                    System.out.println("##############################################################################################");
                    System.out.println("Invalid data for file: " + fPath);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
