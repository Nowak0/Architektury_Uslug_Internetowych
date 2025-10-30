package nba.project.service;

import lombok.AllArgsConstructor;
import nba.project.dto.franchise.FranchiseCreateUpdateDTO;
import nba.project.entity.Franchise;
import nba.project.repository.FranchiseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FranchiseService {
    private final FranchiseRepository franchiseRepository;

    public FranchiseService(FranchiseRepository franchiseRepository) {
        this.franchiseRepository = franchiseRepository;
    }

    public List<Franchise> findAll() {
        return franchiseRepository.findAll();
    }

    public Optional<Franchise> findById(UUID franchiseId) {
        return franchiseRepository.findById(franchiseId);
    }

    public Optional<Franchise> findByName(String franchiseName) {
        return franchiseRepository.findByName(franchiseName);
    }

    public void save(Franchise franchise) {
        franchiseRepository.save(franchise);
    }

    public void update(UUID id, Franchise given_franchise) {
        Franchise existingFranchise = franchiseRepository.findById(id).orElseThrow();
        existingFranchise.setName(given_franchise.getName());
        existingFranchise.setCity(given_franchise.getCity());
        existingFranchise.setConference(given_franchise.getConference());
        existingFranchise.setCurrentPosition(given_franchise.getCurrentPosition());
        existingFranchise.setTitles(given_franchise.getTitles());

        franchiseRepository.save(existingFranchise);
    }

    public void update(UUID id, FranchiseCreateUpdateDTO dto) {
        Franchise existingFranchise = franchiseRepository.findById(id).orElseThrow();
        existingFranchise.setName(dto.getName());
        existingFranchise.setCity(dto.getCity());
        existingFranchise.setConference(dto.getConference());
        existingFranchise.setCurrentPosition(dto.getCurrentPosition());
        existingFranchise.setTitles(dto.getTitles());

        franchiseRepository.save(existingFranchise);
    }

    public void delete(Franchise franchise) {
        franchiseRepository.delete(franchise);
    }

    public void deleteById(UUID id) {
        franchiseRepository.deleteById(id);
    }
}
