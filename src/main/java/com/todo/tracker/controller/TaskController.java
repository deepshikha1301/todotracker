package com.todo.tracker.controller;

import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todo.tracker.exception.ResourceNotFoundException;
import com.todo.tracker.model.TaskItem;
import com.todo.tracker.repo.TaskRepository;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class TaskController {

	@Autowired
	private TaskRepository taskRepo;

	@GetMapping("/tasks")
	public List<TaskItem> getAllTasks() {
		return taskRepo.findAll();
	}

	@GetMapping("/tasks/{id}")
	public ResponseEntity<TaskItem> getTaskById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
		TaskItem task = taskRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Task not found for id: " + id));
		return ResponseEntity.ok().body(task);
	}

	@PostMapping("/tasks")
	public TaskItem createTask(@Valid @RequestBody TaskItem task) {
		return taskRepo.save(task);
	}

	@PutMapping("/tasks/{id}")
	public ResponseEntity<TaskItem> updateTask(@PathVariable(value = "id") Long id, @Valid @RequestBody TaskItem task)
			throws ResourceNotFoundException {

		TaskItem taskItem = taskRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Task not found for id: " + id));
		
		taskItem.setDateTime(task.getDateTime());
		taskItem.setTaskName(task.getTaskName());
		final TaskItem updatedTask = taskRepo.save(taskItem);
		return ResponseEntity.ok().body(updatedTask);
	}
	
	@DeleteMapping("/tasks/{id}")
	public boolean deleteTask(@PathVariable(value = "id") Long id) throws ResourceNotFoundException{
		TaskItem taskItem = taskRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Task not found for id: " + id));
		
		taskRepo.delete(taskItem);
		return true;
	}

}
