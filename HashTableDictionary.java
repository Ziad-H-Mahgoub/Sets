package com.solutions;


/**
 * This hash table is implemented by the separate chaining way.
 * The SingleLinkedList is an imported class from a previous project ,it's not built in.
 * Class has 3 constructors.
 * @param <K> key to insert values
 * @param <V> values to be saved.
 */
public class HashTableDictionary<K,V> implements IDictionary<K,V> {

    private float loadFactor;
    private int MAX_SIZE;

    public int size() {
        return size;
    }

    private int size;
    private LinkedList<Node>[] table;

    public LinkedList<Node>[] getTable() {
        return table;
    }
    public HashTableDictionary(double loadFactor, int max_size){
        this.loadFactor= (float) loadFactor;
        MAX_SIZE=max_size;
        table=new LinkedList[MAX_SIZE];
        size=0;
    }
    public HashTableDictionary(){
        this(0.5f,101);
    }
    public HashTableDictionary(float loadFactor){
        this(loadFactor,101);
    }
    public HashTableDictionary(int max){
        this(0.5f,max);
    }

    @Override
    public V get(K key) {
        checkNullity(key);
        int index=hashFunction(key);
        LinkedList<Node> slot=table[index];
        if(slot!=null){
            for(int i=0;i<slot.size();i++) {
                Node x=slot.get(i);
                if (x.key.equals(key))
                    return x.element;
            }
        }
        return null;
    }

    @Override
    public V set(K key, V value) {
        checkNullity(value);
        checkNullity(key);
        Node node=new Node(key,value);
        int index=hashFunction(key);
//        System.out.println("Index for "+value+" is "+index);
        if(table[index]==null){
            table[index]= new LinkedList<>();
            table[index].add(node);//LinkedNode is successfully added.
        }
        else{//Key index is common
            for(int i=0;i<table[index].size();i++){
                Node x=table[index].get(i);
                if(x.key.equals(key)) {
                    V element = table[index].get(i).element;
                    x.element=value;
                    return element;
                }
            }
            table[index].add(new Node(key,value));
        }
        size++;
        rehash();//Method is called on every new addition.
        return null;
    }

    @Override
    public V remove(K key) {
        checkNullity(key);
        int index=hashFunction(key);
        LinkedList<Node> slot=table[index];
        if(slot!=null){
            for(int i=0;i<slot.size();i++) {
                Node x=slot.get(i);
                if (x.key.equals(key)) {
                    table[index].remove(i);//Remove the whole node.
                    size--;
                    return x.element;
                }
            }
        }
        return null;
    }

    public String[] getAllElements(){
        String[] elements =  new String[size];
        int point = 0;
        for (LinkedList<Node> list : table){
            if(list == null)
                continue;
            for (int i =0;i<list.size();i++){
                elements[point] = (String) list.get(i).element;
                point++;
            }
            if(point == size)
                return elements;
        }
        return elements;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }
    /////////////////////////////////////Additional///////////////////////////////

    /**
     * We need to make a suitable sequence
     * Convert key to a string
     * Get sum of all characters.
     * Square the sum.
     * return (result mod MAX_SIZE);
     * @param key
     * @return indexOfArray
     */
    private int hashFunction(K key){
        int sumOfChars=getSumOfChars(key.toString());
        sumOfChars*=sumOfChars;//Squaring result.
        return sumOfChars %MAX_SIZE;
    }

    private int getSumOfChars(String word){
        int sum=0;
        for(int i=0;i<word.length();i++)
            sum+=word.charAt(i);
        return sum;
    }
    private void checkNullity(Object x){
        if(x==null)
            throw new RuntimeException("Argument is null!");
    }

    /**
     * when (size/MAX_SIZE>loadFactor)
     * create a new array with double the size.
     * arrange elements in the new array.
     */
    private void rehash(){
        if((float)size/MAX_SIZE<=loadFactor)
            return;
//        System.out.println("\\\\\\\\\\\\\\\\REHASHING as size= "+size);
        MAX_SIZE*=2;
        LinkedList<Node>[]oldTable=table;//Pointer to old table to be updated.
        table=new LinkedList[MAX_SIZE];//Initialize new array.
        size=0;//Size increases automatically in set method.
        for(int i=0;i<oldTable.length;i++){
            if(oldTable[i]!=null){
                LinkedList<Node> slot=oldTable[i];
                for(int j=0;j<slot.size();j++){
                    Node x=slot.get(j);
                    set(x.key,x.element);
                }
            }
        }
//        System.out.println("\\\\\\\\\\\\\\\\\\\\\\Hashing is Done \t size is "+size+"\t MAXSIZE is "+MAX_SIZE);
    }
    public void printSize(){
        System.out.println("Size is "+size+"\t MaxSIze is "+MAX_SIZE);
    }
    ////////////////////////////////NodeCLass/////////////////////////////////////
    class Node{
        K key;
        V element;

        public Node(K key, V element) {
            this.key = key;
            this.element = element;
        }

        @Override
        public String toString() {
            return element.toString();
        }
    }

//
//    public void printTable() {
//        for(int i=0;i<table.length;i++){
//            System.out.println("Index is "+i);
//            if(table[i]!=null)
//                System.out.println(table[i].toString());
//        }
//    }
}