package com.tqs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TqsStackTest {

    TqsStack stack;

    @BeforeEach
    public void setUp(){
        stack = new TqsStack();
    }

    @DisplayName("Test isEmpty")
    @Test
    public void testIsEmpty(){
        assertTrue(stack.isEmpty());
    }

    @DisplayName("Test size")
    @Test
    public void testSize(){
        assertEquals(0, stack.size());
    }

    // After n > 0 pushes to an empty stack, the stack is not empty and its size is n
    @DisplayName("Test push")
    @Test
    public void testPush(){
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertEquals(3, stack.size());
        assertTrue(!stack.isEmpty());
    }

    // If one pushes x then pops, the value popped is x.
    @DisplayName("Test pop")
    @Test
    public void testPop(){
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertEquals(3, stack.pop());
        assertEquals(2, stack.pop());
        assertEquals(1, stack.pop());
    }

    // If one pushes x then peeks, the value returned is x, but the size stays the same
    @DisplayName("Test peek")
    @Test
    public void testPeek(){
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertEquals(3, stack.peek());
        assertEquals(3, stack.size());
    }

    // If the size is n, then after n pops, the stack is empty and has a size 0
    @DisplayName("Test pop until empty stack")
    @Test
    public void testPop2(){
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.pop();
        stack.pop();
        stack.pop();
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
    }

    // Popping from an empty stack throws a NoSuchElementException
    @DisplayName("Test pop exception on empty stack")
    @Test
    public void testPopException(){
        assertTrue(stack.isEmpty());
        try {
            stack.pop();
        } catch (IllegalStateException e) {
            assertEquals("Stack is empty", e.getMessage());
        }
    }

    // Peeking into an empty stack throws a NoSuchElementException
    @DisplayName("Test peek exception on empty stack")
    @Test
    public void testPeekException(){
        assertTrue(stack.isEmpty());
        try {
            stack.peek();
        } catch (IllegalStateException e) {
            assertEquals("Stack is empty", e.getMessage());
        }
    }

    // For bounded stacks only, pushing onto a full stack throws an IllegalStateException
    @DisplayName("Test push exception on full stack")
    @Test
    public void testPushException(){
        TqsStack boundedStack = new TqsStack(2);
        boundedStack.push(1);
        boundedStack.push(2);
        try {
            boundedStack.push(3);
        } catch (IllegalStateException e) {
            assertEquals("Stack is full", e.getMessage());
        }
    }
}