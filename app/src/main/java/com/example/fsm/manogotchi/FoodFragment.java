package com.example.fsm.manogotchi;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FoodFragment extends Fragment {
    LinearLayout foodList;
    Context listContext;

    public FoodFragment() {
        // Required empty public constructor
    }

    public void addFoodStuff(String name, String desc, int iconId) {

        LinearLayout newListItem = new LinearLayout(listContext);
        newListItem.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        newListItem.setBackgroundColor(Color.LTGRAY);
        newListItem.setOrientation(LinearLayout.HORIZONTAL);

        ImageView foodIcon = new ImageView(listContext);
        foodIcon.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT));
        foodIcon.setAdjustViewBounds(true);
        foodIcon.setImageResource(iconId);

        TextView foodTextView = new TextView(listContext);
        foodTextView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        foodTextView.setText(name+ ": " + desc);

        newListItem.addView(foodIcon);
        newListItem.addView(foodTextView);

        foodList.addView(newListItem);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_food, container, false);
        listContext = container.getContext();
        foodList = (LinearLayout)view.findViewById(R.id.food_list);

        addFoodStuff("Apple", "Healthy af", R.drawable.happy_android);
        addFoodStuff("Cocaine", "Deadly af", R.drawable.dead_android);
        addFoodStuff("Cake", "It's a lie!", R.drawable.sad_android);
        return view;
    }

}
