package com.insignia.jnguyen.robotchallenge.util;

import com.insignia.jnguyen.robotchallenge.dto.Action;
import com.insignia.jnguyen.robotchallenge.model.CommandEvent;
import com.insignia.jnguyen.robotchallenge.model.Facing;
import com.insignia.jnguyen.robotchallenge.model.Position;
import org.springframework.core.convert.converter.Converter;

public class CommandConverter implements Converter<String, CommandEvent> {
    @Override
    public CommandEvent convert(String source) {
        final var tokens = source.split("\\s");
        var command = Action.valueOf(tokens[0]);

        var builder = CommandEvent.builder().action(Action.valueOf(tokens[0]));
        if (Action.PLACE.equals(command)) {
            builder.position(extractPosition(tokens[1]));
        }
        return builder.build();
    }

    public static Position extractPosition(String location) {
        var positionTokens = location.split(",");
        return Position.builder()
                .x(Integer.parseInt(positionTokens[0]))
                .y(Integer.parseInt(positionTokens[1]))
                .facing(Facing.valueOf(positionTokens[2]))
                .build();
    }
}
