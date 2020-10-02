package matchbuilder;

import matchparser.Chance;

import java.util.List;
import java.util.Map;

public interface MatchBuilder {
    /**
     * Builds a list of Chance robjects representing players throws and its results as a Map
     *
     * @param chancesStream
     * @return
     */
    Map<String, List<Chance>> mapPlayersChance(List<Chance> chancesStream);

    /**
     * Validates the list of chance as being a valid compilation of throws in a game
     *
     * @param chances
     * @return
     */
    Boolean validateDoesNotHaveErrors(List<Chance> chances);

    /**
     * Validades list of chance for invalid data regarding actual results upper and lower limits
     *
     * @param chancesStream
     * @return
     */
    Boolean validatePlayerMatchData(List<Chance> chancesStream);

    /**
     * Builds a List of Frames which is composed of one up to three Chances in a bowling game format.
     *
     * @param playerChances
     * @return
     */
    List<FrameScore> getPlayersChancesAsFrameScore(List<Chance> playerChances);

}
