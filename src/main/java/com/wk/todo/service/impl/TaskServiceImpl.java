package com.wk.todo.service.impl;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.wk.todo.data.helper.DataHelper;
import com.wk.todo.model.Task;
import com.wk.todo.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {

	@Override
	public void addTask(Task task) {
		BigInteger taskId = DataHelper.nextTaskId();
		task.setTaskId(taskId);
		Map<BigInteger, Task> taskMap = DataHelper.taskMap.get(task.getListId());
		if(taskMap == null){
			taskMap = new HashMap<BigInteger, Task>();
		}
		task.setIsDone(false);
		taskMap.put(taskId, task);
		DataHelper.taskMap.put(task.getListId(), taskMap);
	}

	@Override
	public void removeTask(BigInteger todoListId, BigInteger taskId) {
		if(DataHelper.taskMap.containsKey(todoListId) && DataHelper.taskMap.get(todoListId).containsKey(taskId)){
			DataHelper.taskMap.get(todoListId).remove(taskId);
		}
	}

	@Override
	public void updateTask(Task task) {
		if(DataHelper.taskMap.containsKey(task.getListId())){
			DataHelper.taskMap.get(task.getListId()).put(task.getTaskId(), task);
		}
	}

	@Override
	public Collection<Task> getTaskList(BigInteger todoListId) {
		if(DataHelper.taskMap.containsKey(todoListId)){
			return DataHelper.taskMap.get(todoListId).values();
		}
		return null;
	}

}
