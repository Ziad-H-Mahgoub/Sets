package com.solutions;

//////////////////////////////LinkedList class/////////////////////


public class LinkedList<T> {

    private Node<T> head;
    private int size = 0;


    public LinkedList() {
        head = new Node<>();
    }

    public void add(T element) {
        if (element == null)
            throw new NullPointerException("Element added is null!");

        Node<T> temp = head;
        while (temp.next != null) {
            temp = temp.next;
        }
        temp.next = new Node<>(element);
        size++;
    }

    public T get(int index) {
        Node<T> temp = getNode(index);
        return temp == null ? null : temp.element;
    }

    public void remove(int index) {

        Node<T> temp = getNode(index);
        if (temp == null)
            return;
        if (index == 0) {
            head.next = temp.next;
            size--;
            return;
        }
        Node<T> before = getNode(index - 1);
        before.next = temp.next;
        size--;
    }

    private Node<T> getNode(int index) {
        int i = -1;
        Node<T> temp = head;
        while (temp != null) {
            if (i == index)
                return temp;
            temp = temp.next;
            i++;
        }
        return null;
    }

    public int size() {
        return size;
    }

    public String toString(){
        String x="";
        Node<T> iter = head;
        while(iter != null){
            x += iter.element+" , ";
            iter = iter.next;
        }
        return x;
    }

    private class Node<T> {

        T element;
        Node<T> next;

        public Node(T element) {
            this.element = element;
        }

        public Node() {
            this(null);
        }

    }
}