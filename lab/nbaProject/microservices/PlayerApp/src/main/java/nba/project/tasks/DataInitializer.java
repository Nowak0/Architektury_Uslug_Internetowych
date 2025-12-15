package nba.project.tasks;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import nba.project.ProjectApplication;
import nba.project.client.FranchiseClient;
import nba.project.entity.Franchise;
import nba.project.entity.Player;
import nba.project.entity.Position;
import nba.project.repository.FranchiseRepository;
import nba.project.repository.PlayerRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Random;

@Component
public class DataInitializer {
    private final PlayerRepository playerRepository;
	private final FranchiseClient franchiseClient;
	private final FranchiseRepository franchiseRepository;

	public DataInitializer(PlayerRepository playerRepository, FranchiseClient franchiseClient, FranchiseRepository franchiseRepository) {
        this.playerRepository = playerRepository;
		this.franchiseClient = franchiseClient;
        this.franchiseRepository = franchiseRepository;
    }

    @PostConstruct
    public void initData() {
        // Check if data already exists
        if (playerRepository.count() > 0) {
            System.out.println("Data already exists, skipping initialization. Found " + playerRepository.count() + " player(s).");
            return;
        }

        System.out.println("Initializing Data...");

        try {
            List<Franchise> franchises = franchiseClient.syncFranchises();
            franchiseRepository.saveAll(franchises);
            List<Player> players = new ArrayList<>();
            Random random = new Random();

            Player butler = Player.builder()
                    .id(UUID.randomUUID())
                    .firstName("Jimmy")
                    .lastName("Butler")
                    .age(36)
                    .position(Position.FORWARD)
                    .build();

            Player embiid = Player.builder()
                    .id(UUID.randomUUID())
                    .firstName("VJ")
                    .lastName("Edgecombe")
                    .age(20)
                    .position(Position.CENTER)
                    .build();

            Player maxey = Player.builder()
                    .id(UUID.randomUUID())
                    .firstName("Tyrese")
                    .lastName("Maxey")
                    .age(24)
                    .position(Position.GUARD)
                    .build();

            players.add(butler);
            players.add(embiid);
            players.add(maxey);

            for (Player player : players) {
                player.setFranchise(franchises.get(random.nextInt(franchises.size())));
            }

            playerRepository.saveAll(players);

            System.out.println("Data initialized successfully. Created " + players.size() + " player(s).");
        } catch (Exception e) {
            System.err.println("Failed to initialize data: " + e.getMessage());
            System.err.println("This may happen if FranchiseApp is not available. Data will be initialized on next restart.");
        }
    }
}
