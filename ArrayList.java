package com.solutions;

public class ArrayList {

    private Object[] array;
    private int length = 1;
    private int size =0;
    private int lastElement;

    public ArrayList(){
        array = new Object[length];
//        refreshArr();
    }

    public void add(Object element){
        size++;
        refreshArr();
        array[++lastElement] = element;
    }

    public int getSize() {
        return size;
    }
    public Object get(int index){
        if(!(index>0&&index<=lastElement)){
            throw new RuntimeException("Index out of bounds");
        }
        return array[index];
    }
    public String toString(){
        String arr  = "";
        for (Object i:array){
            arr+=i+" , ";
        }
        return arr;
    }
    private void refreshArr(){
        if (length != size)
            return;
        length+=length;
        Object[] newArr=new Object[length];
        if (size >= 0)
            System.arraycopy(array, 0, newArr, 0, size);
        array = newArr;
    }
}