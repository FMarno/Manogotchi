package com.example.fsm.manogotchi;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

public class StatisticsFragment extends Fragment {

    OnChangeListener mCallback;
    LineGraphSeries<DataPoint> energySeries = new LineGraphSeries<>();
    LineGraphSeries<DataPoint> hungerSeries = new LineGraphSeries<>();
    LineGraphSeries<DataPoint> fitnessSeries = new LineGraphSeries<>();
    LineGraphSeries<DataPoint> happinessSeries = new LineGraphSeries<>();
    int t = 0;

    public interface OnChangeListener{
        public int[] getStatistics();
    }

    private GraphView energyGraph;
    private GraphView hungerGraph;
    private GraphView fitnessGraph;
    private GraphView happinessGraph;

    public StatisticsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        //Checks if the activity has implemented the required interface
        try{
            mCallback = (OnChangeListener) activity;
        }
        catch(ClassCastException e){
            throw new ClassCastException(activity.toString() + " must implement OnChangeListener!");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistics, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        energyGraph = (GraphView) view.findViewById(R.id.energy_graph);
        hungerGraph = (GraphView) view.findViewById(R.id.hunger_graph);
        fitnessGraph = (GraphView) view.findViewById(R.id.fitness_graph);
        happinessGraph = (GraphView) view.findViewById(R.id.happiness_graph);

        energyGraph.getViewport().setYAxisBoundsManual(true);
        energyGraph.getViewport().setMinY(0);
        energyGraph.getViewport().setMaxY(100);
        energyGraph.addSeries(energySeries);

        hungerGraph.getViewport().setYAxisBoundsManual(true);
        hungerGraph.getViewport().setMinY(0);
        hungerGraph.getViewport().setMaxY(100);
        hungerGraph.addSeries(hungerSeries);

        fitnessGraph.getViewport().setYAxisBoundsManual(true);
        fitnessGraph.getViewport().setMinY(0);
        fitnessGraph.getViewport().setMaxY(100);
        fitnessGraph.addSeries(fitnessSeries);

        happinessGraph.getViewport().setYAxisBoundsManual(true);
        happinessGraph.getViewport().setMinY(0);
        happinessGraph.getViewport().setMaxY(100);
        happinessGraph.addSeries(happinessSeries);
    }

    public void addToGraph(int[] statValues) {

        //Use graph id to indicate which graph will be modified


        //graph.getViewport().setScalable(true);
        t++;

        //Create a DataPoint array which plots the stats against time
        DataPoint energyData = new DataPoint(t, statValues[0]);
        DataPoint hungerData = new DataPoint(t, statValues[1]);
        DataPoint fitnessData = new DataPoint(t, statValues[2]);
        DataPoint happinessData = new DataPoint(t, statValues[3]);

        energySeries.appendData(energyData,true,40);
        hungerSeries.appendData(hungerData,true,40);
        fitnessSeries.appendData(fitnessData,true,40);
        happinessSeries.appendData(happinessData,true,40);

        //Create the series object from the array and add to graph


    }

}
