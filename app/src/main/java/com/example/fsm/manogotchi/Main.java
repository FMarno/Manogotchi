//package com.example.fsm.manogotchi;

/**
 * Created by fsm on 02/06/15.
 */
public class Main {
    public static void main(String[] args){
        Person jim = new Person();
        jim.print();
        while (jim.runHour() != -1){
            if (jim.getAge() % 24 ==0)
            jim.print();
        }
        System.out.println("Jim died!");
        jim.print();
    }
}
