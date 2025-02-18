package com.tqs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

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

    // Testes gerados com auxilio de AI

    // Test pushing a large number of elements to check scalability
    @DisplayName("Test pushing a large number of elements")
    @Test
    public void testPushLargeAmount() {
        int largeNumber = 1000;
        IntStream.rangeClosed(1, largeNumber).forEach(stack::push);
        assertEquals(largeNumber, stack.size());
    }

    // Test pushing and popping with mixed data types (assuming stack supports generics)
    @DisplayName("Test pushing and popping mixed data types")
    @Test
    public void testPushPopMixedTypes() {
        TqsStack<Object> mixedStack = new TqsStack<>();
        mixedStack.push("Hello");
        mixedStack.push(42);
        mixedStack.push(3.14);

        assertEquals(3.14, mixedStack.pop());
        assertEquals(42, mixedStack.pop());
        assertEquals("Hello", mixedStack.pop());
        assertTrue(mixedStack.isEmpty());
    }

    // Test pushing null values (if stack supports them)
    @DisplayName("Test pushing null values")
    @Test
    public void testPushNull() {
        stack.push(null);
        assertNull(stack.pop());
        assertTrue(stack.isEmpty());
    }

    // Test behavior when pushing the same element multiple times
    @DisplayName("Test pushing duplicate elements")
    @Test
    public void testPushDuplicates() {
        stack.push(5);
        stack.push(5);
        stack.push(5);

        assertEquals(5, stack.pop());
        assertEquals(5, stack.pop());
        assertEquals(5, stack.pop());
        assertTrue(stack.isEmpty());
    }

    // Test alternate push-pop sequences
    @DisplayName("Test alternating push and pop operations")
    @Test
    public void testAlternatePushPop() {
        stack.push(10);
        stack.push(20);
        assertEquals(20, stack.pop());
        stack.push(30);
        assertEquals(30, stack.pop());
        assertEquals(10, stack.pop());
        assertTrue(stack.isEmpty());
    }

    // Test popping all elements then pushing new ones
    @DisplayName("Test pushing after emptying the stack")
    @Test
    public void testPushAfterEmptying() {
        stack.push(1);
        stack.push(2);
        stack.pop();
        stack.pop();
        assertTrue(stack.isEmpty());
        stack.push(3);
        assertEquals(1, stack.size());
        assertEquals(3, stack.peek());
    }

    // Test stack with only one element
    @DisplayName("Test stack with a single element")
    @Test
    public void testSingleElement() {
        stack.push(99);
        assertFalse(stack.isEmpty());
        assertEquals(99, stack.peek());
        assertEquals(99, stack.pop());
        assertTrue(stack.isEmpty());
    }

    // Test handling of memory constraints (for large data)
    @DisplayName("Test stack memory constraints")
    @Test
    public void testMemoryConstraints() {
        TqsStack<Integer> largeStack = new TqsStack<>();
        try {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                largeStack.push(i);
            }
            fail("Stack should have thrown an exception due to memory constraints.");
        } catch (OutOfMemoryError | IllegalStateException e) {
            assertTrue(true);
        }
    }

    // Test popTopN method (if implemented)
    @DisplayName("Test popTopN method")
    @Test
    public void testPopTopN() {
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);

        assertEquals(2, stack.popTopN(3));
        assertEquals(1, stack.pop());
        assertTrue(stack.isEmpty());
    }
}