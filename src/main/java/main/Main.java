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
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class Main {

    public static void main(String[] args) {
        List<String> filesPath = Arrays.stream(args).map(String::valueOf).collect(toList());
        String workdir = System.getProperty("user.dir");
        Optional<String> filePthOpt = filesPath.stream().findFirst();
        MatchParser fileParser = new MatchTextParser();
        MatchBuilder bowlingMatchBuilder = new BowlingMatchBuilder();
        ScoreCalculator scoreCalculator = new BowlingScoreCalculator();
        MatchScorePresenter presenter = new BowlingScorePresenter();

        if (filePthOpt.isEmpty()) {
            presenter.fileNotProvided();
        } else {
            filePthOpt.ifPresent(filePathX -> {
                presenter.initialPresentation(filePathX);

                Path pathx = Paths.get(workdir + "/matchesFiles/" + filePathX);
                try {
                    String filePath = pathx.toRealPath().toString();
                    List<Chance> chanceStream = fileParser.parseInput(filePath);

                    if (bowlingMatchBuilder.validateDoesNotHaveErrors(chanceStream) &&
                            bowlingMatchBuilder.validatePlayerMatchData(chanceStream)) {

                        Map<String, List<Chance>> playersMap = bowlingMatchBuilder.mapPlayersChance(chanceStream);

                        List<List<FrameScore>> allPlayersScores = playersMap.values().stream()
                                .map(bowlingMatchBuilder::getPlayersChancesAsFrameScore)
                                .map(scoreCalculator::calculateFramesScores).collect(toList());

                        List<List<FrameScore>> sortedByPlayerName = allPlayersScores.stream()
                                .sorted((o1, o2) -> o1.get(0).getPlayerName().compareToIgnoreCase(o2.get(0).getPlayerName())).collect(toList());

                        sortedByPlayerName.forEach(list -> presenter.presentPlayerScore(list).presentConsole());

                    } else {
                        presenter.presentInvalidData(filePathX);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        }
    }
}
