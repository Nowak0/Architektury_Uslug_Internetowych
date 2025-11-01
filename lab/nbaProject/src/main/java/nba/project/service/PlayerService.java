package nba.project.service;

import nba.project.dto.player.PlayerCreateUpdateDTO;
import nba.project.entity.Franchise;
import nba.project.entity.Player;
import nba.project.entity.Position;
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

    public Player save(Player player) {
        return playerRepository.save(player);
    }

    public Player update(UUID id, PlayerCreateUpdateDTO dto) {
        Player player = findById(id).orElseThrow();

        player.setFirstName(dto.getFirstName());
        player.setLastName(dto.getLastName());
        player.setAge(dto.getAge());
        player.setPosition(dto.getPosition());

        return save(player);
    }

    public Player transfer(Player player, Franchise franchise) {
        player.setFranchise(franchise);
        return save(player);
    }

    public Player addToFranchise(PlayerCreateUpdateDTO dto, Franchise franchise) {
        Player player = new Player();
        player.setId(UUID.randomUUID());
        player.setFirstName(dto.getFirstName());
        player.setLastName(dto.getLastName());
        player.setAge(dto.getAge());
        player.setPosition(dto.getPosition());
        player.setFranchise(franchise);

        playerRepository.save(player);
        return player;
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

    public Position mapPlayerPosition(String position) {
        if (position == null) {return null;}

        position = position.toUpperCase();
        if(position.equals("GUARD")) {
            return Position.GUARD;
        }
        if(position.equals("FORWARD")) {
            return Position.FORWARD;
        }
        else {
            return Position.CENTER;
        }
    }
}
