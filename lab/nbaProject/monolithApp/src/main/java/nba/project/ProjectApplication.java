package nba.project;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import nba.project.entity.*;
import nba.project.tasks.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@SpringBootApplication
public class ProjectApplication {

	public static List<Franchise> basicData() {
		Franchise warriors = Franchise.builder()
				.id(UUID.randomUUID())
				.name("Golden State Warriors")
				.city("San Francisco")
				.conference("West")
				.currentPosition(1)
				.titles(7)
				.build();

		Franchise sixers = Franchise.builder()
				.id(UUID.randomUUID())
				.name("Philadelphia 76ers")
				.city("Philadelphia")
				.conference("East")
				.currentPosition(2)
				.titles(3)
				.build();

		Player curry = Player.builder()
				.id(UUID.randomUUID())
				.firstName("Stephen")
				.lastName("Curry")
				.age(37)
				.position(Position.GUARD)
				.build();

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
				.position(Position.GUARD)
				.build();

		Player maxey = Player.builder()
				.id(UUID.randomUUID())
				.firstName("Tyrese")
				.lastName("Maxey")
				.age(24)
				.position(Position.GUARD)
				.build();

		warriors.addPlayer(curry);
		warriors.addPlayer(butler);
		sixers.addPlayer(embiid);
		sixers.addPlayer(maxey);

		List<Franchise> franchises = new ArrayList<>();
		franchises.add(warriors);
		franchises.add(sixers);

		return franchises;
	}

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}
}
