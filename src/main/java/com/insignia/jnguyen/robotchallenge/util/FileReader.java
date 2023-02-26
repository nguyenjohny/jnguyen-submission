package com.insignia.jnguyen.robotchallenge.util;

import com.insignia.jnguyen.robotchallenge.exception.LoadException;
import com.insignia.jnguyen.robotchallenge.model.CommandEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Slf4j
@Component
public class FileReader {
    private final CommandConverter commandConverter = new CommandConverter();

    public List<CommandEvent> read(String filePath) {
        var commandEvents = new ArrayList<CommandEvent>();
        try {
            var scanner = new Scanner(Path.of(filePath));
            while (scanner.hasNextLine()) {
                var event = commandConverter.convert(scanner.nextLine());
                commandEvents.add(event);
            }
            return commandEvents;
        } catch (IOException e) {
            log.error("Unable to read file", e);
            throw new LoadException("Exit application", e);
        }
    }
}