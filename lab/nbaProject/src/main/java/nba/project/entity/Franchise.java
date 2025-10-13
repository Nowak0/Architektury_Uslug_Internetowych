package nba.project.entity;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Franchise implements Comparable<Franchise>, Serializable {
    private String name;
    private String city;
    private String conference;
    private int currentPosition;
    private int titles;
    @EqualsAndHashCode.Exclude
    @Builder.Default
    @ToString.Exclude
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
                ", number of players = " + players.size() +
                "}";
    }
}
