package org.example;

import java.util.LinkedList;
import java.util.NoSuchElementException;


public class TqsStack<T> {

    private LinkedList<T> collection;
    private int limit;
    private Boolean hasLimit;

    public TqsStack(){
        this.collection = new LinkedList<T>();
        this.hasLimit = Boolean.FALSE;
    }
    public TqsStack(int limit){
        this.collection = new LinkedList<T>();
        this.hasLimit = Boolean.TRUE;
        this.limit = limit;
    }

    public T pop(){
        if (this.collection.size()==0){
            throw new NoSuchElementException();
        }
        return this.collection.pop();
    }
    public int size(){
        return this.collection.size();
    }
    public T peek(){
        if (this.collection.size()==0){
            throw new NoSuchElementException();
        }
        return this.collection.peek();
    }
    public void push(T t){
        if (this.hasLimit && this.limit == this.collection.size()){
            throw new IllegalStateException();
        }
        this.collection.push(t);
    }
    public Boolean isEmpty(){
        return this.collection.isEmpty();
    }
}
