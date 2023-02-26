package com.insignia.jnguyen.robotchallenge.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@Entity
@RequiredArgsConstructor
@AllArgsConstructor
public class Robot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Embedded
    private Position position;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "robot", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("id")
    private List<CommandEvent> commandEvents;

    @Override
    public String toString() {
        return "Robot " + id + " (" + name + ")";
    }
}
