package com.example.fsm.manogotchi;


import android.app.ActionBar;
import android.support.v4.app.*;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements StatisticsFragment.OnChangeListener{

    private Person jim;

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
}
