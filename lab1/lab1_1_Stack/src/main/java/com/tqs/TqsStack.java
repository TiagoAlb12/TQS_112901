package com.tqs;

import java.util.LinkedList;

public class TqsStack<T> {
    private final LinkedList<T> elements = new LinkedList<>();
    private final int maxSize;

    public TqsStack(int maxSize) {
        this.maxSize = maxSize;
    }

    public TqsStack() {
        this.maxSize = -1; // No max size
    }

    public T pop() {
        if (elements.isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return elements.pop();
    }

    public int size() {
        return elements.size();
    }

    public T peek() {
        if (elements.isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return elements.peek();
    }

    public void push(T element) {
        if (maxSize != -1 && elements.size() >= maxSize) {
            throw new IllegalStateException("Stack is full");
        }
        elements.push(element);
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    public T popTopN(int n) {
        if (n <= 0 || n > elements.size()) {
            throw new IllegalArgumentException("Invalid number of elements to pop");
        }

        T top = null;
        for (int i = 0; i < n; i++) {
            top = elements.removeFirst();
        }
        return top;
    }
}