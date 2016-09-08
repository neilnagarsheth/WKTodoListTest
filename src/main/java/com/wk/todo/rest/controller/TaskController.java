package com.wk.todo.rest.controller;

import java.math.BigInteger;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wk.todo.model.Task;
import com.wk.todo.service.TaskService;

@RestController
public class TaskController {
	
	@Autowired
	TaskService taskService;
	
	@RequestMapping(value="/taskList/{id}", 
			method= RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Task>> getTaskList(@PathVariable("id") BigInteger todoListId){
		if(todoListId == null){
			return new ResponseEntity<Collection<Task>>(HttpStatus.BAD_REQUEST);
		}
		Collection<Task> taskList = taskService.getTaskList(todoListId);
		return new ResponseEntity<Collection<Task>>(taskList, HttpStatus.OK);
	}
	
	@RequestMapping(value="/addTask",
			method= RequestMethod.POST,
			consumes= MediaType.APPLICATION_JSON_VALUE,
			produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Task> addTask(@RequestBody Task task){
		if(task == null || task.getListId() == null || StringUtils.isEmpty(task.getTaskName())){
			return new ResponseEntity<Task>(HttpStatus.BAD_REQUEST);
		}
		taskService.addTask(task);
		return new ResponseEntity<Task>(task, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/removeTask/{todoListId}/{taskId}",
			method= RequestMethod.DELETE,
			produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Task> removeTask(@PathVariable("todoListId") BigInteger todoListId, @PathVariable("taskId") BigInteger taskId){
		if(todoListId == null || taskId == null){
			return new ResponseEntity<Task>(HttpStatus.BAD_REQUEST);
		}
		taskService.removeTask(todoListId, taskId);
		return new ResponseEntity<Task>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/updateTask",
			method= RequestMethod.PUT,
			consumes= MediaType.APPLICATION_JSON_VALUE,
			produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Task> updateTask(@RequestBody Task task){
		if(task == null || task.getListId() == null || StringUtils.isEmpty(task.getTaskName()) || task.getIsDone() == null){
			return new ResponseEntity<Task>(HttpStatus.BAD_REQUEST);
		}
		taskService.updateTask(task);
		return new ResponseEntity<Task>(task, HttpStatus.CREATED);
	}
	
}
