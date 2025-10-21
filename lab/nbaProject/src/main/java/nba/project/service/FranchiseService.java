package nba.project.service;

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

    public Optional<Franchise> findByFranchiseId(UUID franchiseId) {
        return franchiseRepository.findById(franchiseId);
    }

    public Optional<Franchise> findByFranchiseName(String franchiseName) {
        return franchiseRepository.findByName(franchiseName);
    }

    public void saveFranchise(Franchise franchise) {
        franchiseRepository.save(franchise);
    }

    public void deleteFranchise(Franchise franchise) {
        franchiseRepository.delete(franchise);
    }

    public void deleteFranchiseById(UUID id) {
        franchiseRepository.deleteById(id);
    }
}
