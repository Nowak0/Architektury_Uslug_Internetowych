package nba.project.entity;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Player implements Comparable<Player>, Serializable {
    String firstName;
    String lastName;
    int age;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    Franchise franchise;
    Position position;

    @Override
    public int compareTo(Player player) {
        int compare = this.lastName.compareTo(player.lastName);
        if(compare == 0) {
            return this.firstName.compareTo(player.firstName);
        }
        return compare;
    }

    @Override
    public String toString() {
        String franchiseName = "Free Agent";
        if(franchise != null) {
            franchiseName = franchise.getName();
        }

        return "Player {" + firstName + " " + lastName +
                " (" + position + "), age = " + age +
                ", franchise = " + franchiseName + "}";
    }

    public PlayerDTO toDTO() {
        String franchiseName = "Free Agent";
        if(franchise != null) {
            franchiseName = franchise.getName();
        }

        return new PlayerDTO(firstName, lastName, age, position, franchiseName);
    }
}
