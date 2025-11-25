package nba.project.tasks;

import jakarta.annotation.PostConstruct;
import nba.project.FranchiseApplication;
import nba.project.entity.Franchise;
import nba.project.repository.FranchiseRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class DataInitializer {
    private final FranchiseRepository franchiseRepository;

    public  DataInitializer(FranchiseRepository franchiseRepository) {
        this.franchiseRepository = franchiseRepository;
    }

    @PostConstruct
    public void initData() {
        System.out.println("Initializing Data...");

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

        List<Franchise> franchises = new ArrayList<>();
        franchises.add(warriors);
        franchises.add(sixers);
        franchiseRepository.saveAll(franchises);

        System.out.println("Data initialized");
    }
}
