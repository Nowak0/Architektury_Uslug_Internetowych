package nba.project.mapper;

import nba.project.dto.franchise.FranchiseListDTO;
import nba.project.dto.player.PlayerListDTO;
import nba.project.entity.Franchise;
import nba.project.entity.Player;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FranchiseMapper {
    public FranchiseListDTO toListDTO(Franchise franchise) {
        if (franchise == null) return null;

        FranchiseListDTO dto = new FranchiseListDTO();
        dto.setId(franchise.getId());
        dto.setName(franchise.getName());

        return dto;
    }
}
