package com.insignia.jnguyen.robotchallenge.model;

import com.insignia.jnguyen.robotchallenge.dto.Action;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@Entity
public class CommandEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Position position;

    @Enumerated(EnumType.STRING)
    private Action action;

    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "robotId")
    private Robot robot;
}
