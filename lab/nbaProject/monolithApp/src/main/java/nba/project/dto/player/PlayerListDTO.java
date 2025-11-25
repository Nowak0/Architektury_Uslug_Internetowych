package nba.project.dto.player;

import lombok.Data;

import java.util.UUID;

@Data
public class PlayerListDTO {
    private UUID id;
    private String fullName;
}
