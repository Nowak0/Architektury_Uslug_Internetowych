package nba.project.mapper;

import nba.project.dto.franchise.FranchiseCreateUpdateDTO;
import nba.project.dto.franchise.FranchiseListDTO;
import nba.project.dto.franchise.FranchiseReadDTO;
import nba.project.entity.Franchise;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FranchiseMapper {
    public Franchise toEntity(FranchiseCreateUpdateDTO dto) {
        if (dto == null) return null;

        Franchise franchise = new Franchise();
        franchise.setId(UUID.randomUUID());
        franchise.setName(dto.getName());
        franchise.setCity(dto.getCity());
        franchise.setConference(dto.getConference());
        franchise.setCurrentPosition(dto.getCurrentPosition());
        franchise.setTitles(dto.getTitles());

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
