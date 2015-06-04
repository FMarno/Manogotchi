package com.example.fsm.manogotchi;

import android.os.Bundle;
import android.support.v4.app.*;
import android.support.v4.view.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class TabContainerFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final int NUM_TABS = 3;

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private ArrayList<Fragment> mPages = new ArrayList<>();

    public TabContainerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPages.add(new HomeFragment());
        mPages.add(new FoodFragment());
        mPages.add(new StatisticsFragment());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_container, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mPager = (ViewPager) view.findViewById(R.id.content_slider);

        mPagerAdapter = new ContentSliderPagerAdapter(getChildFragmentManager());
        mPager.setAdapter(mPagerAdapter);

    }

    public void addToGraph(int stats[]){
       
        ((StatisticsFragment) mPages.get(2)).addToGraph(stats);
    }

    private class ContentSliderPagerAdapter extends FragmentStatePagerAdapter{

        public ContentSliderPagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return mPages.get(i);
        }

        @Override
        public int getCount() {
            return NUM_TABS;
        }
    }
}
