//package com.example.fsm.manogotchi;

import java.util.HashMap;
/**
 * Created by finlay on 01/06/15.
 */
public class Person {
    private int energy;
    private int hunger;
    private int happiness;
    private int fitness;
    private int age;
    private boolean alive = true;
    private HashMap<Activity, Integer> Activities = new HashMap<Activity, Integer>();

    public enum Activity {SLEEP, WORKOUT, COMEDOWN}

    public enum Food {FRUIT(0,5,5,2), CHOCOLATE(-5,10,10,-10), COCAINE(50,-10,-10,-30);

        private int energyFactor;
        private int hungerFactor;
        private int happinessFactor;
        private int fitnessFactor;


        Food(int energyFactor, int hungerFactor, int happinessFactor, int fitnessFactor) {
            this.energyFactor = energyFactor;
            this.hungerFactor = hungerFactor;
            this.happinessFactor = happinessFactor;
            this.fitnessFactor = fitnessFactor;
        }

        public int getEnergyFactor() {
            return energyFactor;
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

    public Person(int energy, int hunger, int happiness, int fitness, int age) {
        this.energy = energy;
        this.hunger = hunger;
        this.happiness = happiness;
        this.fitness = fitness;
        this.age = age;
    }

    public Person() {
        energy = 70;
        hunger = 70;
        happiness = 100;
        fitness = 70;
        age = 0;
    }

    public void doSomething(){
        //this is what the pupil will fill out
        if (hunger > 50){
            System.out.println("ate fruit");
            while (hunger < 80) {
                consume(Food.FRUIT);
            }
        }
        if (happiness < 20){
            System.out.println("ate chocolate");
            consume(Food.CHOCOLATE);
        }
        if (fitness < 60) {
            System.out.println("worked out");
            doActivity(Activity.WORKOUT);
        }
        if (energy < 11){
            sleep(6);
        }
    }

    public int runHour(){
        checkLife();
        if (!alive){
            return -1;
        }

        //update activities list
        Integer sleeping = Activities.get(Activity.SLEEP);
        if (sleeping != null) {
            //end of sleep
            if (sleeping.equals(0)) {
                //stop sleeping and carry on
                Activities.remove(Activity.SLEEP);
            } else {
                //sleep and decay for hour
                Activities.put(Activity.SLEEP, (sleeping - 1));
                changeEnergy(20);
                decayPerson();
                age++;
                System.out.println("sleeping");
                return 0;
            }
        }


        //TODO comedowns

        decayPerson();

        //run pupil suggestion
        doSomething();
        age++;
        return 1;
    }

    private void decayPerson(){
        changeHunger(-15);
        changeEnergy(-10);
        changeHappiness(-5);
        changeFitness(-5);
    }

    private void checkLife(){
        if (hunger == 0 || energy == 0 || fitness == 0 || happiness == 0){
            alive = false;
        }
    }

    private void sleep(int time) {
        Activities.put(Activity.SLEEP, time);
    }

    private void consume(Food food) {
        changeEnergy(food.getEnergyFactor());
        changeHunger(food.getHungerFactor());
        changeHappiness(food.getHappinessFactor());
        changeFitness(food.getFitnessFactor());
    }

    private void doActivity(Activity activity){
        switch (activity){
            case WORKOUT: {
                changeFitness(10);
                changeEnergy(20);
                changeHappiness(15);
                changeHunger(20);
                return;
            }
            default:return;
        }
    }

    private void changeEnergy(int amount){
        int newT = energy + amount;
        if (newT < 0){
            energy = 0;
        } else if (newT > 100){
            energy = 100;
        } else {
            energy = newT;
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

    public boolean isAlive(){
        return alive;
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

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
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

    public void print(){
        System.out.println("Age: " + age + "\nenergy: " + energy + "\thunger: " + hunger + "\nhappiness: " + happiness + "\t fitness: " + fitness);
    }
}