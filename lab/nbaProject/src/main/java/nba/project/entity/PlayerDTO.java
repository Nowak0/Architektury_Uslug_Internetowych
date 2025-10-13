package nba.project.entity;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDTO implements Comparable<PlayerDTO>, Serializable {
    private String firstName;
    private String lastName;
    private int age;
    private Position position;
    private String franchiseName;

    @Override
    public String toString() {
        return firstName + " " + lastName + " (" + position + "), age = " + age + ", franchise = " + franchiseName;
    }

    @Override
    public int compareTo(PlayerDTO p) {
        int compare = this.lastName.compareTo(p.lastName);
        if(compare == 0) {
            return this.firstName.compareTo(p.firstName);
        }
        return compare;
    }
}
