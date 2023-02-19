package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

public class TqsStackTest {

    private TqsStack<Object> tqsStack;

    @BeforeEach
    public void beforeEachTest(){
        this.tqsStack = new TqsStack<Object>();
    }

    @Test
    public void testTqsStack()
    {
        assertTrue(true);
    }

    @DisplayName("Test a) A stack is empty on construction")
    @Test
    public void testA(){
        assertTrue(this.tqsStack.isEmpty());
    }

    @DisplayName("Test b) A stack has size 0 on construction")
    @Test
    public void testB(){
        assertEquals(0,this.tqsStack.size());
    }

    @DisplayName("Test c) After n pushes to an empty stack, n > 0, the stack is not empty and its size is n")
    @Test
    public void TestC(){
        int nPushes = 2;
        for (int i = 0; i < nPushes; i ++) {
            this.tqsStack.push(Integer.toString(i));
        }
        assertFalse(this.tqsStack.isEmpty());
        assertEquals(nPushes, this.tqsStack.size());
    }

    @DisplayName("Test d) If one pushes x then pops, the value popped is x ")
    @Test
    public void TestD(){
        String push = "Push1";
        this.tqsStack.push(push);
        assertEquals(push, this.tqsStack.pop());
    }

    @DisplayName("Test e) If one pushes x then peeks, the value returned is x, but the size stays the same")
    @Test
    public void TestE(){
        String push = "Push1";
        this.tqsStack.push(push);
        int initialSize = this.tqsStack.size(); 
        assertEquals(push, this.tqsStack.peek());
        assertEquals(initialSize, this.tqsStack.size());
    }

    @DisplayName("Test f) If the size is n, then after n pops, the stack is empty and has a size 0")
    @Test
    public void TestF(){
        int nPushes = 2;
        for (int i = 0; i < nPushes; i++) {
            this.tqsStack.push(Integer.toString(i));
        }
        assertEquals(nPushes, this.tqsStack.size());
        for (int j = 0; j <= this.tqsStack.size(); j++){
            this.tqsStack.pop();
        }
        assertTrue(this.tqsStack.isEmpty());
        assertEquals(0, this.tqsStack.size());;
    }

    @DisplayName("Test g) Popping from an empty stack does throw a NoSuchElementException")
    @Test
    public void TestG(){
        assertTrue(this.tqsStack.isEmpty());
        assertThrows(NoSuchElementException.class, () -> this.tqsStack.pop());
    }

    @DisplayName("Test h) Peeking into an empty stack does throw a NoSuchElementException")
    @Test
    public void TestH(){
        assertTrue(this.tqsStack.isEmpty());
        assertThrows(NoSuchElementException.class, () -> this.tqsStack.peek());
    }
    @DisplayName("Test i) For bounded stacks only: pushing onto a full stack does throw an IllegalStateException")
    @Test
    public void TestI(){
        int limit = 1;
        this.tqsStack = new TqsStack<Object>(limit);
        for (var i = 0; i < limit; i++) {
            this.tqsStack.push("Push");
        }
        assertThrows(IllegalStateException.class, () -> this.tqsStack.push("PushLimit"));
    }
}
