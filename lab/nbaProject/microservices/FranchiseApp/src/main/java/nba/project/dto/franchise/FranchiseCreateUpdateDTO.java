package nba.project.dto.franchise;

import lombok.Data;
import nba.project.entity.Position;

@Data
public class FranchiseCreateUpdateDTO {
    private String name;
    private String city;
    private String conference;
    private int currentPosition;
    private int titles;
}
