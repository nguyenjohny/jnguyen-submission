package com.insignia.jnguyen.robotchallenge.model;

import com.insignia.jnguyen.robotchallenge.dto.Action;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringExclude;

import java.util.Optional;

@Data
@Builder
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
public class CommandEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Action action;

    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "robotId")
    @ToStringExclude
    private Robot robot;

    public Integer getPositionX() {
        if (robot != null && robot.getPosition() != null) {
            return robot.getPosition().getX();
        }
        return null;
    }

    public Integer getPositionY() {
        if (robot != null && robot.getPosition() != null) {
            return robot.getPosition().getY();
        }
        return null;
    }
}
