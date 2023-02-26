package com.insignia.jnguyen.robotchallenge.util;

import com.insignia.jnguyen.robotchallenge.model.CommandEvent;
import com.insignia.jnguyen.robotchallenge.model.Robot;

import static com.insignia.jnguyen.robotchallenge.dto.Action.*;

public class CommandEventGenerator {
    public static CommandEvent generatePlacement(String placement) {
        var position = CommandConverter.extractPosition(placement);
        return CommandEvent.builder().action(PLACE).robot(Robot.builder().position(position).build()).build();
    }

    public static CommandEvent generateMove() {
        return CommandEvent.builder().action(MOVE).build();
    }

    public static CommandEvent generateLeft() {
        return CommandEvent.builder().action(LEFT).build();
    }

    public static CommandEvent generateRight() {
        return CommandEvent.builder().action(RIGHT).build();
    }

    public static CommandEvent generateReport() {
        return CommandEvent.builder().action(REPORT).build();
    }

}
