package com.solutions;
import java.io.*;
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
        load();
        program();
    }

    public static int getInteger(String input) {
        while (true) {
            System.out.print(input);
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
        System.out.println("\n\n\\");
        System.out.println("1:Intersection of 2 sets");
        System.out.println("2:Union of 2 sets");
        System.out.println("3:Compliment of a set");
        System.out.println("4:Add a subset of universe");
        System.out.println("5:Add a string to universe ");
        System.out.println("6:Show all sets ");
        System.out.println("0:Exit");
    }

    public static void program(){
        boolean quit = false;
        while(!quit){
            menu();
            int choice = getInteger("Enter your choice : ");
            switch(choice){
                case 1 : intersection();break;
                case 2 : union();break;
                case 3 : compliment();break;
                case 4: addSubsets();break;
                case 5 : addToUniverse();break;
                case 6 : showData();break;
                case 0: save();quit = true;break;
                default:
                    System.out.println("Invalid Input!");
            }
        }
    }
    public static void intersection(){
        System.out.println("First set: ");
        HashTableDictionary<String,String>  set1 = Sets.get(checkSet());
        System.out.println("Second set: ");
        HashTableDictionary<String,String>  set2 = Sets.get(checkSet());
        HashTableDictionary<String,String> big;
        Object[] small;
        LinkedList<String> intersection = new LinkedList<>();
        if(set1.size() > set2.size()){
            big = set1;
            small = set2.getAllElements();
        }else{
            big = set2;
            small = set1.getAllElements();
        }
        for (Object i:small){
            Object check = big.get(i.toString());
            if(check!= null)
                intersection.add(i.toString());
        }
        System.out.println(intersection.toString());
    }

    public static void union(){
        System.out.println("First set: ");
        HashTableDictionary<String,String>  set1 = Sets.get(checkSet());
        System.out.println("Second set: ");
        HashTableDictionary<String,String>  set2 = Sets.get(checkSet());

        HashTableDictionary<String,String> union = new HashTableDictionary<>();
        Object[] setOne =set1.getAllElements();
        Object[] setTwo=set2.getAllElements();
        for (Object word: setOne){
            union.set(word.toString(),word.toString());
        }
        for(Object word : setTwo){
            union.set(word.toString(),word.toString());
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
            System.out.println("Wrong !");
        }
    }

    public static void addToUniverse(){
        System.out.println("On end :just press enter");
        while (true) {
            System.out.print("Enter your element: \r");
            String element = getString();
            if (element.equals("")) break;
            Universe.set(element,element);
        }
        System.out.println("Your Universe set is: "+ Arrays.toString(Universe.getAllElements())+"\n");
    }

    public static void addSubsets(){
        int number_of_sets = getInteger("Enter number of sets: ");
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
    }

    public static void save(){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("universe.txt")));
            Object[] names =Universe.getAllElements();
            for (Object name : names)
                writer.write(name.toString()+"\n");
            writer.close();
            ///New we save the set names
            Object[] sets = Sets.getAllKeys();
            writer =  new BufferedWriter(new FileWriter(new File("sets_names.txt")));
            for (Object name : sets)
                writer.write(name.toString()+"\n");
            writer.close();

            //Now a file for each set
            for(Object setName : sets){
                writer = new BufferedWriter(new FileWriter(new File(setName+".txt")));
                HashTableDictionary<String,String> set = Sets.get(setName.toString());
                Object[] words = set.getAllElements();
                for(Object word : words){
                    writer.write(word.toString()+"\n");
                }
                writer.close();
            }
        }catch (IOException e){
            System.out.println("Failed to save");
        }
    }

    public static void load(){
        try{
            BufferedReader read = new BufferedReader(new FileReader("universe.txt"));
            String word;
            while((word=read.readLine()) !=null)
//                System.out.println(word);
                Universe.set(word,word);
            //Now we read set names
            //And in same loop we fetch the set file
            BufferedReader setsFile = new BufferedReader(new FileReader("sets_names.txt"));
            String nameOfSet;
            while ((nameOfSet=setsFile.readLine())!=null){
                read = new BufferedReader(new FileReader(nameOfSet+".txt"));
                HashTableDictionary<String,String> set = new HashTableDictionary<>();
                String element;
                while((element = read.readLine())!=null)
                    set.set(element,element);
                Sets.set(nameOfSet , set);
            }

        }catch (IOException e){
            System.out.println("Failed to load");
        }
    }
    public static void showData(){
        //First universe
        System.out.println("Universe: "+Universe.toString());
        Object[]sets =Sets.getAllKeys();
        for (Object set : sets){
            System.out.println(set.toString()+" : "+Sets.get(set.toString()).toString());
        }
    }
}