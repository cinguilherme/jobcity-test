package matchparser;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Chance {
    private String player;
    private String res;

    private boolean error;
    private String errorStr;
}
