package presenter;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PlayerPresenterScore {
    private String frame;
    private String playerName;
    private String pinFalls;
    private String score;
}
