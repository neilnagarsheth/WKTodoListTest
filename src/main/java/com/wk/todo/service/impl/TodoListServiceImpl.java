package com.wk.todo.service.impl;

import java.math.BigInteger;
import java.util.Collection;

import org.springframework.stereotype.Service;

import com.wk.todo.data.helper.DataHelper;
import com.wk.todo.model.TodoList;
import com.wk.todo.service.TodoListService;

@Service
public class TodoListServiceImpl implements TodoListService {

	@Override
	public void addList(TodoList todoList) {
		BigInteger newId = DataHelper.nextTodoListId();
		todoList.setListId(newId);
		DataHelper.todoListMap.put(newId, todoList);
	}

	@Override
	public void removeList(BigInteger todoListId) {
		if(DataHelper.todoListMap.containsKey(todoListId)){
			DataHelper.todoListMap.remove(todoListId);
		}
		if(DataHelper.taskMap.containsKey(todoListId)){
			DataHelper.taskMap.remove(todoListId);
		}
	}

	@Override
	public void updateList(TodoList todoList) {
		DataHelper.todoListMap.put(todoList.getListId(), todoList);
	}

	@Override
	public Collection<TodoList> getLists() {
		return DataHelper.todoListMap.values();
	}

	
}
