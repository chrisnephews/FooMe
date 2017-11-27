package com.example.cknev.foome.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.cknev.foome.R;
import com.example.cknev.foome.activity.meal.AddMealActivity;
import com.example.cknev.foome.adapter.BottomBarAdapter;
import com.example.cknev.foome.adapter.MealsAdapter;
import com.example.cknev.foome.fragment.HomeFragment;
import com.example.cknev.foome.fragment.MealsFragment;
import com.example.cknev.foome.fragment.ProgressFragment;
import com.example.cknev.foome.model.Meal;
import com.example.cknev.foome.utitility.DataSource;
import com.example.cknev.foome.view.ViewPagerWithoutSwiping;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private CallbackManager callbackManager;
    RecyclerView recyclerView;
    MealsAdapter mAdapter;
    List<Meal> mealList;
    AHBottomNavigation bottomNavigation;
    ViewPagerWithoutSwiping viewPager;
    BottomBarAdapter pagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);


        //Create bottom navigation and set it to meals
        setBottomNavigation();
        bottomNavigation.setCurrentItem(0);

        //Custom viewpager for fragments without the option to swipe
        viewPager = (ViewPagerWithoutSwiping) findViewById(R.id.viewPager);
        viewPager.setPagingEnabled(false);
        pagerAdapter = new BottomBarAdapter(getSupportFragmentManager());
        setupAdapterWithFragments();
        viewPager.setAdapter(pagerAdapter);
    }


    private void setupAdapterWithFragments()
    {
        HomeFragment homeFragment = new HomeFragment();
        MealsFragment mealsFragment = new MealsFragment();
        ProgressFragment progressFragment = new ProgressFragment();
        pagerAdapter.addFragments(homeFragment, mealsFragment, progressFragment);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void updateUI()
    {
        DataSource dataSource = new DataSource(this);
        mealList = dataSource.getMeals();
        if (mAdapter == null)
        {
            //Implement the adapter
            mAdapter = new MealsAdapter(mealList, this);
//          recyclerView.setAdapter(mAdapter);
        } else
        {
            //Reflect changes made to the list/
            // adapter.
            mAdapter.updateList(mealList);
            mAdapter.notifyDataSetChanged();
        }
    }

    public void onResume()
    {
        super.onResume();  // Always call the superclass method first
        updateUI();
    }

    @Override
    public void onBackPressed()
    {
        // disable going back to the LoginActivity
        moveTaskToBack(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id)
        {
            case R.id.action_add:
                Intent intent = new Intent(MainActivity.this, AddMealActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_settings:
                intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setBottomNavigation()
    {
        //Create a bottomNavigation
        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
        //Initialize the menu items: home, meals, progress
        //TODO workouts

        //Home
        AHBottomNavigationItem home =
                new AHBottomNavigationItem(getString(R.string.bottomnav_home),
                        R.drawable.ic_home_black_24dp);

        //Meals
        AHBottomNavigationItem meals =
                new AHBottomNavigationItem(getString(R.string.bottomnav_meals), R.drawable.ic_view_agenda_black_24dp);

        //Stats/Progress. Need to figure out what's more appealing to the end user
        AHBottomNavigationItem stats = new AHBottomNavigationItem(getString(R.string.bottomnav_stats), R.drawable.ic_trending_down_black_24dp);

        //Add the items to the menu
        bottomNavigation.addItems(Arrays.asList(home, meals, stats));
        //Set some app colors
        bottomNavigation.setDefaultBackgroundColor(Color.WHITE);
        bottomNavigation.setInactiveColor(Color.LTGRAY);
        bottomNavigation.setAccentColor(fetchColor(R.color.colorAccent));
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener()
        {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected)
            {
                //When loading a fragment the actionbar title will change accordingly
                setActionBarTitle(position);
                if (!wasSelected)
                {
                    viewPager.setCurrentItem(position);
                }
                return true;
            }
        });

    }

    /**
     * Method for setting the actionbar according to the fragments
     */

    public void setActionBarTitle(int position)
    {
        switch (position)
        {
            case 0:
                setActionBarTitle("Home");
                break;
            case 1:
                setActionBarTitle("Diary");
                break;
            case 2:
                setActionBarTitle("Progress");
                break;
            default:
                setActionBarTitle("Foome");
        }
    }


    private void setActionBarTitle(String title)
    {
        getSupportActionBar().setTitle(title);
    }

    public void setActionBarSubtitle(String subtitle)
    {
        getSupportActionBar().setTitle(subtitle);
    }


    /*
    * Method for returning a usable color value
    * */
    private int fetchColor(int color)
    {
        return ContextCompat.getColor(this, color);
    }
}
