package nba.project.mapper;

import nba.project.dto.franchise.FranchiseCreateUpdateDTO;
import nba.project.dto.franchise.FranchiseListDTO;
import nba.project.dto.franchise.FranchiseReadDTO;
import nba.project.dto.player.PlayerListDTO;
import nba.project.entity.Franchise;
import nba.project.entity.Player;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FranchiseMapper {
    private final PlayerMapper playerMapper;

    public FranchiseMapper(PlayerMapper playerMapper) {
        this.playerMapper = playerMapper;
    }

    public Franchise toEntity(FranchiseCreateUpdateDTO dto) {
        if (dto == null) return null;

        Franchise franchise = new Franchise();
        franchise.setId(UUID.randomUUID());
        franchise.setName(dto.getName());
        franchise.setCity(dto.getCity());
        franchise.setConference(dto.getConference());
        franchise.setCurrentPosition(dto.getCurrentPosition());
        franchise.setTitles(dto.getTitles());
        franchise.setPlayers(new ArrayList<>());

        return franchise;
    }

    public FranchiseReadDTO toReadDTO(Franchise franchise) {
        if (franchise == null) return null;

        FranchiseReadDTO dto = new FranchiseReadDTO();
        dto.setId(franchise.getId());
        dto.setName(franchise.getName());
        dto.setCity(franchise.getCity());
        dto.setConference(franchise.getConference());
        dto.setCurrentPosition(franchise.getCurrentPosition());
        dto.setTitles(franchise.getTitles());

        List<PlayerListDTO> playerListDTO = new ArrayList<>();
        if(franchise.getPlayers() != null) {
            for (Player player : franchise.getPlayers()) {
                playerListDTO.add(playerMapper.toListDTO(player));
            }
        }

        dto.setPlayers(playerListDTO);
        return dto;
    }

    public FranchiseListDTO toListDTO(Franchise franchise) {
        if (franchise == null) return null;

        FranchiseListDTO dto = new FranchiseListDTO();
        dto.setId(franchise.getId());
        dto.setName(franchise.getName());

        return dto;
    }
}
