package com.wk.todo.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wk.todo.model.TodoList;
import com.wk.todo.service.TodoListService;

@RestController
public class ListController {
	
	@Autowired
	TodoListService todoListService;
	
	@RequestMapping(value="/lists", 
			method= RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<TodoList>> getLists(){
		Collection<TodoList> todoLists = todoListService.getLists();
		return new ResponseEntity<Collection<TodoList>>(todoLists, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/addList", 
			method= RequestMethod.POST, 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TodoList> addList(@RequestBody TodoList todoList){
		if(todoList == null || StringUtils.isEmpty((todoList.getListName()))){
			return new ResponseEntity<TodoList>(todoList, HttpStatus.BAD_REQUEST);
		}
		todoListService.addList(todoList);
		return new ResponseEntity<TodoList>(todoList, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/removeList/{id}",
			method= RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TodoList> removeList(@PathVariable("id") BigInteger todoListId){
		if(todoListId == null){
			return new ResponseEntity<TodoList>(HttpStatus.BAD_REQUEST);
		}
		todoListService.removeList(todoListId);
		return new ResponseEntity<TodoList>(HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/updateList",
			method= RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TodoList> updateList(@RequestBody TodoList todoList){
		if(todoList == null || StringUtils.isEmpty((todoList.getListName()))){
			return new ResponseEntity<TodoList>(todoList, HttpStatus.BAD_REQUEST);
		}
		todoListService.updateList(todoList);
		return new ResponseEntity<TodoList>(todoList, HttpStatus.OK);
	}
}
