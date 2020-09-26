package presenter;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PlayerScore {

    private Integer frame;
    private String playerName;
    private String pitFalls;
    private String score;

}
