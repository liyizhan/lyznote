package com.lyz.io.queue;

import java.lang.reflect.Array;

public class SqQueue<T> {
    private T[] elements;
    private int front;
    private int rear;
    private int maxSize;

    private static final int default_max_size=10;

    public SqQueue(Class<T> typeToken){
        this(typeToken,default_max_size);
    }

    @SuppressWarnings("unchecked")
    public SqQueue(Class<T> typeToken,int maxSize){
        this.elements=(T[]) Array.newInstance(typeToken,maxSize+1);
        this.front=0;
        this.rear=0;
        this.maxSize=maxSize+1;
    }

    public void enQueue(T element){
        if(isFull()){
            return;
        }
        elements[rear]=element;
        rear=(rear+1)%maxSize;
    }

    public T deQueue(){
        if(isEmpty()){
            return null;
        }
        T result=elements[front];
        elements[front]=null;   //No lazy for memory leak
        front=(front+1)%maxSize;
        return result;
    }

    public T getHead(){
        if(isEmpty()){
            return null;
        }
        return elements[front];
    }

    public int length(){
        int result=rear-front;
        return result>=0?result:maxSize+result;
    }

    public boolean isFull(){
        return front==(rear+1)%maxSize;
    }

    public boolean isEmpty(){
        return front==rear;
    }

    public void clear(){
        for(int i=front;i!=rear;i=(i+1)%maxSize){
            elements[i]=null;
        }
        front=0;
        rear=0;
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        sb.append("head>");
        for(int i=front;i!=rear;i=(i+1)%maxSize){
            sb.append(elements[i]);
            sb.append(" ");
        }
        return sb.toString();
    }
}
