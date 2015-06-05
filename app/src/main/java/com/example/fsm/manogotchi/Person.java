package com.example.fsm.manogotchi;

import android.widget.ImageView;

import java.util.ArrayList;
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

    private ArrayList<Integer> energy_stats = new ArrayList<>();
    private ArrayList<Integer> hunger_stats = new ArrayList<>();
    private ArrayList<Integer> happiness_stats = new ArrayList<>();
    private ArrayList<Integer> fitness_stats = new ArrayList<>();

    private boolean alive = true;
    private HashMap<Affect, Integer> affects = new HashMap<Affect, Integer>();

    public void refreshImage(ImageView img) {

        if (isSad()) {
            img.setImageResource(R.drawable.sad_android);
        } else if (isHappy()) {
            img.setImageResource(R.drawable.happy_android);
        } else if (!isAlive()) {
            img.setImageResource(R.drawable.dead_android);
        } else {
            img.setImageResource(R.drawable.default_android);
        }
    }

    public enum Activity {WORKOUT, WATCH_TV}

    public enum Affect {SLEEP, COMEDOWN, HANGOVER}

    public static enum Consumable {
        APPLE(0, 30, 5, 2, R.drawable.apple), CHOCOLATE(5, 10, 10, -10, R.drawable.chocolate), COFFEE(20, 0, 5, 0, R.drawable.coffee), BEER(0, -30, 30, 0, R.drawable.beer);

        private int energyFactor;
        private int hungerFactor;
        private int happinessFactor;
        private int fitnessFactor;
        private int image;


        Consumable(int energyFactor, int hungerFactor, int happinessFactor, int fitnessFactor, int image) {
            this.energyFactor = energyFactor;
            this.hungerFactor = hungerFactor;
            this.happinessFactor = happinessFactor;
            this.fitnessFactor = fitnessFactor;
            this.image = image;
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

        public int getImage() {return image; }
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
        happiness = 70;
        fitness = 70;
        age = 0;
    }

    public void doSomething() {

        if (age == 1) {
            consume(Consumable.BEER);

        }
        //this is what the pupil will fill out
        if (hunger < 50) {
            System.out.println("ate fruit");
            while (hunger < 80) {
                consume(Consumable.APPLE);
            }
        }
        if (happiness < 20) {
            System.out.println("ate chocolate");
            consume(Consumable.CHOCOLATE);
        }
        if (fitness < 60) {
            System.out.println("worked out");
            doActivity(Activity.WORKOUT);
        }
        if (energy < 20) {
            sleep(4);
        }
    }

    public int runHour() {

        recordState();
        checkLife();
        if (!alive){
            checkLife();
            return -1;
        }

        if (checkAffects() == 0) {
            checkLife();
            return 0;
        }


        //run pupil suggestion
        //doSomething();
        decayPerson();

        age++;
        checkLife();
        return 1;
    }

    private int checkAffects() {
        //update activities list

        //comedown
        Integer comedown = affects.get(Affect.COMEDOWN);
        if (comedown != null) {
            if (comedown.equals(0)) {
                changeHunger(-2);
                changeEnergy(-25);
                changeHappiness(-2);
                changeFitness(-1);
                affects.remove(Affect.COMEDOWN);
            } else {
                affects.put(Affect.COMEDOWN, (comedown - 1));
                System.out.println("coffee buzz");
                return 1;
            }
        }

        //hangover
        Integer hangover = affects.get(Affect.HANGOVER);
        if (hangover != null){
            if (hangover.equals(0)) {
                affects.remove(Affect.HANGOVER);
            } else {
                affects.put(Affect.HANGOVER, (hangover - 1));
                if (hangover < 5) {
                    System.out.println("hangover");
                    changeHunger(-10);
                    changeEnergy(-10);
                    changeHappiness(-10);
                    changeFitness(-10);
                }
            }
        }

        //sleep
        Integer sleeping = affects.get(Affect.SLEEP);
        if (sleeping != null) {
            //end of sleep
            if (sleeping.equals(0)) {
                //stop sleeping and carry on
                affects.remove(Affect.SLEEP);
            } else {
                //sleep and decay for hour
                affects.put(Affect.SLEEP, (sleeping - 1));
                changeEnergy(20);
                decayPerson();
                age++;
                System.out.println("sleeping");
                return 0;
            }
        }
        return 1;
    }

    private void recordState() {

        energy_stats.add(energy);
        hunger_stats.add(hunger);
        happiness_stats.add(happiness);
        fitness_stats.add(fitness);

    }

    private void decayPerson() {
        double changeInHunger = (0.1 * hunger) + (0.05 * (100 - energy)) + (0.1 * (Math.abs(50 - fitness)));
        double changeInEnergy = (0.1 * energy) + (0.1 * (100 - hunger)) + (0.2 * (100 - happiness)) + (0.2 * (100 - fitness)) + (age * 100 / 1753160);
        double changeInHappiness = (0.1 * (100 - happiness)) + (0.1 * (100 - hunger)) + (0.05 * (100 - energy)) + (0.05 * (100 - fitness));
        double changeInFitness = (0.1 * (100 - fitness)) + (0.2 * (100 - hunger)) + (0.07 * (100 - energy)) + (age * 100 / 1753160);

        changeHunger(-1 * (int) Math.round(changeInHunger));
        changeEnergy(-1 * (int) Math.round(changeInHunger));
        changeHappiness(-1 * (int) Math.round(changeInHunger));
        changeFitness(-1 * (int) Math.round(changeInHunger));
    }

    private void checkLife() {
        if (hunger == 0 || energy == 0 || fitness == 0 || happiness == 0) {
            alive = false;
        }
    }

    private void sleep(int time) {
        affects.put(Affect.SLEEP, time);
    }

    public void consume(Consumable food) {
        System.out.println(age + ": " + food);
        changeEnergy(food.getEnergyFactor());
        changeHunger(food.getHungerFactor());
        changeHappiness(food.getHappinessFactor());
        changeFitness(food.getFitnessFactor());

        switch (food) {
            case BEER: {
                affects.put(Affect.HANGOVER, 10);
                break;
            }
            case COFFEE: {
                affects.put(Affect.COMEDOWN, 2);
            }
        }


    }

    private void doActivity(Activity activity) {
        switch (activity) {
            case WORKOUT: {
                changeFitness(20);
                changeEnergy(-20);
                changeHappiness(15);
                changeHunger(-20);
                return;
            }
            case WATCH_TV:{
                changeFitness(-5);
                changeEnergy(0);
                changeHappiness(5);
                changeHunger(0);
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

    public void changeFitness(int amount){
        int newT = fitness + amount;
        if (newT < 0){
            fitness = 0;
        } else if (newT > 100){
            fitness = 100;
        } else {
            fitness = newT;
        }
    }

    public boolean isSad() {
        return happiness < 30;
    }

    public boolean isHappy() {
        return happiness > 70;
    }

    public boolean isAlive() {
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

    public ArrayList<Integer> getFitnessStats() {
        return fitness_stats;
    }

    public ArrayList<Integer> getHungerStats() {
        return hunger_stats;
    }

    public ArrayList<Integer> getHappinessStats() {
        return happiness_stats;
    }

    public ArrayList<Integer> getEnergyStats() {
        return energy_stats;
    }

    public void print() {
        System.out.println("Age: " + age + "\n energy: " + energy + "\t hunger: " + hunger + "\n happiness: " + happiness + "\t fitness: " + fitness);
    }
}