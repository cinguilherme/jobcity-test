package presenter;

import matchbuilder.FrameScore;

import java.util.List;

public interface MatchScorePresenter {

    PlayerPresenterScore presentPlayerScore(List<FrameScore> calculatedScore);

}
