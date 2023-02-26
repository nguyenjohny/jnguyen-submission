package com.insignia.jnguyen.robotchallenge.repository;

import com.insignia.jnguyen.robotchallenge.model.CommandEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandEventRepository extends JpaRepository<CommandEvent, Long> {
}
