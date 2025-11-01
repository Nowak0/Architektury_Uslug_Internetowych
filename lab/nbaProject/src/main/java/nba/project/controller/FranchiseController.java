package nba.project.controller;

import lombok.RequiredArgsConstructor;
import nba.project.dto.franchise.FranchiseCreateUpdateDTO;
import nba.project.dto.franchise.FranchiseListDTO;
import nba.project.dto.franchise.FranchiseReadDTO;
import nba.project.entity.Franchise;
import nba.project.mapper.FranchiseMapper;
import nba.project.service.FranchiseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/franchises")
@RequiredArgsConstructor
public class FranchiseController {
    private final FranchiseService franchiseService;
    private final FranchiseMapper franchiseMapper;

    @GetMapping
    public List<FranchiseListDTO> getAllFranchises() {
        return franchiseService.findAll().stream()
                .map(franchiseMapper::toListDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FranchiseReadDTO> getFranchise(@PathVariable UUID id) {
        FranchiseReadDTO dto = franchiseService.findById(id)
                .map(franchiseMapper::toReadDTO)
                .orElse(null);

        if(dto == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(dto);
        }
    }

    @PostMapping
    public ResponseEntity<FranchiseReadDTO> create(@RequestBody FranchiseCreateUpdateDTO dto) {
        Franchise saved = franchiseService.save(franchiseMapper.toEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(franchiseMapper.toReadDTO(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FranchiseReadDTO> update(@PathVariable UUID id, @RequestBody FranchiseCreateUpdateDTO dto) {
        if(franchiseService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            franchiseService.update(id, dto);
            Franchise updated = franchiseService.findById(id).orElseThrow();
            return ResponseEntity.ok(franchiseMapper.toReadDTO(updated));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?>  delete(@PathVariable UUID id) {
        franchiseService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
