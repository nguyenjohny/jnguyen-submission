package com.insignia.jnguyen.robotchallenge.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Embeddable
public class Position {
    private Integer x;
    private Integer y;
    @Enumerated(EnumType.STRING)
    private Facing facing;

    @Override
    public String toString() {
        return String.format("%s,%s,%s", x, y, facing);
    }
}
