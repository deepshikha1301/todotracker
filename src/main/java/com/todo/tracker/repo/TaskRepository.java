package com.todo.tracker.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todo.tracker.model.TaskItem;

@Repository
public interface TaskRepository extends JpaRepository<TaskItem, Long>{

}
