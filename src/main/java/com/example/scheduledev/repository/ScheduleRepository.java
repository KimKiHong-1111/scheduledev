package com.example.scheduledev.repository;

import com.example.scheduledev.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

}
