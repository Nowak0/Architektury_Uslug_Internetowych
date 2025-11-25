package nba.project.dto.player;

import lombok.Data;
import nba.project.entity.Position;

@Data
public class PlayerCreateUpdateDTO {
    private String firstName;
    private String lastName;
    private int age;
    private Position position;
}
