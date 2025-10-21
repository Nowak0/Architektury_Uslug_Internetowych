package nba.project.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "franchises")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Franchise implements Comparable<Franchise>, Serializable {
    @Id
    @Column(nullable = false, unique = true, updatable = false)
    private UUID id;
    @Column(unique = true, nullable = false)
    private String name;
    @Column
    private String city;
    @Column
    private String conference;
    @Column(name = "current_position")
    private int currentPosition;
    @Column
    private int titles;
    @EqualsAndHashCode.Exclude
    @Builder.Default
    @ToString.Exclude
    @OneToMany(mappedBy = "franchise", cascade = CascadeType.ALL)
    private List<Player> players = new ArrayList<>();

    public void addPlayer(Player player) {
        if(!players.contains(player)) {
            players.add(player);
            player.setFranchise(this);
        }
    }

    @Override
    public int compareTo(Franchise franchise) {
        int compare = this.name.compareTo(franchise.name);
        if(compare == 0) {
            return this.city.compareTo(franchise.city);
        }
        return compare;
    }

    @Override
    public String toString() {
        return "Franchise {" +
                "name = '" + name + '\'' +
                ", city = '" + city + '\'' +
                ", titles = " + titles +
                "}";
    }
}
