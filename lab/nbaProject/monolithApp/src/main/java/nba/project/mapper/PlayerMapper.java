package nba.project.mapper;

import nba.project.dto.player.PlayerCreateUpdateDTO;
import nba.project.dto.player.PlayerListDTO;
import nba.project.dto.player.PlayerReadDTO;
import nba.project.entity.Player;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PlayerMapper {
    public Player toEntity(PlayerCreateUpdateDTO dto) {
        if (dto == null) return null;

        Player player = new Player();
        player.setId(UUID.randomUUID());
        player.setFirstName(dto.getFirstName());
        player.setLastName(dto.getLastName());
        player.setAge(dto.getAge());
        player.setPosition(dto.getPosition());
        return player;
    }

    public PlayerReadDTO toReadDTO(Player player) {
        if (player == null) return null;

        PlayerReadDTO dto = new PlayerReadDTO();
        dto.setId(player.getId());
        dto.setFirstName(player.getFirstName());
        dto.setLastName(player.getLastName());
        dto.setAge(player.getAge());
        dto.setPosition(player.getPosition());

        if(player.getFranchise() != null) dto.setFranchiseId(player.getFranchise().getId());
        else dto.setFranchiseId(null);

        return dto;
    }

    public PlayerListDTO toListDTO(Player player) {
        if (player == null) return null;
        PlayerListDTO dto = new PlayerListDTO();
        dto.setId(player.getId());

        String fullName = "";
        if(player.getFirstName() != null && player.getLastName() != null) fullName = player.getFirstName() + " " + player.getLastName();
        dto.setFullName(fullName);

        return dto;
    }
}
