package nba.project.repository;

import nba.project.entity.Franchise;
import nba.project.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlayerRepository extends JpaRepository<Player, UUID> {
    List<Player> findByFranchise(Franchise franchise);
    List<Player> findByFranchiseId(UUID franchiseID);
    List<Player> findByFranchiseName(String franchiseName);
}
