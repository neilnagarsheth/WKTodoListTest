package com.wk.todo.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.wk.todo.data.helper.DataHelper;
import com.wk.todo.model.Task;
import com.wk.todo.model.TodoList;
import com.wk.todo.service.TaskService;
import com.wk.todo.service.TodoListService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TodoListServiceImplTeset {
	
	@Autowired TodoListService todoListService;
	
	@Autowired TaskService taskService;
	
	@Test
	public void testGetTodoList(){
		assertTrue(DataHelper.todoListMap.isEmpty());
		initializeTodoList();
		assertFalse(DataHelper.todoListMap.isEmpty());
		cleanDataHelper();
	}
	
	@Test
	public void testAddTodoList(){
		TodoList todoList = initializeTodoList();
		assertNotNull(todoList.getListId());
		assertTrue(DataHelper.todoListMap.containsKey(todoList.getListId()));
		cleanDataHelper();
	}
	
	
	@Test
	public void testUpdateTodoList(){
		TodoList todoList = initializeTodoList();
		assertEquals(DataHelper.todoListMap.get(todoList.getListId()).getListName(), "TestList");
		todoList.setListName("NewList");
		todoListService.updateList(todoList);
		assertEquals(DataHelper.todoListMap.get(todoList.getListId()).getListName(), "NewList");
		cleanDataHelper();
	}
	
	@Test
	public void testDeleteTodoList(){
		TodoList todoList = initializeTodoList();
		Task task = new Task();
		task.setTaskName("TestTask");
		task.setListId(todoList.getListId());
		taskService.addTask(task);
		todoListService.removeList(todoList.getListId());
		assertFalse(DataHelper.todoListMap.containsKey(todoList.getListId()));
		assertFalse(DataHelper.taskMap.containsKey(todoList.getListId()));
		cleanDataHelper();
	}
	
	private void cleanDataHelper(){
		DataHelper.todoListMap.clear();
		DataHelper.taskMap.clear();
	}
	
	private TodoList initializeTodoList(){
		TodoList todoList = new TodoList();
		todoList.setListName("TestList");
		todoListService.addList(todoList);
		return todoList;
	}
	
	

}
