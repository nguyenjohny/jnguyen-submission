package com.insignia.jnguyen.robotchallenge.service;

import com.insignia.jnguyen.robotchallenge.dto.Action;
import com.insignia.jnguyen.robotchallenge.model.CommandEvent;
import com.insignia.jnguyen.robotchallenge.model.Facing;
import com.insignia.jnguyen.robotchallenge.model.Position;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.insignia.jnguyen.robotchallenge.model.Facing.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class Navigator {

    private static final EnumSet<Facing> X_FACING_REFERENCE = EnumSet.of(WEST, EAST);
    private static final EnumSet<Facing> Y_FACING_REFERENCE = EnumSet.of(NORTH, SOUTH);

    @Value("${table.width:5}")
    int width;

    @Value("${table.height:5}")
    int height;

    public List<Position> makeRoute(List<CommandEvent> commandEvents) {

        var route = new Stack<Position>();
        for (var event : commandEvents) {
            var next = switch(event.getAction()) {
                case PLACE -> {
                    if (!checkPosition(event.getPosition().getX(), event.getPosition().getY())) {
                        yield null;
                    }
                    yield event.getPosition();
                }
                case LEFT -> {
                    if (route.empty()) {
                        yield null;
                    }
                    var current = route.peek();
                    yield Position.builder().x(current.getX()).y(current.getY()).facing(turnLeft(current.getFacing())).build();
                }
                case RIGHT -> {
                    if (route.empty()) {
                        yield null;
                    }
                    var current = route.peek();
                    yield Position.builder().x(current.getX()).y(current.getY()).facing(turnRight(current.getFacing())).build();
                }
                case MOVE -> {
                    if (route.empty()) {
                        yield null;
                    }
                    var current = route.peek();
                    int x = current.getX() + (X_FACING_REFERENCE.contains(current.getFacing()) ? 1 : 0);
                    int y = current.getY() + (Y_FACING_REFERENCE.contains(current.getFacing()) ? 1 : 0);
                    if (!checkPosition(x, y)) {
                        yield null;
                    }
                    yield Position.builder().x(x).y(y).facing(current.getFacing()).build();
                }
                case REPORT -> {
                    log.info("{}", route.peek());
                    yield null;
                }
            };

            if (next != null) {
                route.push(next);
            }
        }

        return route;
    }

    public Facing turnLeft(Facing current) {
        return switch (current) {
            case NORTH -> WEST;
            case WEST -> SOUTH;
            case SOUTH -> EAST;
            case EAST -> NORTH;
        };
    }

    public Facing turnRight(Facing current) {
        return switch (current) {
            case NORTH -> EAST;
            case EAST -> SOUTH;
            case SOUTH -> WEST;
            case WEST -> NORTH;
        };
    }
    public boolean checkPosition(int x, int y) {
        // the origin (0,0) can be considered to be the SOUTH WEST most corner.
        if (x < 0 || x >= width || y < 0 || y >= height) {
            log.warn("Navigated beyond the boundaries of the site!");
            return false;
        }
        return true;
    }

}