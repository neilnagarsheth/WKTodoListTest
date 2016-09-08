package com.wk.todo.service;

import java.math.BigInteger;
import java.util.Collection;

import com.wk.todo.model.TodoList;

public interface TodoListService {
	public void addList(TodoList todoList);
	public void removeList(BigInteger todoListId);
	public void updateList(TodoList todoList);
	public Collection<TodoList> getLists();

}
