package presenter;

import matchbuilder.FrameScore;

import java.util.List;

public interface MatchScorePresenter {

    PlayerPresenterScore presentPlayerScore(List<FrameScore> calculatedScore);

    void presentInvalidData(String fPath);

    void initialPresentation(String filePath);

    void fileNotProvided();
}
