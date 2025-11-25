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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class DataInitializer {
    private final PlayerRepository playerRepository;

    public  DataInitializer(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @PostConstruct
    public void initData() {
        System.out.println("Initializing Data...");

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

        List<Player> players = new ArrayList<>();
        players.add(butler);
        players.add(embiid);
        players.add(maxey);
        playerRepository.saveAll(players);

        System.out.println("Data initialized");
    }
}
