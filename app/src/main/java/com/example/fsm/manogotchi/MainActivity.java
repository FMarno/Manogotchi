package com.example.fsm.manogotchi;


import android.app.ActionBar;
import android.support.v4.app.*;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

public class MainActivity extends FragmentActivity implements StatisticsFragment.OnChangeListener{

    private Person jim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        setTheme(android.R.style.Theme_Holo);


        //Create test person
        jim = new Person();

    }

    //Method to add a stat set against time to graph



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

        //Updating Progressbars and the graphs
        ProgressBar energyBar = (ProgressBar) findViewById(R.id.energy_bar);
        ProgressBar hungerBar = (ProgressBar) findViewById(R.id.hunger_bar);
        ProgressBar fitnessBar = (ProgressBar) findViewById(R.id.fitness_bar);
        ProgressBar happinessBar = (ProgressBar) findViewById(R.id.happiness_bar);

        jim.runHour();
        energyBar.setProgress(jim.getEnergy());
        hungerBar.setProgress(jim.getHunger());
        fitnessBar.setProgress(jim.getFitness());
        happinessBar.setProgress(jim.getHappiness());

        TabContainerFragment tabs = (TabContainerFragment)getSupportFragmentManager().findFragmentById(R.id.tab_container);

        //Passing on the stats to the statistics fragment to update the graphs
        int[] stats = {jim.getEnergy(), jim.getHunger(), jim.getFitness(), jim.getHappiness()};

        tabs.addToGraph(stats);
    }

    @Override
    public int[] getStatistics() {
        return new int[0];
    }
}
