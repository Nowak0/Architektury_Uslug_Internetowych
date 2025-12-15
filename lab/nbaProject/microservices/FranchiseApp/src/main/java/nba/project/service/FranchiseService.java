package nba.project.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import nba.project.dto.franchise.FranchiseCreateUpdateDTO;
import nba.project.entity.Franchise;
import nba.project.repository.FranchiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FranchiseService {
    private final FranchiseRepository franchiseRepository;
    @Value("${player.app.url}")
    private String playerAppUrl;
    private final RestTemplate restTemplate;

    public FranchiseService(FranchiseRepository franchiseRepository, RestTemplate restTemplate) {
        this.franchiseRepository = franchiseRepository;
        this.restTemplate = restTemplate;
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

    public Franchise save(Franchise franchise) {
        Franchise saved = franchiseRepository.save(franchise);
        notifyAdd(saved);
        return saved;
    }

    public void update(UUID id, FranchiseCreateUpdateDTO dto) {
        Franchise existingFranchise = franchiseRepository.findById(id).orElseThrow();
        if(dto.getName() != null) existingFranchise.setName(dto.getName());
        if(dto.getCity() != null) existingFranchise.setCity(dto.getCity());
        if(dto.getConference() != null) existingFranchise.setConference(dto.getConference());
        existingFranchise.setCurrentPosition(dto.getCurrentPosition());
        if(dto.getTitles() > 0) existingFranchise.setTitles(dto.getTitles());

        franchiseRepository.save(existingFranchise);
    }

    public void delete(Franchise franchise) {
        franchiseRepository.delete(franchise);
    }

    public void deleteById(UUID id) {
        franchiseRepository.deleteById(id);
        notifyDelete(id);
    }

    public void notifyAdd(Franchise franchise) {
        String url = this.playerAppUrl + "/api/franchises";
        restTemplate.postForObject(url, franchise, Void.class);
    }

    public void notifyDelete(UUID franchiseId) {
        String url = this.playerAppUrl + "/api/franchises";
        restTemplate.delete(url + "/" + franchiseId);
    }
}
