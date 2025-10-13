package nba.project;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import nba.project.entity.*;
import nba.project.tasks.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootApplication
public class ProjectApplication {
	public static void main(String[] args) {
		Franchise warriors = Franchise.builder()
			.name("Golden State Warriors")
			.city("San Francisco")
			.conference("West")
			.currentPosition(1)
			.titles(7)
			.build();

		Franchise sixers = Franchise.builder()
				.name("Philadelphia 76ers")
				.city("Philadelphia")
				.conference("East")
				.currentPosition(2)
				.titles(3)
				.build();

		Player curry = Player.builder()
				.firstName("Stephen")
				.lastName("Curry")
				.age(37)
				.position(Position.GUARD)
				.build();

		Player butler = Player.builder()
				.firstName("Jimmy")
				.lastName("Butler")
				.age(36)
				.position(Position.FORWARD)
				.build();

		Player embiid = Player.builder()
				.firstName("VJ")
				.lastName("Edgecombe")
				.age(20)
				.position(Position.GUARD)
				.build();

		Player maxey = Player.builder()
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

		Lab1.task7(franchises);
	}
}
