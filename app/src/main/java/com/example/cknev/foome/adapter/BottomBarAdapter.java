package com.example.cknev.foome.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cknev on 23-10-2017.
 */

public class BottomBarAdapter extends SmartFragmentStatePagerAdapter
{
    private final List<Fragment> fragments = new ArrayList<>();

    public BottomBarAdapter(FragmentManager fragmentManager)
    {
        super(fragmentManager);
    }

    // A custom method that populates this Adapter with Fragments
    public void addFragments(Fragment fragment)
    {
        fragments.add(fragment);
    }

    // A custom method that populates this Adapter with Fragments
    public void addFragments(Fragment... fragmentsList)
    {
        for (Fragment fragment : fragmentsList)
            fragments.add(fragment);
    }

    @Override
    public Fragment getItem(int position)
    {
        return fragments.get(position);
    }

    @Override
    public int getCount()
    {
        return fragments.size();
    }
}
