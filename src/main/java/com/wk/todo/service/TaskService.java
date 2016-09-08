package com.wk.todo.service;

import java.math.BigInteger;
import java.util.Collection;

import com.wk.todo.model.Task;

public interface TaskService {
	public void addTask(Task task);
	public void removeTask(BigInteger todoListId, BigInteger taskId);
	public void updateTask(Task task);
	public Collection<Task> getTaskList(BigInteger todoListId);

}
