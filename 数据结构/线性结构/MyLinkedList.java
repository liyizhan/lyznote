package com.lyz.io.list;

public class MyLinkedList<T> {
    private class Node<E>{
        E data;
        Node<E> next;
    }

    private Node<T> head;
    private int length;

    public MyLinkedList(){
        head=new Node<>();
        head.data=null;
        head.next=null;
        length=0;
    }

    //插入到表头
    public void addToHead(T element){
        Node<T> node=makeNode(element);
        node.next=head.next;
        head.next=node;
        length++;
    }

    //插入到表尾
    public void addToTail(T element){
        Node<T> node=makeNode(element);
        Node<T> tail=getTail();
        if(tail==null){
            head.next=node;
        }else{
            tail.next=node;
        }
        length++;
    }

    //插入到索引值代表的节点前面，如果没有该节点，插入不成功
    public boolean addBefore(T element,int index){
        if(length<=index||index<0){
            return false;
        }
        if(index==0&&head.next!=null){
            addToHead(element);
            return true;
        }
        if(index==0){
            return false;
        }
        return addAfter(element,index-1);
    }

    //插入到索引值代表的节点后面，如果没有该节点，插入不成功
    public boolean addAfter(T element,int index){
        if(length<=index||index<0){
            return false;
        }
        if(index==length-1){
            addToTail(element);
            return true;
        }
        Node<T> prev=getNode(index);
        if(prev!=null){
            Node<T> node=makeNode(element);
            node.next=prev.next;
            prev.next=node;
            length++;
            return true;
        }else {
            return false;
        }
    }

    //删除索引值代表的节点
    public void delete(int index){
        if(length<=index||index<0){
            return;
        }
        if(index==0){
            head.next=head.next.next;
            length--;
            return;
        }
        Node<T> prev=getNode(index-1);
        if(prev==null){
            return;
        }
        prev.next=prev.next.next;
        length--;
    }

    //删除匹配的第一个节点
    public void deleteFirst(T element){
        int index=getFirstIndex(element);
        if(-1==index){
            return;
        }
        delete(index);
    }

    //删除匹配的最后一个节点
    public void deleteLast(T element){
        int index=getLastIndex(element);
        if(-1==index){
            return;
        }
        delete(index);
    }

    //获取具有实际意义的表头内容
    public T getHeadData(){
        Node<T> first=getHead();
        if(first==null){
            return null;
        }else{
            return first.data;
        }
    }

    //获取表尾内容
    public T getTailData(){
        Node<T> tail=getTail();
        if(tail==null){
            return null;
        }else {
            return tail.data;
        }
    }

    //查找某元素的第一个索引，从0开始算
    public int getFirstIndex(T element){
        Node<T> p;
        int index=0;
        for(p=head.next;p!=null;p=p.next,index++){
            if(p.data.equals(element)){
                return index;
            }
        }
        return -1;
    }

    //查找某元素的最后一个索引，从0开始算
    public int getLastIndex(T element){
        Node<T> p;
        int i=0;
        int index=-1;
        for(p=head.next;p!=null;p=p.next,i++){
            if(p.data.equals(element)){
                index=i;
            }
        }
        return index;
    }

    //通过索引获取节点内容
    public T getElement(int index){
        Node<T> result=getNode(index);
        if(result==null){
            return null;
        }else {
            return result.data;
        }
    }

    //修改索引处节点内容
    public void setElement(T element,int index){
        Node<T> result=getNode(index);
        if(result!=null){
            result.data=element;
        }
    }

    //获取长度
    public int length(){
        return length;
    }

    //反转列表
    public void reverse(){
        if(length==0||length==1){
            return;
        }
        Node<T> p=head.next;
        head.next=null;
        Node<T> q;
        while(p!=null){
            q=p.next;
            p.next=head.next;
            head.next=p;
            p=q;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        sb.append("head>");
        for(Node<T> p=head.next;p!=null;p=p.next){
            sb.append(p.data);
            sb.append(" ");
        }
        return sb.toString();
    }

    //通过索引获取节点
    private Node<T> getNode(int index){
        if(length<=index||index<0){
            return null;
        }
        Node<T> result=head.next;
        for(int i=0;i!=index;i++){
            result=result.next;
        }
        return result;
    }

    //获取具有实际意义的表头
    private Node<T> getHead(){
        return head.next;
    }

    //获取表尾
    private Node<T> getTail(){
        Node<T> p;
        for(p=head.next;p!=null&&p.next!=null;p=p.next)
            ;
        return p;
    }

    //创建节点
    private Node<T> makeNode(T element){
        Node<T> node=new Node<>();
        node.data=element;
        return node;
    }
}
