package matchbuilder;

import lombok.Builder;
import lombok.Getter;
import matchparser.Chance;

import java.util.List;

@Builder
@Getter
public class PlayerScore {
    private String playerName;
    private List<Chance> playerChances;
}
