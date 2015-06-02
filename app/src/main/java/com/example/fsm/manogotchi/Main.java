package com.example.fsm.manogotchi;

/**
 * Created by fsm on 02/06/15.
 * used for testing Person.java without android
 */
public class Main {
    public static void main(String[] args){
        Person jim = new Person();
        jim.print();
        while (jim.runHour() != -1){
            jim.print();
            System.out.println();
        }
        System.out.println("Jim died!");
        jim.print();
    }
}
