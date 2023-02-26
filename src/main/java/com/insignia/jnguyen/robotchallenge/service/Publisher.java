package com.insignia.jnguyen.robotchallenge.service;

import com.insignia.jnguyen.robotchallenge.model.CommandEvent;
import com.insignia.jnguyen.robotchallenge.model.Robot;
import com.insignia.jnguyen.robotchallenge.repository.RobotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class Publisher {
    
    private final RobotRepository robotRepository;

    public void report(Robot robot) {
        log.info("Output: {} | Active robot: {} | Number of robots: {}", robot.getPosition(), robot, robotRepository.getMaxRobotId());
    }

    public void report(List<CommandEvent> commandEvent) {
        commandEvent.forEach(event -> {
            switch (event.getAction()) {
                case PLACE -> log.info("{} {}", event.getAction(), event.getRobot().getPosition());
                case MOVE, LEFT, REPORT -> log.info("{}", event.getAction());
                case ROBOT -> log.info("{} {}", event.getAction(), event.getRobot().getId());
            }
        });
    }

}
