package com.example.fsm.manogotchi;

import java.util.HashMap;
/**
 * Created by finlay on 01/06/15.
 */
public class Person {
    private int tiredness;
    private int hunger;
    private int happiness;
    private int fitness;
    private int age;
    private HashMap<Activity, Integer> toDo;

    public enum Activity {SLEEP, COMEDOWN}

    public enum Food {FRUIT(0,0,0,2), CHOCOLATE(0,-10,10,-10), COCAINE(50,10,-10,-30);

        private int tirednessFactor;
        private int hungerFactor;
        private int happinessFactor;
        private int fitnessFactor;


        Food(int tirednessFactor, int hungerFactor, int happinessFactor, int fitnessFactor) {
            this.tirednessFactor = tirednessFactor;
            this.hungerFactor = hungerFactor;
            this.happinessFactor = happinessFactor;
            this.fitnessFactor = fitnessFactor;
        }

        public int getTirednessFactor() {
            return tirednessFactor;
        }

        public int getHungerFactor() {
            return hungerFactor;
        }

        public int getHappinessFactor() {
            return happinessFactor;
        }

        public int getFitnessFactor() {
            return fitnessFactor;
        }
    }

    public Person(int tiredness, int hunger, int happiness, int fitness, int age) {
        this.tiredness = tiredness;
        this.hunger = hunger;
        this.happiness = happiness;
        this.fitness = fitness;
        this.age = age;
    }

    public Person() {
        tiredness = 0;
        hunger = 0;
        happiness = 100;
        fitness = 70;
        age = 0;
    }

    public void sleep(int time) {
        toDo.put(Activity.SLEEP, time);
    }

    public void consume(Food food) {
        changeTiredness(food.getTirednessFactor());
        changeHunger(food.getHungerFactor());
        changeHappiness(food.getHappinessFactor());
        changeFitness(food.getFitnessFactor());
    }

    private void changeTiredness(int amount){
        int newT = tiredness + amount;
        if (newT < 0){
            tiredness = 0;
        } else if (newT > 100){
            tiredness = 100;
        } else {
            tiredness = newT;
        }
    }

    private void changeHunger(int amount){
        int newT = hunger + amount;
        if (newT < 0){
            hunger = 0;
        } else if (newT > 100){
            hunger = 100;
        } else {
            hunger = newT;
        }
    }

    private void changeHappiness(int amount){
        int newT = happiness + amount;
        if (newT < 0){
            happiness = 0;
        } else if (newT > 100){
            happiness = 100;
        } else {
            happiness = newT;
        }
    }

    private void changeFitness(int amount){
        int newT = fitness + amount;
        if (newT < 0){
            fitness = 0;
        } else if (newT > 100){
            fitness = 100;
        } else {
            fitness = newT;
        }
    }

    public int getAge() {
        return age;
    }

    public int getFitness() {
        return fitness;
    }

    public int getHunger() {
        return hunger;
    }

    public int getHappiness() {
        return happiness;
    }

    public int getTiredness() {
        return tiredness;
    }

    public void setTiredness(int tiredness) {
        this.tiredness = tiredness;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public void setAge(int age) {
        this.age = age;
    }
}