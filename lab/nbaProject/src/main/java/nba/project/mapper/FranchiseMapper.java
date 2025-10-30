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

    public void updateEntityFromDTO(Franchise franchise, FranchiseCreateUpdateDTO dto) {
        if (dto == null || franchise == null) return;

        if(dto.getName() != null) franchise.setName(dto.getName());
        if(dto.getCity() != null) franchise.setCity(dto.getCity());
        if(dto.getConference() != null) franchise.setConference(dto.getConference());
        if(dto.getCurrentPosition() > 0) franchise.setCurrentPosition(dto.getCurrentPosition());
        if(dto.getTitles() > 0) franchise.setTitles(dto.getTitles());
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
