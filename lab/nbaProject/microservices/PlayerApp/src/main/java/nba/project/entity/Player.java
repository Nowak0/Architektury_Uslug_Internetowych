package nba.project.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "players")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Player implements Comparable<Player>, Serializable {
    @Id
    @Column(nullable = false, unique = true, updatable = false)
    private UUID id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column
    private int age;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "franchise")
    private Franchise franchise;
    @Column
    private Position position;

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
                ", franchise = " + franchiseName +
                ", id = " + id + "}";
    }

    public PlayerDTO toDTO() {
        String franchiseName = "Free Agent";
        if(franchise != null) {
            franchiseName = franchise.getName();
        }

        return new PlayerDTO(firstName, lastName, age, position, franchiseName);
    }
}
