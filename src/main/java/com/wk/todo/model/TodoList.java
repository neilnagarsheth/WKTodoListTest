package com.wk.todo.model;

import java.math.BigInteger;

public class TodoList {
	private BigInteger listId;
	private String listName;
	public BigInteger getListId() {
		return listId;
	}
	public void setListId(BigInteger listId) {
		this.listId = listId;
	}
	public String getListName() {
		return listName;
	}
	public void setListName(String listName) {
		this.listName = listName;
	}
}
