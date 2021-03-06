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
    LineGraphSeries<DataPoint> energySeries;
    LineGraphSeries<DataPoint> hungerSeries;
    LineGraphSeries<DataPoint> fitnessSeries;
    LineGraphSeries<DataPoint> happinessSeries;

    int t = 0;

    final int MAX_DATA_POINTS = 120;

    public interface OnChangeListener{
        public void setAccurateAge(int age);
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

        resetGraphs();


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

        energySeries.appendData(energyData, false, MAX_DATA_POINTS);
        hungerSeries.appendData(hungerData, false, MAX_DATA_POINTS);
        fitnessSeries.appendData(fitnessData, false, MAX_DATA_POINTS);
        happinessSeries.appendData(happinessData, false, MAX_DATA_POINTS);

        mCallback.setAccurateAge(t);

    }

    public void resetGraphs() {

        t = 0;

        energyGraph.removeAllSeries();
        hungerGraph.removeAllSeries();
        fitnessGraph.removeAllSeries();
        happinessGraph.removeAllSeries();

        energySeries = new LineGraphSeries<>();
        hungerSeries = new LineGraphSeries<>();
        fitnessSeries = new LineGraphSeries<>();
        happinessSeries = new LineGraphSeries<>();

        DataPoint energyData = new DataPoint(0, 70);
        DataPoint hungerData = new DataPoint(0, 70);
        DataPoint fitnessData = new DataPoint(0, 70);
        DataPoint happinessData = new DataPoint(0, 70);


        energySeries.appendData(energyData, false, 40);
        hungerSeries.appendData(hungerData, false, 40);
        fitnessSeries.appendData(fitnessData, false, 40);
        happinessSeries.appendData(happinessData, false, 40);

        energyGraph.getViewport().setYAxisBoundsManual(true);
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

        mCallback.setAccurateAge(t);

    }

}
