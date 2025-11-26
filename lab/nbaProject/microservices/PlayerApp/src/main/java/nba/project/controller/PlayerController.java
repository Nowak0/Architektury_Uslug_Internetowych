package nba.project.controller;

import lombok.RequiredArgsConstructor;
import nba.project.dto.franchise.FranchiseListDTO;
import nba.project.dto.player.PlayerCreateUpdateDTO;
import nba.project.dto.player.PlayerListDTO;
import nba.project.dto.player.PlayerReadDTO;
import nba.project.entity.Franchise;
import nba.project.entity.Player;
import nba.project.mapper.FranchiseMapper;
import nba.project.mapper.PlayerMapper;
import nba.project.service.FranchiseService;
import nba.project.service.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PlayerController {
    private final PlayerService playerService;
    private final PlayerMapper playerMapper;
    private final FranchiseService franchiseService;
    private final FranchiseMapper franchiseMapper;

    @GetMapping("/players")
    public List<PlayerListDTO> getAllPlayers() {
        return playerService.findAll().stream()
                .map(playerMapper::toListDTO)
                .toList();
    }

    @GetMapping("/players/{id}")
    public ResponseEntity<PlayerReadDTO> getPlayer(@PathVariable UUID id) {
        Optional<Player> player = playerService.findById(id);
        if(player.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(playerMapper.toReadDTO(player.get()));
    }

    @GetMapping("/players/franchise/{id}/players")
    public ResponseEntity<List<PlayerListDTO>> getAllPlayersFromFranchise(@PathVariable UUID id) {
        if(franchiseService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<PlayerListDTO> players = playerService.findByFranchiseId(id).stream()
                .map(playerMapper::toListDTO)
                .toList();

        if(players.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(players);
    }

    @PostMapping("/players/{franchiseID}")
    public ResponseEntity<PlayerReadDTO> addPlayer(@PathVariable UUID franchiseID, @RequestBody PlayerCreateUpdateDTO dto) {
        Optional<Franchise> franchise = franchiseService.findById(franchiseID);
        if(franchise.isEmpty()) {return ResponseEntity.notFound().build();}

        Player player = playerService.addToFranchise(dto, franchise.get());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(playerMapper.toReadDTO(player));
    }

    @PutMapping("/players/{id}")
    public ResponseEntity<PlayerReadDTO> update(@PathVariable UUID id, @RequestBody PlayerCreateUpdateDTO dto) {
        if(playerService.findById(id).isEmpty()) {return ResponseEntity.notFound().build();}

        Player updated = playerService.update(id, dto);
        return ResponseEntity.ok(playerMapper.toReadDTO(updated));
    }

    @PutMapping("/players/{playerID}/franchise/{franchiseID}")
    public ResponseEntity<PlayerReadDTO> transfer(@PathVariable UUID playerID, @PathVariable UUID franchiseID) {
        Optional<Player> player = playerService.findById(playerID);
        if(player.isEmpty()) {return ResponseEntity.notFound().build();}

        Optional<Franchise> franchise = franchiseService.findById(franchiseID);
        if(franchise.isEmpty()) {return ResponseEntity.notFound().build();}

        Player transferred = playerService.transfer(player.get(), franchise.get());
        return ResponseEntity.ok(playerMapper.toReadDTO(transferred));
    }

    @DeleteMapping("/players/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        if(playerService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        playerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
