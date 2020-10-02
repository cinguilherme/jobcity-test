package presenter;

import matchbuilder.FrameScore;

import java.util.List;

public interface MatchScorePresenter {

    /**
     * Presents a player complete set of lines, name, score, throws etc.
     *
     * @param calculatedScore
     * @return
     */
    PlayerPresenterScore presentPlayerScore(List<FrameScore> calculatedScore);

    /**
     * Presents that the data in the file was not valid
     *
     * @param fPath
     */
    void presentInvalidData(String fPath);

    /**
     * Initial presentation of the program
     *
     * @param filePath
     */
    void initialPresentation(String filePath);

    /**
     * Presentation of file not provided on the console
     */
    void fileNotProvided();
}
