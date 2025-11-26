package nba.project.dto.franchise;

import lombok.Data;
import nba.project.entity.Position;

import java.util.List;
import java.util.UUID;

@Data
public class FranchiseReadDTO {
    private UUID id;
    private String name;
    private String city;
    private String conference;
    private int currentPosition;
    private int titles;
}
