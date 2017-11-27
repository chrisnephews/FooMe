package com.example.cknev.foome.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.example.cknev.foome.R;
import com.example.cknev.foome.activity.meal.EditMealActivity;
import com.example.cknev.foome.model.Meal;
import com.example.cknev.foome.utitility.DataSource;

import java.util.List;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.ViewHolder>
{
    final Context context;
    private final List<Meal> mealList;
    public View view;
    SwipeLayout swipeLayout;
    OnDataChangeListener mOnDataChangeListener;

    public MealsAdapter(List<Meal> list, Context context)
    {
        mealList = list;
        this.context = context;
    }

    private Meal getItem(int position)
    {
        return mealList.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_food_row, parent, false);
        initSwipeLayout(itemView);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MealsAdapter.ViewHolder holder, int position)
    {
        //Populate the row
        holder.populateRow(getItem(position));

    }

    @Override
    public long getItemId(int position)
    {
        return mealList.get(position).getId();
    }

    public void updateList(List<Meal> newlist)
    {
        // Set new updated meal list
        mealList.clear();
        mealList.addAll(newlist);
    }

    @Override
    public int getItemCount()
    {
        return mealList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener
    {
        private final TextView _foodName;
        private final TextView _calories;
        private LinearLayout _editButton;
        private LinearLayout _deleteButton;

        //initialize the variables
        public ViewHolder(View view)
        {
            super(view);
            _foodName = (TextView) view.findViewById(R.id.foodNameText);
            _calories = (TextView) view.findViewById(R.id.caloriesText);

            //Define edit button and its action
            _editButton = (LinearLayout) view.findViewById(R.id.editButtonLayout);
            _editButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    doEditActivity();
                }
            });

            //Define delete button and its action
            _deleteButton = (LinearLayout) view.findViewById(R.id.deleteButtonLayout);
            _deleteButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    //User clicked on the confirm button of the Dialog, delete the game from Database
                    DataSource datasource = new DataSource(view.getContext());
                    Meal selectedMeal = getItem(getAdapterPosition());
                    removeAt(getAdapterPosition());
                    //We only need the id of the game to delete it
                    datasource.deleteGame(selectedMeal.getId());
                    //notify the adapter that a meal has been deleted
                    mOnDataChangeListener.onDataChanged();
                    //Show a toast to user that the meal has succesfully been deleted
                    Toast toast = Toast.makeText(context, "Meal has been deleted", Toast.LENGTH_SHORT);
                    toast.show();

                }
            });

            //Set action when long clicked on row
            view.setOnLongClickListener(this);
        }

        public void removeAt(int position)
        {
            mealList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, mealList.size());
        }

        @Override
        public boolean onLongClick(View view)
        {
            doEditActivity();
            return false;
        }

        /**
         * Method for setting edit activity
         **/
        private void doEditActivity()
        {
            Intent intent = new Intent(context, EditMealActivity.class);
            // Get the correct meal based on which listitem got clicked, and put it as parameter in the intent
            Meal selectedMeal = getItem(getAdapterPosition());
            intent.putExtra("selectedMeal", selectedMeal);
            // Open MealDetailsActivity
            context.startActivity(intent);
        }

        public void populateRow(Meal meal)
        {
            _foodName.setText(meal.getTitle());
            _calories.setText(String.valueOf(meal.getCalories().getTotalAmount()) + " _calories");
        }
    }

    private void initSwipeLayout(View view)
    {
        //Initialize the swipe layout
        swipeLayout = (SwipeLayout) view.findViewById(R.id.swipeLayout);
        //Set show mode.
        swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        //Disable left swiping
        swipeLayout.setLeftSwipeEnabled(false);
        //When clicking on the row, the bottomview disappears
        swipeLayout.setClickToClose(true);
        //Add drag edge.(If the BottomView has 'layout_gravity' attribute, this line is unnecessary)
        swipeLayout.addDrag(SwipeLayout.DragEdge.Left, view.findViewById(R.id.bottom_wrapper));

        //Swipe listener for doing stuff when swiping
        swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener()
        {
            @Override
            public void onClose(SwipeLayout layout)
            {
                //when the SurfaceView totally covers the BottomView.
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset)
            {
                //you are swiping.
            }

            @Override
            public void onStartOpen(SwipeLayout layout)
            {

            }

            @Override
            public void onOpen(SwipeLayout layout)
            {
            }

            @Override
            public void onStartClose(SwipeLayout layout)
            {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel)
            {
                //when user's hand released.
            }
        });


    }

    public interface OnDataChangeListener
    {
        public void onDataChanged();
    }

    public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener)
    {
        mOnDataChangeListener = onDataChangeListener;
    }
}