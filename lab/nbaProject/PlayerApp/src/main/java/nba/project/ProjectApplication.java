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

//	public static List<Franchise> basicData() {
//		Player butler = Player.builder()
//				.id(UUID.randomUUID())
//				.firstName("Jimmy")
//				.lastName("Butler")
//				.age(36)
//				.position(Position.FORWARD)
//				.build();
//
//		Player embiid = Player.builder()
//				.id(UUID.randomUUID())
//				.firstName("VJ")
//				.lastName("Edgecombe")
//				.age(20)
//				.position(Position.GUARD)
//				.build();
//
//		Player maxey = Player.builder()
//				.id(UUID.randomUUID())
//				.firstName("Tyrese")
//				.lastName("Maxey")
//				.age(24)
//				.position(Position.GUARD)
//				.build();
//	}

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}
}
