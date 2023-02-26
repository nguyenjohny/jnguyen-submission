package com.insignia.jnguyen.robotchallenge.repository;

import com.insignia.jnguyen.robotchallenge.model.Robot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RobotRepository extends JpaRepository<Robot, Long> {
    @Query(value = "SELECT coalesce(max(id), 0) FROM Robot")
    public Long getMaxRobotId();
}