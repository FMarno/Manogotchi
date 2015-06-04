package com.example.fsm.manogotchi;


import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FoodFragment extends Fragment {
    LinearLayout foodList;


    public FoodFragment() {
        // Required empty public constructor
    }

    public void addFoodStuff(MainActivity context, String name, String desc) {


        TextView foodTextView = new TextView(context);
        foodTextView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        foodTextView.setText(name + ": " + desc);

        foodList.addView(foodTextView);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_food, container, false);

        return view;
    }


}
