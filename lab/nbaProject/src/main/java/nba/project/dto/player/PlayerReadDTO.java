package nba.project.dto.player;

import lombok.Data;
import nba.project.entity.Position;

import java.util.UUID;

@Data
public class PlayerReadDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private int age;
    private Position position;
    private UUID franchiseId;
}
