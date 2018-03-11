package com.lyz.io.stack;

import java.lang.reflect.Array;

public class MyStack<T> {
    private T[] elements;
    private int top;
    private int size;
    private Class<T> typeToken;

    private static final int defaultSize=10;

    public MyStack(Class<T> typeToken){
        this(typeToken,defaultSize);
    }

    @SuppressWarnings("unchecked")
    public MyStack(Class<T> typeToken,int size){
        this.elements= (T[])Array.newInstance(typeToken,size);
        this.top=0;
        this.size=size;
        this.typeToken=typeToken;
    }

    public void push(T element){
        if(this.top>=size){
            resize();
        }
        elements[top]=element;
        top++;
    }

    public T pop(){
        if(this.top<=0){
            return null;
        }
        top--;
        T element=elements[top];
        elements[top]=null;
        return element;
    }

    public T getTop(){
        if(this.top<=0){
            return null;
        }
        return elements[top-1];
    }

    public int length(){
        return top;
    }

    public void empty(){
        for(int i=0;i<top;i++){
            elements[i]=null;
        }
        top=0;
    }

    @SuppressWarnings("unchecked")
    private void resize(){
        int oldSize=this.size;
        int newSize=oldSize+(oldSize>>1);
        T[] newBase=(T[])Array.newInstance(typeToken,newSize);
        for(int i=0;i<oldSize;i++){
            newBase[i]=elements[i];
        }
        elements=newBase;
        this.size=newSize;
    }

    @Override
    public String toString(){
        StringBuilder sb=new StringBuilder();
        sb.append("Bottom>");
        for(int i=0;i<top;i++){
            sb.append(elements[i]);
            sb.append(" ");
        }
        return sb.toString();
    }
}


