package com.wk.todo.data.helper;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import com.wk.todo.model.Task;
import com.wk.todo.model.TodoList;

public class DataHelper {
	public static Map<BigInteger, TodoList> todoListMap = new HashMap<BigInteger, TodoList>();
	public static Map<BigInteger, Map<BigInteger, Task>> taskMap = new HashMap<BigInteger, Map<BigInteger, Task>>();
	
	private static BigInteger todoListIdSequence = BigInteger.ZERO;
	private static BigInteger taskIdSequence = BigInteger.ZERO;
	public static BigInteger nextTodoListId(){
		todoListIdSequence = todoListIdSequence.add(BigInteger.ONE);
		return todoListIdSequence;
	}
	public static BigInteger nextTaskId(){
		taskIdSequence = taskIdSequence.add(BigInteger.ONE);
		return taskIdSequence;
	}
}
