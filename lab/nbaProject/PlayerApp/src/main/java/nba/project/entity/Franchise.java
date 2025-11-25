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
public class Franchise {
    @Id
    @Column(nullable = false, unique = true, updatable = false)
    private UUID id;
    @Column(unique = true, nullable = false)
    private String name;
}
