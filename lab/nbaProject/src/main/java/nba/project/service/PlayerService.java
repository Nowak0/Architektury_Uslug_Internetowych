package nba.project.service;

import nba.project.entity.Franchise;
import nba.project.entity.Player;
import nba.project.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    public Optional<Player> findById(UUID id) {
        return playerRepository.findById(id);
    }

    public void save(Player player) {
        playerRepository.save(player);
    }

    public void deleteById(UUID id) {
        playerRepository.deleteById(id);
    }

    public List<Player> findByFranchise(Franchise franchise) {
        return playerRepository.findByFranchise(franchise);
    }

    public List<Player> findByFranchiseId(UUID franchiseId) {
        return playerRepository.findByFranchiseId(franchiseId);
    }

    public List<Player> findByFranchiseName(String franchiseName) {
        return playerRepository.findByFranchiseName(franchiseName);
    }
}
