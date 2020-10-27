package com.solutions;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;


public class Main {
    public static HashTableDictionary<String , String> Universe;
    public static HashTableDictionary<String , HashTableDictionary<String , String>> Sets;
    public static   Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        Universe = new HashTableDictionary<>();
        Sets = new HashTableDictionary<>();
        while (true) {
            System.out.print("Enter your element: \r");
            String element = getString();
            if (element.equals("")) break;
            Universe.set(element,element);
        }
        System.out.println("Your Universe set is: "+ Arrays.toString(Universe.getAllElements())+"\n");
        int number_of_sets = getInteger();
        for (int i = 1 ;i <= number_of_sets ;i++){
            System.out.println("Enter name of " + i + " the set :\r");
            String set;
            while(true) {
                set = getString();
                Object ch = Sets.get(set);
                if(ch != null)
                    System.out.println(set+" is already there!\nEnter name of set: ");
                else
                    break;
            }
            System.out.println("Enter elements of set "+set);
            HashTableDictionary<String,String> theSet = new HashTableDictionary<>();
            while(true){
                String element = checkValid();
                if (element.equals(""))
                    break;
                theSet.set(element,element);
            }
            System.out.println("the set is : " + set);
            System.out.println(Arrays.toString(theSet.getAllElements()));
            Sets.set(set , theSet);
        }

        program();



    }

    public static int getInteger() {
        while (true) {
            System.out.print("How many sets you Want: \r");
            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
                return choice;
            } catch (InputMismatchException k) {
                scanner.nextLine();
                System.out.println("Enter valid integer!");
            }
        }
    }

    public static String getString() {
        String element ;
        element = scanner.nextLine();
        return element;
    }

    public static String checkSet(){
        while (true){
            System.out.println("Enter name of set : \r");
            String set = getString();
            HashTableDictionary<String,String> setElements = Sets.get(set);
            if(setElements == null){//Set is not found
                System.out.println("You entered a fuckin name!!");
            }
            else
                return set;
        }
    }

    public static void menu(){
        System.out.println("1:Intersection of 2 sets");
        System.out.println("2:Union of 2 sets");
        System.out.println("3:Compliment of a set");
        System.out.println("4:Add a subset of universe");
        System.out.println("5:Add a string to universe ");
        System.out.println("0:Exit");
    }

    public static void program(){
        boolean quit = false;
        while(!quit){
            menu();
            int choice = getInteger();
            switch(choice){
                case 1 : intersection();break;
                case 2 : union();break;
                case 3 : compliment();break;
                default: quit = true;
            }
        }
    }
    public static void intersection(){
        System.out.println("First set: ");
        HashTableDictionary<String,String>  set1 = Sets.get(checkSet());
        System.out.println("Second set: ");
        HashTableDictionary<String,String>  set2 = Sets.get(checkSet());
        HashTableDictionary<String,String> big;
        String[] small;
        LinkedList<String> intersection = new LinkedList<>();
        if(set1.size() > set2.size()){
            big = set1;
            small = set2.getAllElements();
        }else{
            big = set2;
            small = set1.getAllElements();
        }
        for (String i:small){
            Object check = big.get(i);
            if(check!= null)
                intersection.add(i);
        }
        System.out.println(intersection.toString());
    }

    public static void union(){
        System.out.println("First set: ");
        HashTableDictionary<String,String>  set1 = Sets.get(checkSet());
        System.out.println("Second set: ");
        HashTableDictionary<String,String>  set2 = Sets.get(checkSet());

        HashTableDictionary<String,String> union = new HashTableDictionary<>();
        String[] setOne =set1.getAllElements();
        String[] setTwo=set2.getAllElements();
        for (String word: setOne){
            union.set(word,word);
        }
        for(String word : setTwo){
            union.set(word,word);
        }
        System.out.println(Arrays.toString(union.getAllElements()));
    }

    public static void compliment(){
        HashTableDictionary<String,String>  set1 = Sets.get(checkSet());
        Object[] universe = Universe.getAllElements();
        LinkedList<String> compliment = new LinkedList<>();

        for (Object word : universe){
            if (word == null)
                continue;
            Object chk = set1.get(word.toString());
            if(chk == null)
                compliment.add(word.toString());
        }
        System.out.println(compliment.toString());
    }
    public static String checkValid(){
        while(true){
            System.out.print("Enter element : \r");
            String element = getString();
            if (element.equals(""))
                return "";
            Object chk = Universe.get(element);
            if (chk != null)
                return element;
            System.out.println("Wrong bitch!");
        }
    }
}