package com.wk.todo.data.helper;


import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataHelperTests {
	
	@Test
	public void testValidNextTodoListId(){
		BigInteger nextTodoListId = DataHelper.nextTodoListId();
		assertEquals(nextTodoListId, BigInteger.ONE);
	}
	
	@Test
	public void testValidNextTaskId(){
		BigInteger nextTaskId = DataHelper.nextTaskId();
		assertEquals(nextTaskId, BigInteger.ONE);
	}

}
