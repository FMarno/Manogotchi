package com.example.fsm.manogotchi;


import android.app.ActionBar;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.app.*;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends FragmentActivity implements StatisticsFragment.OnChangeListener, SensorEventListener, FoodFragment.OnFoodClickListener{

    private Person jim;
    private float[] gravity = {0,0,0};
    private double gravMag = 0;
    private SensorManager mSensorManager;
    private Sensor mSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        setTheme(android.R.style.Theme_Holo);

        ImageView img = (ImageView) findViewById(R.id.android_figure);

        //Create test person
        jim = new Person();
        jim.refreshImage(img);
        updateStatBars(jim);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_GAME);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
    }

    //Event handler for the Test button click
    public void testBars(View view) {

        ImageView img = (ImageView) findViewById(R.id.android_figure);

        if (jim.isAlive()) {


            jim.runHour();

            updateStatBars(jim);

            jim.refreshImage(img);


            TabContainerFragment tabs = (TabContainerFragment) getSupportFragmentManager().findFragmentById(R.id.tab_container);

            //Passing on the stats to the statistics fragment to update the graphs
            int[] stats = {jim.getEnergy(), jim.getHunger(), jim.getFitness(), jim.getHappiness()};

            tabs.addToGraph(stats);
        } else {
            img.setImageResource(R.drawable.dead_android);
            Toast toast = Toast.makeText(getApplicationContext(), "You died!!!", Toast.LENGTH_LONG);
            toast.show();

        }
    }

    public void testBars(){
        ImageView img = (ImageView) findViewById(R.id.android_figure);

        if (jim.isAlive()) {


            jim.runHour();

            updateStatBars(jim);

            jim.refreshImage(img);


            TabContainerFragment tabs = (TabContainerFragment) getSupportFragmentManager().findFragmentById(R.id.tab_container);

            //Passing on the stats to the statistics fragment to update the graphs
            int[] stats = {jim.getEnergy(), jim.getHunger(), jim.getFitness(), jim.getHappiness()};

            tabs.addToGraph(stats);
        } else {
            img.setImageResource(R.drawable.dead_android);
            Toast toast = Toast.makeText(getApplicationContext(), "You died!!!", Toast.LENGTH_LONG);
            toast.show();

        }
    }

    @Override
    public int[] getStatistics() {
        return new int[0];
    }

    public void updateStatBars(Person jim) {
        ProgressBar energyBar = (ProgressBar) findViewById(R.id.energy_bar);
        ProgressBar hungerBar = (ProgressBar) findViewById(R.id.hunger_bar);
        ProgressBar fitnessBar = (ProgressBar) findViewById(R.id.fitness_bar);
        ProgressBar happinessBar = (ProgressBar) findViewById(R.id.happiness_bar);


        energyBar.setProgress(jim.getEnergy());
        hungerBar.setProgress(jim.getHunger());
        fitnessBar.setProgress(jim.getFitness());
        happinessBar.setProgress(jim.getHappiness());
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // In this example, alpha is calculated as t / (t + dT),
        // where t is the low-pass filter's time-constant and
        // dT is the event delivery rate.
        // final float alpha = 0.8f;
        // Isolate the force of gravity with the low-pass filter.

        final float alpha = 0.8f;

        gravity[0] = Math.abs(event.values[0]);
        gravity[1] = Math.abs(event.values[1]);
        gravity[2] = Math.abs(event.values[2]);

        gravMag = Math.sqrt((double)gravity[0] + (double)gravity[1] + (double)gravity[2]) - Math.sqrt(9.80665);

        if (gravMag > 3){
            jim.changeFitness(1);
            updateStatBars(jim);
            System.out.println("shake it off");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    private boolean timerOn = false;

    public void startTimer(View view) {
        final Timer timer = new Timer();
        if (!timerOn) {
            class TimedButton extends TimerTask {
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            testBars();
                        }
                    });
                    if (!jim.isAlive()){
                        timer.cancel();
                    }
                }
            }
            timer.scheduleAtFixedRate(new TimedButton(), 0, 1000);
        } else {
            timer.cancel();
        }
    }
    @Override
    public void consumeFood(Person.Consumable food) {
        jim.consume(food);
    }

}