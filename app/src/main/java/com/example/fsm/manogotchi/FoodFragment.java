package com.example.fsm.manogotchi;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.fsm.manogotchi.Person.Consumable;


/**
 * A simple {@link Fragment} subclass.
 */
public class FoodFragment extends Fragment {

    private final int LIST_ITEM_PADDING_DP = 5;
    private final int LIST_ITEM_HEIGHT_DP = 80;
    //private final int LIST_ITEM_TEXT_SIZE = 5;
    private float scale;

    LinearLayout foodList;
    Context listContext;
    OnFoodClickListener mCallback;

    public interface OnFoodClickListener {
        public void consumeFood(Consumable food);
    }

    public FoodFragment() {
        // Required empty public constructor
    }

    public void addFoodStuff(final Consumable food) {

        int paddingInPx = (int)(LIST_ITEM_PADDING_DP * scale + .5f);

        final LinearLayout newListItem = new LinearLayout(listContext);
        newListItem.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        newListItem.setOrientation(LinearLayout.HORIZONTAL);
        newListItem.setPadding(paddingInPx, paddingInPx, paddingInPx, paddingInPx);
        newListItem.setMinimumHeight((int) (LIST_ITEM_HEIGHT_DP * scale));

        ImageView foodIcon = new ImageView(listContext);

        foodIcon.setAdjustViewBounds(true);
        foodIcon.setImageResource(food.getImage());
        foodIcon.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        foodIcon.setMaxHeight((int) (LIST_ITEM_HEIGHT_DP * scale));

        TextView foodTextView = new TextView(listContext);
        foodTextView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        foodTextView.setText("Energy: " + food.getEnergyFactor() + "\r\nHunger: " + food.getHungerFactor() + "\r\nFitness: " + food.getFitnessFactor() + "\r\nHappiness: " + food.getHappinessFactor());
        foodTextView.setGravity(Gravity.CENTER);
        //foodTextView.setTextSize(LIST_ITEM_TEXT_SIZE * scale);

        newListItem.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        newListItem.setBackgroundColor(Color.DKGRAY);
                        mCallback.consumeFood(food);
                        break;
                    case MotionEvent.ACTION_UP:
                        newListItem.setBackgroundColor(Color.parseColor("#00000000"));
                        break;
                }
                return true;
            }
        });

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
        scale = getResources().getDisplayMetrics().density;
        Consumable[] foodstuffs = Consumable.values();

        for (int i = 0; i<foodstuffs.length;i++){
            addFoodStuff(foodstuffs[i]);
        }
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try{
            mCallback = (OnFoodClickListener) activity;
        }
        catch (ClassCastException e){
            System.out.println(e.getMessage());
        }
    }
}
