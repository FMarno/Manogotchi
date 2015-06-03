package com.example.fsm.manogotchi;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;


public class MainActivity extends FragmentActivity implements SensorEventListener{

    private Person jim;
    private float[] gravity = {0,0,0};
    private float[] linear_acceleration = {0,0,0};

    private SensorManager mSensorManager;
    private Sensor mSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create test person
        jim = new Person();

        // Add hunger stats to graph
        addToGraph(jim.getHungerStats(), R.id.graph);
       // addToGraph(jim.getFitnessStats(), R.id.fitness);
       // addToGraph(jim.getEnergyStats(), R.id.energy);
        //addToGraph(jim.getHappinessStats(), R.id.happiness);


        //added sensors that do nothing
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this,mSensor,SensorManager.SENSOR_DELAY_GAME);



    }

    //Method to add a stat set against time to graph
    private void addToGraph(ArrayList<Integer> stats, int graph_id) {

        //Use graph id to indicate which graph will be modified
        GraphView graph = (GraphView) findViewById(graph_id);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(100);
        //graph.getViewport().setScalable(true);

        //Create a DataPoint array which plots the stats against time
        DataPoint[] data = new DataPoint[stats.size()];
        for (int i = 0; i < stats.size(); i++) {
            System.out.println(stats.get(i));
            data[i] = new DataPoint(i, stats.get(i));
        }

        //Create the series object from the array and add to graph
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(data);
        graph.addSeries(series);
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

    public void testBars(View view) {


        ProgressBar energyBar = (ProgressBar) findViewById(R.id.energy_bar);
        ProgressBar hungerBar = (ProgressBar) findViewById(R.id.hunger_bar);
        ProgressBar fitnessBar = (ProgressBar) findViewById(R.id.fitness_bar);
        ProgressBar happinessBar = (ProgressBar) findViewById(R.id.happiness_bar);

        jim.runHour();
        energyBar.setProgress(jim.getEnergy());
        hungerBar.setProgress(jim.getHunger());
        fitnessBar.setProgress(jim.getFitness());
        happinessBar.setProgress(jim.getHappiness());

    }

    public void onSensorChanged(SensorEvent event){
        // In this example, alpha is calculated as t / (t + dT),
        // where t is the low-pass filter's time-constant and
        // dT is the event delivery rate.

        final float alpha = 0.8f;

        // Isolate the force of gravity with the low-pass filter.
        gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
        gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
        gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

        // Remove the gravity contribution with the high-pass filter.
        linear_acceleration[0] = event.values[0] - gravity[0];
        linear_acceleration[1] = event.values[1] - gravity[1];
        linear_acceleration[2] = event.values[2] - gravity[2];
        System.out.println("X " + event.values[0] + "\t\t Y " + event.values[1] + "\t\t Z " + event.values[2]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


}
