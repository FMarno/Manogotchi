package com.example.fsm.manogotchi;

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


public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create test person
        Person jim = new Person();
        //Run sample of life choices
        runJimTest(jim);
        // Add hunger stats to graph
        addToGraph(jim.getHungerStats(), R.id.graph);
        addToGraph(jim.getFitnessStats(), R.id.graph);


    }

    //Method to add a stat set against time to graph
    private void addToGraph(ArrayList<Integer> stats, int graph_id) {

        //Use graph id to indicate which graph will be modified
        GraphView graph = (GraphView) findViewById(graph_id);

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


    private void runJimTest(Person jim) {

        jim.print();

        while (jim.runHour() != -1) {
            jim.print();
            System.out.println();
        }

        System.out.println("Jim died!");
        jim.print();

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

        energyBar.setProgress(energyBar.getProgress() - 1);
    }
}
