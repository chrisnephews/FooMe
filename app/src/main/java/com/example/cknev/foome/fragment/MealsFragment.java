package com.example.cknev.foome.fragment;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cknev.foome.R;
import com.example.cknev.foome.adapter.MealsAdapter;
import com.example.cknev.foome.model.Day;
import com.example.cknev.foome.model.Meal;
import com.example.cknev.foome.utitility.DataSource;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MealsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MealsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MealsFragment extends Fragment
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    RecyclerView recyclerView;
    private OnFragmentInteractionListener mListener;
    List<Meal> mealList;
    MealsAdapter mAdapter;
    View rootView;
    TextView totalCaloriesText;
    TextView remainingCaloriesText;

    public MealsFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DummyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MealsFragment newInstance()
    {
        MealsFragment fragment = new MealsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        menu.clear();
        inflater.inflate(R.menu.menu_meals,menu);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.content_main, container, false);
        //Initialize the views
        totalCaloriesText = (TextView) rootView.findViewById(R.id.totalCaloriesText);
        remainingCaloriesText = (TextView) rootView.findViewById(R.id.totalCaloriesRemText);

        //Setup the recyclerview
        initMealRecyclerView(rootView);
        //Add a listener for when the adapter changes(meals get deleted etc.)
        mAdapter.setOnDataChangeListener(new MealsAdapter.OnDataChangeListener(){
            public void onDataChanged(){
                updateUI();
            }
        });
        // Inflate the layout for this fragment
        return rootView;


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri)
    {
        if (mListener != null)
        {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener
    {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void initMealRecyclerView(View view)
    {


        //initialize the RecyclerView
        recyclerView = (RecyclerView) view.findViewById(R.id.foodRecyclerView);
        //To improve performance of the RecyclerView I've set the fixed size to true because the size won't be changing.
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        updateUI();
        //Adding ItemAnimator
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(100L);
        itemAnimator.setRemoveDuration(100L);
        recyclerView.setItemAnimator(itemAnimator);


    }

    public void onResume()
    {
        super.onResume();  // Always call the superclass method first
        updateUI();
    }

    public void updateUI()
    {
        DataSource dataSource = new DataSource(getActivity());
        mealList = dataSource.getMeals();
        //Methods for setting total calories and remaining calories of a day
        setRemainingCalories();
        setTotalCalories();
        if (mAdapter == null)
        {
            //Implement the adapter
            mAdapter = new MealsAdapter(mealList, getActivity());
            recyclerView.setAdapter(mAdapter);
        } else
        {
            //Reflect changes made to the list/
            // adapter.
            mAdapter.updateList(mealList);
            mAdapter.notifyDataSetChanged();
        }
    }
    /*
     * Method for setting the total calories in the TextView totalCaloriesText
     */
    public void setTotalCalories()
    {
        totalCaloriesText.setText("2500");
    }
    /*
 * Method for setting the total calories in the TextView totalCaloriesText
 */
    public void setRemainingCalories()
    {
        int remainingCalories = 0;
        for (Meal meal : mealList)
            remainingCalories += meal.getCalories().getTotalAmount();
        String remainingCaloriesString = String.valueOf((2500 - remainingCalories));
        remainingCaloriesText.setText(remainingCaloriesString);
        // If remaining calories of the day is a negative number then the color of the caloriestext is set to red
        if((2500 - remainingCalories) < 0)
            remainingCaloriesText.setTextColor(Color.RED);
    }



}
