package nba.project.tasks;

import nba.project.entity.*;
import java.io.*;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

public class Lab1 {
    public static void task1(Player p) {
        Player curryCopy = Player.builder()
                .firstName("Stephen")
                .lastName("Curry")
                .age(35)
                .position(Position.GUARD)
                .build();

        System.out.println("curry.equals(curryCopy) = " + p.compareTo(curryCopy));
        System.out.println("curry.hashCode() = " + p.hashCode());
        System.out.println("curryCopy.hashCode() = " + curryCopy.hashCode());
    }

    public static void task2(List<Franchise> franchises) {
        franchises.forEach(franchise -> {
            System.out.println("\n" + franchise + "\n");
            franchise.getPlayers().forEach(player -> System.out.println(player + " "));
        });
    }

    public static void task3(List<Franchise> franchises) {
        Set<Player> players = franchises.stream()
                .flatMap(f -> f.getPlayers().stream())
                .collect(Collectors.toSet());
        players.forEach(System.out::println);
    }

    public static void task4(List<Franchise> franchises) {
        Set<Player> players = franchises.stream()
                .flatMap(f -> f.getPlayers().stream())
                .collect(Collectors.toSet());
        players.stream()
                .filter(player -> player.getPosition() == Position.GUARD)
                .sorted(Comparator.comparing(Player::getAge))
                .forEach(System.out::println);
    }

    public static void task5(List<Franchise> franchises) {
        Set<Player> players = franchises.stream()
                .flatMap(f -> f.getPlayers().stream())
                .collect(Collectors.toSet());
        List<PlayerDTO> playersDTO = players.stream()
                .map(Player::toDTO)
                .sorted()
                .collect(Collectors.toList());
        playersDTO.forEach(System.out::println);
    }

    public static void task6(List<Franchise> franchises) {
        String fileName = "franchises.txt";

        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(franchises);
            System.out.println(fileName + " has been written");
        } catch(IOException e) {
            e.printStackTrace();
        }

        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            List<Franchise> newFranchises = (List<Franchise>) in.readObject();
            System.out.println(fileName + " has been read");
            newFranchises.forEach(System.out::println);
        } catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void task7(List<Franchise> franchises) {
        ForkJoinPool pool = new ForkJoinPool(3);

        try {
            pool.submit(() -> franchises.parallelStream()
                    .forEach(franchise -> {
                        System.out.println(Thread.currentThread().getName() + " Franchise: " + franchise.getName());
                        franchise.getPlayers().forEach(player -> {
                            try {
                                Thread.sleep(1000);
                                System.out.println("\t" + Thread.currentThread().getName() + " Player: " + player.getFirstName() + " " + player.getLastName());
                            } catch(InterruptedException e) {
                                e.printStackTrace();
                            }
                        });
                    })).join();
        } finally {
            pool.shutdown();
        }
    }
}
