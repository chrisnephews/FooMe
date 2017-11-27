package com.example.cknev.foome.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cknev.foome.R;

import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProgressFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProgressFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProgressFragment extends Fragment
{
    View rootView;
    ValueLineChart mCubicValueLineChart;

    private OnFragmentInteractionListener mListener;

    public ProgressFragment()
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
    public static ProgressFragment newInstance()
    {
        ProgressFragment fragment = new ProgressFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_progress, container, false);
        initWeightGraph();
        return rootView;

    }

    /**
     * Method for setting up the graph with the user weights. As of now only hardcoded data is used
     * TODO: Fix db data and relationship between the user and the weights
     **/

    private void initWeightGraph()
    {
        mCubicValueLineChart = (ValueLineChart) rootView.findViewById(R.id.cubiclinechart);
        ValueLineSeries series = new ValueLineSeries();
        series.setColor(0xFF56B7F1);
        series.addPoint(new ValueLinePoint("Jan", 100));
        series.addPoint(new ValueLinePoint("Feb", 98));
        series.addPoint(new ValueLinePoint("Mar", 96));
        series.addPoint(new ValueLinePoint("Apr", 97));
        series.addPoint(new ValueLinePoint("May", 95));
        series.addPoint(new ValueLinePoint("Jun", 94));
        series.addPoint(new ValueLinePoint("Jul", 94));
        series.addPoint(new ValueLinePoint("Aug", 92));
        series.addPoint(new ValueLinePoint("Sep", 93));
        series.addPoint(new ValueLinePoint("Oct", 92));
        series.addPoint(new ValueLinePoint("Nov", 91));
        series.addPoint(new ValueLinePoint("Dec", 90));
        mCubicValueLineChart.addSeries(series);
        mCubicValueLineChart.startAnimation();
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
}
