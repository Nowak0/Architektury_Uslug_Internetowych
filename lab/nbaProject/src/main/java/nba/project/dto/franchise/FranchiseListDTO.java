package nba.project.dto.franchise;

import lombok.Data;

import java.util.UUID;

@Data
public class FranchiseListDTO {
    private UUID id;
    private String name;
}
