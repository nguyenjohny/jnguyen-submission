package com.insignia.jnguyen.robotchallenge.service;

import com.github.javafaker.Faker;
import com.insignia.jnguyen.robotchallenge.model.CommandEvent;
import com.insignia.jnguyen.robotchallenge.model.Robot;
import com.insignia.jnguyen.robotchallenge.repository.CommandEventRepository;
import com.insignia.jnguyen.robotchallenge.repository.RobotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.insignia.jnguyen.robotchallenge.dto.Action.PLACE;
import static com.insignia.jnguyen.robotchallenge.dto.Action.ROBOT;

@Slf4j
@RequiredArgsConstructor
@Component
public class Simulation {
    private final RobotRepository robotRepository;
    private final CommandEventRepository commandEventRepository;
    private final Navigator navigator;

    public void runSimulator(List<CommandEvent> commandEvents) {
        var robots = createRobots(commandEvents);
        loadRobotEvents(robots, commandEvents);

        var processedEvents = commandEventRepository.findAll();
        navigator.calculate(processedEvents);
    }

    @Transactional
    private void loadRobotEvents(Map<Long, Robot> robots, List<CommandEvent> commandEvents) {
        Long activeRobotId = null;
        for (var event : commandEvents) {
           if (activeRobotId == null) {
               if (PLACE.equals(event.getAction()) && navigator.checkPosition(event.getPositionX(), event.getPositionX())) {
                   activeRobotId = 1L; // first successful placement, actions are now live.
               }
           } else {
               if (ROBOT.equals(event.getAction()))  {
                   activeRobotId = event.getRobot().getId();
               } else {
                   // start aggregating requests & i really want to only save usable events but lets save all events instead below.
                   event.setRobot(robots.get(activeRobotId));
               }
           }
           commandEventRepository.save(event);
        }
    }

    @Transactional
    public Map<Long, Robot> createRobots(List<CommandEvent> commandEvents) {
        return commandEvents.stream()
            .filter(item -> PLACE.equals(item.getAction()) && navigator.checkPosition(item.getPositionX(), item.getPositionY()))
            .map(item -> {
                var robot = Robot.builder()
                        .name(new Faker().gameOfThrones().character())
                        .position(item.getRobot().getPosition())
                        .commandEvents(new ArrayList<>())
                        .build();
                robotRepository.save(robot);
                item.setRobot(robot);
                log.info("Created robot. {}", robot);
                return robot;
            }).collect(Collectors.toMap(Robot::getId, Function.identity()));
    }

    @Deprecated(since = "This was used before multiple robots could be placed.")
    @Transactional
    public Optional<Robot> createRobotAndCommands(List<CommandEvent> commandEvents) {

        var hasValidPlacement = commandEvents.stream().findAny().filter(item -> navigator.checkPosition(item.getPositionX(), item.getPositionY()));
        if (hasValidPlacement.isEmpty()) {
            return Optional.empty();
        }

        var robot = Robot.builder()
                .name(new Faker().funnyName().name())
                .commandEvents(commandEvents)
                .build();

        return Optional.of(robotRepository.save(robot));
    }

}
