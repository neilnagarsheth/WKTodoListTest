package com.wk.todo.model;

import java.math.BigInteger;

public class Task {
	private BigInteger taskId;
	private String taskName;
	private Boolean isDone;
	private BigInteger listId;
	public BigInteger getTaskId() {
		return taskId;
	}
	public void setTaskId(BigInteger taskId) {
		this.taskId = taskId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public Boolean getIsDone() {
		return isDone;
	}
	public void setIsDone(Boolean isDone) {
		this.isDone = isDone;
	}
	public BigInteger getListId() {
		return listId;
	}
	public void setListId(BigInteger listId) {
		this.listId = listId;
	}

}
