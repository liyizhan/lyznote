package com.lyz.io.hashtable;

import java.lang.reflect.Array;

public class LinkedHashTable<E> {
    private class Node<T>{
        T element;
        Node<T> next;
    }

    private int[] depths;
    private Class<E> typeToken;
    private int arrayLength;
    private int size;
    private Node<E>[] heads;
    private DIYHashFunction<E> diyHashFunction;

    @SuppressWarnings("unchecked")
    //创建哈希表
    public LinkedHashTable(int arrayLength,Class<E> typeToken,DIYHashFunction function){
        this.arrayLength=arrayLength;
        this.size=0;
        this.heads=(Node<E>[]) Array.newInstance(Node.class,arrayLength);
        //this.heads=(Node<E>[])new Object[arrayLength];
        this.diyHashFunction=function;
        this.typeToken=typeToken;
        this.depths=new int[arrayLength];
    }

    //判断给定的对象在哈希表是否存在
    public boolean isExistent(E element){
        int hash=diyHashFunction.hash(element);
        if(hash<0||hash>=arrayLength){
            return false;
        }
        Node<E> head=heads[hash];
        for(Node<E> p=head;p!=null;p=p.next){
            if(p.element.equals(element)){
                return true;
            }
        }
        return false;
    }

    //根据给定的对象，查找哈希表中与其相等（Equal）的对象，返回其引用
    public E getEqualObject(E element){
        int hash=diyHashFunction.hash(element);
        if(hash<0||hash>=arrayLength){
            return null;
        }
        for(Node<E> p=heads[hash];p!=null;p=p.next){
            if(p.element.equals(element)){
                return p.element;
            }
        }
        return null;
    }

    //插入一个对象到哈希表中，如果哈希表已存在相同（Equal）的对象，则插入失败
    public boolean insert(E element){
        int hash=diyHashFunction.hash(element);
        if(hash<0||hash>=arrayLength){
            return false;
        }
        for(Node<E> p=heads[hash];p!=null;p=p.next){
            if(p.element.equals(element)){
                return false;
            }
        }
        //插入到表头
        Node<E> node=makeNode(element);
        node.next=heads[hash];
        heads[hash]=node;
        size++;
        depths[hash]++;
        return true;
    }

    //删除给出的对象在哈希表与其相等（Equal）的对象
    public boolean delete(E element){
        int hash=diyHashFunction.hash(element);
        if(hash<0||hash>=arrayLength){
            return false;
        }
        Node<E> cur,prev;
        for(prev=heads[hash],cur=heads[hash];cur!=null;prev=cur,cur=cur.next){
            if(cur.element.equals(element)){
                break;
            }
        }
        if(cur==null){
            return false;
        }
        if(cur==heads[hash]){
            heads[hash]=cur.next;
        }else {
            prev.next=cur.next;
        }
        depths[hash]--;
        size--;
        return true;
    }

    //返回哈希表内部数组的长度
    public int getArrayLength(){
        return this.arrayLength;
    }

    //返回哈希表目前容量
    public int getSize(){
        return this.size;
    }

    //清空哈希表
    public void clear(){
        for(int i=0;i<heads.length;i++){
            heads[i]=null;
            depths[i]=0;
        }
        size=0;
    }

    //返回深度数组
    public int[] getDepths(){
        return this.depths;
    }

    @SuppressWarnings("unchecked")
    //以数组形式输出哈希表的所有对象
    public E[] output(){
        E[] data=(E[])Array.newInstance(typeToken,size);
        int j=0;
        for(int i=0;i<heads.length;i++){
            for(Node<E> p=heads[i];p!=null;p=p.next){
                data[j]=p.element;
                j++;
            }
        }
        return data;
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<heads.length;i++){
            sb.append("Bucket No.");
            sb.append(i);
            sb.append(":[ ");
            for(Node<E> p=heads[i];p!=null;p=p.next){
                sb.append(p.element);
                sb.append(" ");
            }
            sb.append("]\n");
        }
        return sb.toString();
    }

    //创建结点
    private Node<E> makeNode(E element){
        Node<E> node=new Node<>();
        node.element=element;
        return node;
    }
}
