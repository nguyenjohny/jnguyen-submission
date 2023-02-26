package com.insignia.jnguyen.robotchallenge.service;

import com.insignia.jnguyen.robotchallenge.model.CommandEvent;
import com.insignia.jnguyen.robotchallenge.model.Facing;
import com.insignia.jnguyen.robotchallenge.model.Position;
import com.insignia.jnguyen.robotchallenge.model.Robot;
import com.insignia.jnguyen.robotchallenge.repository.RobotRepository;
import com.insignia.jnguyen.robotchallenge.util.CommandEventGenerator;
import com.insignia.jnguyen.robotchallenge.util.FileReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

import static com.insignia.jnguyen.robotchallenge.dto.Action.PLACE;
import static com.tyro.oss.randomdata.RandomEnum.randomEnumValue;
import static com.tyro.oss.randomdata.RandomInteger.randomIntegerBetween;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@SpringBootTest
class SimulationTest {
    @Autowired
    Simulation simulation;
    @Autowired
    RobotRepository robotRepository;
    @MockBean
    FileReader fileReader;
    @Value("${table.width:5}")
    int width;
    @Value("${table.height:5}")
    int height;

    @Test
    void shouldCreateRobotsWithIncrementalId() {
        var currentIndex = robotRepository.getMaxRobotId();
        var commands = Collections.singletonList(generateRandomValidPlacement());
        var result = simulation.createRobots(commands);
        assertEquals(1, result.size());
        assertTrue(result.containsKey(currentIndex + 1));
    }

    @ParameterizedTest
    @MethodSource
    void shouldNotCreateRobotsOnInvalidPlacements(CommandEvent commandEvent) {
        var result = simulation.createRobots(Collections.singletonList(commandEvent));
        assertTrue(result.isEmpty());
    }

    private static Stream<Arguments> shouldNotCreateRobotsOnInvalidPlacements() {
        return Stream.of(
                arguments(CommandEventGenerator.generatePlacement("-1,0,NORTH")),
                arguments(CommandEventGenerator.generatePlacement("0,-1,NORTH")),
                arguments(CommandEventGenerator.generatePlacement(String.format("%d,0,NORTH", 6))),
                arguments(CommandEventGenerator.generatePlacement(String.format("0,%d,NORTH", 6)))
        );
    }

    @Test
    void shouldCreatedRobotIdOnValidPlacements() {
        var result = simulation.createRobots(Arrays.asList(generateRandomValidPlacement(), generateRandomInvalidPlacement(), generateRandomValidPlacement()));
        assertEquals(2, result.size());
        assertTrue(result.containsKey(1L));
        assertTrue(result.containsKey(2L));
    }

    private CommandEvent generateRandomValidPlacement() {
        var position = Position.builder()
                .x(randomIntegerBetween(0, width))
                .y(randomIntegerBetween(0, height))
                .facing(randomEnumValue(Facing.class)).build();
        return CommandEvent.builder().action(PLACE).robot(Robot.builder().position(position).build()).build();
    }

    private CommandEvent generateRandomInvalidPlacement() {
        var position = Position.builder()
                .x(randomIntegerBetween(width, width + 10))
                .y(randomIntegerBetween(height, height + 10))
                .facing(randomEnumValue(Facing.class)).build();
        return CommandEvent.builder().action(PLACE).robot(Robot.builder().position(position).build()).build();
    }


}