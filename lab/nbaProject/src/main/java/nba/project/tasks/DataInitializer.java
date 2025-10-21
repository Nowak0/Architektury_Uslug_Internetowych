package nba.project.tasks;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import nba.project.ProjectApplication;
import nba.project.entity.Franchise;
import nba.project.entity.Player;
import nba.project.entity.Position;
import nba.project.repository.FranchiseRepository;
import nba.project.repository.PlayerRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class DataInitializer {
    private final FranchiseRepository franchiseRepository;
    private final PlayerRepository playerRepository;

    public  DataInitializer(FranchiseRepository franchiseRepository, PlayerRepository playerRepository) {
        this.franchiseRepository = franchiseRepository;
        this.playerRepository = playerRepository;
    }

    @PostConstruct
    public void initData() {
        System.out.println("Initializing Data...");

        List<Franchise> franchises;
        franchises = ProjectApplication.basicData();
        franchiseRepository.saveAll(franchises);

        System.out.println("Data initialized");
    }
}
