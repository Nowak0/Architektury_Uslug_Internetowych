package nba.project.controller;

import lombok.RequiredArgsConstructor;
import nba.project.dto.franchise.FranchiseListDTO;
import nba.project.entity.Franchise;
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
@RequestMapping("/api/franchises")
@RequiredArgsConstructor
public class FranchiseController {
    private final FranchiseService franchiseService;
    private final FranchiseMapper franchiseMapper;
    private final PlayerMapper playerMapper;
    private final PlayerService playerService;

    @PostMapping
    public ResponseEntity<Franchise> addFranchise(@RequestBody Franchise franchise) {
        Franchise saved = franchiseService.save(franchise);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFranchise(@PathVariable UUID id) {
        Optional<Franchise> f = franchiseService.findById(id);
        franchiseService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public List<FranchiseListDTO> getAllFranchises() {
        return franchiseService.findAll().stream()
                .map(franchiseMapper::toListDTO)
                .toList();
    }
}