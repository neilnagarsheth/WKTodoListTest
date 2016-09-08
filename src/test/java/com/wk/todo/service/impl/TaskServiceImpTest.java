package com.wk.todo.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
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
public class TaskServiceImpTest {
	
	@Autowired TodoListService todoListService;
	
	@Autowired TaskService taskService;
	
	private TodoList todoList;
	
	@Before
	public void setUp(){
		todoList = new TodoList();
		todoList.setListName("TestList");
		todoListService.addList(todoList);
	}
	
	@Test
	public void testAddTask(){
		Task task = initializeTask();
		assertNotNull(task.getTaskId());
		assertFalse(task.getIsDone());
		assertTrue(DataHelper.taskMap.get(todoList.getListId()).containsKey(task.getTaskId()));
		cleanDataHelper();
	}
	
	@Test
	public void testGetTasks(){
		assertTrue(DataHelper.taskMap.isEmpty());
		initializeTask();
		assertFalse(DataHelper.taskMap.isEmpty());
		cleanDataHelper();
	}
	
	@Test
	public void testRemoveTasks(){
		Task task = initializeTask();
		taskService.removeTask(todoList.getListId(), task.getTaskId());
		assertFalse(DataHelper.taskMap.get(todoList.getListId()).containsKey(task.getTaskId()));
		cleanDataHelper();
	}
	
	@Test
	public void testUpdateTasks(){
		Task task = initializeTask();
		assertEquals(DataHelper.taskMap.get(todoList.getListId()).get(task.getTaskId()).getTaskName(), "Test");
		task.setTaskName("NewTask");
		taskService.updateTask(task);
		assertEquals(DataHelper.taskMap.get(todoList.getListId()).get(task.getTaskId()).getTaskName(), "NewTask");
		task.setIsDone(true);
		taskService.updateTask(task);
		assertEquals(DataHelper.taskMap.get(todoList.getListId()).get(task.getTaskId()).getIsDone(), true);
		cleanDataHelper();		
	}

	private void cleanDataHelper(){
		DataHelper.taskMap.clear();
	}
	
	private Task initializeTask(){
		Task task = new Task();
		task.setTaskName("Test");
		task.setListId(todoList.getListId());
		taskService.addTask(task);
		return task;
	}
}
