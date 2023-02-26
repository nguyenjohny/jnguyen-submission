package com.insignia.jnguyen.robotchallenge;

import com.insignia.jnguyen.robotchallenge.service.Simulation;
import com.insignia.jnguyen.robotchallenge.util.FileReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
@RequiredArgsConstructor
public class RobotCli implements CommandLineRunner {
    public static final String COMMANDS_PATH_DEFAULT = "commands.txt";
    public static final String COMMANDS_PATH_KEY = "inputFile";

    private final Simulation simulation;
    private final FileReader fileReader;

    @Value("${inputFile:" + COMMANDS_PATH_DEFAULT + "}")
    String inputFile;

    @Override
    public void run(String... args) throws Exception {
        boolean isInputFileSpecified = Arrays.stream(args).anyMatch(s -> s.startsWith(String.format("--%s=", COMMANDS_PATH_KEY)));
        if (!isInputFileSpecified) {
            // just nice to have logging, we don't need to else & assert as our unit tests and spring covers property sources.
            log.info("File input option --inputFile={" + COMMANDS_PATH_DEFAULT + "} not provided. Using default instead.");
        }

        // unsure if this is a good decision or not but at this time I rather use the application-property than args
        log.info("Loading commands from './{}'", inputFile);
        simulation.runSimulator(fileReader.read(inputFile));
    }
}