package nba.project.tasks;

import nba.project.entity.Franchise;
import nba.project.entity.Player;
import nba.project.entity.Position;
import nba.project.service.FranchiseService;
import nba.project.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private final FranchiseService franchiseService;
    private final PlayerService playerService;

    public ConsoleRunner(FranchiseService franchiseService, PlayerService playerService) {
        this.franchiseService = franchiseService;
        this.playerService = playerService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nNBA");
        System.out.println("Type 'help' to see available commands\n");

        boolean running = true;
        while (running) {
            System.out.print("> ");
            String command = scanner.nextLine().trim().toLowerCase();

            switch (command) {
                case "help" -> printHelp();
                case "list franchises" -> listFranchises();
                case "list players" -> listPlayers();
                case "add player" -> addPlayer(scanner);
                case "delete player" -> deletePlayer(scanner);
                case "exit" -> running = false;
                default -> System.out.println("Unknown command. Type 'help' for a list of commands.");
            }
        }

        System.out.println("\nEnd of program");
    }

    private void printHelp() {
        System.out.println("""
                Available commands:
                - help
                - list franchises
                - list players
                - add player
                - delete player
                - exit
                """);
    }

    private void listFranchises() {
        List<Franchise> franchises = franchiseService.findAll();
        if(franchises.isEmpty()) {
            System.out.println("No franchises found");
        } else {
            franchises.forEach(System.out::println);
        }
    }

    private void listPlayers() {
        List<Player> players = playerService.findAll();
        if(players.isEmpty()) {
            System.out.println("No players found");
        } else  {
            players.forEach(System.out::println);
        }
    }

    private void addPlayer(Scanner scanner) {
        System.out.println("Enter first name: ");
        String firstName = scanner.nextLine().trim();
        System.out.println("Enter last name: ");
        String lastName = scanner.nextLine().trim();
        System.out.println("Enter age: ");
        int age = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter position (guard, forward, center): ");
        String chosenPosition = scanner.nextLine().trim().toLowerCase();
        Position position = castPosition(chosenPosition);
        List<Franchise> franchises = franchiseService.findAll();
        if(franchises.isEmpty()) {
            System.out.println("No franchises found");
            return;
        }

        System.out.println("Choose franchise (select number 1-" + franchises.size() + "): ");
        franchises.forEach(f -> System.out.println(f.getName()));

        int choice = Integer.parseInt(scanner.nextLine()) - 1;
        if(choice < 0 || choice > franchises.size()) {
            System.out.println("Invalid choice");
            return;
        }

        Player newPlayer = Player.builder()
                .id(UUID.randomUUID())
                .firstName(firstName)
                .lastName(lastName)
                .age(age)
                .position(position)
                .franchise(franchises.get(choice))
                .build();
        playerService.save(newPlayer);
        System.out.println("New player has been created");
    }

    private Position castPosition(String chosenPosition) {
        Position position;
        if (chosenPosition.equals("guard")) {
            position = Position.GUARD;
        } else if (chosenPosition.equals("forward")) {
            position = Position.FORWARD;
        } else {
            position = Position.CENTER;
        }

        return position;
    }

    private void deletePlayer(Scanner scanner) {
        listPlayers();
        System.out.println("Enter player id to delete: ");
        String id = scanner.nextLine();
        try {
            playerService.deleteById(UUID.fromString(id));
            System.out.println("Player deleted successfully");
        } catch(Exception e) {
            System.out.println("Error while deleting player");
        }
    }
}
